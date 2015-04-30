package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class ProjectileSetting extends SpriteSetting{

    public ProjectileSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }
    
    @Override
    protected void setSpriteDisplayed(){
        spriteDisplayed = "Projectile";
    }
}
