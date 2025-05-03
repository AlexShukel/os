public class VirtualMachine {
    public static final byte CF = 0b00000001;
    public static final byte ZF = 0b00000010;

    public Word pc = new Word(0);         // Program counter
    public Word sp = new Word(65536);     // Stack pointer

    public byte c = 0;                    // Flags (CF/ZF)

    public final MemoryProxy memory;

    public VirtualMachine(MemoryProxy memory) {
        this.memory = memory;
    }

    public Word popFromStack() {
        Word word = memory.readWord(sp.toInteger());
        sp.increment();
        return word;
    }

    public void pushToStack(Word value) {
        sp.decrement();
        memory.writeWord(value, sp.toInteger());
    }

    public void setZeroFlag(Word result) {
        if (result.toInteger() == 0) {
            c |= ZF;
        } else {
            c &= ~ZF;
        }
    }

    public void setCarryFlag(boolean value) {
        if (value) {
            c |= CF;
        } else {
            c &= ~CF;
        }
    }
}
