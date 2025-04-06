//
// Created by alexs on 3/29/2025.
//

#include "DataExchange.h"

#include <fstream>
#include <iostream>
#include <stdexcept>

DataExchange::DataExchange(RAM *memory) {
    this->memory = memory;
    destinationPointer = Word();
    sourcePointer = Word();
    destinationObject = MEMORY;
    sourceObject = MEMORY;
    byteCount = Word();
}

void DataExchange::xchg() {
    if (destinationObject == EXTERNAL) {
        throw std::runtime_error("DataExchange::xchg() called with dt set to EXTERNAL. Writing in external storage is not supported.");
    }

    if (sourceObject == EXTERNAL) {
        std::ifstream file(path);

        if (!file) {
            std::cerr << "ERROR: failed to open file " << path << std::endl;
            exit(1);
        }

        // Move to the sourcePointer
        file.seekg(sourcePointer.toInteger(), std::ios::cur);

        if (destinationObject == MEMORY) {
            std::string line;
            int i = destinationPointer.toInteger();
            while (std::getline(file, line)) {
                if (line.size() != WORD_SIZE) {
                    throw std::runtime_error("ERROR: each line on external storage must contain exactly one word.");
                }

                memory->writeWord(Word(line), i);
                ++i;
            }
        }

        file.close();
    }
}

