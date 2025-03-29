//
// Created by alexs on 3/29/2025.
//

#ifndef WORD_H
#define WORD_H

#include <string>

#include "shared.h"


class Word {
public:
    char word[WORD_SIZE]{ '0', '0', '0', '0', '0', '0' };

    Word() = default;

    explicit Word(int n);

    explicit Word(const std::string &str);

    bool isNumber() const;
};



#endif //WORD_H
