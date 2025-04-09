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

    int pickRandomBlockIndex();

    void writeWord(Word word, int pointer);

    // debug
    void __print() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                printf("%.6s ", blocks[i].data[j].word);
            }
            printf("\n");
        }
    }
};



#endif //RAM_H
