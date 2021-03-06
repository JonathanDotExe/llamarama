package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.bullets.Hook;

public class HookAbility implements Ability {
	
	private double hookRange = 20;

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		Hook hook = new Hook(0, 0, 0, object.getArea(), object, character.getDirection().toVector(), 37.5, hookRange);
		Vector3D pos = object.getPositionVector().add(object.getSize().multiply(0.5)).subtract(hook.getSize().multiply(0.5));
		hook.setX(pos.getX());
		hook.setY(pos.getY());
		hook.setZ(pos.getZ());
		level.spawn(hook);
		return true;
	}

	@Override
	public double getUsePriority(Level level, GameObject object, CharacterComponent character) {
		double priority = 0.0;
		for (GameObject obj : object.getObjectsInDirection(level, character.getDirection().toVector(), hookRange, GameObject.class)) {
			if (obj.getComponent(CharacterComponent.class) != null) {
				priority = Math.max(priority, 1.0);
			}
			else if (!obj.isSolid()) {
				priority = Math.max(priority, 0.7);
			}
		}
		return priority;
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data,
			Camera cam) {
		
	}

	@Override
	public double getCooldown() {
		return 1.0;
	}

}
