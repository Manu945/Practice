package fr.manu.practice.gestion.ranks;

import java.util.Arrays;

public enum RankUnit {
  ADMINISTRATEUR("§c§lADMIN", "Administrateur", 10, "§c§lADMINISTRATEUR§c"),
  RESPONSABLE("§6§lRESP", "Responsable", 11, "§6§lRESPONSABLE§6"),
  MODERATEUR("§9§lMOD", "Mod", 12, "§9§lMODERATEUR§9"),
  DEVELOPPEUR("§2§lDEV", "Dev", 13, "§2§lDEVELOPPEUR§2"),
  ANIMATEUR("§5§lANIM", "Animateur", 14, "§5§lANIMIMATEUR§5"),
  STAFF("§a§lSTAFF", "Staff", 15, "§a§lSTAFF§a"),
  FAMOUS("§d§lFAMOUS", "Famous", 16, "§d§lFAMOUS§d"),
  FRIEND("§d§l❤FRIEND", "Friend", 17, "§d§l❤§d"),
  YONKO("§5§lYONKO", "Yonko", 18, "§5§lYONKO§5"),
  HOLLOW("§c§lHOLLOW", "Hollow", 19, "§c§lHOLLOW§c"),
  SAIYAN("§6§lSAIYAN", "Saiyan", 20, "§6§lSAIYAN§6"),
  JOUEUR("§7JOUEUR", "Joueur", 21, "§7");


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
