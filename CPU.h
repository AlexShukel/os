//
// Created by alexs on 3/15/2025.
//

#ifndef CPU_H
#define CPU_H

#include <iostream>
#include <queue>

#include "shared.h"
#include "VirtualMachine.h"
#include "Word.h"

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

class CPU {
public:
    Word ptr; // Paging table
    Word pc; // Program counter
    Word sp; // Stack pointer
    Byte c; // Flags
    Byte pi; // Program interruptions
    Byte si; // Supervisor interruptions
    Byte mode; // Mode (0 - user, 1 - supervisor)
    Byte ti; // Timer

    CPU() = default;

    int exec(VirtualMachine& virtualMachine);

    void signalProgramInterrupt(VirtualMachine& virtualMachine, const ProgramInterrupt& interrupt);
    void signalSupervisorInterrupt(VirtualMachine& virtualMachine, const SupervisorInterrupt& interrupt);
    void processInterrupt();
private:
    std::queue<Interrupt> interrupts;
    void signalInterrupt(VirtualMachine& virtualMachine, Byte interrupt, InterruptType type);
    void processProgramInterrupt(VirtualMachine& virtualMachine);
    void processSupervisorInterrupt(VirtualMachine& virtualMachine);
};

#endif //CPU_H
