//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

#include "Logger.h"

int CPU::exec(VirtualMachine *vm) {
    int commandAddress = vm->pc.toInteger();
    Word *command = vm->memory->readWord(commandAddress / BLOCK_SIZE, commandAddress % BLOCK_SIZE);

    Logger::debug("Executing command %.6s", command->word);

    if (command->equals("ADD000")) {
    } else if (command->equals("SUB000")) {
    } else if (command->equals("MUL000")) {
    } else if (command->equals("DIV000")) {
    } else if (command->equals("COMP00")) {
    } else if (command->equals("AND000")) {
    } else if (command->equals("OR0000")) {
    } else if (command->equals("XOR000")) {
    } else if (command->equals("JUMP00")) {
    } else if (command->equals("JUMPB0")) {
    } else if (command->equals("JUMPBE")) {
    } else if (command->equals("JUMPA0")) {
    } else if (command->equals("JUMPE0")) {
    } else if (command->equals("JUMPNE")) {
    } else if (command->startsWith("PU")) {
        std::string arg = command->substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        --vm->sp; // Move stack pointer one step to the left
        // Copy from data segment to stack
        // TODO: add address validation
        int stackTop = vm->sp.toInteger();
        Word target = *vm->memory->readWord(address.toInteger() / BLOCK_SIZE, address.toInteger() % BLOCK_SIZE);
        vm->memory->writeWord(target, stackTop / BLOCK_SIZE, stackTop % BLOCK_SIZE);

        Logger::debug("Copied value %d to stack", target.toInteger());
    } else if (command->startsWith("PO")) {
    } else if (command->equals("GETD00")) {
    } else if (command->equals("PRTW00")) {
    } else if (command->equals("PRTS00")) {
    } else if (command->equals("HALT00")) {
        return -1;
    } else {
        throw std::runtime_error("Unknown instruction: " + std::string(command->word));
    }

    ++vm->pc;
    return 0;
}
