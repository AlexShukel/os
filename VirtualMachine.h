//
// Created by alexs on 3/15/2025.
//

#ifndef VIRTUAL_MACHINE_H
#define VIRTUAL_MACHINE_H

#include "shared.h"
#include "Word.h"

class VirtualMachine {
    Word pc = Word(0); // Program counter
    Word sp = Word(255); // Stack pointer
    unsigned char c = 0; // Flags
public:

    VirtualMachine() = default;

    void step();
};

class Process {
public:
    int pid;
    int pageTableAddress;
    VirtualMachine virtualMachine;

    Process(const int& pid, const int& pageTableAddress, const VirtualMachine& virtualMachine);
    void update();
};

#endif //VM_H
