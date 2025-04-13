//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), memoryProxy(&memory), dataExchange(&memoryProxy) {}

VirtualMachine RealMachine::loadProgram(const std::string &fileName) {
    newPageTable();

    // DataExchange will copy from hdd.txt all program's parts on the fly
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    dataExchange.xchg();

    VirtualMachine vm(&memoryProxy);
    virtualMachines.push_back(vm);

    return vm;
}

void RealMachine::runProgram(VirtualMachine& virtualMachine) {
    while (cpu.exec(virtualMachine) != -1) {}
}

void RealMachine::debugProgram(VirtualMachine& virtualMachine) {

}

void RealMachine::newPageTable() {
    int pageTableBlockIndex = memory.pickFreeBlockIndex();
    cpu.ptr = Word(pageTableBlockIndex); // set PTR to point on new page table

    for (int i = 0; i < BLOCK_SIZE; ++i) {
        int randomBlock = memory.pickFreeBlockIndex();
        memory.writeWord(Word(randomBlock), pageTableBlockIndex, i);
    }

    memoryProxy.setPageTable(memory.getBlock(pageTableBlockIndex));
}

