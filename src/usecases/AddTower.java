package usecases;
import authoring.environment.InstanceManager;

public class AddTower {

	public void addTower(){
		String partName = InstanceManager.getPartType(this.getClass());
		InstanceManager.addPart(partName);
	}
}
