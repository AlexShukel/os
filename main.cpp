#include "RM.h"

int main() {
    RM real_machine{};
    real_machine.load_programs_from_file("../hdd.txt");

    return 0;
}
