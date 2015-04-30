package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class ProjectileSetting extends SpriteSetting{
    private static final String PROJECTILE = "Projectile";
    
    public ProjectileSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }
    
    @Override
    protected void setSpriteDisplayed(){
        spriteDisplayed = PROJECTILE;
    }
}
