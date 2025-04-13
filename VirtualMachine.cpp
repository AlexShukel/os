//
// Created by alexs on 3/15/2025.
//

#include "VirtualMachine.h"

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

void VirtualMachine::setZeroFlag(const Word& result) {
    if (result.toInteger() == 0) {
        c |= ZF;
    } else {
        c &= ~ZF;
    }
}

void VirtualMachine::setCarryFlag(bool value) {
    if (value) {
        c |= CF;
    } else {
        c &= ~CF;
    }
}

