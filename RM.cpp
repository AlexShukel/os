//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RM.h"
#include "Word.h"

RM::RM(): cpu(), dataExchange(&memory) {}

int getFileSize(const std::string &filename) {
    std::ifstream file(filename, std::ios::binary | std::ios::ate);
    if (!file) return 0;
    return file.tellg();
}

void RM::loadAndRunProgram(const std::string &path) {
    Word destinationPointer(0);

    dataExchange.sourcePointer = Word(0);
    dataExchange.destinationPointer = Word(destinationPointer);
    dataExchange.byteCount = Word(getFileSize(path));
    dataExchange.path = path;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    dataExchange.xchg();

    memory.__print();
    // TODO: create VM
}

