//
// Created by alexs on 4/13/2025.
//

#include "MemoryProxy.h"

MemoryProxy::MemoryProxy(RAM *memory): ram(memory), pageTable(nullptr) {}

Word& MemoryProxy::readWord(const int& address) {
    int block = address / BLOCK_SIZE;
    int index = address % BLOCK_SIZE;
    int realBlock = pageTable->data[block].toInteger();
    return ram->readWord(realBlock, index);
}

void MemoryProxy::writeWord(Word word, const int& address) {
    int block = address / BLOCK_SIZE;
    int index = address % BLOCK_SIZE;
    int realBlock = pageTable->data[block].toInteger();
    ram->writeWord(word, realBlock, index);
}

void MemoryProxy::setPageTable(MemoryBlock *pageTable) {
    this->pageTable = pageTable;
}

void MemoryProxy::printBlock(int block) {
    int realBlock = pageTable->data[block].toInteger();
    ram->printBlock(realBlock);
}


