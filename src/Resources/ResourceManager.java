package Resources;

import Processes.Process;
import Processes.ProcessManager;

import java.util.ArrayList;

public class ResourceManager {
    private final ArrayList<Resource> resources;
    private final ProcessManager processManager;

    public ResourceManager(ProcessManager processManager) {
        resources = new ArrayList<>();
        this.processManager = processManager;
    }

    public Resource getResourceByName(String name) {
        for (Resource resource : resources) {
            if (resource.getName().equals(name)) {
                return resource;
            }
        }

        return null;
    }

    public void addResource(Resource resource, Process parent) {
        resources.add(resource);
        parent.addChildResource(resource);
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public void requestResource(Resource resource, Process requester) {
        resource.addWaitingProcess(requester);
        processManager.blockProcess(requester);
    }

    public void releaseResource(Resource resource) {
        Process process = resource.pollFirstWaitingProcess();
        processManager.activateProcess(process);
    }
}
