package Processes;

import Resources.Resource;
import Resources.ResourceManager;
import Resources.ResourceName;
import Resources.ResourceType;
import utils.Logger;

public class StartStop extends Process {
    private int executionStep = 1;
    private final ShutdownCallback shutdownCallback;

    private final ProcessManager processManager;

    public StartStop(ShutdownCallback shutdownCallback, ProcessManager processManager) {
        super("StartStop", null, 100);
        this.shutdownCallback = shutdownCallback;
        this.processManager = processManager;
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                initializeSystemResources();
                ++executionStep;
                break;
            case 2:
                initializeSystemProcesses();
                ++executionStep;
                break;
            case 3:
                Logger.debug("StartStop: blocking for MOS_END");
                ResourceManager resourceManager = processManager.getResourceManager();
                Resource resource = resourceManager.getResourceByName(ResourceName.MOS_PABAIGA.name());
                resourceManager.requestResource(resource, this);
                ++executionStep;
                break;
            case 4:
                cleanupProcesses();
                ++executionStep;
                break;
            case 5:
                cleanupResources();
                finish();
                shutdownCallback.requestShutdown();
                break;
        }
    }

    private void initializeSystemResources() {
        Logger.debug("StartStop: Initializing system resources (Step %d)", executionStep);
        ResourceManager resourceManager = processManager.getResourceManager();
        resourceManager.addResource(new Resource(ResourceName.IS_VARTOTOJO_SASAJOS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.SUPERVIZORINE_ATMINTIS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.EILUTE_ATMINTYJE.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.UZDUOTIES_VYKDYMO_PARAMETRAI_SUPERVIZORINEJE_ATMINTYJE.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.ISORINE_ATMINTIS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.KANALU_IRENGINYS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.UZDUOTIS_BUGNE.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.IS_LOADER.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.VARTOTOJO_ATMINTIS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.NEEGZISTUOJANTIS.name(), ResourceType.STATIC, this), this);
        resourceManager.addResource(new Resource(ResourceName.MOS_PABAIGA.name(), ResourceType.STATIC, this), this);
    }

    private void initializeSystemProcesses() {
        Logger.debug("StartStop: Initializing system processes (Step %d)", executionStep);
        processManager.addProcess(new WhileTrue(this));
        processManager.addProcess(new ReadFromInterface(this, processManager.getResourceManager()));
        processManager.addProcess(new JCL(this));
        processManager.addProcess(new JobToSwap(this));
        processManager.addProcess(new MainProc(this));
        processManager.addProcess(new Loader(this));
        processManager.addProcess(new Interrupt(this));
        processManager.addProcess(new PrintLine(this, processManager.getResourceManager()));
    }

    private void cleanupProcesses() {
        Logger.debug("StartStop: Cleaning up %d child processes (Step %d)", children.size(), executionStep);
        for (Process child : children) {
            Logger.debug("StartStop: Destroying process %s", child.getName());
        }
        children.clear();
        Logger.debug("StartStop: Process cleanup completed");
    }

    private void cleanupResources() {
        Logger.debug("StartStop: Cleaning up system resources (Step %d)", executionStep);
        ResourceManager resourceManager = processManager.getResourceManager();
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.IS_VARTOTOJO_SASAJOS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.SUPERVIZORINE_ATMINTIS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.EILUTE_ATMINTYJE.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.UZDUOTIES_VYKDYMO_PARAMETRAI_SUPERVIZORINEJE_ATMINTYJE.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.ISORINE_ATMINTIS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.KANALU_IRENGINYS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.UZDUOTIS_BUGNE.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.IS_LOADER.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.VARTOTOJO_ATMINTIS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.NEEGZISTUOJANTIS.name()));
        resourceManager.removeResource(resourceManager.getResourceByName(ResourceName.MOS_PABAIGA.name()));
    }
}
