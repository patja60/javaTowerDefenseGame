package logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import input.AudioUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sharedObject.RenderableHolder;

public class NormalCreep extends Creep{
	
	private int i = 0;
	private int dimension = 30;
	private  List<OrbitEntity> collideWith;
	private int direction = 0;
	private int rotate = direction;
	private boolean isRotate = false;
	
	private boolean isSlow = false;
	private long lastHitSlow = 0;
	private double slowBy = 1;
	private Image creep;
	
	public NormalCreep(String name, double x,double y, double speed, double rotateSpeed, double hp, double armor, int price,Spawn spawn,GameLogic gameLogic, String image) {
		super(name, x, y, speed, rotateSpeed, hp, armor, price, spawn, gameLogic);
		collideWith = new ArrayList<>();
		switch (image) {
		case "creep1":
			this.creep = RenderableHolder.normalCreep1;
			break;

		case "creep2":
			this.creep = RenderableHolder.normalCreep2;
			break;
			
		case "creep3":
			this.creep = RenderableHolder.normalCreep3;
			break;
			
		case "creep4":
			this.creep = RenderableHolder.normalCreep4;
			break;
			
		case "aircraft1":
			this.creep = RenderableHolder.aircraft1;
			break;

		case "aircraft2":
			this.creep = RenderableHolder.aircraft2;
			break;
			
		case "aircraft3":
			this.creep = RenderableHolder.aircraft3;
			break;
			
		case "aircraft4":
			this.creep = RenderableHolder.aircraft4;
			break;
			
		}
	}

	@Override
	public void tick() {
		if((int)((x+dimension/2)/Field.SIZE) == spawn.findEnd().getKey() && (int)((y+dimension/2)/Field.SIZE) == spawn.findEnd().getValue()) {
			gameLogic.setHealth(gameLogic.getHealth()-1);
			this.destroyed = true;
		}
		if(this.hp <= 0) {
			do {
				gameLogic.setMoney(gameLogic.getMoney() + price);
				AudioUtility.playSound("coin");
			}while(false);
			this.destroyed = true;
		}
		if(isSlow) {
			if(lastHitSlow/1000000000+ 3 >gameLogic.getNow()/1000000000) {
				this.speed = this.maxSpeed - this.maxSpeed*slowBy/100;
			}else {
				this.speed = maxSpeed;
				isSlow = false;
			}
		}
		if(distance > 0 && !isRotate && i < spawn.getPath().size()) {
			if(spawn.getPath().get(i) == 1) {
				moveUp();
			}else if(spawn.getPath().get(i) == 2) {
				moveRight();
			}else if(spawn.getPath().get(i) == 3) {
				moveDown();
			}else if(spawn.getPath().get(i) == 4) {
				moveLeft();
			}else if(spawn.getPath().get(i) == -1) {
				isRotate = true;
				rotate += 90;
				i++;
			}else if(spawn.getPath().get(i) == -2) {
				isRotate = true;
				rotate -= 90;
				i++;
			}
			if(distance <= 0) {
				i++;
				distance = Field.SIZE;
			}
		}
		for(Entity x : gameLogic.getGameObjectContainer()) {
			if(x instanceof OrbitEntity) {
				OrbitEntity o = (OrbitEntity)x;
				if(collideWith(o)){
					getHit(o);
					collideWith.add(o);
				}else {
					collideWith.remove((OrbitEntity)x);
				}
				
			}
			
			if(x instanceof SlowOrbitEntity) {
				if(collideWith((SlowOrbitEntity) x)){
					isSlow = true;
					lastHitSlow = gameLogic.getNow();
					if(((SlowOrbitEntity) x).getSlow()>slowBy) {
						slowBy = ((SlowOrbitEntity) x).getSlow();
					}
				}
			}
		}
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		this.cX = x + Field.SIZE/2;
		this.cY = y + Field.SIZE/2;
		gc.setFill(Color.RED);
		//gc.fillOval(cX - dimension/2, cY - dimension/2, dimension, dimension);
		
		gc.setFill(Color.BLACK);
		gc.fillRect(cX - dimension/2  - (80-dimension)/2, y-20, 80, 18);
		gc.setFill(Color.GREEN);
		checkHp(hp, maxHp, 0);
		Color c = new Color(0.3, 1*(hp/maxHp), 0, 1);
		gc.setFill(c);
		gc.fillRect(cX - dimension/2  - (80-dimension)/2 + 2,  y-18, (hp/maxHp)*76, 14);
		
		gc.translate(x+25, y+25);
		if(rotate>direction) {
			gc.rotate(direction);
			gc.drawImage(creep, -25, -25);
			gc.rotate(-direction);
			direction += rotateSpeed;
		}else if(rotate<direction) {
			gc.rotate(direction);
			gc.drawImage(creep, -25, -25);
			gc.rotate(-direction);
			direction -= rotateSpeed;
		}else {
			isRotate = false;
			gc.rotate(direction);
			gc.drawImage(creep, -25, -25);
			gc.rotate(-direction);
		}
		gc.translate(-x-25, -y-25);
		
	}
	
	@Override
	protected Shape getBound() {
		return new Circle(cX, cY, dimension/2);
	}
	
	public void getHit(OrbitEntity o) {
		if(!collideWith.contains(o)) {
			this.hp = this.hp - (this.armor > o.damage ? 0 : o.damage-this.armor);
		}
	}
	
	public int getZ() {
		return 10;
	}

	@Override
	public List creepData() {
		List<String> list = new ArrayList<String>();
		list.add(this.name);
		list.add("hp : " + Double.toString(hp));
		list.add("speed : " + this.speed);
		list.add("armor : " + this.armor);
		list.add("rotate speed : " + this.rotateSpeed);
		return list;
	}
	
}
