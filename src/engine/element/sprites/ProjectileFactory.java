package engine.element.sprites;

/**
 * Factory for producing projectiles
 * 
 * @author Bojia Chen
 *
 */

public class ProjectileFactory extends GameElementFactory {
    private final static String MY_CLASS_NAME = "engine.sprites.Projectile";

    public ProjectileFactory () {
        super(MY_CLASS_NAME);
    }

    /**
     * Method for getting a new instance of a specific projectile
     * 
     * @param projectileID GUID of the template tower
     * @return New projectile object with the parameters of the template projectile
     */
    public Projectile getProjectile (String projectileID) {
        return (Projectile) super.getGameElement(projectileID);
    }
}
