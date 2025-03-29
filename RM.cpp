//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RM.h"
#include "Word.h"

RM::RM(): cpu(CPU()), memory(RAM()), dataExchange(memory) {}

int getFileSize(const std::string &filename) {
    std::ifstream file(filename, std::ios::binary | std::ios::ate);
    if (!file) return 0;
    return file.tellg();
}

void RM::load_program_from_file(const std::string &path) {
    dataExchange.sourcePointer = Word(0);
    dataExchange.destinationPointer = Word(0);
    dataExchange.byteCount = Word(getFileSize(path));
    dataExchange.path = path;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    dataExchange.xchg();
}

