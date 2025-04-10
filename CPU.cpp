//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

void CPU::exec(const Word& command) {
    std::string opcode(command.word, 6); // Extract the first 6 characters of the command as the opcode
    std::cout << "Executing opcode: " << opcode << std::endl;

    if (opcode == "ADD000") {
        std::cout << "Parsed ADD000" << std::endl;
        // Handle addition
    } else if (opcode == "SUB000") {
        std::cout << "Parsed SUB000" << std::endl;
        // Handle subtraction
    } else if (opcode == "MUL000") {
        std::cout << "Parsed MUL000" << std::endl;
        // Handle multiplication
    } else if (opcode == "DIV000") {
        std::cout << "Parsed DIV000" << std::endl;
        // Handle division
    } else if (opcode == "COMP00") {
        std::cout << "Parsed COMP00" << std::endl;
        // Handle comparison
    } else if (opcode == "AND000") {
        std::cout << "Parsed AND000" << std::endl;
        // Handle bitwise AND
    } else if (opcode == "OR0000") {
        std::cout << "Parsed OR0000" << std::endl;
        // Handle bitwise OR
    } else if (opcode == "XOR000") {
        std::cout << "Parsed XOR000" << std::endl;
        // Handle bitwise XOR
    } else if (opcode == "JUMP00") {
        std::cout << "Parsed JUMP00" << std::endl;
        // Handle unconditional jump
    } else if (opcode == "JUMPB0") {
        std::cout << "Parsed JUMPB0" << std::endl;
        // Handle jump if below
    } else if (opcode == "JUMPBE") {
        std::cout << "Parsed JUMPBE" << std::endl;
        // Handle jump if below or equal
    } else if (opcode == "JUMPA0") {
        std::cout << "Parsed JUMPA0" << std::endl;
        // Handle jump if above
    } else if (opcode == "JUMPE0") {
        std::cout << "Parsed JUMPE0" << std::endl;
        // Handle jump if equal
    } else if (opcode == "JUMPNE") {
        std::cout << "Parsed JUMPNE" << std::endl;
        // Handle jump if not equal
    } else if (opcode.substr(0, 2) == "PU") {
        std::cout << "Parsed PU" << std::endl;
        // Handle push (PUxyzw)
    } else if (opcode.substr(0, 2) == "PO") {
        std::cout << "Parsed PO" << std::endl;
        // Handle pop (POxyzw)
    } else if (opcode == "GETD00") {
        std::cout << "Parsed GETD00" << std::endl;
        // Handle get data
    } else if (opcode == "PRTW00") {
        std::cout << "Parsed PRTW00" << std::endl;
        // Handle print word
    } else if (opcode == "PRTS00") {
        std::cout << "Parsed PRTS00" << std::endl;
        // Handle print string
    } else if (opcode == "HALT00") {
        std::cout << "Parsed HALT00" << std::endl;
        // Handle halt
    } else {
        throw std::runtime_error("Unknown instruction: " + opcode);
    }
}
