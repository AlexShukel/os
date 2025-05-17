package Processes;

public interface Process {
    void Step();
    boolean EndedWork();
    int GetTick();
}
