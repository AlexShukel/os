package Processes;

import Resources.ResourceManager;
import Resources.ResourceType;
import Resources.StaticResourceName;

import java.util.ArrayList;

public class StartStop extends Process {
    private int currentStep = 1;

    public StartStop(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        switch (currentStep) {
            case 1: // Sisteminiu resursu inicializacija
                resourceManager.CreateResource(descriptor, StaticResourceName.IS_VARTOTOJO_SASAJOS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.SUPERVIZORINE_ATMINTIS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.EILUTE_ATMINTYJE.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.UZDUOTIES_VYKDYMO_PARAMETRAI_SUPERVIZORINEJE_ATMINTYJE.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.ISORINE_ATMINTIS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.KANALU_IRENGINYS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.UZDUOTIS_BUGNE.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.IS_LOADER.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.VARTOTOJO_ATMINTIS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.NEEGZISTUOJANTIS.toString(), ResourceType.STATIC);
                resourceManager.CreateResource(descriptor, StaticResourceName.MOS_PABAIGA.toString(), ResourceType.STATIC);

                break;
            case 2: // Sisteminiu permanentiniu procesu inicializacija
                processManager.CreateProcess(new WhileTrue(processManager, resourceManager), descriptor, "WhileTrue", 0, ProcessState.READY);

                processManager.CreateProcess(new ReadFromInterface(processManager, resourceManager), descriptor, "ReadFromInterface", 10, ProcessState.READY);

                processManager.CreateProcess(new JCL(processManager, resourceManager), descriptor, "JCL", 10, ProcessState.READY);

                processManager.CreateProcess(new JobToSwap(processManager, resourceManager), descriptor, "JobToSwap", 10, ProcessState.READY);

                processManager.CreateProcess(new MainProc(processManager, resourceManager), descriptor, "MainProc", 10, ProcessState.READY);

                processManager.CreateProcess(new Loader(processManager, resourceManager), descriptor, "Loader", 10, ProcessState.READY);

                processManager.CreateProcess(new Interrupt(processManager, resourceManager), descriptor, "Interrupt", 10, ProcessState.READY);

                processManager.CreateProcess(new PrintLine(processManager, resourceManager), descriptor, "PrintLine", 10, ProcessState.READY);
                break;
            case 3: // Blokavimasis laukiant "Mos pabaiga" resurso
                resourceManager.RequestResource(StaticResourceName.MOS_PABAIGA.toString(), descriptor);
                break;
            case 4: // Sisteminiu procesu naikinimas
                ArrayList<ProcessDescriptor> children = new ArrayList<>(descriptor.GetChildProcesses());
                for (ProcessDescriptor descriptor : children) {
                    processManager.RemoveProcess(descriptor);
                }
                break;
            case 5: // Sisteminiu resursu naikinimas
                CompleteWork();
                break;
        }

        ++currentStep;
    }
}
