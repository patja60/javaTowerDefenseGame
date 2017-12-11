package input;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.Entity;

public abstract class CollidableEntity extends Entity{
	protected double speed;
	protected double cX;
	protected double cY;
	protected abstract Shape getBound();
	protected boolean collideWith(CollidableEntity other){
		return this.getBound().getBoundsInParent().intersects(other.getBound().getBoundsInParent());
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
