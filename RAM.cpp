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

int RAM::pickRandomBlock() {
    std::vector<int> freeIndices;

    for (bool &i : free) {
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

void RAM::writeWord(Word word, int pointer) {
    free[pointer / BLOCK_SIZE] = false;
    blocks[pointer / BLOCK_SIZE].data[pointer % BLOCK_SIZE] = word;
}

