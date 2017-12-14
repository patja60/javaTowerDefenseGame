package logic;


import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class FieldOption implements IRenderable{

	private GameLogic gameLogic;
	private Tower[][] tower;
	private int fieldOnX;
	private int fieldOnY;
	
	private boolean buildMode;
	private String build;
	private String laserTower;
	
	private int upgradX;
	private int upgradY;
	
	private int circleOptionDimension;
	
	public FieldOption(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.tower = new Tower[10][16];
		this.buildMode = false;
		this.build = "";
		this.laserTower = "";
		this.fieldOnX = -1;
		this.fieldOnY = -1;
		this.upgradX = -1;
		this.upgradY = -1;
		this.circleOptionDimension = 50;
	}
	
	@Override
	public void tick() {
		try {
			if(InputUtility.mouseOnGameScreen) {
				if(InputUtility.isRightClickRelease()){
					upgradX = -1;
					upgradY = -1;
					}
				if(getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 2 || getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 3
						|| getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 4 || getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 5) {
					if(!buildMode) {
						fieldOnX = (int)(InputUtility.mouseX/Field.SIZE);
						fieldOnY = (int)(InputUtility.mouseY/Field.SIZE);
						if(InputUtility.isLeftClickRelease()&& gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] == 2) {
							buildMode = true;
							build = "tower";
							showUpgrade(fieldOnX, fieldOnY);
						}else if(InputUtility.isLeftClickRelease() && gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] == 3) {
							NormalTower o = (NormalTower)tower[fieldOnY][fieldOnX];
							buildMode = true;
							build = "normalTower";
							showUpgrade(fieldOnX, fieldOnY);
						}else if(InputUtility.isLeftClickRelease() && gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] == 4) {
							IceTower o = (IceTower)tower[fieldOnY][fieldOnX];
							buildMode = true;
							build = "iceTower";
							showUpgrade(fieldOnX, fieldOnY);
						}else if(InputUtility.isLeftClickRelease() && gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] == 5) {
							LaserTower o = (LaserTower)tower[fieldOnY][fieldOnX];
							buildMode = true;
							build = "laserTower";
							showUpgrade(fieldOnX, fieldOnY);
						}
					}
				}else {
					if(!buildMode) {
						fieldOnX = -1;
						fieldOnY = -1;
					}
				}
				
				if(buildMode) {
					if(InputUtility.isRightClickRelease()) {
						buildMode = false;
						fieldOnX = -1;
						fieldOnY = -1;
					}
					if(InputUtility.isLeftClickRelease()) {
						switch(build) {
						case "tower" :
							if(clickCircle(upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2, upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2)) {
								if(gameLogic.getMoney() >= NormalTower.price) {
									NormalTower o = new NormalTower(upgradX*Field.SIZE, upgradY*Field.SIZE, gameLogic, 3, 1, 1);
									tower[fieldOnY][fieldOnX] = o;
									gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] = 3;
									gameLogic.addNewObject(o);
									gameLogic.setMoney(gameLogic.getMoney() - NormalTower.price);
									buildMode = false;
									build = "";
								}else {
									System.out.println("not enough money");
								}
							}else if(clickCircle(upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2, upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2)) {
								if(gameLogic.getMoney() >= IceTower.price) {
									IceTower o = new IceTower(upgradX*Field.SIZE, upgradY*Field.SIZE, gameLogic, 3, 1, 1, 1);
									tower[fieldOnY][fieldOnX] = o;
									gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] = 4;
									gameLogic.addNewObject(o);
									gameLogic.setMoney(gameLogic.getMoney() - IceTower.price);
									buildMode = false;
									build = "";
								}else {
									
								}
							}else if(clickCircle(upgradX*Field.SIZE - Field.SIZE*3/2 + circleOptionDimension/2, upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2)) {
								if(gameLogic.getMoney() >= LaserTower.price) {
									buildMode = true;
									build = "buildLaserTower";
								}else {
									
								}
							}
							break;
						case "normalTower":
							if(clickCircle(upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2, upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2)) {
								NormalTower o = (NormalTower)tower[fieldOnY][fieldOnX];
								if(gameLogic.getMoney()>=o.getUpgradeAmountPrice(o.getAmount())) {
									if(o.getAmount() < NormalTower.MAX_AMOUNT) {
										gameLogic.setMoney(gameLogic.getMoney() - o.getUpgradeAmountPrice(o.getAmount()));
										int amount = o.getAmount() + 1;
										int attackLevel = o.getAttackLevel();

										o.destroyTower();
										o = new NormalTower(upgradX*Field.SIZE, upgradY*Field.SIZE, gameLogic, 3, amount, attackLevel);
										tower[fieldOnY][fieldOnX] = o;
										gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] = 3;
										gameLogic.addNewObject(o);
										buildMode = false;
										build = "";
								}else {
									
								}
								}
							}else if(clickCircle(upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2, upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2)) {
								NormalTower o = (NormalTower)tower[fieldOnY][fieldOnX];
								if(gameLogic.getMoney()>=o.getUpgradeAttackPrice(o.getAttackLevel())) {
									if(o.getAttackLevel() < NormalTower.MAX_ATTACK_LEVEL) {
										gameLogic.setMoney(gameLogic.getMoney() - o.getUpgradeAttackPrice(o.getAttackLevel()));
										int attackLevel = o.getAttackLevel() + 1;
										o.setAttackLevel(attackLevel);
										buildMode = false;
										build = "";
										}
								}else {
									
								}
							}
							break;
						case "iceTower":
							if(clickCircle(upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2, upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2)) {
								IceTower o = (IceTower)tower[fieldOnY][fieldOnX];
								if(gameLogic.getMoney()>=o.getUpgradeSlowPrice(o.getSlowLevel())) {
									if(o.getSlowLevel() < IceTower.MAX_SLOW_LEVEL) {
										gameLogic.setMoney(gameLogic.getMoney() - o.getUpgradeSlowPrice(o.getSlowLevel()));
										int slow = o.getSlowLevel() + 1;
										o.setSlowLevel(slow);
										buildMode = false;
										build = "";
									}
								}else {
									
								}
							}else if(clickCircle(upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2, upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2)) {
								IceTower o = (IceTower)tower[fieldOnY][fieldOnX];
								if(gameLogic.getMoney()>=o.getUpgradeAttackPrice(o.getAttackLevel())) {
									if(o.getAttackLevel() < IceTower.MAX_ATTACK_LEVEL) {
										gameLogic.setMoney(gameLogic.getMoney() - o.getUpgradeAttackPrice(o.getAttackLevel()));
										int attackLevel = o.getAttackLevel() + 1;
										o.setAttackLevel(attackLevel);
										buildMode = false;
										build = "";
									}
								}else {
									
								}
							}
							break;
						case "laserTower":
							if(clickCircle(upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2, upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2)) {
								LaserTower o = (LaserTower)tower[fieldOnY][fieldOnX];
								if(gameLogic.getMoney()>=o.getUpgradeAttackPrice(o.getAttackLevel())) {
									if(o.getAttackLevel() < LaserTower.MAX_ATTACK_LEVEL) {
										gameLogic.setMoney(gameLogic.getMoney() - o.getUpgradeAttackPrice(o.getAttackLevel()));
										int attackLevel = o.getAttackLevel() + 1;
										o.setAttackLevel(attackLevel);
										buildMode = false;
										build = "";
									}
								}else {
									
								}
							}
							break;
						case "buildLaserTower" :
							LaserTower o = null;
							if(laserTower == "l") {
								o = new LaserTower(upgradX*Field.SIZE, upgradY*Field.SIZE, gameLogic, 3, 1, 1, "l");
							}else if(laserTower == "r") {
								o = new LaserTower(upgradX*Field.SIZE, upgradY*Field.SIZE, gameLogic, 3, 1, 1, "r");
							}
							if(laserTower != ""){
								tower[fieldOnY][fieldOnX] = o;
								gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][fieldOnY][fieldOnX] = 5;
								gameLogic.setMoney(gameLogic.getMoney() - LaserTower.price);
								gameLogic.addNewObject(o);
								buildMode = false;
								build = "";
								laserTower = "";
							}
							break;
						}
					}
					if(build == "buildLaserTower") {
						if(InputUtility.mouseX<upgradX*Field.SIZE+25) {
							laserTower = "l";
						}else if(InputUtility.mouseX>=(upgradX+1)*Field.SIZE-25) {
							laserTower = "r";
						}else {
							laserTower = "";
						}
					}
				}
			}else {
				if(!buildMode) {
					fieldOnX = -1;
					fieldOnY = -1;
				}
			}
			
			InputUtility.updateInputState();
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			build = "";
			buildMode = false;
		}
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 20);
		gc.setFont(theFont);
		
		if((getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 2 || 
				getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 3 ||
				getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 4 ||
				getFieldType(InputUtility.mouseX, InputUtility.mouseY) == 5) && !buildMode) {
			gc.setStroke(Color.WHITE);
			gc.strokeRect(fieldOnX*Field.SIZE, fieldOnY*Field.SIZE, Field.SIZE, Field.SIZE);
		}
		if(buildMode) {
			switch(build) {
			case "tower":
				gc.setStroke(Color.ORANGE);
				gc.strokeRect(upgradX*Field.SIZE, upgradY*Field.SIZE, Field.SIZE, Field.SIZE);
				
				gc.drawImage(RenderableHolder.normalTowerIcon, upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2 , upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2);
				drawPrice(Integer.toString(NormalTower.price), gc, upgradX, upgradY);
				
				gc.drawImage(RenderableHolder.iceTowerIcon, upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2, upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2);	
				drawPrice(Integer.toString(IceTower.price), gc, (upgradX+1), (upgradY+1));
				
				gc.drawImage(RenderableHolder.laserTowerIcon, upgradX*Field.SIZE - Field.SIZE*3/2 + circleOptionDimension/2 , upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2);
				drawPrice(Integer.toString(LaserTower.price), gc, (upgradX-1), (upgradY+1));
				break;
			case "normalTower":
				NormalTower o1 = (NormalTower)tower[fieldOnY][fieldOnX];
				gc.setStroke(Color.ORANGE);
				gc.strokeRect(upgradX*Field.SIZE, upgradY*Field.SIZE, Field.SIZE, Field.SIZE);
				gc.drawImage(RenderableHolder.normalUpgradeAmountIcon, upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2 , upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2 );
				if(o1.getAmount()>=NormalTower.MAX_AMOUNT) {
					drawPrice("MAX", gc, upgradX, upgradY);
				}else {
					drawPrice(Integer.toString(o1.getUpgradeAmountPrice(o1.getAmount())), gc, upgradX, upgradY);
				}
				
				gc.drawImage(RenderableHolder.normalUpgradeAttackIcon, upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2 , upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2);
				if(o1.getAttackLevel()>=NormalTower.MAX_ATTACK_LEVEL) {
					drawPrice("MAX", gc, upgradX+1, upgradY+1);
				}else {
					drawPrice(Integer.toString(o1.getUpgradeAttackPrice(o1.getAttackLevel())), gc, upgradX+1, upgradY+1);
				}
				break;
			case "iceTower":
				IceTower o2 = (IceTower)tower[fieldOnY][fieldOnX];
				gc.setStroke(Color.ORANGE);
				gc.strokeRect(upgradX*Field.SIZE, upgradY*Field.SIZE, Field.SIZE, Field.SIZE);
				gc.drawImage(RenderableHolder.iceUpgradeSlowIcon, upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2 , upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2 );
				if(o2.getSlowLevel()>=IceTower.MAX_SLOW_LEVEL) {
					drawPrice("MAX", gc, upgradX, upgradY);
				}else {
					drawPrice(Integer.toString(o2.getUpgradeSlowPrice(o2.getSlowLevel())), gc, upgradX, upgradY);
				}
				
				gc.drawImage(RenderableHolder.iceUpgradeAttackIcon, upgradX*Field.SIZE + Field.SIZE*3/2 - circleOptionDimension/2 , upgradY*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2);
				if(o2.getAttackLevel()>=IceTower.MAX_ATTACK_LEVEL) {
					drawPrice("MAX", gc, upgradX+1, upgradY+1);
				}else {
					drawPrice(Integer.toString(o2.getUpgradeAttackPrice(o2.getAttackLevel())), gc, upgradX+1, upgradY+1);
				}
				break;
			case "laserTower":
				LaserTower o3 = (LaserTower)tower[fieldOnY][fieldOnX];
				gc.setStroke(Color.ORANGE);
				gc.strokeRect(upgradX*Field.SIZE, upgradY*Field.SIZE, Field.SIZE, Field.SIZE);
				gc.drawImage(RenderableHolder.laserUpgradeIcon, upgradX*Field.SIZE + Field.SIZE/2 - circleOptionDimension/2 , upgradY*Field.SIZE - Field.SIZE/2 - circleOptionDimension/2 );
				if(o3.getAttackLevel()>=LaserTower.MAX_ATTACK_LEVEL) {
					drawPrice("MAX", gc, upgradX, upgradY);
				}else {
					drawPrice(Integer.toString(o3.getUpgradeAttackPrice(o3.getAttackLevel())), gc, upgradX, upgradY);
				}
				break;
			case "buildLaserTower":
				gc.setStroke(Color.ORANGE);
				gc.strokeRect(upgradX*Field.SIZE, upgradY*Field.SIZE, Field.SIZE, Field.SIZE);
				if(laserTower == "l") {
					gc.drawImage(RenderableHolder.laserTowerL, upgradX*Field.SIZE, upgradY*Field.SIZE);
				}else if(laserTower == "r") {
					gc.drawImage(RenderableHolder.laserTowerR, upgradX*Field.SIZE, upgradY*Field.SIZE);
				}
				break;
			}
		}
	}
	
	public boolean clickCircle(double Cx, double Cy) {
		return Math.abs(InputUtility.mouseX-(Cx+circleOptionDimension/2)) < circleOptionDimension/2 && Math.abs(InputUtility.mouseY-(Cy+circleOptionDimension/2)) < circleOptionDimension/2;
	}
	
	public void showUpgrade(int x, int y) {
		upgradX = x;
		upgradY = y;
	}
	
	public int getFieldType(double x, double y) {
		try {
			return gameLogic.getField().getFieldMap()[gameLogic.getField().getFieldNumber()][(int)(y/Field.SIZE)][(int)(x/Field.SIZE)];
		}catch(Exception e) {
			return 1;
		}
	}
	
	public Tower getTowerClick() {
		try {
			return tower[upgradY][upgradX];
		}catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void destroyField() {
		for (int x = 0; x <  10; x++) {
			for (int y = 0; y <  16; y++) {
				Tower o = (Tower) tower[x][y];
				try {
					o.destroyTower();
					tower[x][y] = null;
				}catch (NullPointerException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public void drawPrice(String price, GraphicsContext gc,int x, int y) {
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 20);
		Font theFont2 = Font.font("Times New Roman", FontWeight.LIGHT, 15);
		if(price == "MAX") {
			Font theFont3 = Font.font("Times New Roman", FontWeight.LIGHT, 10);
			gc.setFont(theFont3);
			gc.setFill(Color.ORANGE);
			gc.fillOval(x*Field.SIZE+32, y*Field.SIZE - 20, 25, 25);
			gc.setFill(Color.WHITE);
			gc.fillText(price, x*Field.SIZE+34, y*Field.SIZE-4);
			gc.setFont(theFont);
			gc.setFont(theFont);
			return;
		}
		if(price.length()>2) {
			gc.setFont(theFont2);
		}
		gc.setFill(Color.ORANGE);
		gc.fillOval(x*Field.SIZE+32, y*Field.SIZE - 20, 25, 25);
		gc.setFill(Color.WHITE);
		gc.fillText(price, x*Field.SIZE+34, y*Field.SIZE-2);
		gc.setFont(theFont);
	}
	
	@Override
	public int getZ() {
		return 20;
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
