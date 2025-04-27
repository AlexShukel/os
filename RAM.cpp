//
// Created by alexs on 3/15/2025.
//

#include "RAM.h"

#include <random>
#include <vector>
#include <ctime>
#include <strings.h>

RAM::RAM() {
    for (bool &i : free) {
        i = true;
    }
}

int RAM::pickFreeBlockIndex() {
    std::vector<int> freeIndices;

    for (int i = 0; i < RM_RAM_SIZE; ++i) {
        if (free[i]) {
            freeIndices.push_back(i);
        }
    }

    if (freeIndices.empty()) {
        return -1;
    }

    static std::mt19937 rng(static_cast<unsigned int>(std::time(nullptr)));
    std::uniform_int_distribution<size_t> dist(0, freeIndices.size() - 1);

    return freeIndices[dist(rng)];
}

int RAM::pickRandomBlockIndex() {
    random_device rd;
    mt19937 rng(rd());
    std::uniform_int_distribution<size_t> dist(0, RM_RAM_SIZE - 1);

    return dist(rng);
}

void RAM::writeWord(Word word, int block, int index) {
    free[block] = false;
    blocks[block].data[index] = word;
}

Word& RAM::readWord(int block, int index) {
    return blocks[block].data[index];
}


void RAM::printBlock(int block) {
    for (int i = 0; i < BLOCK_SIZE; i++) {
        printf("%.6s ", blocks[block].data[i].word);
    }
    printf("\n");
}

MemoryBlock *RAM::getBlock(int index) {
    return &blocks[index];
}

bool RAM::hasFreeSpace() {
    for (bool &i : free) {
        if (i) {
            return true;
        }
    }
    return false;
}