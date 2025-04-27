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
#include "Interrupt.h"

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
