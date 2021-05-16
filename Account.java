package fr.manu.practice.gestion;

import fr.manu.practice.Main;
import fr.manu.practice.gestion.ranks.RankUnit;

import java.sql.SQLException;
import java.util.UUID;

public class Account extends Data {
    private boolean newPlayer;
    private DataRank datarank;

    public Account(UUID uuid) {
        this.uuid = uuid;
        this.newPlayer = false;
        this.datarank = new DataRank(uuid);
    }

    private String[] getDataFromMySQL() {
        String[] data = new String[2];

        Main.getInstance().getMySQL().query(String.format("SELECT * FROM accounts WHERE uuid='%s'", getUUID()), rs -> {
            try {
                if(rs.next()){
                    data[0] = rs.getString("grade");
                    data[1] = rs.getString("grade_end");
                } else {
                    newPlayer = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return data;
    }

    private void sendDataToMySQL() {
        if (newPlayer){
            Main.getInstance().getMySQL().update(String.format("INSERT INTO accounts (uuid, grade, grade_end) VALUES ('%s', '%s', '%s')",
                    getUUID(), datarank.getRank().getName(), datarank.getEnd()));
        } else {
            Main.getInstance().getMySQL().update(String.format("UPDATE accounts SET grade='%s', grade_end='%s' WHERE uuid='%s'",
                    datarank.getRank().getName(), datarank.getEnd(), getUUID()));
        }
    }

    public void onLogin() {
        Main.getInstance().getAccounts().add(this);
        String[] data = getDataFromMySQL();

        if(newPlayer) {
            datarank.setRank(RankUnit.JOUEUR);
        } else {
            datarank.setRank(RankUnit.getByName(data[0]), Long.parseLong(data[1]));
        }
    }

    public void onLogout() {
        sendDataToMySQL();
        Main.getInstance().getAccounts().remove(this);
    }

    public DataRank getDataRank() {
        return datarank;
    }

}

