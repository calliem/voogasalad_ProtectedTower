package authoringEnvironment.editors;

import authoringEnvironment.Controller;


/**
 * Sets up the round editor that allows the user to pick a map and designate spawn points for
 * selected waves of units that will spawn at one time in a level
 * 
 * @author Megan Gutter
 */
public class RoundEditor extends FlowEditor {

    public RoundEditor (Controller c, String name, String nameWithoutEditor) {
        super(c, name, nameWithoutEditor);
    }

    @Override
    protected String returnEditorTypeName () {
        return "Round";
    }
    

}
