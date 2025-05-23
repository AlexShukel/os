package Resources;

public enum DynamicResourceName {
    PAKROVIMO_PAKETAS("Pakrovimo paketas"),
    PERTRAUKIMAS("Pertraukimas"),
    IS_INTERRUPT("Iš interrupt");

    private final String name;

    DynamicResourceName(String str) {
        name = str;
    }

    public String toString() {
        return "'" + this.name + "'";
    }
}
