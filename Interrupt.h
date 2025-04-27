//
// Created by alexs on 4/27/2025.
//

#ifndef INTERRUPT_H
#define INTERRUPT_H

#include "VirtualMachine.h"

enum class ProgramInterrupt {
    NONE = 0,
    BADCODE = 1,
    SEGFAULT = 2,
    OVERFLOW = 3,
    ZERODIV = 4,
    BADNUM = 5
};

enum class SupervisorInterrupt {
    NONE = 0,
    GETD = 1,
    PRTW = 2,
    PRTS = 3,
    HALT = 4
};

enum class InterruptType {
    PROGRAM = 0,
    SUPERVISOR = 1
};

class Interrupt {
public:
    VirtualMachine& virtualMachine;
    Byte interrupt;
    InterruptType type;
};

#endif //INTERRUPT_H
