#ifndef SWAP_H
#define SWAP_H

#include <string>
#include <iostream>
#include <fstream>

#include "shared.h"
#include "Word.h"

class SwapFile {
public:
    SwapFile() = delete;
    SwapFile(const std::string& filename, size_t size);
    ~SwapFile();

    void writeWord(Word& word, const int& block, const int& index);
    Word readWord(const int& block, const int& index);
    int allocateBlock();
private:
    std::fstream file;
    size_t size;
    int currentFreeBlock = 0;
};

#endif //SWAP_H