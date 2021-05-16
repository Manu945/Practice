package fr.manu.practice.gestion.world;

public enum World {

    OVERWORLD("world"),
    NETHER("world_nether"),
    END("world_the_end");

    private String worldName;

    World(String worldName) {
        this.worldName = worldName;
    }

    public String getWorldName() {
        return this.worldName;
    }
}


