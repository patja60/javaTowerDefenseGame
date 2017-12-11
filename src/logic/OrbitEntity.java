package logic;

import input.CollidableEntity;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public abstract class OrbitEntity extends CollidableEntity{
	
	protected int orbitRange;
	protected int damage;
	protected double angle;
	protected double locationX;
	protected double locationY;
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getZ() {
		return 7;
	}
	
	
}
