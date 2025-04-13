//
// Created by alexs on 3/15/2025.
//

#ifndef VIRTUAL_MACHINE_H
#define VIRTUAL_MACHINE_H

#include "MemoryProxy.h"
#include "Word.h"

#define CF 0b00000001
#define ZF 0b00000010

class VirtualMachine {
public:
    Word pc = Word(0); // Program counter
    Word sp = Word(65536); // Stack pointer

    // 1 bit - CF (carry flag)
    // 2 bit - ZF (zero flag)
    unsigned char c = 0; // Flags

    MemoryProxy *memory;

    explicit VirtualMachine(MemoryProxy *memory);

    Word& popFromStack();
    void pushToStack(Word& value);

    void setZeroFlag(const Word& result);

    void setCarryFlag(bool value);
};



#endif //VM_H
