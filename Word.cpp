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
    saveInHex(n);
}

Word::Word(const std::string &str) {
    memcpy(word, str.c_str(), WORD_SIZE);
}

void Word::saveInHex(const int& n) {
    char buffer[WORD_SIZE + 1];
    snprintf(buffer, sizeof(buffer), "%06X", n);
    memcpy(word, buffer, WORD_SIZE);
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

    saveInHex(toInteger() - 1);
}

void Word::operator++() {
    if (!isNumber()) {
        throw std::invalid_argument("Word::operator++: Invalid number");
    }

    saveInHex(toInteger() + 1);
}

Word Word::operator+(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word((toInteger() + other.toInteger()) % 0x1000000);
}

Word Word::operator-(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    int i1 = toInteger();
    int i2 = other.toInteger();

    if (i1 < i2) {
        i1 += 0x1000000;
    }

    return Word(i1 - i2);
}

Word Word::operator*(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word(toInteger() * other.toInteger());
}

Word Word::operator/(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word(toInteger() / other.toInteger());
}

Word Word::operator&(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word(toInteger() & other.toInteger());
}

Word Word::operator|(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word(toInteger() | other.toInteger());
}

Word Word::operator^(const Word& other) const {
    if (!isNumber() || !other.isNumber()) {
        throw std::invalid_argument("Word::operator+: Invalid number");
    }

    return Word(toInteger() ^ other.toInteger());
}

std::ostream& operator<<(std::ostream& os, const Word& word) {
    for (int i = 0; i < 6; ++i)
        os << word.word[i];
    return os;
}