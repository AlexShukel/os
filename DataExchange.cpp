//
// Created by alexs on 3/29/2025.
//

#include "DataExchange.h"
#include "Logger.h"

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

int DataExchange::xchg() {
    if (destinationObject == EXTERNAL) {
        Logger::debug("DataExchange::xchg() called with dt set to EXTERNAL. Writing in external storage is not supported.");
        return -1;
    }

    if (sourceObject == EXTERNAL) {
        std::ifstream hdd("hdd.txt");

        if (!hdd) {
            Logger::debug("ERROR: failed to open hdd.txt");
            return -1;
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
                    Logger::debug("ERROR: no @CODE0 in %s", path.c_str());
                    return -1;
                }
                line = readLine(hdd);

                int currentWordIndex = CODE_SEGMENT_START_BLOCK;
                while (line != "@DATA0") {
                    if (line.size() != WORD_SIZE) {
                        Logger::debug("ERROR: each line on external storage must contain exactly one word.");
                        return -1;
                    }

                    memory->writeWord(Word(line), currentWordIndex);
                    ++currentWordIndex;

                    line = readLine(hdd);
                }
                line = readLine(hdd);
                
                currentWordIndex = DATA_SEGMENT_START_BLOCK * BLOCK_SIZE;
                while (line != "@END00") {
                    if (line.size() != WORD_SIZE) {
                        Logger::debug("ERROR: each line on external storage must contain exactly one word.");
                        return -1;
                    }
    
                    memory->writeWord(Word(line), currentWordIndex);
                    ++currentWordIndex;

                    line = readLine(hdd);
                }
                break;
            }
        }

        hdd.close();
        return 0;
    }

    // Copy a block by index sourcePointer from SWAP to memory block by destinationPointer
    if (sourceObject == SWAP) {
        if (destinationObject == MEMORY) {
            std::fstream swapFile(path);

            int sourceBlock = sourcePointer.toInteger();
            int destinationBlock = destinationPointer.toInteger();

            Word currentWord;
            for (int i = 0; i < BLOCK_SIZE; ++i) {
                swapFile.seekg(sourceBlock * BLOCK_SIZE + i * WORD_SIZE, std::ios::beg);
                swapFile.read(currentWord.word, WORD_SIZE);
                memory->writeWord(currentWord, destinationBlock * BLOCK_SIZE + i * WORD_SIZE);
            }

            swapFile.close();
        }
    }

    // Copy a block by index sourcePointer from memory to SWAP block by destinationPointer
    if (sourceObject == MEMORY) {
        if (destinationObject == SWAP) {
            std::fstream swapFile(path);

            int sourceBlock = sourcePointer.toInteger();
            int destinationBlock = destinationPointer.toInteger();

            for (int i = 0; i < BLOCK_SIZE; ++i) {
                Word currentWord = memory->readWord(sourceBlock * BLOCK_SIZE + i * WORD_SIZE);

                swapFile.seekg(destinationBlock * BLOCK_SIZE + i * WORD_SIZE, std::ios::beg);
                swapFile.write(currentWord.word, WORD_SIZE);
            }

            swapFile.close();
        }
    }
}

