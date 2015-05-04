//This entire file is part of my masterpiece
//Janan Zhu

package engine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import engine.element.sprites.GameElement;


public class GroovyEngine {

    private ScriptEngine myEngine;
    private static final String SCRIPT_LANGUAGE = "groovy";
    private static final String SCRIPT_DEFINITION_HEAD = "def ";
    private static final String CLOSURE_HEAD = " = { GameElement e1, GameElement e2 -> { ";
    private static final String CLOSURE_TAIL = "}}";
    private static final String ELEMENT_ONE_BINDING = "elementOne";
    private static final String ELEMENT_TWO_BINDING = "elementTwo";
    private static final String EVAL_PARAMETERS = "(" + ELEMENT_ONE_BINDING + "," +
                                                  ELEMENT_TWO_BINDING + ")";
    private Collection<String> myCurrentScripts;

    public GroovyEngine (Map<String, Object> environmentMap) {
        ScriptEngineManager manager = new ScriptEngineManager();
        myEngine = manager.getEngineByName(SCRIPT_LANGUAGE);
        myCurrentScripts = new ArrayList<String>();
        for (String tag : environmentMap.keySet()) {
            myEngine.put(tag, environmentMap.get(tag));
        }
    }

    public void addScriptToEngine (String tag, String script) {
        myCurrentScripts.add(tag);
        String scriptDefinition =
                SCRIPT_DEFINITION_HEAD + tag + CLOSURE_HEAD + script + CLOSURE_TAIL;
        try {
            myEngine.eval(scriptDefinition);
        }
        catch (ScriptException e) {
            System.out.println(e.getMessage());
        }

    }

    public void applyScript (String scriptTag, GameElement elementOne, GameElement elementTwo) {
        if (myCurrentScripts.contains(scriptTag)) {
            myEngine.put(ELEMENT_ONE_BINDING, elementOne);
            myEngine.put(ELEMENT_TWO_BINDING, elementTwo);

            String evalScript = scriptTag + EVAL_PARAMETERS;

            try {
                myEngine.eval(evalScript);
            }
            catch (ScriptException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            throw new InvalidParameterException(scriptTag +
                                                " is a script that has not yet been defined");
        }
    }
}
