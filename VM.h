//
// Created by alexs on 3/15/2025.
//

#ifndef VM_H
#define VM_H

#include "shared.h"
#include "Word.h"

class VM {
    Word pc = Word(0); // Program counter
    Word sp = Word(0); // Stack pointer
    unsigned char c = 0; // Flags

public:
    VM() = default;
};



#endif //VM_H
