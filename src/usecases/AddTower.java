package usecases;
import authoring.environment.InstanceManager;
import authoring.environment.TowerEditor;

public class AddTower {

	public void addTower(){
		String partName = InstanceManager.getPartType(new TowerEditor().getClass());
		InstanceManager im = new InstanceManager();
		im.addPart(partName);
	}
}
