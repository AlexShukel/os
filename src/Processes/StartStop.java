package Processes;

public class StartStop extends Process {
    private int currentStep = 1;

    private WhileTrue whileTrue;
    private ReadFromInterface readFromInterface;
    private JCL jcl;
    private JobToSwap jobToSwap;
    private MainProc mainProc;
    private Loader loader;
    private JobGorvernor jobGorvernor;
    private Interrupt interrupt;
    private PrintLine printLine;

    public StartStop(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        switch (currentStep) {
            case 1: // Sisteminiu resursu inicializacija
                break;
            case 2: // Sisteminiu permanentiniu procesu inicializacija
                whileTrue = new WhileTrue(GetProcessManager());
                CreateProcess(whileTrue, "WhileTrue", 0);

                readFromInterface = new ReadFromInterface(GetProcessManager());
                CreateProcess(readFromInterface, "ReadFromInterface", 2);

                jcl = new JCL(GetProcessManager());
                CreateProcess(jcl, "JCL", 3);

                jobToSwap = new JobToSwap(GetProcessManager());
                CreateProcess(jobToSwap, "JobToSwap", 4);

                mainProc = new MainProc(GetProcessManager());
                CreateProcess(mainProc, "MainProc", 5);

                loader = new Loader(GetProcessManager());
                CreateProcess(loader, "Loader", 6);

                jobGorvernor = new JobGorvernor(GetProcessManager());
                CreateProcess(jobGorvernor, "JobGorvernor", 7);

                interrupt = new Interrupt(GetProcessManager());
                CreateProcess(interrupt, "Interrupt", 8);

                printLine = new PrintLine(GetProcessManager());
                CreateProcess(printLine, "PrintLine", 9);
                break;
            case 3: // Blokavimasis laukiant "Mos pabaiga" resurso
                break;
            case 4: // Sisteminiu procesu naikinimas
                RemoveProcess(whileTrue);
                RemoveProcess(readFromInterface);
                RemoveProcess(jcl);
                RemoveProcess(jobToSwap);
                RemoveProcess(mainProc);
                RemoveProcess(loader);
                RemoveProcess(jobGorvernor);
                RemoveProcess(interrupt);
                RemoveProcess(printLine);
                break;
            case 5: // Sisteminiu resursu naikinimas
                CompleteWork();
                break;
        }

        ++currentStep;
    }
}
