#include <sstream>
#include <vector>

#include "RealMachine.h"

void printHelp() {
    std::cout << "Available commands:\n\n";

    std::cout << "  help\n";
    std::cout << "      Display this help message.\n\n";

    std::cout << "  exit\n";
    std::cout << "      Exit the program.\n\n";

    std::cout << "  run <path>\n";
    std::cout << "      Load and execute the program from the specified input file.\n";
    std::cout << "      <path> is a string representing the path to the input file.\n\n";

    std::cout << "  debug <path>\n";
    std::cout << "      Load the program from the specified input file and start it in debug mode.\n";
    std::cout << "      <path> is a string representing the path to the input file.\n\n";
}

int main() {
    std::string line;
    RealMachine realMachine{};

    while (true) {
        // get the input
        std::getline(std::cin, line);

        // parse the input
        std::stringstream ss(line);
        std::string token;
        std::vector<std::string> tokens;

        while (ss >> token) {
            tokens.push_back(token);
        }

        if (!tokens.empty()) {
            std::string command = tokens[0];
            std::vector<std::string> args(tokens.begin() + 1, tokens.end());

            if (command == "exit") {
                std::cout << "Goodbye!" << std::endl;
                return 0;
            }

            if (command == "help") {
                printHelp();
            }

            if (command == "run") {
                std::string path = args[0];
                realMachine.loadAndRunProgram(path);
            }

            if (command == "debug") {
                // TODO
            }
        } else {
            std::cout << "No command entered." << std::endl;
        }
    }

    return 0;
}
