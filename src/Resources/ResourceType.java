package Resources;

public enum ResourceType {
    // static resource is created by StartStop process initially
    STATIC("static"),
    // dynamic resources are created and deleted in runtime
    DYNAMIC("dynamic");

    private final String name;

    ResourceType(String str)
    {
        name = str;
    }

    public String toString()
    {
        return "'" + this.name + "'";
    }
}
