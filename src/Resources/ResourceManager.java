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

    public Resource getFirstResourceByDescriptor(ResourceDescriptor descriptor) {
        for (Resource resource : resources) {
            if (resource.getName().equals(descriptor.getName())) {
                return resource;
            }
        }

        return null;
    }

    public Resource getFirstOrNewResourceByDescriptor(ResourceDescriptor descriptor, Process creator) {
        Resource resource = getFirstResourceByDescriptor(descriptor);

        if (resource == null) {
            resource = new Resource(descriptor, creator);
            addResource(resource, creator);
        }

        return resource;
    }

    public void addResource(Resource resource, Process parent) {
        resources.add(resource);
        parent.addChildResource(resource);
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public Resource requestResource(ResourceDescriptor descriptor, Process requester) {
        Resource resource = getFirstResourceByDescriptor(descriptor);
        if (resource == null) {
            descriptor.addWaitingProcess(requester);
            processManager.blockProcess(requester);
        } else {
            // TODO
        }
        return resource;
    }

    public void releaseResource(Resource resource) {
        Process process = resource.getDescriptor().pollFirstWaitingProcess();
        processManager.activateProcess(process);
    }
}
