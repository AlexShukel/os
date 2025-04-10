//
// Created by alexs on 3/15/2025.
//

#ifndef RAM_H
#define RAM_H

#include <iostream>

#include "shared.h"
#include "Word.h"

class MemoryBlock {
public:
    Word data[BLOCK_SIZE];
    MemoryBlock() = default;
};

class RAM {
    bool free[RM_RAM_SIZE] = {};
    MemoryBlock blocks[RM_RAM_SIZE];

public:
    RAM();
    
    int initPageTable();
    
    void writeWord(const Word& word, const int& block, const int& index);
    
    void printBlock(const int& block);

    MemoryBlock& getPageTable(const int& index);

    MemoryBlock& getBlock(const int address);

private:
    int pageTableCount = 0;

    int pickRandomBlockIndex();
};



#endif //RAM_H
