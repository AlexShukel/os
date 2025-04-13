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

    void loadAndRunProgram(const std::string &fileName);
private:
    void newPageTable();
};


#endif //RM_H
