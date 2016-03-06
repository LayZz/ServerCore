package de.flashbeatzz.servercore.utils.levelsystem;

public class Level {
    private Integer lvl = 0;
    public Integer MAX_EXP = (int) (Math.pow(lvl, 2) / Math.sqrt(lvl));

    public Level(Integer lvl) {
        this.lvl = lvl;
    }

    public Object[] /* ? */ getRewards() {
        return null;
    }

    public Integer toInt() {
        return lvl;
    }


}
