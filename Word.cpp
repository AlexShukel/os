//
// Created by alexs on 3/29/2025.
//

#include "Word.h"

#include <cstring>
#include <iomanip>
#include <ios>
#include <iosfwd>
#include <iostream>

Word::Word(const int n) {
    sprintf(word, "%06X", n);
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

bool Word::equals(std::string str) const {
    return memcmp(word, str.c_str(), WORD_SIZE) == 0;
}

bool Word::startsWith(std::string str) const {
    return memcmp(word, str.c_str(), str.size()) == 0;
}

std::string Word::substring(int index) const {
    if (index < 0 || index >= WORD_SIZE) {
        throw std::invalid_argument("Word::substring: Invalid index");
    }

    return std::string(word).substr(index);
}

void Word::operator--() {
    if (!isNumber()) {
        throw std::invalid_argument("Word::operator--: Invalid number");
    }

    char buffer[WORD_SIZE + 1];
    snprintf(buffer, sizeof(buffer), "%06X", toInteger() - 1);
    memcpy(word, buffer, WORD_SIZE);
}

void Word::operator++() {
    if (!isNumber()) {
        throw std::invalid_argument("Word::operator++: Invalid number");
    }

    char buffer[WORD_SIZE + 1];
    snprintf(buffer, sizeof(buffer), "%06X", toInteger() + 1);
    memcpy(word, buffer, WORD_SIZE);
}


