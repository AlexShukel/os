package Resources;

import java.util.ArrayList;

public class ResourceList {
    private ArrayList<Resource> resources;

    public ResourceList()
    {
        resources = new ArrayList<>();
    }

    public int Count()
    {
        return resources.size();
    }
}
