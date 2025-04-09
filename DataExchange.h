//
// Created by alexs on 3/29/2025.
//

#ifndef DATAEXCHANGE_H
#define DATAEXCHANGE_H

#include <string>

#include "RAM.h"
#include "Word.h"

enum MemoryObject {
    MEMORY,
    SUPERVISOR,
    EXTERNAL,
    STDIN,
    STDOUT,
    SWAP
};

class DataExchange {
public:
    Word sourcePointer;
    Word destinationPointer;
    Word byteCount;
    MemoryObject sourceObject;
    MemoryObject destinationObject;
    int pageTableIndex;

    RAM *memory;
    std::string path;

    explicit DataExchange(RAM *memory);

    void xchg();
};



#endif //DATAEXCHANGE_H
