package de.flashbeatzz.servercore.utils.currency;

public class CurrencyResult {

    private Result result;
    private int oldMoney, newMoney;

    CurrencyResult(Result result, int oldMoney, int newMoney) {
        this.result = result;
        this.oldMoney = oldMoney;
        this.newMoney = newMoney;
    }

    public Result getResult() {
        return result;
    }

    public int getOldMoney() {
        return oldMoney;
    }

    public int getNewMoney() {
        return newMoney;
    }

    enum Result {
        SUCCESS,
        FAILURE,
        NO_MONEY,
        USER_DONT_EXIST,
        NOT_GREATER_THAN_ZERO,
        NOT_GREATER_EQUALS_ZERO
    }

}
