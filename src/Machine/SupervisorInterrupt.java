package Machine;

public enum SupervisorInterrupt {
    NONE(0),
    GETD(1),
    PRTW(2),
    PRTS(3),
    HALT(4);
    public final byte value;
    SupervisorInterrupt(int value) { this.value = (byte) value; }
}
