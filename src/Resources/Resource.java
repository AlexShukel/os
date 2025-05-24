package Resources;

import Processes.Process;
import java.util.ArrayList;

public class Resource {
    private int id;

    private ResourceDescriptor descriptor;
    private Process creator;
    private ArrayList<Element> elements;

    private static int nextId = 0;

    public Resource(ResourceDescriptor descriptor, Process creator) {
        this.id = nextId++;
        this.creator = creator;
        this.elements = new ArrayList<>();
        this.descriptor = descriptor;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public String getName() {
        return descriptor.getName();
    }

    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }
}
