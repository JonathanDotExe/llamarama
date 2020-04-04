package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.characters.DamageCause;

public class CollisionDamager extends GameObject {
	
	private CharacterComponent shooter;
	private int damage;
	private DamageCause cause;
	
	public CollisionDamager(double x, double y, double z, String area, String renderTag, CharacterComponent shooter,
			int damage, DamageCause cause) {
		super(x, y, z, area, renderTag);
		this.shooter = shooter;
		this.damage = damage;
		this.cause = cause;
	}

	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		for (GameObject collided : level.getObjectsInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea())) {
			CharacterComponent comp = collided.getComponent(CharacterComponent.class);
			if (comp != null && comp != shooter && comp.isAlive()) {
				damage(level, cause, collided, comp);
				delete(level);
			}
		}
	}
	
	protected void damage (Level level, DamageCause cause, GameObject obj, CharacterComponent comp) {
		comp.damage(level, shooter, damage, cause);
	}
	
	public CharacterComponent getShooter() {
		return shooter;
	}

	public void setShooter(CharacterComponent shooter) {
		this.shooter = shooter;
	}

}
