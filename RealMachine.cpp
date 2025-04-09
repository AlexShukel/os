//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), dataExchange(&memory) {}

MemoryBlock RealMachine::initPageTable() {
    MemoryBlock pageTable;

    for (int i = 0; i < BLOCK_SIZE; ++i) {
        int randomBlock = memory.pickRandomBlockIndex();
        pageTable.data[i] = Word(randomBlock);
    }

    return pageTable;
}

void RealMachine::loadAndRunProgram(const std::string &fileName) {
    MemoryBlock pageTable = initPageTable();
    
    dataExchange.sourcePointer = Word(0);
    dataExchange.byteCount = Word(0); // TODO: do something with this
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;
    dataExchange.pageTable = pageTable;

    dataExchange.xchg();

    // print code segment
    memory.printBlock(pageTable.data[CODE_SEGMENT_START_BLOCK].toInteger());
    // print data segment
    memory.printBlock(pageTable.data[DATA_SEGMENT_START_BLOCK].toInteger()); 

    // TODO: create VM
}

