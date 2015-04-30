package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class TowerSetting extends SpriteSetting {

    public TowerSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }
    
    @Override
    protected void setSpriteDisplayed(){
        spriteDisplayed = "Tower";
    }
}
