//
// Created by alexs on 4/13/2025.
//

#include "MemoryProxy.h"

MemoryProxy::MemoryProxy(RAM *memory): ram(memory), pageTable(nullptr) {}

Word *MemoryProxy::readWord(int block, int index) {
    if (pageTable == nullptr) {
        throw std::runtime_error("MemoryProxy::readWord: pageTable is null");
    }

    int realBlock = pageTable->data[block].toInteger();
    return ram->readWord(realBlock, index);
}

void MemoryProxy::writeWord(Word word, int block, int index) {
    if (pageTable == nullptr) {
        throw std::runtime_error("MemoryProxy::writeWord: pageTable is null");
    }

    int realBlock = pageTable->data[block].toInteger();
    ram->writeWord(word, realBlock, index);
}

void MemoryProxy::setPageTable(MemoryBlock *pageTable) {
    this->pageTable = pageTable;
}

void MemoryProxy::printBlock(int block) {
    if (pageTable == nullptr) {
        throw std::runtime_error("MemoryProxy::printBlock: pageTable is null");
    }

    int realBlock = pageTable->data[block].toInteger();
    ram->printBlock(realBlock);
}


