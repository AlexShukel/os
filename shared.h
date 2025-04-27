//
// Created by alexs on 3/15/2025.
//

#ifndef SHARED_H
#define SHARED_H

#include <cstdint>

#define RM_RAM_SIZE 640 // 640 blocks
#define BLOCK_SIZE 256 // 256 words in a block
#define WORD_SIZE 6 // 6 bytes in a word

// Virtual machine memory
#define VIRTUAL_MEMORY_BLOCKS 256 // 256 blocks of 256 words each
#define CODE_SEGMENT_START_BLOCK 0
#define DATA_SEGMENT_START_BLOCK 16
#define STACK_BEGIN 255

// Swap file
#define SWAP_FILE "swap.txt"
#define SWAP_BLOCKS 1024

using Byte = char;

#endif //SHARED_H
