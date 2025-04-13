//
// Created by alexs on 3/15/2025.
//

#ifndef REAL_MACHINE_H
#define REAL_MACHINE_H

#include <iostream>
#include <string>
#include <vector>

#include "CPU.h"
#include "DataExchange.h"
#include "RAM.h"
#include "VirtualMachine.h"

class RealMachine {
public:
    CPU cpu;
    RAM memory;
    MemoryProxy memoryProxy;
    DataExchange dataExchange;
    std::vector<VirtualMachine> virtualMachines;

    RealMachine();

    VirtualMachine loadProgram(const std::string &fileName);
    void runProgram(VirtualMachine& virtualMachine);
    void debugProgram(VirtualMachine& virtualMachine);
private:
    void newPageTable();
};


#endif //RM_H
