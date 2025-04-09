//
// Created by alexs on 3/15/2025.
//

#include "VirtualMachine.h"

Process::Process(const int& pid, const int& pageTableAddress, const VirtualMachine& virtualMachine): pid(pid), pageTableAddress(pageTableAddress), virtualMachine(virtualMachine) {}

void Process::update() {
    virtualMachine.step();
}

void VirtualMachine::step() {
    // TODO: execute code
}