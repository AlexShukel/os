//
// Created by alexs on 3/15/2025.
//

#ifndef SHARED_H
#define SHARED_H

#include <cstdint>

#define RM_RAM_SIZE 256 // 640 blocks
#define BLOCK_SIZE 256 // 256 words in a block
#define WORD_SIZE 6 // 6 bytes in a word

// Virtual machine memory
#define VIRTUAL_MEMORY_BLOCKS 256 // 256 blocks of 256 words each
#define CODE_SEGMENT_START_BLOCK 0
#define DATA_SEGMENT_START_BLOCK 16
#define STACK_BEGIN 255

using Byte = char;

#define SWAP_FILE "swap.txt"
#define SWAP_FILE_SIZE 1024

#endif //SHARED_H
