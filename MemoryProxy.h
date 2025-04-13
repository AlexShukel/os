//
// Created by alexs on 4/13/2025.
//

#ifndef MEMORYPROXY_H
#define MEMORYPROXY_H
#include "RAM.h"



class MemoryProxy {
    RAM *ram;
    MemoryBlock *pageTable;

public:
    explicit MemoryProxy(RAM *ram);

    void writeWord(Word word, int block, int index);

    Word *readWord(int block, int index);

    void setPageTable(MemoryBlock *pageTable);

    void printBlock(int block);
};



#endif //MEMORYPROXY_H
