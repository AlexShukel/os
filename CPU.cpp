//
// Created by alexs on 3/15/2025.
//

#include "CPU.h"

#include "Logger.h"
#include <bitset>

int CPU::exec(VirtualMachine& virtualMachine) {
    int commandAddress = virtualMachine.pc.toInteger();
    Word& command = virtualMachine.memory->readWord(commandAddress);

    Logger::debug("Executing command %.6s", command.word);

    bool isJump = false;
    if (command.equals("ADD000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 + arg2;
        Logger::debug("ADD000: %.6s + %.6s = %.6s", arg1.word, arg2.word, result.word);

        virtualMachine.setZeroFlag(result);
        virtualMachine.setCarryFlag(arg1.toInteger() + arg2.toInteger() > result.toInteger());

        virtualMachine.pushToStack(result);
    } else if (command.equals("SUB000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 - arg2;
        Logger::debug("SUB000: %.6s - %.6s = %.6s", arg1.word, arg2.word, result.word);

        virtualMachine.setZeroFlag(result);
        virtualMachine.setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());

        virtualMachine.pushToStack(result);
    } else if (command.equals("MUL000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 * arg2;
        Logger::debug("MUL000: %.6s * %.6s = %.6s", arg1.word, arg2.word, result.word);
        virtualMachine.setZeroFlag(result);
        virtualMachine.pushToStack(result);
    } else if (command.equals("DIV000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 / arg2;
        Logger::debug("DIV000: %.6s / %.6s = %.6s", arg1.word, arg2.word, result.word);
        virtualMachine.setZeroFlag(result);
        virtualMachine.pushToStack(result);
    } else if (command.equals("COMP00")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 - arg2;

        virtualMachine.setZeroFlag(result);
        virtualMachine.setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());
    } else if (command.equals("AND000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 & arg2;
        Logger::debug("AND000: %.6s & %.6s = %.6s", arg1.word, arg2.word, result.word);
        virtualMachine.pushToStack(result);
    } else if (command.equals("OR0000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 | arg2;
        Logger::debug("OR0000: %.6s | %.6s = %.6s", arg1.word, arg2.word, result.word);
        virtualMachine.pushToStack(result);
    } else if (command.equals("XOR000")) {
        Word& arg1 = virtualMachine.popFromStack();
        Word& arg2 = virtualMachine.popFromStack();
        Word result = arg1 ^ arg2;
        Logger::debug("XOR000: %.6s ^ %.6s = %.6s", arg1.word, arg2.word, result.word);
        virtualMachine.pushToStack(result);
    } else if (command.equals("JUMP00")) {
        Word& arg1 = virtualMachine.popFromStack();
        virtualMachine.pc = arg1;
        isJump = true;
        Logger::debug("JUMP00: %.6s", arg1.word);
    } else if (command.equals("JUMPB0")) {
        Word& arg1 = virtualMachine.popFromStack();
        if (virtualMachine.c & CF) {
            virtualMachine.pc = arg1;
            isJump = true;
            Logger::debug("JUMPB0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPB0: NO JUMP");
        }
    } else if (command.equals("JUMPBE")) {
        Word& arg1 = virtualMachine.popFromStack();
        if (virtualMachine.c & CF || virtualMachine.c & ZF) {
            virtualMachine.pc = arg1;
            isJump = true;
            Logger::debug("JUMPBE: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPBE: NO JUMP");
        }
    } else if (command.equals("JUMPA0")) {
        Word& arg1 = virtualMachine.popFromStack();
        if (!(virtualMachine.c & CF) && !(virtualMachine.c & ZF)) {
            virtualMachine.pc = arg1;
            isJump = true;
            Logger::debug("JUMPA0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPA0: NO JUMP");
        }
    } else if (command.equals("JUMPE0")) {
        Word& arg1 = virtualMachine.popFromStack();
        if (virtualMachine.c & ZF) {
            virtualMachine.pc = arg1;
            isJump = true;
            Logger::debug("JUMPE0: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPE0: NO JUMP");
        }
    } else if (command.equals("JUMPNE")) {
        Word& arg1 = virtualMachine.popFromStack();
        if (!(virtualMachine.c & ZF)) {
            virtualMachine.pc = arg1;
            isJump = true;
            Logger::debug("JUMPNE: %.6s", arg1.word);
        } else {
            Logger::debug("JUMPNE: NO JUMP");
        }
    } else if (command.startsWith("PU")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = virtualMachine.memory->readWord(address.toInteger());
        virtualMachine.pushToStack(target);
        Logger::debug("PU: %.6s", target.word);
    } else if (command.startsWith("PO")) {
        std::string arg = command.substring(2); // Get the address from command inline args
        Word address("00" + arg); // Adjust the length of arg string
        Word& target = virtualMachine.popFromStack();
        virtualMachine.memory->writeWord(target, address.toInteger());
    } else if (command.equals("GETD00")) {
        Word& address = virtualMachine.popFromStack();
        Word& size = virtualMachine.popFromStack();
        std::string buffer;
        std::getline(std::cin, buffer);

        for (int i = 0; i < size.toInteger(); ++i) {
            virtualMachine.memory->writeWord(Word(buffer.substr(i * WORD_SIZE, WORD_SIZE)), address.toInteger());
        }

        Logger::debug("GETD00: %.6s", address.word);
    } else if (command.equals("PRTW00")) {
        Word& arg = virtualMachine.popFromStack();
        std::cout << arg << std::endl;
    } else if (command.equals("PRTS00")) {
    } else if (command.equals("HALT00")) {
        return -1;
    } else {
        throw std::runtime_error("Unknown instruction: " + std::string(command.word));
    }
    Logger::debug("Flags: %s", std::bitset<8>(virtualMachine.c).to_string().c_str());
    if (!isJump) {
        ++virtualMachine.pc;
    }
    return 0;
}
