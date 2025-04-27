#include "Swap.h"

SwapFile::SwapFile(const std::string& filename, size_t size) : file() {
    file.open(filename, std::ios::out | std::ios::trunc);

    if (file.is_open()) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < BLOCK_SIZE; ++j) {
                file << "000000";
            }
            file << "\n";
        }
    }
}

SwapFile::~SwapFile() {
    file.close();
}

void SwapFile::writeWord(Word& word, const int& block, const int& index) {
    file.seekp(block * BLOCK_SIZE + index * WORD_SIZE);
    file.write(word.word, WORD_SIZE);
}

Word SwapFile::readWord(const int& block, const int& index) {
    file.seekg(block * BLOCK_SIZE + index * WORD_SIZE);
    Word word = Word();
    file.read(word.word, WORD_SIZE);
    return word;
}

int SwapFile::allocateBlock() {
    int freeBlock = currentFreeBlock;

    if (currentFreeBlock >= size) {
        return -1;
    }

    ++currentFreeBlock;
    return freeBlock;
}