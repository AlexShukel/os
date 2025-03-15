//
// Created by alexs on 3/15/2025.
//

#include <fstream>
#include <sstream>

#include "RM.h"

VM *RM::alloc_vm() {

}

void RM::load_program(std::string program) {
    std::string line;
    std::istringstream stream(program);

    while(std::getline(stream, line)) {
        switch(line) {
            case "@PRM":
                break;
            case "@COD":
                break;
            case "@DAT":
                break;
        }
    }
}

void RM::load_programs_from_file(const std::string &path) {
    std::ifstream file(path);

    if (!file) {
        std::cerr << "ERROR: failed to open file " << path << std::endl;
        exit(1);
    }

    std::string line;
    std::string program;
    while (std::getline(file, line)) {
        program.append(line + '\n');

        if (line == "@END") {
            load_program(program);
            program.clear();
        }
    }

    file.close();
}

