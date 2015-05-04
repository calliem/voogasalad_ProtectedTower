// This entire file is part of my masterpiece.
// Kevin He
package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class TowerSetting extends SpriteSetting {
    private static final String TOWER = "Tower";

    public TowerSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }
    
    @Override
    protected void setSpriteDisplayed(){
        spriteDisplayed = TOWER;
    }
}
