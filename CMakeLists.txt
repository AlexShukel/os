cmake_minimum_required(VERSION 3.22)
project(OS2025)

set(CMAKE_CXX_STANDARD 23)

file(GLOB SOURCES
    "*.cpp"
    "*.h"
)

add_executable(OS2025 ${SOURCES})
