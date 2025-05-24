package Resources;

public enum StaticResourceName {
    IS_VARTOTOJO_SASAJOS("Iš vartotojo sąsajos"),
    SUPERVIZORINE_ATMINTIS("Supervizorinė atmintis"),
    UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE("Užduotis supervizorinėje atmintyje"),
    EILUTE_ATMINTYJE("Eilutė atmintyje"),
    UZDUOTIES_VYKDYMO_PARAMETRAI_SUPERVIZORINEJE_ATMINTYJE("Užduoties vykdymo parametrai supervizorinėje atmintyje"),
    UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE("Užduoties programa supervizorinėje atmintyje"),
    ISORINE_ATMINTIS("Išorinė atmintis"),
    KANALU_IRENGINYS("Kanalų įrenginys"),
    UZDUOTIS_BUGNE("Užduotis būgne"),
    IS_LOADER("Iš Loader"),
    VARTOTOJO_ATMINTIS("Vartotojo atmintis"),
    NEEGZISTUOJANTIS("Neegzistuojantis"),
    MOS_PABAIGA("MOS_PABAIGA");

    private final String name;

    StaticResourceName(String str) {
        name = str;
    }

    public String toString() {
        return "'" + this.name + "'";
    }
}
