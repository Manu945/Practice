package fr.manu.practice.gestion.ranks;

import java.util.Arrays;

public enum RankUnit {
    STAFF("§a§lSTAFF","Staff", 10, "§a§lSTAFF§a "),
    FAMOUS("§b§lFAMOUS","Famous", 11, "§b§l️FAMOUS§d "),
    FRIEND("§d§l❤️FRIEND","Friend", 12, "§d§l❤️️§d "),
    YONKO("§c§lYonko","Yonko", 13, "§c§lYonko§e "),
    HOLLOW("§5§lHollow","Hollow", 14, "§5§lHollow§e "),
    SAIYAN("§6§lSaiyan","Saiyan", 15, "§6§lSaiyan§e "),
    JOUEUR("§7JOUEUR","Joueur", 16, "§7");


    private String shortName;
    private String name;
    private int power;
    private String prefix;

    RankUnit(String shortName, String name, int power, String prefix) {
        this.shortName = shortName;
        this.name = name;
        this.power = power;
        this.prefix = prefix;
    }

    public static RankUnit getByName(String name){
        return Arrays.stream(values()).filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(RankUnit.JOUEUR);
    }

    public static RankUnit getByPower(int power){
        return Arrays.stream(values()).filter(r -> r.getPower() == power).findAny().orElse(RankUnit.JOUEUR);
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getShortName() {
        return shortName;
    }
}
