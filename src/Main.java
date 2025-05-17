import Machine.RealMachine;
import Machine.VirtualMachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static void printHelp() {
        System.out.println("Available commands:\n");

        System.out.println("  help");
        System.out.println("      Display this help message.\n");

        System.out.println("  exit");
        System.out.println("      Exit the program.\n");

        System.out.println("  run <path>");
        System.out.println("      Load and execute the program from the specified input file.");
        System.out.println("      <path> is a string representing the path to the input file.\n");

        System.out.println("  debug <path>");
        System.out.println("      Load the program from the specified input file and start it in debug mode.");
        System.out.println("      <path> is a string representing the path to the input file.\n");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        RealMachine realMachine = new RealMachine();

        while (true) {
            String line = scanner.nextLine();
            String[] tokensArray = line.trim().split("\\s+");
            List<String> tokens = new ArrayList<>();

            for (String token : tokensArray) {
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
            }

            if (!tokens.isEmpty()) {
                String command = tokens.getFirst();
                List<String> arguments = tokens.subList(1, tokens.size());

                switch (command) {
                    case "exit":
                        System.out.println("Goodbye!");
                        return;

                    case "help":
                        printHelp();
                        break;

                    case "run":
                        if (arguments.isEmpty()) {
                            System.err.println("Missing path argument for 'run'");
                            break;
                        }
                        String runPath = arguments.getFirst();
                        VirtualMachine vmRun = realMachine.loadProgram(runPath);

                        if (vmRun == null) {
                            System.err.println("Failed to load program from " + runPath);
                            break;
                        }

                        realMachine.runProgram(vmRun);
                        break;

                    case "debug":
                        if (arguments.isEmpty()) {
                            System.err.println("Missing path argument for 'debug'");
                            break;
                        }

                        String debugPath = arguments.getFirst();
                        VirtualMachine vmDebug = realMachine.loadProgram(debugPath);

                        if (vmDebug == null) {
                            System.err.println("Failed to load program from " + debugPath);
                            break;
                        }

                        realMachine.debugProgram(vmDebug);
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                        break;
                }
            } else {
                System.out.println("No command entered.");
            }
        }
    }
}
