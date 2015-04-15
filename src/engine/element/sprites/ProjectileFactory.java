package engine.element.sprites;

import util.reflection.Reflection;


/**
 * Factory for producing projectiles
 * 
 * @author Bojia Chen
 *
 */

public class ProjectileFactory extends SpriteFactory {
    private final static String MY_CLASS_NAME = "engine.sprites.Projectile";

    public ProjectileFactory () {
        super(MY_CLASS_NAME);
    }

    public Projectile getProjectile (String projectileID) {
        super.checkID(projectileID);

        Projectile projectile = (Projectile) Reflection.createInstance(MY_CLASS_NAME);
        projectile.setParameterMap(super.getSpriteParameters(projectileID));

        return projectile;
    }
}
