//
// Created by alexs on 3/15/2025.
//

#ifndef VM_H
#define VM_H

#include "shared.h"

class VM {
    HW pc = 0; // Program counter
    HW sp = 0; // Stack pointer
    B c = 0; // Flags

public:
    VM() = default;
};



#endif //VM_H
