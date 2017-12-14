package logic;

import input.OverridableEntity;

public abstract class OrbitEntity extends OverridableEntity{
	
	protected int orbitRange;
	protected int damage;
	protected double angle;
	protected double locationX;
	protected double locationY;
	protected int z = 7;
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	
	
}
