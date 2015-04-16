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

    /**
     * Given a GUID, returns the projectile object with a prefilled parameters map and values that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Projectile object
     */
    public Projectile getProjectile (String projectileID) {
        super.checkID(projectileID);

        Projectile projectile = (Projectile) Reflection.createInstance(MY_CLASS_NAME);
        projectile.setParameterMap(super.getParameters(projectileID));

        return projectile;
    }
}
