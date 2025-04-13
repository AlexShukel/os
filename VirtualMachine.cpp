//
// Created by alexs on 3/15/2025.
//

#include "VirtualMachine.h"

VirtualMachine::VirtualMachine(MemoryProxy *memory): memory(memory) {}

void VirtualMachine::pushToStack(Word& address) {
    --sp; // Move stack pointer one step to the left
    Word& target = memory->readWord(address.toInteger());
    memory->writeWord(target, sp.toInteger());
}

void VirtualMachine::popFromStack(Word& address) {
    Word& target = memory->readWord(sp.toInteger());
    memory->writeWord(target, address.toInteger());
    ++sp; // Move stack pointer one step to the right
}