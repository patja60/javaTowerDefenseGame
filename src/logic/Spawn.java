package logic;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import logic.GameLogic.STATE;
import sharedObject.RenderableHolder;

public class Spawn {
	
	private GameLogic gameLogic;
	private Field field;
	private int x;
	private int y;
	private int[][] time = 	
		{	{50,50,50,50},
			{50,50,50,50},
			{50,50,50,50}	};
	/*private int[][] time = 	
		{	{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0}	};*/
	private double[][] freq = 
		{	{2,3,2,7},
			{1,2,3,2},
			{2,1,2,2}	};
	private int[] money = {50,140,400};
	private int waveTime;
	private boolean isWaveStart;
	private boolean isStageClear;
	private int count = 5;
	private boolean draw;
	private boolean firstWave;
	private double currentTime;
	private double delayEnd;
	private Creep currentCreep;
	
	public Spawn(GameLogic gameLogic,Field field){
		this.gameLogic = gameLogic;
		this.field = field;
		this.currentTime = -1;
		this.isWaveStart = false;
		this.firstWave = true;
		this.delayEnd = -1;
		this.isStageClear = false;
	}
	
	public void tick(){
		if(isWaveStart) {
			gameLogic.setTime((int)(waveTime-gameLogic.getNow()/1000000000));
		}else {
			try {
				gameLogic.setTime(time[field.getFIELD_NUMBER()][gameLogic.getWave()-1]);
			}catch(ArrayIndexOutOfBoundsException e) {		
			}
		}
		x = findStart().getKey();
		y = findStart().getValue();
		
		if(gameLogic.gameState == STATE.Game) {
			//assign current time
			if(currentTime<0) {
				currentTime = gameLogic.getNow();
			}
			if(!isWaveStart) {
				// do set up and count part
				if(firstWave) {
					// if it wave 1 set money and stage
					gameLogic.setMoney(money[field.getFIELD_NUMBER()]);
					gameLogic.setStage(field.getFIELD_NUMBER());
					firstWave = false;
					//currentTime = gameLogic.getNow();
				}else {
					
					// set current creep
					switch (field.getFIELD_NUMBER()) {
					case 0:
						switch(gameLogic.getWave()) {
						
						//don't forget current creep = new creep
						
						case 1 :
							currentCreep = new NormalCreep("Deacoon : war front type", x*this.field.SIZE, y*this.field.SIZE, 3, 3, 50, 0, 4, this, gameLogic, "creep1");
							break;
						case 2 :
							currentCreep = new NormalCreep("Morph : speed type", x*this.field.SIZE, y*this.field.SIZE, 5, 4.5, 70, 0, 7, this, gameLogic, "creep3");
							break;
							
						case 3 :
							currentCreep = new NormalCreep("Theontic  : tough skin type", x*this.field.SIZE, y*this.field.SIZE, 3, 3, 130, 7, 10, this, gameLogic, "creep2");
							break;
							
						case 4 :
							currentCreep = new NormalCreep("E23-6 : Fast Robot", x*this.field.SIZE, y*this.field.SIZE, 10, 4.5, 200, 10, 7, this, gameLogic, "creep4");
							break;
						}
						break;

					case 1:
						switch(gameLogic.getWave()) {
						
						//don't forget current creep = new creep
						
						case 1 :
							currentCreep = new NormalCreep("Deacoon : war front type",x*this.field.SIZE, y*this.field.SIZE, 3, 3, 100, 0, 3, this, gameLogic, "creep1");
							break;
						case 2 :
							currentCreep = new NormalCreep("E24-0 : armor robot",x*this.field.SIZE, y*this.field.SIZE, 4, 9, 100, 15, 15, this, gameLogic, "creep4");
							break;
							
						case 3 :
							currentCreep = new NormalCreep("Luna-44 : killer aircraft", x*this.field.SIZE, y*this.field.SIZE, 6, 15, 150, 10, 20, this, gameLogic, "aircraft1");
							break;
							
						case 4 :
							currentCreep = new NormalCreep("Andromeda-47 : speed aircraft",x*this.field.SIZE, y*this.field.SIZE, 10, 30, 100, 5, 20, this, gameLogic, "aircraft2");
							break;
						}
						break;
						
					case 2:
						switch(gameLogic.getWave()) {
						
						//don't forget current creep = new creep
						
						case 1 :
							currentCreep = new NormalCreep("E24-0 : armor robot",x*this.field.SIZE, y*this.field.SIZE, 6, 15, 250, 20, 15, this, gameLogic, "creep4");
							break;
						case 2 :
							currentCreep = new NormalCreep("Morph : speed type",x*this.field.SIZE, y*this.field.SIZE, 15, 30, 150, 10, 8, this, gameLogic, "creep3");
							break;
							
						case 3 :
							currentCreep = new NormalCreep("Quasar-C : mega aircraft", x*this.field.SIZE, y*this.field.SIZE, 20, 15, 300, 35, 40, this, gameLogic, "aircraft3");
							break;
							
						case 4 :
							currentCreep = new NormalCreep("Supernova-E12 : ultimate aircraft", x*this.field.SIZE, y*this.field.SIZE, 15, 15, 500, 40, 60, this, gameLogic, "aircraft4");
							break;
						}
						break;
					}
					
					// check if it is the last wave do end part, else count 3-2-1 for next wave
					if(gameLogic.getWave()-1>=time[field.getFIELD_NUMBER()].length) {
						// end part
						if(delayEnd<0) {
							delayEnd = gameLogic.getNow();
						}
						if((gameLogic.getNow()-delayEnd)/1000000000>7) {
							isStageClear = true;
						}
						if((gameLogic.getNow()-delayEnd)/1000000000>10) {
							int nextStage = field.getFIELD_NUMBER();
							System.out.println(nextStage+1);
							if(nextStage+1>=time.length) {
								System.out.println("gameend");
								// check if it is the last stage then go to main menu
								gameLogic.setReset(true);
								gameLogic.gameState = STATE.GameEnd;
								return;
							}
							field.setFIELD_NUMBER(nextStage+1);
							gameLogic.getFieldOption().destroyField();
							gameLogic.setWave(1);
							RenderableHolder.state = "loading";
							gameLogic.setLoadStage(nextStage+2);
							firstWave = true;
							delayEnd = -1;
							isStageClear = false;
						}
					}else {
						// count 3-2-1
						if((gameLogic.getNow()-currentTime)/1000000000>1) {
							draw = true;
							count--;
							if(count<=0) {
								waveTime = (int)(gameLogic.getNow()/1000000000+time[field.getFIELD_NUMBER()][gameLogic.getWave()-1]);
								isWaveStart = true;
								draw = false;
								count = 5;
							}
							currentTime = gameLogic.getNow();
						}
					}	
				}
			}else {
				//check if it in wave time send creep part, else set waveStart to false then do end part
				if(waveTime>=gameLogic.getNow()/1000000000) {
					if((gameLogic.getNow()-currentTime)/1000000000>freq[field.getFIELD_NUMBER()][gameLogic.getWave()-1]) {
						
						switch (field.getFIELD_NUMBER()) {
						case 0:
							switch(gameLogic.getWave()) {
							
							//don't forget current creep = new creep
							
							case 1 :
								gameLogic.addNewObject(new NormalCreep("Deacoon : war front type",x*this.field.SIZE, y*this.field.SIZE, 3, 3, 50, 0, 4, this, gameLogic, "creep1"));
								currentTime = gameLogic.getNow();
								break;
							case 2 :
								gameLogic.addNewObject(new NormalCreep("Morph : speed type",x*this.field.SIZE, y*this.field.SIZE, 5, 4.5, 70, 0, 7, this, gameLogic, "creep3"));
								currentTime = gameLogic.getNow();
								break;
								
							case 3 :
								gameLogic.addNewObject(new NormalCreep("Theontic  : tough skin type",x*this.field.SIZE, y*this.field.SIZE, 3, 3, 130, 7, 10, this, gameLogic, "creep2"));
								currentTime = gameLogic.getNow();
								break;
								
							case 4 :
								gameLogic.addNewObject(new NormalCreep("E23-6 : Fast Robot",x*this.field.SIZE, y*this.field.SIZE, 10, 4.5, 200, 10, 7, this, gameLogic, "creep4"));
								currentTime = gameLogic.getNow();
								break;
							}
							break;

						case 1:
							switch(gameLogic.getWave()) {
							
							//don't forget current creep = new creep
							
							case 1 :
								gameLogic.addNewObject(new NormalCreep("Deacoon : war front type",x*this.field.SIZE, y*this.field.SIZE, 3, 3, 100, 0, 3, this, gameLogic, "creep1"));
								currentTime = gameLogic.getNow();
								break;
							case 2 :
								gameLogic.addNewObject(new NormalCreep("E24-0 : armor robot",x*this.field.SIZE, y*this.field.SIZE, 4, 9, 100, 15, 13, this, gameLogic, "creep4"));
								currentTime = gameLogic.getNow();
								break;
								
							case 3 :
								gameLogic.addNewObject(new NormalCreep("Luna-44 : killer aircraft", x*this.field.SIZE, y*this.field.SIZE, 6, 15, 250, 10, 15, this, gameLogic, "aircraft1"));
								currentTime = gameLogic.getNow();
								break;
								
							case 4 :
								gameLogic.addNewObject(new NormalCreep("Andromeda-47 : speed aircraft",x*this.field.SIZE, y*this.field.SIZE, 10, 30, 300, 10, 20, this, gameLogic, "aircraft2"));
								currentTime = gameLogic.getNow();
								break;
							}
							break;
							
						case 2:
							switch(gameLogic.getWave()) {
							
							//don't forget current creep = new creep
							
							case 1 :
								gameLogic.addNewObject(new NormalCreep("E24-0 : armor robot",x*this.field.SIZE, y*this.field.SIZE, 6, 15, 250, 20, 15, this, gameLogic, "creep4"));
								currentTime = gameLogic.getNow();
								break;
							case 2 :
								gameLogic.addNewObject(new NormalCreep("Morph : speed type",x*this.field.SIZE, y*this.field.SIZE, 15, 30, 150, 10, 8, this, gameLogic, "creep3"));
								currentTime = gameLogic.getNow();
								break;
								
							case 3 :
								gameLogic.addNewObject(new NormalCreep("Quasar-C : mega aircraft", x*this.field.SIZE, y*this.field.SIZE, 20, 15, 300, 35, 40, this, gameLogic, "aircraft3"));
								currentTime = gameLogic.getNow();
								break;
								
							case 4 :
								gameLogic.addNewObject(new NormalCreep("Supernova-E12 : ultimate aircraft", x*this.field.SIZE, y*this.field.SIZE, 15, 15, 500, 40, 60, this, gameLogic, "aircraft4"));
								currentTime = gameLogic.getNow();
								break;
							}
							break;
						}
					}
				}else {
					isWaveStart = false;
					gameLogic.setWave(gameLogic.getWave()+1);
				}
			}
		}
	}
	
