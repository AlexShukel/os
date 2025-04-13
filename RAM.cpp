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

int RAM::pickRandomBlockIndex() {
    std::vector<int> freeIndices;

    for (int i = 0; i < OS_MEMORY_START_BLOCK; ++i) {
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

void RAM::writeWord(const Word& word, const int& block, const int& index) {
    free[block] = false;
    blocks[block].data[index] = word;
}

void RAM::printBlock(const int& block) {
    for (int i = 0; i < BLOCK_SIZE; i++) {
        printf("%.6s ", blocks[block].data[i].word);
    }
    printf("\n");
}

int RAM::initPageTable() {
    int pageTableIndex = pageTableCount;
    MemoryBlock& pageTable = getPageTable(pageTableIndex);

    for (int i = 0; i < VIRTUAL_MACHINE_MEMORY_SIZE; ++i) {
        int randomBlock = pickRandomBlockIndex();
        pageTable.data[i] = Word(randomBlock);
    }

    ++pageTableCount;
    return pageTableIndex;
}

MemoryBlock& RAM::getPageTable(const int& index) {
    return getBlock(OS_MEMORY_START_BLOCK + index - 1);
}

MemoryBlock& RAM::getBlock(const int address) {
    return blocks[address];
}