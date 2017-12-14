package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import sharedObject.RenderableHolder;

public class IceTower extends Tower{
	public static final int price = 15;
	public static final int MAX_SLOW_LEVEL = 3;
	private List<SlowOrbitEntity> orbitEntities;
	private int slowLevel;
	
	public IceTower(int x, int y,GameLogic gameLogic, double speed, int amount, int attackLevel,int slowLevel) {
		super(x,y,gameLogic,speed,amount,attackLevel);
		this.orbitEntities = new ArrayList<>();
		addOrbitEntities();
		setAttackLevel(attackLevel);
		this.slowLevel = slowLevel;
		this.speed = speed;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(RenderableHolder.iceTower, x, y);
	}
	
	public void addOrbitEntities() {
		SlowOrbitEntity o;
		if(amount == 1) {
			o = new SlowOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,0,this.speed);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
		}
	}
	
	public void destroyTower() {
		for(OrbitEntity orbitEntity : orbitEntities) {
			orbitEntity.destroyed = true;
		}
		this.destroyed = true;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAttackLevel() {
		return attackLevel;
	}
	
	public int getSlowLevel() {
		return slowLevel;
	}

	public void setSlowLevel(int slowLevel) {
		for(SlowOrbitEntity orbitEntity : orbitEntities) {
			if(slowLevel == 1) {
				orbitEntity.setSlow(30);
			}else if(slowLevel == 2) {
				orbitEntity.setSlow(45);
			}else if(slowLevel == 3) {
				orbitEntity.setSlow(60);
			}
		}
		this.slowLevel = slowLevel;
	}

	public void setAttackLevel(int attackLevel) {
		for(OrbitEntity orbitEntity : orbitEntities) {
			if(attackLevel == 1) {
				orbitEntity.setDamage(5);
			}else if(attackLevel == 2) {
				orbitEntity.setDamage(10);
				orbitEntity.setSpeed(5);
				this.speed = 5;
			}else if(attackLevel == 3) {
				orbitEntity.setDamage(20);
				this.speed = 9;
				orbitEntity.setSpeed(9);
			}
		}
		this.attackLevel = attackLevel;
	}
	
	public List towerData(){
		List<String> list = new ArrayList<String>();
		list.add("ICE TOWER");
		list.add("orbitentities : " + Integer.toString(amount));
		list.add("damage : " + Integer.toString(orbitEntities.get(0).damage));
		list.add("speed level : " + this.attackLevel);
		list.add("slow : " + Integer.toString((int)orbitEntities.get(0).getSlow()) + " %");
		return list;
	}
	
	public int getUpgradeSlowPrice(int upgradelevel) {
		int price = 0;
		switch (upgradelevel) {
		case 1:
			price = 40;
			break;

		case 2:
			price = 60;
			break;
		}
		return price;
	}
	
	public int getUpgradeAttackPrice(int upgradelevel) {
		int price = 0;
		switch (upgradelevel) {
		case 1:
			price = 30;
			break;

		case 2:
			price = 60;
			break;
		}
		return price;
	}

}
