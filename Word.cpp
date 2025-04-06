//
// Created by alexs on 3/29/2025.
//

#include "Word.h"

#include <cstring>
#include <iomanip>
#include <ios>
#include <iosfwd>

Word::Word(const int n) {
    std::stringstream ss;
    ss << std::hex << std::uppercase << std::setfill('0') << std::setw(WORD_SIZE) << (n & 0xFFFFFF);
    memcpy(word, ss.str().c_str(), WORD_SIZE);
}

Word::Word(const std::string &str) {
    memcpy(word, str.c_str(), WORD_SIZE);
}


bool Word::isNumber() const {
    for (int i = 0; i < WORD_SIZE; i++) {
        if (!(word[i] >= '0' && word[i] <= '9') && !(word[i] >= 'A' && word[i] <= 'F')) {
            return false;
        }
    }

    return true;
}

int Word::toInteger() const {
    if (!isNumber()) {
        throw std::invalid_argument("Invalid number");
    }

    std::stringstream ss;
    ss << std::hex << std::string(word, WORD_SIZE);
    int n;
    ss >> n;
    return n;
}

