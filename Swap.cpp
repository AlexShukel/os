//
// Created by alexs on 4/27/2025.
//

#include "Swap.h"

#include <random>
#include <vector>
#include <ctime>

#include "Logger.h"

Swap::Swap() {
    // Create a file if it doesn't exist
    std::ifstream infile(SWAP_FILE);
    if (!infile) {
        std::ofstream outfile(SWAP_FILE);
        if (!outfile) {
            throw std::runtime_error("Failed to create file");
        }
    }

    file.open(SWAP_FILE, std::ios::in | std::ios::out);
    if (!file) {
        throw std::runtime_error("Failed to open file");
    }

    for (int i = 0; i < SWAP_FILE_SIZE; ++i) {
        for (int j = 0; j < BLOCK_SIZE; ++j) {
            file << "000000";
        }
        file << "\n";
    }

    file.seekg(0, std::ios::beg);

    for (bool &i : free) {
        i = true;
    }
}

Swap::~Swap() {
    if (file.is_open()) {
        file.close();
    }
}

void Swap::printAll() {
    char block[BLOCK_SIZE * WORD_SIZE];
    for (int i = 0; i < SWAP_FILE_SIZE; ++i) {
        file.read(block, BLOCK_SIZE * WORD_SIZE); // read a block from swap
        Logger::debug(block);
    }

    file.seekg(0, std::ios::beg);
}

void Swap::printBlock(int index) {
    char block[BLOCK_SIZE * WORD_SIZE];
    file.seekg(index * BLOCK_SIZE, std::ios::beg);
    file.read(block, BLOCK_SIZE * WORD_SIZE);
    Logger::debug(block);
    file.seekg(0, std::ios::beg);
}

int Swap::pickFreeBlockIndex() {
    std::vector<int> freeIndices;

    for (int i = 0; i < SWAP_FILE_SIZE; ++i) {
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

void Swap::writeBlock(Byte *inBuffer, int index) {
    free[index] = false;

    file.seekg(index * BLOCK_SIZE, std::ios::beg);
    file.write(inBuffer, BLOCK_SIZE);

    file.seekg(0, std::ios::beg);
}

void Swap::readBlock(Byte *outBuffer, int index) {
    file.seekg(index * BLOCK_SIZE, std::ios::beg);
    file.read(outBuffer, BLOCK_SIZE);
    file.seekg(0, std::ios::beg);
}
