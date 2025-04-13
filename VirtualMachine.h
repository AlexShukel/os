//
// Created by alexs on 3/15/2025.
//

#ifndef VIRTUAL_MACHINE_H
#define VIRTUAL_MACHINE_H

#include "MemoryProxy.h"
#include "shared.h"
#include "Word.h"

class VirtualMachine {
public:
    Word pc = Word(0); // Program counter
    Word sp = Word(65536); // Stack pointer
    unsigned char c = 0; // Flags
    // 1 bit - CF (carry flag)
    // 2 bit - ZF (zero flag)

    MemoryProxy *memory;

    explicit VirtualMachine(MemoryProxy *memory);

    Word& popFromStack();
    void pushToStack(Word& value);

    void setZeroFlag(const Word& result);
};



#endif //VM_H
