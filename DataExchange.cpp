//
// Created by alexs on 3/29/2025.
//

#include "DataExchange.h"

#include <fstream>
#include <iostream>
#include <stdexcept>

DataExchange::DataExchange(MemoryProxy *memory): memory(memory) {
    destinationPointer = Word();
    sourcePointer = Word();
    destinationObject = MEMORY;
    sourceObject = MEMORY;
    byteCount = Word();
}

std::string readLine(std::ifstream& file) {
    std::string line = "";
    std::getline(file, line);

    if (!line.empty() && line.back() == '\r') {
        line.pop_back();
    }

    return line;
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

        if (destinationObject == MEMORY) {
            std::string line = readLine(hdd);
            while (!line.empty()) {
                while (line != "@FILE0") {
                    line = readLine(hdd);
                }
                
                line = readLine(hdd);
                if (line != path)
                    continue;
                
                line = readLine(hdd);
                if (line != "@CODE0") {
                    throw std::runtime_error("ERROR: no @CODE0 in " + path);
                }
                line = readLine(hdd);

                int currentWordIndex = CODE_SEGMENT_START_BLOCK;
                while (line != "@DATA0") {
                    if (line.size() != WORD_SIZE) {
                        throw std::runtime_error("ERROR: each line on external storage must contain exactly one word.");
                    }

                    memory->writeWord(Word(line), currentWordIndex);
                    ++currentWordIndex;

                    line = readLine(hdd);
                }
                line = readLine(hdd);
                
                currentWordIndex = DATA_SEGMENT_START_BLOCK * BLOCK_SIZE;
                while (line != "@END00") {
                    if (line.size() != WORD_SIZE) {
                        throw std::runtime_error("ERROR: each line on external storage must contain exactly one word.");
                    }
    
                    memory->writeWord(Word(line), currentWordIndex);
                    ++currentWordIndex;

                    line = readLine(hdd);
                }
                break;
            }
        }

        hdd.close();
    }
}

