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
        std::ifstream hdd("hdd.txt");

        if (!hdd) {
            std::cerr << "ERROR: failed to open hdd.txt" << std::endl;
            exit(1);
        }

        // Move to the sourcePointer
        hdd.seekg(sourcePointer.toInteger(), std::ios::cur);

        if (destinationObject == MEMORY) {
            std::string line;
            int i = destinationPointer.toInteger();
            while (std::getline(hdd, line)) {
                while (line != "@FILE0") {
                    std::getline(hdd, line);
                }

                std::getline(hdd, line);
                if (line != path)
                    continue;

                while (line != "@END00") {
                    std::getline(hdd, line);

                    if (line.size() != WORD_SIZE) {
                        throw std::runtime_error("ERROR: each line on external storage must contain exactly one word.");
                    }
    
                    memory->writeWord(Word(line), i);
                    ++i;
                }
                break;
            }
        }

        hdd.close();
    }
}

