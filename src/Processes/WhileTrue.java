package Processes;

public class WhileTrue extends Process {
    private int executionStep = 1;

    public WhileTrue(Process parent) {
        super("WhileTrue", parent, 0);
    }

    @Override
    public void execute() {
        // noop
    }
}
