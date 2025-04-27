#include "Swap.h"
#include "Logger.h"

SwapFile::SwapFile(const std::string& filename, size_t size) : file(), size(size) {
    file.open(filename, std::ios::in | std::ios::out | std::ios::binary);

    if (file.is_open()) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < BLOCK_SIZE; ++j) {
                file << "000000";
            }
            file << std::endl;
        }
    }
}

SwapFile::~SwapFile() {
    file.close();
}

void SwapFile::writeWord(Word& word, const int& block, const int& index) {
    int blockOffset = block * BLOCK_SIZE * WORD_SIZE;
    
    int currentBlock = 0;
    std::string line;
    std::streampos pos;
    file.seekg(0, std::ios::beg);
    file.clear();

    while (currentBlock < block && std::getline(file, line)) {
        pos = file.tellg();
        ++currentBlock;
    }

    if (currentBlock != block) {
        Logger::debug("Error: Block not found in swap file.");
        Logger::debug("Current block: %d, expected block: %d", currentBlock, block);
        return;
    }

    file.seekp(pos + std::streamoff(index * WORD_SIZE));
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