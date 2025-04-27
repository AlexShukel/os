//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>
#include <bitset>
#include <cstring>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), memoryProxy(&memory), dataExchange(&memoryProxy), swapFile(SWAP_FILE, SWAP_BLOCKS) {}

VirtualMachine RealMachine::loadProgram(const std::string &fileName) {
    newPageTable();

    // DataExchange will copy from hdd.txt all program's parts on the fly
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    if (dataExchange.xchg() == -1) {
        return VirtualMachine(nullptr);
    }

    VirtualMachine vm(&memoryProxy);
    virtualMachines.push_back(vm);

    return vm;
}

void RealMachine::runProgram(VirtualMachine& virtualMachine) {
    while (cpu.exec(virtualMachine) != -1) {}
}

void RealMachine::debugProgram(VirtualMachine& virtualMachine) {
    int commandResult = 0;
    while (commandResult != -1) {
        std::string command;
        std::cout << "===Registers===" << std::endl;
        std::cout << "PTR: " << cpu.ptr << std::endl;
        std::cout << "PC: " << virtualMachine.pc << std::endl;
        std::cout << "SP: " << virtualMachine.sp << std::endl;
        std::cout << "C: " << std::bitset<8>(virtualMachine.c) << std::endl;
        std::cout << "===============" << std::endl;
        std::cout << "Available commands:" << std::endl;
        std::cout << "'next' - perform next instruction" << std::endl;
        std::cout << "'block <block_number>' - print virtual machine block" << std::endl;
        std::cout << "'rmblock <block_number>' - print real machine block\n\n";
        std::cout << "Next instruction: " << virtualMachine.memory->readWord(virtualMachine.pc.toInteger()) << std::endl;
        std::cout << "Enter command: ";
        std::getline(std::cin, command);

        std::string blockCommand = "block";
        std::string rmBlockCommand = "rmblock";
        
        if (command == "next") {
            commandResult = cpu.exec(virtualMachine);
        } else if (strncmp(command.c_str(), blockCommand.c_str(), strlen(blockCommand.c_str())) == 0) {
            try {
                int blockNumber = std::stoi(command.substr(blockCommand.length() + 1));
                if (blockNumber < 0 || blockNumber >= VIRTUAL_MEMORY_BLOCKS) {
                    std::cout << "Invalid block number. Please enter a number between 0 and " << VIRTUAL_MEMORY_BLOCKS - 1 << "." << std::endl;
                    continue;
                }
                virtualMachine.memory->printBlock(blockNumber);
            }
            catch(std::out_of_range& exception) {
                std::cerr << "block command failed: " << exception.what() << '\n';
            }
        } else if (strncmp(command.c_str(), rmBlockCommand.c_str(), strlen(rmBlockCommand.c_str())) == 0) {
            try {
                int blockNumber = std::stoi(command.substr(rmBlockCommand.length() + 1));
                if (blockNumber < 0 || blockNumber >= RM_RAM_SIZE) {
                    std::cout << "Invalid block number. Please enter a number between 0 and " << RM_RAM_SIZE - 1 << "." << std::endl;
                    continue;
                }
                memory.printBlock(blockNumber);
            }
            catch(std::out_of_range& exception) {
                std::cerr << "rmBlock command failed: " << exception.what() << '\n';
            }
        } else if (command == "exit") {
            std::cout << "Exiting debug mode." << std::endl;
            break;
        } else {
            std::cout << "Unknown command: " << command << std::endl;
        }
    }
}

void RealMachine::newPageTable() {
    int pageTableBlockIndex = memory.pickFreeBlockIndex();
    cpu.ptr = Word(pageTableBlockIndex); // set PTR to point on new page table

    for (int i = 0; i < VIRTUAL_MEMORY_BLOCKS; ++i) {
        int block;
        if (memory.hasFreeSpace())
        {
            block = memory.pickFreeBlockIndex();
        }
        else
        {
            int swapBlock = swapFile.allocateBlock();
            if (swapBlock == -1) {
                Logger::debug("Swap file is full!");
                return;
            }

            block = swapBlock + RM_RAM_SIZE;
        }
        memory.writeWord(Word(block), pageTableBlockIndex, i);
    }

    memoryProxy.setPageTable(memory.getBlock(pageTableBlockIndex));
}

