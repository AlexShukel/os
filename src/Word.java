import utils.Logger;

import java.util.Arrays;

public class Word {
    private final char[] word = new char[Constants.WORD_SIZE];

    public Word() {
        Arrays.fill(word, '0');
    }

    public Word(int n) {
        saveInHex(n);
    }

    public Word(String str) {
        if (str.length() > Constants.WORD_SIZE) {
            Arrays.fill(word, '0');
        } else {
            for (int i = 0; i < Constants.WORD_SIZE; i++) {
                word[i] = str.charAt(i);
            }
        }
    }

    private void saveInHex(int n) {
        String hex = String.format("%06X", n & 0xFFFFFF);  // limit to 24 bits
        for (int i = 0; i < Constants.WORD_SIZE; i++) {
            word[i] = hex.charAt(i);
        }
    }

    public int toInteger() {
        return Integer.parseInt(new String(word), 16);
    }

    public boolean equals(String str) {
        return new String(word).equals(str);
    }

    public boolean startsWith(String str) {
        return new String(word).startsWith(str);
    }

    public String substring(int index) {
        if (index < 0 || index >= Constants.WORD_SIZE) {
            Logger.debug("Word.substring: Invalid index: " + index);
        }
        return new String(word).substring(index);
    }

    public void decrement() {
        saveInHex(toInteger() - 1);
    }

    public void increment() {
        saveInHex(toInteger() + 1);
    }

    public Word add(Word other) {
        return new Word((this.toInteger() + other.toInteger()) % 0x1000000);
    }

    public Word subtract(Word other) {
        int i1 = this.toInteger();
        int i2 = other.toInteger();
        if (i1 < i2) {
            i1 += 0x1000000;
        }
        return new Word(i1 - i2);
    }

    public Word multiply(Word other) {
        return new Word(this.toInteger() * other.toInteger());
    }

    public Word divide(Word other) {
        return new Word(this.toInteger() / other.toInteger());
    }

    public Word and(Word other) {
        return new Word(this.toInteger() & other.toInteger());
    }

    public Word or(Word other) {
        return new Word(this.toInteger() | other.toInteger());
    }

    public Word xor(Word other) {
        return new Word(this.toInteger() ^ other.toInteger());
    }

    @Override
    public String toString() {
        return new String(word);
    }
}
