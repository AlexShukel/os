//
// Created by alexs on 3/15/2025.
//

#ifndef SHARED_H
#define SHARED_H

#include <cstdint>

#define RM_RAM_SIZE 640 // 640 blocks
#define BLOCK_SIZE 256 // 256 words in a block
#define WORD_SIZE 6 // 6 bytes in a word

// Real machine memory. 630 block for user memory and 10 blocks for OS memory
#define USER_MEMORY_START_BLOCK 0
#define OS_MEMORY_START_BLOCK (RM_RAM_SIZE - 10)

// Virtual machine memory
#define VIRTUAL_MACHINE_MEMORY_SIZE 256
#define CODE_SEGMENT_START_BLOCK 0
#define DATA_SEGMENT_START_BLOCK 16
#define STACK_SEGMENT_START_BLOCK 136

using Byte = char;

#endif //SHARED_H
