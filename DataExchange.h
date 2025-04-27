//
// Created by alexs on 3/29/2025.
//

#ifndef DATAEXCHANGE_H
#define DATAEXCHANGE_H

#include <string>

#include "MemoryProxy.h"
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

    MemoryProxy *memory;

    std::string path;

    explicit DataExchange(MemoryProxy *memory);

    int xchg();
};



#endif //DATAEXCHANGE_H
