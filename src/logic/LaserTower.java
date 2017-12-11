package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sharedObject.RenderableHolder;

public class LaserTower extends Tower{
	public static final int price = 70;
	private String direction;
	private StraightEntity o;
	
	public LaserTower(int x, int y,GameLogic gameLogic, double speed, int amount, int attackLevel, String direction) {
		super(x,y,gameLogic,speed,amount,attackLevel);
		o = new StraightEntity(x, y, direction,gameLogic);
		gameLogic.addNewObject(o);
		setAttackLevel(attackLevel);
		this.direction = direction;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		if(direction == "r") {
			gc.drawImage(RenderableHolder.laserTowerR, x, y);
		}else if(direction == "l") {
			gc.drawImage(RenderableHolder.laserTowerL, x, y);
		}
	}
	
	public void destroyTower() {
		this.o.destroyed = true;
		this.destroyed = true;
	}

	public int getAttackLevel() {
		return attackLevel;
	}

	public void setAttackLevel(int attackLevel) {
		if(attackLevel == 1) {
			o.setDamage(0.7);
		}else if(attackLevel == 2) {
			o.setDamage(1.5);
		}
		else if(attackLevel == 3) {
			o.setDamage(3.0);
		}
		this.attackLevel = attackLevel;
	}
	
	public List towerData(){
		List<String> list = new ArrayList<String>();
		list.add("LASER TOWER");
		list.add("attack level : " + (int)this.attackLevel);
		return list;
	}
	
	public int getZ() {
		return 6;
	}
	
	public int getUpgradeAttackPrice(int upgradelevel) {
		int price = 0;
		switch (upgradelevel) {
		case 1:
			price = 150;
			break;

		case 2:
			price = 250;
			break;
		}
		return price;
	}

}
