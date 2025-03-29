#include "RM.h"

int main() {
    RM real_machine{};
    real_machine.load_program_from_file("../loop.txt");
    real_machine.memory.__print_memory();

    return 0;
}
