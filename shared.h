//
// Created by alexs on 3/15/2025.
//

#ifndef SHARED_H
#define SHARED_H

#include <cstdint>

typedef uint64_t DW; // double word
typedef uint32_t W; // word
typedef uint16_t HW; // half word
typedef uint8_t B;

#define RM_RAM_SIZE 640 // 640 blocks in user memory
#define BLOCK_SIZE 256 // 256 words in a block
#define WORD_SIZE 4 // 4 bytes in a word

#endif //SHARED_H
