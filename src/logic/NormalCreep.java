package logic;

import java.util.ArrayList;
import java.util.List;

import input.AudioUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sharedObject.RenderableHolder;

public class NormalCreep extends Creep{
	
	private  List<OrbitEntity> overrideWith;
	private int path;
	private int dimension;
	private int direction;
	private int rotate;
	private boolean isRotate;
	private boolean isSlow;
	private long lastHitSlow;
	private double slowBy;
	private Image creep;
	
	public NormalCreep(String name, double x,double y, double speed, double rotateSpeed, double hp, double armor, int price,Spawn spawn,GameLogic gameLogic, String image) {
		super(name, x, y, speed, rotateSpeed, hp, armor, price, spawn, gameLogic);
		overrideWith = new ArrayList<>();
		this.path = 0;
		this.dimension = 30;
		this.direction = 0;
		this.rotate = this.direction;
		this.isRotate = false;
		this.isSlow = false;
		this.lastHitSlow = 0;
		this.slowBy = 1;
		this.z = 10;
		setImage(image);
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
		//move
		if(distance > 0 && !isRotate && path < spawn.getPath().size()) {
			if(spawn.getPath().get(path) == 1) {
				moveUp();
			}else if(spawn.getPath().get(path) == 2) {
				moveRight();
			}else if(spawn.getPath().get(path) == 3) {
				moveDown();
			}else if(spawn.getPath().get(path) == 4) {
				moveLeft();
			}else if(spawn.getPath().get(path) == -1) {
				isRotate = true;
				rotate += 90;
				path++;
			}else if(spawn.getPath().get(path) == -2) {
				isRotate = true;
				rotate -= 90;
				path++;
			}
			if(distance <= 0) {
				path++;
				distance = Field.SIZE;
			}
		}
		//check get attack
		for(Entity x : gameLogic.getGameObjectContainer()) {
			if(x instanceof OrbitEntity) {
				OrbitEntity o = (OrbitEntity)x;
				if(overrideWith(o)){
					getHit(o);
					overrideWith.add(o);
				}else {
					overrideWith.remove((OrbitEntity)x);
				}
				
			}
			
			if(x instanceof SlowOrbitEntity) {
				if(overrideWith((SlowOrbitEntity) x)){
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
	
	public void setImage(String image) {
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
	protected Shape getBound() {
		return new Circle(cX, cY, dimension/2);
	}
	
	public void getHit(OrbitEntity o) {
		if(!overrideWith.contains(o)) {
			this.hp = this.hp - (this.armor > o.damage ? 0 : o.damage-this.armor);
		}
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
