//
// Created by alexs on 4/13/2025.
//

#include "Logger.h"

#include <cstdio>
#include <string>
#include <cstdarg>

void Logger::debug(const char* format, ...) {
    std::printf("[DEBUG]: ");

    va_list args;
    va_start(args, format);
    std::vprintf(format, args);
    va_end(args);

    std::printf("\n");
}

