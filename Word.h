//
// Created by alexs on 3/29/2025.
//

#ifndef WORD_H
#define WORD_H

#include <string>

#include "shared.h"


class Word {
private:
    void saveInHex(const int& n);
public:
    char word[WORD_SIZE]{ '0', '0', '0', '0', '0', '0' };

    Word() = default;

    explicit Word(int n);

    explicit Word(const std::string &str);

    bool isNumber() const;

    int toInteger() const;



    bool equals(std::string str) const;

    bool startsWith(std::string str) const;

    std::string substring(int index) const;

    void operator--();

    void operator++();

    Word operator+(const Word& other) const;
    Word operator-(const Word& other) const;
    Word operator*(const Word& other) const;
    Word operator/(const Word& other) const;
    Word operator&(const Word& other) const;
    Word operator|(const Word& other) const;

    friend std::ostream& operator<<(std::ostream& os, const Word& word);
};



#endif //WORD_H
