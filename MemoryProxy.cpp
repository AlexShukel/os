//
// Created by alexs on 4/13/2025.
//

#include "MemoryProxy.h"

MemoryProxy::MemoryProxy(RAM *memory, Swap *swap): ram(memory), pageTable(nullptr), swap(swap) {}

// TODO: check if pageTable is null

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
    if (realBlock >= RM_RAM_SIZE) {
        swap->writeBlock(word.word,  - RM_RAM_SIZE);
    } else {
        ram->writeWord(word, realBlock, index);
    }
}

void MemoryProxy::setPageTable(MemoryBlock *pageTable) {
    this->pageTable = pageTable;
}

void MemoryProxy::printBlock(int block) {
    int realBlock = pageTable->data[block].toInteger();
    ram->printBlock(realBlock);
}

struct ByteAddress {
    int block;
    int index;
    int offset;
};

ByteAddress addressToByteAddress(const int &address) {
    const int block = address / (BLOCK_SIZE * WORD_SIZE);
    const int offsetInBlock = address % (BLOCK_SIZE * WORD_SIZE);
    const int index = offsetInBlock / WORD_SIZE;
    const int offset = offsetInBlock % WORD_SIZE;
    return ByteAddress(block, index, offset);
}
