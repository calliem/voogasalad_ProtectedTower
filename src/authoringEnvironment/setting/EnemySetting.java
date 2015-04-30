package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class EnemySetting extends SpriteSetting{
    private static final String ENEMY = "Enemy";
    
    public EnemySetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }

    @Override
    protected void setSpriteDisplayed(){
        spriteDisplayed = ENEMY;
    }
}
