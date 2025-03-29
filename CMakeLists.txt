cmake_minimum_required(VERSION 3.22)
project(OS2025)

set(CMAKE_CXX_STANDARD 23)

add_executable(OS2025 main.cpp
        VM.cpp
        VM.h
        shared.h
        RM.cpp
        RM.h
        CPU.cpp
        CPU.h
        RAM.cpp
        RAM.h
        DataExchange.cpp
        DataExchange.h
        Word.cpp
        Word.h)
