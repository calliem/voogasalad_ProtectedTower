package engine.element.sprites;

import java.util.Map;

/**
 * This class represents an object which may carry a modification to set upon another object, such
 * as a status effect.
 * 
 * @author Qian Wang
 * @author Greg McKeon
 *
 */
public class Modifier extends GameElement {


    public Modifier() {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
    }

    @Override
    public void onCollide (GameElement element) {
        
    }

}
