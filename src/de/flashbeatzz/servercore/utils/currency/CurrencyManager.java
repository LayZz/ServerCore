package de.flashbeatzz.servercore.utils.currency;

import de.flashbeatzz.servercore.utils.UserData;
import org.bukkit.Bukkit;

import java.util.UUID;

public class CurrencyManager {

    public static CurrencyResult deposit(UUID uuid, int amount) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        int oldMoney = userData.getMoney();
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_THAN_ZERO, oldMoney, userData.getMoney());
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();

        CurrencyLogger.log(userData.getUuid(), amount, "", Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(userData.getMoney() + amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }
    public static CurrencyResult deposit(UUID uuid, int amount, String description) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        int oldMoney = userData.getMoney();
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_THAN_ZERO, oldMoney, userData.getMoney());
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();

        CurrencyLogger.log(userData.getUuid(), amount, description, Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(userData.getMoney() + amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }

    public static CurrencyResult withdraw(UUID uuid, int amount) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        int oldMoney = userData.getMoney();
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_THAN_ZERO, oldMoney, userData.getMoney());
        }
        if(amount > userData.getMoney()) {
            return new CurrencyResult(CurrencyResult.Result.NO_MONEY, oldMoney, userData.getMoney());
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();

        CurrencyLogger.log(userData.getUuid(), -amount, "", Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(userData.getMoney() + amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }
    public static CurrencyResult withdraw(UUID uuid, int amount, String description) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        int oldMoney = userData.getMoney();
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_THAN_ZERO, oldMoney, userData.getMoney());
        }
        if(amount > userData.getMoney()) {
            return new CurrencyResult(CurrencyResult.Result.NO_MONEY, oldMoney, userData.getMoney());
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();

        CurrencyLogger.log(userData.getUuid(), -amount, description, Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(userData.getMoney() + amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }

    public static CurrencyResult set(UUID uuid, int amount) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_EQUALS_ZERO, userData.getMoney(), userData.getMoney());
        }
        int oldMoney = userData.getMoney();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();
        CurrencyLogger.log(userData.getUuid(), userData.getMoney() - oldMoney, "", Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }
    public static CurrencyResult set(UUID uuid, int amount, String description) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        if(amount < 0) {
            return new CurrencyResult(CurrencyResult.Result.NOT_GREATER_EQUALS_ZERO, userData.getMoney(), userData.getMoney());
        }
        int oldMoney = userData.getMoney();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String classPackage = stackTrace[2].getClassName();
        CurrencyLogger.log(userData.getUuid(), userData.getMoney() - oldMoney, description, Bukkit.getServerName(), classPackage, oldMoney, userData.getMoney());
        if(!userData.setMoney(amount)) {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, oldMoney, userData.getMoney());
        }

        return new CurrencyResult(CurrencyResult.Result.SUCCESS, oldMoney, userData.getMoney());
    }

    public static CurrencyResult get(UUID uuid) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        return new CurrencyResult(CurrencyResult.Result.SUCCESS, userData.getMoney(), userData.getMoney());
    }

    public static CurrencyResult has(UUID uuid, int amount) {
        UserData userData = UserData.getUserData(uuid);

        if(userData == null) {
            return new CurrencyResult(CurrencyResult.Result.USER_DONT_EXIST, 0, 0);
        }
        if(userData.getMoney() > amount) {
            return new CurrencyResult(CurrencyResult.Result.SUCCESS, userData.getMoney(), userData.getMoney());
        } else {
            return new CurrencyResult(CurrencyResult.Result.FAILURE, userData.getMoney(), userData.getMoney());
        }
    }

}
