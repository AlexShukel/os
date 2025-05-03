public enum ProgramInterrupt {
    NONE(0),
    BADCODE(1),
    SEGFAULT(2),
    OVERFLOW(3),
    ZERODIV(4),
    BADNUM(5);
    public final byte value;
    ProgramInterrupt(int value) { this.value = (byte) value; }
}
