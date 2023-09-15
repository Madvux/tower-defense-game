package objects;

import utils.Constants;

import static utils.Constants.Towers.*;

public class Tower {
    private int x, y, id, dmg, towerType, cdTick, tier;
    private float range, cooldown;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    public void update() {
        cdTick++;
    }

    public void upgradeTower() {
        this.tier++;

        switch (towerType) {
            case ARCHER -> {
                dmg += 2;
                range += 20;
                cooldown -= 5;
            }
            case CANNON -> {
                dmg += 5;
                range += 20;
                cooldown -= 15;
            }
            case WIZARD -> {
                range += 20;
                cooldown -= 10;
            }
        }
    }

    public boolean isCooldownOver() {
        return cdTick >= cooldown;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    private void setDefaultCooldown() {
        cooldown = Constants.Towers.GetDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.Towers.GetDefaultRange(towerType);
    }

    private void setDefaultDamage() {
        dmg = Constants.Towers.GetStartDmg(towerType);
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public int getTier() {
        return tier;
    }
}
