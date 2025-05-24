package Resources;

import Processes.ProcessDescriptor;
import Processes.ProcessManager;

import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Resource> resources;
    private ProcessManager processManager;

    public ResourceManager(ProcessManager processManager) {
        resources = new ArrayList<>();
        this.processManager = processManager;
    }

    public Resource GetResource(String resourceName) {
        for (Resource res : resources) {
            if (res.GetName().equals(resourceName)) {
                return res;
            }
        }

        return null;
    }

    public Resource CreateResource(ProcessDescriptor parent, String name, ResourceType type) {
        Resource resource;
        if (type == ResourceType.STATIC) {
            resource = new StaticResource(parent, name);
        } else {
            resource = new DynamicResource(parent, name);
        }

        resources.add(resource);
        parent.AddChildResource(resource);

        resource.SetId(resources.size());

        return resource;
    }

    public void RemoveResource(Resource resource) {

    }

    public void RequestResource(String resourceName, ProcessDescriptor process) {
        Resource resource = GetResource(resourceName);
        resource.AddWaitingProcess(process);
        processManager.BlockProcess(process);
    }

    public void FreeResource(String resourceName, ProcessDescriptor process) {
        Resource resource = GetResource(resourceName);
        resource.RemoveWaitingProcess(process);
        processManager.ActivateProcess(process);
    }
}
