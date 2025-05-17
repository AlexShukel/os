import java.util.ArrayList;

public class ResourceList {
    private Kernel kernel;
    private ArrayList<Resource> resources;

    public ResourceList(Kernel kernel)
    {
        this.kernel = kernel;
        resources = new ArrayList<>();
    }

    public int Count()
    {
        return resources.size();
    }
}
