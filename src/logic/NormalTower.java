package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sharedObject.RenderableHolder;

public class NormalTower extends Tower{
	private List<OrbitEntity> orbitEntities;
	public static final int price = 10;
	
	public NormalTower(int x, int y,GameLogic gameLogic, double speed, int amount, int attackLevel) {
		super(x,y,gameLogic,speed,amount,attackLevel);
		this.orbitEntities = new ArrayList<>();
		
		addOrbitEntities();
		setAttackLevel(attackLevel);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(RenderableHolder.normalTower, x, y);
	}
	
	public void addOrbitEntities() {
		NormalOrbitEntity o;
		if(amount == 1) {
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,0);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
		}else if(amount == 2) {
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,0);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,180);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
		}else if(amount == 3) {
			
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,0);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
			
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,120);
			gameLogic.addNewObject(o);
			orbitEntities.add(o);
			
			o = new NormalOrbitEntity((int)x+Field.SIZE/2,(int) y+Field.SIZE/2,60,240);
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

	public void setAttackLevel(int attackLevel) {
		for(OrbitEntity orbitEntity : orbitEntities) {
			if(attackLevel == 1) {
				orbitEntity.setDamage(10);
			}else if(attackLevel == 2) {
				orbitEntity.setDamage(20);
			}else if(attackLevel == 3) {
				orbitEntity.setDamage(30);
			}
		}
		this.attackLevel = attackLevel;
	}
	
	public List towerData(){
		List<String> list = new ArrayList<String>();
		list.add("ELECTRIC BALL TOWER");
		list.add("speed level : 1");
		list.add("orbitentities : " + Integer.toString(amount));
		list.add("damage : " + Integer.toString(orbitEntities.get(0).damage));
		return list;
	}
	
	public int getZ() {
		return 5;
	}
	
	public int getUpgradeAmountPrice(int upgradelevel) {
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
	
	public int getUpgradeAttackPrice(int upgradelevel) {
		int price = 0;
		switch (upgradelevel) {
		case 1:
			price = 25;
			break;

		case 2:
			price = 50;
			break;
		}
		return price;
	}

}
