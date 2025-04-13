cmake_minimum_required(VERSION 3.22)
project(OS2025)

set(CMAKE_CXX_STANDARD 23)

file(GLOB SOURCES
    "*.cpp"
    "*.h"
)

add_executable(OS2025 ${SOURCES})

file(COPY ${CMAKE_CURRENT_SOURCE_DIR}/hdd.txt DESTINATION ${CMAKE_CURRENT_BINARY_DIR})