	public void draw(GraphicsContext gc){
		if(draw) {
			if(gameLogic.getWave() == 1) {
				gc.drawImage(RenderableHolder.gameStart, 400-225, 250-150);
			}
			switch (count) {
			case 1:
				gc.drawImage(RenderableHolder.one, 400-75, 250-75);
				break;
			case 2:
				gc.drawImage(RenderableHolder.two, 400-75, 250-75);
				break;
			case 3:
				gc.drawImage(RenderableHolder.three, 400-75, 250-75);
				break;

			}
		}
		if(isStageClear) {
			gc.drawImage(RenderableHolder.stageClear, 400-175, 250-150);
		}
	}

	private Pair<Integer,Integer> findStart() {
		Pair<Integer, Integer> o = new Pair<Integer, Integer>(-1,-1);
		for(int x = 0 ; x < field.getFIELD()[field.getFIELD_NUMBER()][0].length ; x++) {
			for (int y = 0; y < field.getFIELD()[field.getFIELD_NUMBER()].length; y++) {
				if(field.getFIELD()[field.getFIELD_NUMBER()][y][x] == -8) {
					o = new Pair<Integer, Integer>(x,y);
					return o;
				}
			}
		}
		return o;
	}

	public Pair<Integer,Integer> findEnd() {
		Pair<Integer, Integer> o = new Pair<Integer, Integer>(-1,-1);
		for(int x = 0 ; x < field.getFIELD()[field.getFIELD_NUMBER()][0].length ; x++) {
			for (int y = 0; y < field.getFIELD()[field.getFIELD_NUMBER()].length; y++) {
				if(field.getFIELD()[field.getFIELD_NUMBER()][y][x] == -9) {
					o = new Pair<Integer, Integer>(x,y);
					return o;
				}
			}
		}
		return o;
	}
	
