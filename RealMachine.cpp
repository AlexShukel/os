//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), dataExchange(&memory), createdProcesses(std::vector<Process>()) {}

Process::Process(const int& pid, const int& pageTableAddress, const VirtualMachine& virtualMachine): pid(pid), pageTableAddress(pageTableAddress), virtualMachine(virtualMachine) {}

void RealMachine::loadAndRunProgram(const std::string &fileName) {
    int pageTableIndex = memory.initPageTable();
    
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;
    dataExchange.pageTableIndex = pageTableIndex;

    dataExchange.xchg();

    MemoryBlock& newProcessPageTable = memory.getPageTable(pageTableIndex);
    // print code segment
    memory.printBlock(newProcessPageTable.data[CODE_SEGMENT_START_BLOCK].toInteger());
    // print data segment
    memory.printBlock(newProcessPageTable.data[DATA_SEGMENT_START_BLOCK].toInteger()); 
    // print page table from OS memory
    memory.printBlock(OS_MEMORY_START_BLOCK + pageTableIndex - 1);

    Process newProcess(createdProcesses.size(), pageTableIndex, VirtualMachine());
    createdProcesses.push_back(newProcess);
}

