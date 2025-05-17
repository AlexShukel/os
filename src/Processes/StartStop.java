package Processes;

public class StartStop implements Process {
    private int currentStep = 1;
    private int currentTick = 0;
    private boolean endedWork = false;

    @Override
    public void Step() {
        switch (currentStep) {
            case 1: // Sisteminiu resursu inicializacija
                break;
            case 2: // Sisteminiu permanentiniu procesu inicializacija
                break;
            case 3: // Blokavimasis laukiant "Mos pabaiga" resurso
                break;
            case 4: // Sisteminiu procesu naikinimas
                break;
            case 5: // Sisteminiu resursu naikinimas
                endedWork = true;
                break;
        }

        ++currentStep;
        ++currentTick;
    }

    @Override
    public boolean EndedWork() {
        return endedWork;
    }

    @Override
    public int GetTick() {
        return currentTick;
    }
}
