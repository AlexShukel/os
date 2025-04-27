//
// Created by alexs on 4/27/2025.
//

#ifndef SWAP_H
#define SWAP_H

#include <fstream>
#include "shared.h"

class Swap {
public:
    Swap();

    ~Swap();

    int pickFreeBlockIndex();

    void readBlock(Byte *outBuffer, int index);

    void writeBlock(Byte *inBuffer, int index);

    // debug functions
    void printAll();
    void printBlock(int index);

private:
    std::fstream file;
    bool free[SWAP_FILE_SIZE] = {};
};



#endif //SWAP_H
