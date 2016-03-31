package de.flashbeatzz.servercore.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserData {

    private static HashMap<UUID, UserData> userDatas = new HashMap<>();

    public static UserData getUserData(UUID uuid) {
        if (userDatas.containsKey(uuid)) {
            return userDatas.get(uuid);
        } else {
            UserData userData = new UserData("", uuid);
            return (userData.isValid ? userData : null);
        }
    }

    public static void removeUserData(UUID uuid) {
        userDatas.remove(uuid);
    }

    private String name;
    private UUID uuid;

    private int guild_id = -1;
    private int money = 0;
    private int level = 0;
    private int exp = 0;

    private boolean isValid = false;

    public UserData(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;

        try {
            load();
            isValid = true;
            if (!name.equals("")) {
                userDatas.put(uuid, UserData.this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void load() throws SQLException {
        ResultSet resultSet = MySQL.query("SELECT * FROM `userdata` WHERE `uuid` = '" + uuid.toString() + "'");
        if (resultSet != null && resultSet.next()) {
            if (name.equals("")) {
                name = resultSet.getString("name");
            }
            guild_id = resultSet.getInt("guild_id");
            level = resultSet.getInt("level");
            exp = resultSet.getInt("exp");
            money = resultSet.getInt("money");
        } else {
            insert();
        }
    }

    private void insert() {
        MySQL.execute("INSERT INTO `userdata` (`uuid`, `name`, `money`, `guild_id`, `level`, `exp`) VALUES ('" + uuid.toString() + "', '" + name + "', '" + money + "', '-1', '" + level + "', '" + exp + "')");
    }

    public void save() {
        MySQL.update("UPDATE `userdata` SET `name` = '" + name + "' AND `money` = '" + money + "' AND `guild_id` = '-1' AND `level` = '" + level + "' AND `exp` = '" + exp + "'");
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        save();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        save();
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        save();
    }

    public int getGuildID() {
        return guild_id;
    }

    public void setGuildID(int guild_id) {
        this.guild_id = guild_id;
    }
}