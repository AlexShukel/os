//
// Created by alexs on 3/15/2025.
//

#ifndef RM_H
#define RM_H

#include <iostream>
#include <string>

#include "CPU.h"
#include "RAM.h"
#include "VM.h"

class RM {
    CPU cpu;
    RAM memory;

    VM *alloc_vm();

    void load_program(std::string program);

public:
    RM() = default;

    void load_programs_from_file(const std::string &path);
};


#endif //RM_H
