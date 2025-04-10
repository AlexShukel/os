//
// Created by alexs on 3/15/2025.
//

#ifndef CPU_H
#define CPU_H

#include <iostream>

#include "shared.h"
#include "Word.h"

class CPU {
public:
    Word ptr; // Paging table
    char pc[4]; // Program counter
    char sp[4]; // Stack pointer
    Byte c; // Flags
    Byte pi; // Program interruptions
    Byte si; // Supervisor interruptions
    Byte mode; // Mode (0 - user, 1 - supervisor)
    Byte ti; // Timer

    CPU() = default;

    void exec(const Word& command);
};



#endif //CPU_H
