package fr.manu.practice.gestion;

import fr.manu.practice.gestion.ranks.RankUnit;

import java.util.UUID;

public class DataRank extends Data {
    private RankUnit grade;
    private long end;

    public DataRank(UUID uuid) {
        this.uuid = uuid;
    }

    public void setRank(RankUnit rank){
        grade = rank;
        end = -1;
    }

    public void setRank(RankUnit rank, long seconds){
        if(seconds <= 0){
            setRank(rank);
        } else {
            grade = rank;
            end = seconds * 1000 + System.currentTimeMillis();
        }
    }

    public RankUnit getRank(){
        return grade;
    }

    public long getEnd(){
        return end;
    }

    public boolean isTemporary(){
        return end != -1;
    }

    public boolean isValid(){
        return end != -1 && end < System.currentTimeMillis();
    }
}
































