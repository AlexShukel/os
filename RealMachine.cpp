//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RealMachine.h"
#include "Word.h"

RealMachine::RealMachine(): cpu(), dataExchange(&memory) {}

void RealMachine::loadAndRunProgram(const std::string &fileName) {
    Word destinationPointer(0);

    dataExchange.sourcePointer = Word(0);
    dataExchange.destinationPointer = Word(destinationPointer);
    dataExchange.byteCount = Word(0); // TODO: do something with this
    dataExchange.path = fileName;
    dataExchange.sourceObject = EXTERNAL;
    dataExchange.destinationObject = MEMORY;

    dataExchange.xchg();

    memory.__print();
    // TODO: create VM
}

