package Resources;

public class Element {
    private Object data;
    private ElementType type;

    public Element(Object data, ElementType type) {
        this.data = data;
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public ElementType getType() {
        return type;
    }
}
