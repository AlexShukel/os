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
#define CODE_SEGMENT_START_BLOCK 0
#define DATA_SEGMENT_START_BLOCK 120

using Byte = char;

#endif //SHARED_H
