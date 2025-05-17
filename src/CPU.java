import utils.Logger;

import java.util.*;

enum InterruptType {
    PROGRAM,
    SUPERVISOR
}

class Interrupt {
    public VirtualMachine virtualMachine;
    public byte interrupt;
    public InterruptType type;

    public Interrupt(VirtualMachine vm, byte interrupt, InterruptType type) {
        this.virtualMachine = vm;
        this.interrupt = interrupt;
        this.type = type;
    }
}

public class CPU {
    private Word ptr = new Word(0);
    private Word pc = new Word(0);
    private Word sp = new Word(65536);
    private byte c = 0;
    private byte pi = 0;
    private byte si = 0;
    private byte mode = 0;
    private byte ti = 0;

    private final Queue<Interrupt> interrupts = new LinkedList<>();

    public Word getPtr() {
        return ptr;
    }

    public void setPtr(Word word) {
        ptr = word;
    }

    public int exec(VirtualMachine vm) {
        int commandAddress = vm.pc.toInteger();
        Word command = vm.memory.readWord(commandAddress);

        Logger.debug(String.format("Executing command %.6s", command));

        boolean isJump = false;
        if (command.equals("ADD000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.add(arg2);
            Logger.debug(String.format("ADD000: %.6s + %.6s = %.6s", arg1, arg2, result));

            vm.setZeroFlag(result);
            vm.setCarryFlag(arg1.toInteger() + arg2.toInteger() > result.toInteger());

            vm.pushToStack(result);
        } else if (command.equals("SUB000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.subtract(arg2);
            Logger.debug(String.format("SUB000: %.6s - %.6s = %.6s", arg1, arg2, result));

            vm.setZeroFlag(result);
            vm.setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());

            vm.pushToStack(result);
        }
        else if (command.equals("MUL000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.multiply(arg2);
            Logger.debug(String.format("MUL000: %.6s * %.6s = %.6s", arg1, arg2, result));
            vm.setZeroFlag(result);
            vm.pushToStack(result);
        }
        else if (command.equals("DIV000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            if (arg2.toInteger() == 0) {
                signalProgramInterrupt(vm, ProgramInterrupt.ZERODIV);
                processInterrupt();
                return -1;
            }
            Word result = arg1.divide(arg2);
            Logger.debug(String.format("DIV000: %.6s / %.6s = %.6s", arg1, arg2, result));
            vm.setZeroFlag(result);
            vm.pushToStack(result);
        }
        else if (command.equals("COMP00")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.subtract(arg2);
            vm.setZeroFlag(result);
            vm.setCarryFlag(arg1.toInteger() - arg2.toInteger() < result.toInteger());
        }
        else if (command.equals("AND000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.and(arg2);
            Logger.debug(String.format("AND000: %.6s & %.6s = %.6s", arg1, arg2, result));
            vm.pushToStack(result);
        }
        else if (command.equals("OR0000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.or(arg2);
            Logger.debug(String.format("OR0000: %.6s | %.6s = %.6s", arg1, arg2, result));
            vm.pushToStack(result);
        }
        else if (command.equals("XOR000")) {
            Word arg1 = vm.popFromStack();
            Word arg2 = vm.popFromStack();
            Word result = arg1.xor(arg2);
            Logger.debug(String.format("XOR000: %.6s ^ %.6s = %.6s", arg1, arg2, result));
            vm.pushToStack(result);
        }
        else if (command.equals("JUMP00")) {
            Word arg1 = vm.popFromStack();
            vm.pc = arg1;
            isJump = true;
            Logger.debug(String.format("JUMP00: %.6s", arg1));
        }
        else if (command.equals("JUMPB0")) {
            Word arg1 = vm.popFromStack();
            if ((vm.c & VirtualMachine.CF) != 0) {
                vm.pc = arg1;
                isJump = true;
                Logger.debug(String.format("JUMPB0: %.6s", arg1));
            } else {
                Logger.debug("JUMPB0: NO JUMP");
            }
        }
        else if (command.equals("JUMPBE")) {
            Word arg1 = vm.popFromStack();
            if ((vm.c & VirtualMachine.CF) != 0 || (vm.c & VirtualMachine.ZF) != 0) {
                vm.pc = arg1;
                isJump = true;
                Logger.debug(String.format("JUMPBE: %.6s", arg1));
            } else {
                Logger.debug("JUMPBE: NO JUMP");
            }
        }
        else if (command.equals("JUMPA0")) {
            Word arg1 = vm.popFromStack();
            if ((vm.c & VirtualMachine.CF) == 0 && (vm.c & VirtualMachine.ZF) == 0) {
                vm.pc = arg1;
                isJump = true;
                Logger.debug(String.format("JUMPA0: %.6s", arg1));
            } else {
                Logger.debug("JUMPA0: NO JUMP");
            }
        }
        else if (command.equals("JUMPE0")) {
            Word arg1 = vm.popFromStack();
            if ((vm.c & VirtualMachine.ZF) != 0) {
                vm.pc = arg1;
                isJump = true;
                Logger.debug(String.format("JUMPE0: %.6s", arg1));
            } else {
                Logger.debug("JUMPE0: NO JUMP");
            }
        }
        else if (command.equals("JUMPNE")) {
            Word arg1 = vm.popFromStack();
            if ((vm.c & VirtualMachine.ZF) == 0) {
                vm.pc = arg1;
                isJump = true;
                Logger.debug(String.format("JUMPNE: %.6s", arg1));
            } else {
                Logger.debug("JUMPNE: NO JUMP");
            }
        }
        else if (command.startsWith("PU")) {
            String arg = command.substring(2);
            Word address = new Word("00" + arg);
            Word target = vm.memory.readWord(address.toInteger());
            vm.pushToStack(target);
            Logger.debug(String.format("PU: %.6s", target));
        }
        else if (command.startsWith("PO")) {
            String arg = command.substring(2);
            Word address = new Word("00" + arg);
            Word target = vm.popFromStack();
            vm.memory.writeWord(target, address.toInteger());
        }
        else if (command.equals("GETD00")) {
            signalSupervisorInterrupt(vm, SupervisorInterrupt.GETD);
            processInterrupt();
        }
        else if (command.equals("PRTW00")) {
            signalSupervisorInterrupt(vm, SupervisorInterrupt.PRTW);
            processInterrupt();
        }
        else if (command.equals("PRTS00")) {
            signalSupervisorInterrupt(vm, SupervisorInterrupt.PRTS);
            processInterrupt();
        }
        else if (command.equals("HALT00")) {
            signalSupervisorInterrupt(vm, SupervisorInterrupt.HALT);
            processInterrupt();
            return -1;
        } else {
            Logger.debug(String.format("Unknown instruction: %.6s", command));
            return -1;
        }

        Logger.debug("Flags: " + String.format("%8s", Integer.toBinaryString(vm.c & 0xFF)).replace(' ', '0'));

        if (!isJump) {
            vm.pc = vm.pc.add(new Word(1));
        }

        return 0;
    }

    private void signalInterrupt(VirtualMachine vm, byte interrupt, InterruptType type) {
        interrupts.add(new Interrupt(vm, interrupt, type));
    }

    public void signalProgramInterrupt(VirtualMachine vm, ProgramInterrupt interrupt) {
        this.pi = interrupt.value;
        signalInterrupt(vm, pi, InterruptType.PROGRAM);
    }

    public void signalSupervisorInterrupt(VirtualMachine vm, SupervisorInterrupt interrupt) {
        this.si = interrupt.value;
        signalInterrupt(vm, si, InterruptType.SUPERVISOR);
    }

    public void processInterrupt() {
        if (interrupts.isEmpty()) return;

        Interrupt interrupt = interrupts.poll();
        if (interrupt.type == InterruptType.PROGRAM) {
            this.pi = interrupt.interrupt;
            processProgramInterrupt(interrupt.virtualMachine);
        } else {
            this.si = interrupt.interrupt;
            processSupervisorInterrupt(interrupt.virtualMachine);
        }
    }

    private void processProgramInterrupt(VirtualMachine vm) {
        ProgramInterrupt interrupt = ProgramInterrupt.values()[pi];
        switch (interrupt) {
            case BADCODE -> Logger.debug("Bad code exception");
            case SEGFAULT -> Logger.debug("Segmentation fault exception");
            case OVERFLOW -> Logger.debug("Overflow exception");
            case ZERODIV -> Logger.debug("Division by zero exception");
            case BADNUM -> Logger.debug("Bad number exception");
            default -> Logger.debug("Unknown program interrupt");
        }
    }

    private void processSupervisorInterrupt(VirtualMachine vm) {
        SupervisorInterrupt interrupt = SupervisorInterrupt.values()[si];
        switch (interrupt) {
            case GETD -> {
                Logger.debug("GETD supervisor interrupt");
                Word size = vm.popFromStack();
                Word address = vm.popFromStack();
                Scanner scanner = new Scanner(System.in);
                String buffer = scanner.nextLine();

                for (int i = 0; i < size.toInteger(); ++i) {
                    String slice = buffer.substring(i * Constants.WORD_SIZE, (i + 1) * Constants.WORD_SIZE);
                    vm.memory.writeWord(new Word(slice), address.toInteger() + i);
                }
                Logger.debug("GETD00: " + address);
            }
            case PRTW -> {
                Logger.debug("PRTW supervisor interrupt");
                Word arg = vm.popFromStack();
                System.out.println(arg);
            }
            case PRTS -> Logger.debug("PRTS supervisor interrupt");
            case HALT -> Logger.debug("HALT supervisor interrupt");
            default -> Logger.debug("Unknown supervisor interrupt");
        }
    }
}
