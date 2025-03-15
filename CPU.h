//
// Created by alexs on 3/15/2025.
//

#ifndef CPU_H
#define CPU_H

#include "shared.h"

class CPU {
public:
    W ptr; // Paging table
    HW pc; // Program counter
    HW sp; // Stack pointer
    B c; // Flags
    B pi; // Program interruptions
    B si; // Supervisor interruptions
    B mode; // Mode (0 - user, 1 - supervisor)
    B ti; // Timer

    CPU() = default;

    void exec(W command) {
        // switch
    }
};



#endif //CPU_H
