package logic;

import java.util.List;

import input.CollidableEntity;
import javafx.scene.canvas.GraphicsContext;

public abstract class Creep extends CollidableEntity{
	protected double hp;
	protected double speed;
	protected double maxSpeed;
	protected double maxHp;
	protected double armor;
	protected int price;
	protected Spawn spawn;
	protected GameLogic gameLogic;
	protected double rotateSpeed;
	protected double distance;
	protected String name;
	
	public Creep(String name, double x,double y, double speed, double rotateSpeed, double hp, double armor, int price,Spawn spawn,GameLogic gameLogic) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.maxSpeed = speed;
		this.rotateSpeed = rotateSpeed;
		this.hp = hp;
		this.maxHp = hp;
		this.armor = armor;
		this.price = price;
		this.distance = Field.SIZE;
		
		this.spawn = spawn;
		this.gameLogic = gameLogic;
	}
	
	public abstract List creepData();
	
	public void checkHp(double val, double max,double min) {
		if(val < min) this.hp = min;
		if(val > max) this.hp = max;
	}
	
	public int getZ() {
		return 3;
	}


	public double getHp() {
		return hp;
	}


	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public void setDestroy() {
		this.destroyed = true;
	}
	
	public void moveUp() {
		if(distance > 0) {
			this.y -= this.speed;
			distance-= this.speed;
		}
		if(distance<0) {
			this.y -= this.distance;
		}
	}
	public void moveRight() {
		if(distance > 0) {
			this.x += this.speed;
			distance-= this.speed;
		}
		if(distance<0) {
			this.x += this.distance;
		}
	}
	public void moveDown() {
		if(distance > 0) {
			this.y += this.speed;
			distance-= this.speed;
		}
		if(distance<0) {
			this.y += this.distance;
		}
	}
	public void moveLeft() {
		if(distance > 0) {
			this.x -= this.speed;
			distance-= this.speed;
		}
		if(distance<0) {
			this.x -= this.distance;
		}
	}
	

}
