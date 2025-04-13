//
// Created by alexs on 3/15/2025.
//

#include "VirtualMachine.h"
#include <bitset>

VirtualMachine::VirtualMachine(MemoryProxy *memory): memory(memory) {}

Word& VirtualMachine::popFromStack() {
    Word& word = memory->readWord(sp.toInteger());
    ++sp;
    return word;
}

void VirtualMachine::pushToStack(Word& value) {
    --sp;
    memory->writeWord(value, sp.toInteger());
}

void VirtualMachine::setFlags(const Word& result) {
    // 1 bit - CF
    // 2 bit - OF
    // 3 bit - SF
    // 4 bit - ZF

    if (result.toInteger() == 0) {
        c |= 0b1000; // Set ZF;
    } else {
        c &= 0b0111; // Clear ZF;
    }

    std::cout << "Flags: " << std::bitset<8>(c) << std::endl;
}