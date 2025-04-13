//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

int CPU::exec(const Word& command) {
    std::string opcode(command.word, 6);
    std::cout << "Executing opcode: " << opcode << std::endl;

    if (opcode == "ADD000") {
        std::cout << "Parsed ADD000" << std::endl;
    } else if (opcode == "SUB000") {
        std::cout << "Parsed SUB000" << std::endl;
    } else if (opcode == "MUL000") {
        std::cout << "Parsed MUL000" << std::endl;
    } else if (opcode == "DIV000") {
        std::cout << "Parsed DIV000" << std::endl;
    } else if (opcode == "COMP00") {
        std::cout << "Parsed COMP00" << std::endl;
    } else if (opcode == "AND000") {
        std::cout << "Parsed AND000" << std::endl;
    } else if (opcode == "OR0000") {
        std::cout << "Parsed OR0000" << std::endl;
    } else if (opcode == "XOR000") {
        std::cout << "Parsed XOR000" << std::endl;
    } else if (opcode == "JUMP00") {
        std::cout << "Parsed JUMP00" << std::endl;
    } else if (opcode == "JUMPB0") {
        std::cout << "Parsed JUMPB0" << std::endl;
    } else if (opcode == "JUMPBE") {
        std::cout << "Parsed JUMPBE" << std::endl;
    } else if (opcode == "JUMPA0") {
        std::cout << "Parsed JUMPA0" << std::endl;
    } else if (opcode == "JUMPE0") {
        std::cout << "Parsed JUMPE0" << std::endl;
    } else if (opcode == "JUMPNE") {
        std::cout << "Parsed JUMPNE" << std::endl;
    } else if (opcode.substr(0, 2) == "PU") {
        std::cout << "Parsed PU" << std::endl;
    } else if (opcode.substr(0, 2) == "PO") {
        std::cout << "Parsed PO" << std::endl;
    } else if (opcode == "GETD00") {
        std::cout << "Parsed GETD00" << std::endl;
    } else if (opcode == "PRTW00") {
        std::cout << "Parsed PRTW00" << std::endl;
    } else if (opcode == "PRTS00") {
        std::cout << "Parsed PRTS00" << std::endl;
    } else if (opcode == "HALT00") {
        std::cout << "Parsed HALT00" << std::endl;
        return -1;
    } else {
        throw std::runtime_error("Unknown instruction: " + opcode);
    }

    return 0;
}
