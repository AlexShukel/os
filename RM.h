//
// Created by alexs on 3/15/2025.
//

#ifndef RM_H
#define RM_H

#include <iostream>
#include <string>
#include <vector>

#include "CPU.h"
#include "DataExchange.h"
#include "RAM.h"
#include "VM.h"

class RM {
public:
    CPU cpu;
    RAM memory;
    DataExchange dataExchange;

    RM();

    void loadAndRunProgram(const std::string &path);

};


#endif //RM_H
