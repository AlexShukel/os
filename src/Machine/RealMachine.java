package Machine;// Machine.RealMachine.java

import java.util.*;
import java.io.*;

public class RealMachine {
    public CPU cpu = new CPU();
    public RAM memory = new RAM();
    public MemoryProxy memoryProxy = new MemoryProxy(memory);
    public DataExchange dataExchange = new DataExchange(memoryProxy);
    public List<VirtualMachine> virtualMachines = new ArrayList<>();

    public VirtualMachine loadProgram(String fileName) {
        newPageTable();

        // Machine.DataExchange will copy from hdd.txt all program's parts on the fly
        dataExchange.path = fileName;
        dataExchange.sourceObject = MemoryObject.EXTERNAL;
        dataExchange.destinationObject = MemoryObject.MEMORY;

        if (dataExchange.xchg() == -1) {
            return null;
        }

        VirtualMachine vm = new VirtualMachine(memoryProxy);
        virtualMachines.add(vm);
        return vm;
    }

    public void runProgram(VirtualMachine virtualMachine) {
        while (cpu.exec(virtualMachine) != -1) {}
    }

    public void debugProgram(VirtualMachine virtualMachine) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int commandResult = 0;

        while (commandResult != -1) {
            System.out.println("===Registers===");
            System.out.println("PTR: " + cpu.getPtr());
            System.out.println("PC: " + virtualMachine.pc);
            System.out.println("SP: " + virtualMachine.sp);
            System.out.println("C: " + String.format("%8s", Integer.toBinaryString(virtualMachine.c & 0xFF)).replace(' ', '0'));
            System.out.println("===============");
            System.out.println("Available commands:");
            System.out.println("'next' - perform next instruction");
            System.out.println("'block <block_number>' - print virtual machine block");
            System.out.println("'rmblock <block_number>' - print real machine block\n");

            Word nextInstruction = virtualMachine.memory.readWord(virtualMachine.pc.toInteger());
            System.out.println("Next instruction: " + nextInstruction);

            System.out.print("Enter command: ");
            String command = reader.readLine().trim();

            if (command.equals("next")) {
                commandResult = cpu.exec(virtualMachine);
            } else if (command.startsWith("block ")) {
                try {
                    int blockNumber = Integer.parseInt(command.substring(6));
                    if (blockNumber < 0 || blockNumber >= Constants.VIRTUAL_MEMORY_BLOCKS) {
                        System.out.printf("Invalid block number. Please enter a number between 0 and %d.%n", Constants.VIRTUAL_MEMORY_BLOCKS - 1);
                        continue;
                    }
                    virtualMachine.memory.printBlock(blockNumber);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.err.println("block command failed: " + e.getMessage());
                }
            } else if (command.startsWith("rmblock ")) {
                try {
                    int blockNumber = Integer.parseInt(command.substring(8));
                    if (blockNumber < 0 || blockNumber >= Constants.RM_RAM_SIZE) {
                        System.out.printf("Invalid block number. Please enter a number between 0 and %d.%n", Constants.RM_RAM_SIZE - 1);
                        continue;
                    }
                    memory.printBlock(blockNumber);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.err.println("rmBlock command failed: " + e.getMessage());
                }
            } else if (command.equals("exit")) {
                System.out.println("Exiting debug mode.");
                break;
            } else {
                System.out.println("Unknown command: " + command);
            }
        }
    }

    private void newPageTable() {
        int pageTableBlockIndex = memory.pickFreeBlockIndex();
        cpu.setPtr(new Word(pageTableBlockIndex));

        for (int i = 0; i < Constants.VIRTUAL_MEMORY_BLOCKS; i++) {
            int randomBlock = memory.pickFreeBlockIndex();
            memory.writeWord(new Word(randomBlock), pageTableBlockIndex, i);
        }

        memoryProxy.setPageTable(memory.getBlock(pageTableBlockIndex));
    }
}
