//
// Created by alexs on 3/15/2025.
//

#ifndef RAM_H
#define RAM_H

#include "shared.h"

class MemoryBlock {
    W data[BLOCK_SIZE];

public:
    MemoryBlock() = default;
};

class RAM {
    MemoryBlock blocks[RM_RAM_SIZE];

public:
    RAM() = default;
};



#endif //RAM_H
