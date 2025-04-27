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
        signalSupervisorInterrupt(virtualMachine, SupervisorInterrupt::GETD);
        processInterrupt();
    } else if (command.equals("PRTW00")) {
        signalSupervisorInterrupt(virtualMachine, SupervisorInterrupt::PRTW);
        processInterrupt();
    } else if (command.equals("PRTS00")) {
        signalSupervisorInterrupt(virtualMachine, SupervisorInterrupt::PRTS);
        processInterrupt();
    } else if (command.equals("HALT00")) {
        signalSupervisorInterrupt(virtualMachine, SupervisorInterrupt::HALT);
        processInterrupt();
        return -1; // TODO: handle HALT in interrupt
    } else {
        Logger::debug("Unknown instruction: %.6s", command.word);
        return -1;
    }
    Logger::debug("Flags: %s", std::bitset<8>(virtualMachine.c).to_string().c_str());
    if (!isJump) {
        ++virtualMachine.pc;
    }
    return 0;
}

void CPU::signalInterrupt(VirtualMachine& virtualMachine, Byte interrupt, InterruptType type) {
    Interrupt newInterrupt{virtualMachine, interrupt, type};
    interrupts.push(newInterrupt);
}

void CPU::signalProgramInterrupt(VirtualMachine& virtualMachine, const ProgramInterrupt& interrupt) {
    pi = static_cast<Byte>(interrupt);
    signalInterrupt(virtualMachine, pi, InterruptType::PROGRAM);
}

void CPU::signalSupervisorInterrupt(VirtualMachine& virtualMachine, const SupervisorInterrupt& interrupt) {
    si = static_cast<Byte>(interrupt);
    signalInterrupt(virtualMachine, si, InterruptType::SUPERVISOR);
}

void CPU::processInterrupt() {
    if (interrupts.empty()) {
        return;
    }

    Interrupt interrupt = interrupts.front();
    interrupts.pop();

    if (interrupt.type == InterruptType::PROGRAM) {
        pi = interrupt.interrupt;
        processProgramInterrupt(interrupt.virtualMachine);

    } else if (interrupt.type == InterruptType::SUPERVISOR) {
        si = interrupt.interrupt;
        processSupervisorInterrupt(interrupt.virtualMachine);
    }
}

void CPU::processProgramInterrupt(VirtualMachine& virtualMachine) {
    ProgramInterrupt interrupt = static_cast<ProgramInterrupt>(pi);
    
    switch (interrupt) {
        case ProgramInterrupt::BADCODE:
        {
            Logger::debug("Bad code exception");
            break;
        }
        case ProgramInterrupt::SEGFAULT:
        {
            Logger::debug("Segmentation fault exception");
            break;
        }
        case ProgramInterrupt::OVERFLOW:
        {
            Logger::debug("Overflow exception");
            break;
        }
        case ProgramInterrupt::ZERODIV:
        {
            Logger::debug("Division by zero exception");
            break;
        }
        case ProgramInterrupt::BADNUM:
        {
            Logger::debug("Bad number exception");
            break;
        }
        default:
        {
            Logger::debug("Unknown program interrupt");
            break;
        }
    }
}

void CPU::processSupervisorInterrupt(VirtualMachine& virtualMachine) {
    SupervisorInterrupt interrupt = static_cast<SupervisorInterrupt>(si);
    
    switch (interrupt) {
        case SupervisorInterrupt::GETD:
        {
            Logger::debug("GETD supervisor interrupt");
            Word& address = virtualMachine.popFromStack();
            Word& size = virtualMachine.popFromStack();
            std::string buffer;
            std::getline(std::cin, buffer);

            for (int i = 0; i < size.toInteger(); ++i) {
                virtualMachine.memory->writeWord(Word(buffer.substr(i * WORD_SIZE, WORD_SIZE)), address.toInteger());
            }

            Logger::debug("GETD00: %.6s", address.word);
            break;
        }
        case SupervisorInterrupt::PRTW:
        {
            Logger::debug("PRTW supervisor interrupt");
            Word& arg = virtualMachine.popFromStack();
            std::cout << arg << std::endl;
            break;
        }
        case SupervisorInterrupt::PRTS:
        {
            Logger::debug("PRTS supervisor interrupt");
            break;
        }
        case SupervisorInterrupt::HALT:
        {
            Logger::debug("HALT supervisor interrupt");
            break;
        }
        default:
        {
            Logger::debug("Unknown supervisor interrupt");
            break;
        }
    }
}