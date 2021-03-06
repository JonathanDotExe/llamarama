package at.jojokobi.llamarama.entities.ai;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class UseAbilityTask implements CharacterTask {

	private double minPriority;
	
	public UseAbilityTask(double minPriority) {
		super();
		this.minPriority = minPriority;
	}

	@Override
	public boolean canApply(Level level, GameObject obj, CharacterComponent ch) {
		return ch.getCharacter().getAbility() != null && ch.getAbilityCooldown() <= 0 && minPriority <= ch.getCharacter().getAbility().getUsePriority(level, obj, ch);
	}

	@Override
	public Vector3D apply(Level level, GameObject obj, CharacterComponent ch, double delta) {
		return null;
	}

	@Override
	public void activate(Level level, GameObject obj, CharacterComponent ch) {
		ch.setUseAbility(true);
	}

	@Override
	public void deactivate(Level level, GameObject obj, CharacterComponent ch) {
		ch.setUseAbility(false);
	}

}
