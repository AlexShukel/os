package Processes;

public class StartStop extends Process {
    private int currentStep = 1;

    public StartStop(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        switch (currentStep) {
            case 1: // Sisteminiu resursu inicializacija
                break;
            case 2: // Sisteminiu permanentiniu procesu inicializacija
                WhileTrue whileTrue = new WhileTrue(GetProcessManager());
                CreateProcess(whileTrue, "WhileTrue", 0);

                ReadFromInterface readFromInterface = new ReadFromInterface(GetProcessManager());
                CreateProcess(readFromInterface, "ReadFromInterface", 2);

                JCL jcl = new JCL(GetProcessManager());
                CreateProcess(jcl, "JCL", 3);

                JobToSwap jobToSwap = new JobToSwap(GetProcessManager());
                CreateProcess(jobToSwap, "JobToSwap", 4);

                MainProc mainProc = new MainProc(GetProcessManager());
                CreateProcess(mainProc, "MainProc", 5);

                Loader loader = new Loader(GetProcessManager());
                CreateProcess(loader, "Loader", 6);

                JobGorvernor jobGorvernor = new JobGorvernor(GetProcessManager());
                CreateProcess(jobGorvernor, "JobGorvernor", 7);

                Interrupt interrupt = new Interrupt(GetProcessManager());
                CreateProcess(interrupt, "Interrupt", 8);

                PrintLine printLine = new PrintLine(GetProcessManager());
                CreateProcess(printLine, "PrintLine", 9);
                break;
            case 3: // Blokavimasis laukiant "Mos pabaiga" resurso
                break;
            case 4: // Sisteminiu procesu naikinimas
                break;
            case 5: // Sisteminiu resursu naikinimas
                CompleteWork();
                break;
        }

        ++currentStep;
    }
}
