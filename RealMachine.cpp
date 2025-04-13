//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), memoryProxy(&memory), dataExchange(&memoryProxy) {}



void RealMachine::loadAndRunProgram(const std::string &fileName) {
    newPageTable();

    // DataExchange will copy from hdd.txt all program's parts on the fly
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    dataExchange.xchg();

    // print code segment
    memoryProxy.printBlock(CODE_SEGMENT_START_BLOCK);
    // print data segment
    memoryProxy.printBlock(DATA_SEGMENT_START_BLOCK);
    // print page table from OS memory
    memory.printBlock(cpu.ptr.toInteger());

    VirtualMachine vm(&memoryProxy);
    virtualMachines.push_back(vm);

    while (cpu.exec(&vm) != -1) {}
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

