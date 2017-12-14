package input;

import javafx.scene.shape.Shape;
import logic.Entity;

public abstract class OverridableEntity extends Entity{
	protected double speed;
	protected double cX;
	protected double cY;
	protected abstract Shape getBound();
	protected boolean overrideWith(OverridableEntity other){
		return this.getBound().getBoundsInParent().intersects(other.getBound().getBoundsInParent());
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
