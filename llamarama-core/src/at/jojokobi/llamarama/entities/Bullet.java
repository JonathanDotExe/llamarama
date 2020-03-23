package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;

public class Bullet extends GameObject {
//	
//	public static final Image SPIT = new Image(Bullet.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "spit.png"));
//	public static final RenderModel SPIT_MODEL = new Image2DModel(SPIT);
	
	private CharacterComponent shooter;
	private int damage;

	public Bullet(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, "bullet.spit");
		this.shooter = shooter;
		this.damage = damage;
		motion.normalize().multiply(speed);
		setxMotion(motion.getX());
		setyMotion(motion.getY());
		setzMotion(motion.getZ());
		
		setWidth(0.5);
		setHeight(0.5);
		setLength(0.5);
	}
	
	public Bullet() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 37.5);
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		LevelBoundsComponent component = level.getComponent(LevelBoundsComponent.class);
		if ((component != null && component.nearBounds(this)) || !level.getSolidInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea()).isEmpty()) {
			delete(level);
		}
		else {
			for (GameObject collided : level.getObjectsInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea())) {
				CharacterComponent comp = collided.getComponent(CharacterComponent.class);
				if (comp != null && comp != shooter && comp.isAlive()) {
					comp.damage(level, shooter, damage, DamageCause.SHOT);
					delete(level);
				}
			}
		}
	}

	public CharacterComponent getShooter() {
		return shooter;
	}

	public void setShooter(CharacterComponent shooter) {
		this.shooter = shooter;
	}

}
