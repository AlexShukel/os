//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

#include "Logger.h"

int CPU::exec(VirtualMachine *vm) {
    int commandAddress = vm->pc.toInteger();
    Word& command = vm->memory->readWord(commandAddress);

    Logger::debug("Executing command %.6s", command.word);

    if (command.equals("ADD000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 + arg2;
        Logger::debug("ADD000: %.6s + %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("SUB000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 - arg2;
        Logger::debug("SUB000: %.6s - %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("MUL000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 * arg2;
        Logger::debug("MUL000: %.6s * %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("DIV000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 / arg2;
        Logger::debug("DIV000: %.6s / %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("COMP00")) {
    } else if (command.equals("AND000")) {
    } else if (command.equals("OR0000")) {
    } else if (command.equals("XOR000")) {
    } else if (command.equals("JUMP00")) {
    } else if (command.equals("JUMPB0")) {
    } else if (command.equals("JUMPBE")) {
    } else if (command.equals("JUMPA0")) {
    } else if (command.equals("JUMPE0")) {
    } else if (command.equals("JUMPNE")) {
    } else if (command.startsWith("PU")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = vm->memory->readWord(address.toInteger());
        vm->pushToStack(target);
    } else if (command.startsWith("PO")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = vm->popFromStack();
        vm->memory->writeWord(target, address.toInteger());
    } else if (command.equals("GETD00")) {
    } else if (command.equals("PRTW00")) {
        Word& arg = vm->popFromStack();
        std::cout << arg << std::endl;
    } else if (command.equals("PRTS00")) {
    } else if (command.equals("HALT00")) {
        return -1;
    } else {
        throw std::runtime_error("Unknown instruction: " + std::string(command.word));
    }

    ++vm->pc;
    return 0;
}
