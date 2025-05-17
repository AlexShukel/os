import java.util.ArrayList;

public class ElementList {
    private Resource resource;
    private ArrayList<ResourceElement> elements;

    public ElementList(Resource resource)
    {
        this.resource = resource;
        elements = new ArrayList<>();
    }

    public int Count()
    {
        return elements.size();
    }
}