	public List<Integer> getPath() {
		List<Integer> list = getPath(field.getFIELD_NUMBER(), x, y, null);
		for(int i = 0 ; i < list.size()-1 ; i++) {
			if(list.get(i)-list.get(i+1)==-1  || list.get(i)-list.get(i+1)==3)  {
				list.add(i+1, -1); // turn right
				i++;
			}else if(list.get(i)-list.get(i+1)==1 || list.get(i)-list.get(i+1)==-3 ) {
				list.add(i+1, -2); // turn left
				i++;
			}
		}
		return list;
	}
	
	public List<Integer> getPath(int fieldNumber,int x,int y,String from){
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y-1][x] == -9 ) {
				 List<Integer> out = new ArrayList<>();
				 out.add(1);
				 return out;
			}
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y][x-1] == -9) {
				 List<Integer> out = new ArrayList<>();
				 out.add(4);
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y+1][x] == -9) {
				 List<Integer> out = new ArrayList<>();
				 out.add(3);
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y][x+1] == -9) {
				 List<Integer> out = new ArrayList<>();
				 out.add(2);
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		 
		
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y-1][x] == 0 && from != "U") {
				 from = "D";
				 List<Integer> out = new ArrayList<>();
				 out.add(1);
				 out.addAll(getPath(fieldNumber,x,y-1,from));
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y][x-1] == 0 && from != "L") {
				 from = "R";
				 List<Integer> out = new ArrayList<>();
				 out.add(4);
				 out.addAll(getPath(fieldNumber,x-1,y,from));
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y+1][x] == 0 && from != "D") {
				 from = "U";
				 List<Integer> out = new ArrayList<>();
				 out.add(3);
				 out.addAll(getPath(fieldNumber,x,y+1,from));
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][y][x+1] == 0 && from != "R") {
				 from = "L";
				 List<Integer> out = new ArrayList<>();
				 out.add(2);
				 out.addAll(getPath(fieldNumber,x+1,y,from));
				 return out;
			 }
		}catch (IndexOutOfBoundsException e) {
		}
		List<Integer> path = new ArrayList<>();
		return path;
		
	}
	
	private int findPath(int x, int y) {
		try {
			if(field.getFIELD()[field.getFIELD_NUMBER()][x][y-1] == 0) return 1;
			if(field.getFIELD()[field.getFIELD_NUMBER()][x-1][y] == 0) return 2;
			if(field.getFIELD()[field.getFIELD_NUMBER()][x][y+1] == 0) return 3;
			if(field.getFIELD()[field.getFIELD_NUMBER()][x+1][y] == 0) return 4;
			
		}catch (IndexOutOfBoundsException e) {
			
		}
		return 0;
	}

	public Creep getCurrentCreep() {
		return currentCreep;
	}
	
	
}
