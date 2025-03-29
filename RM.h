//
// Created by alexs on 3/15/2025.
//

#ifndef RM_H
#define RM_H

#include <iostream>
#include <string>

#include "CPU.h"
#include "DataExchange.h"
#include "RAM.h"

class RM {
public:
    CPU cpu;
    RAM memory;
    DataExchange dataExchange;

    RM();

    void load_program_from_file(const std::string &path);
};


#endif //RM_H
