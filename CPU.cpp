//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

#include "Logger.h"
#include <bitset>

int CPU::exec(VirtualMachine *vm) {
    int commandAddress = vm->pc.toInteger();
    Word& command = vm->memory->readWord(commandAddress);

    Logger::debug("Executing command %.6s", command.word);

    bool isJump = false;
    if (command.equals("ADD000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 + arg2;
        Logger::debug("ADD000: %.6s + %.6s = %.6s", arg1.word, arg2.word, result.word);

        vm->setZeroFlag(result);
        vm->setCarryFlag(arg1.toInteger() + arg2.toInteger() > result.toInteger());

        vm->pushToStack(result);
    } else if (command.equals("SUB000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 - arg2;
        Logger::debug("SUB000: %.6s - %.6s = %.6s", arg1.word, arg2.word, result.word);

        vm->setZeroFlag(result);
        vm->setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());

        vm->pushToStack(result);
    } else if (command.equals("MUL000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 * arg2;
        Logger::debug("MUL000: %.6s * %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->setZeroFlag(result);
        vm->pushToStack(result);
    } else if (command.equals("DIV000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 / arg2;
        Logger::debug("DIV000: %.6s / %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->setZeroFlag(result);
        vm->pushToStack(result);
    } else if (command.equals("COMP00")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 - arg2;

        vm->setZeroFlag(result);
        vm->setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());
    } else if (command.equals("AND000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 & arg2;
        Logger::debug("AND000: %.6s & %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("OR0000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 | arg2;
        Logger::debug("OR0000: %.6s | %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("XOR000")) {
        Word& arg1 = vm->popFromStack();
        Word& arg2 = vm->popFromStack();
        Word result = arg1 ^ arg2;
        Logger::debug("XOR000: %.6s ^ %.6s = %.6s", arg1.word, arg2.word, result.word);
        vm->pushToStack(result);
    } else if (command.equals("JUMP00")) {
        Word& arg1 = vm->popFromStack();
        vm->pc = arg1;
        isJump = true;
        Logger::debug("JUMP00: %.6s", arg1.word);
    } else if (command.equals("JUMPB0")) {
        Word& arg1 = vm->popFromStack();
        if (vm->c & CF) {
            vm->pc = arg1;
            isJump = true;
            Logger::debug("JUMPB0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPB0: NO JUMP");
        }
    } else if (command.equals("JUMPBE")) {
        Word& arg1 = vm->popFromStack();
        if (vm->c & CF || vm->c & ZF) {
            vm->pc = arg1;
            isJump = true;
            Logger::debug("JUMPBE: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPBE: NO JUMP");
        }
    } else if (command.equals("JUMPA0")) {
        Word& arg1 = vm->popFromStack();
        if (!(vm->c & CF) && !(vm->c & ZF)) {
            vm->pc = arg1;
            isJump = true;
            Logger::debug("JUMPA0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPA0: NO JUMP");
        }
    } else if (command.equals("JUMPE0")) {
        Word& arg1 = vm->popFromStack();
        if (vm->c & ZF) {
            vm->pc = arg1;
            isJump = true;
            Logger::debug("JUMPE0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPE0: NO JUMP");
        }
    } else if (command.equals("JUMPNE")) {
        Word& arg1 = vm->popFromStack();
        if (!(vm->c & ZF)) {
            vm->pc = arg1;
            isJump = true;
            Logger::debug("JUMPNE: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPNE: NO JUMP");
        }
    } else if (command.startsWith("PU")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = vm->memory->readWord(address.toInteger());
        vm->pushToStack(target);
        Logger::debug("PU: %.6s", target.word);
    } else if (command.startsWith("PO")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = vm->popFromStack();
        vm->memory->writeWord(target, address.toInteger());
    } else if (command.equals("GETD00")) {
        Word& address = vm->popFromStack();
        Word& size = vm->popFromStack();
        std::string buffer;
        std::getline(std::cin, buffer);

        for (int i = 0; i < size.toInteger(); ++i) {
            vm->memory->writeWord(Word(buffer.substr(i * WORD_SIZE, WORD_SIZE)), address.toInteger());
        }

        Logger::debug("GETD00: %.6s", address.word);
    } else if (command.equals("PRTW00")) {
        Word& arg = vm->popFromStack();
        std::cout << arg << std::endl;
    } else if (command.equals("PRTS00")) {
    } else if (command.equals("HALT00")) {
        return -1;
    } else {
        throw std::runtime_error("Unknown instruction: " + std::string(command.word));
    }
    Logger::debug("Flags: %s", std::bitset<8>(vm->c).to_string().c_str());
    if (!isJump) {
        ++vm->pc;
    }
    return 0;
}
