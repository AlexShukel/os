package Processes;

public class Loader extends Process {

    public Loader(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
