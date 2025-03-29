//
// Created by alexs on 3/15/2025.
//

#ifndef RAM_H
#define RAM_H

#include "shared.h"
#include "Word.h"

class MemoryBlock {
public:
    Word data[BLOCK_SIZE];
    MemoryBlock() = default;
};

class RAM {
public:
    MemoryBlock blocks[RM_RAM_SIZE];
    RAM() = default;

    // debug
    void __print_memory() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                printf("%.6s ", blocks[i].data[j].word);
            }
            printf("\n");
        }
    }
};



#endif //RAM_H
