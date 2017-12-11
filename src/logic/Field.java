package logic;

import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class Field implements IRenderable {
	private boolean destroyed;
	private int FIELD_NUMBER;
	public static final int SIZE = 50;
	private int[][][] FIELD;
	private double deg;
	public Field() {
		this.deg = 0;
		this.destroyed = false;
		this.FIELD_NUMBER = 0;
		this.FIELD = new int[][][]{{
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 2, 2, 2, 2, 2,-9, 2, 2, 2, 1, 2, 2, 2, 2, 1 },
			{ 1,-8, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1 }, 
			{ 1, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 1 },
			{ 1, 2, 1, 0, 2, 0, 0, 0,-3, 2, 2, 2, 0, 2, 1, 1 }, 
			{ 1, 1, 2, 0, 2, 0, 2, 0, 2, 2, 2, 2, 0, 2, 2, 1 }, 
			{ 1, 2, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 2, 1 },
			{ 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 2, 1, 2, 2, 1 },			
			{ 1, 2, 1, 2, 2,-3, 2, 2, 2, 2, 1, 2, 2, 1, 1, 1 },
			{-3,-3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
		},{
			{-3, 1, 1,-3,-3, 1,-3,-9,-3, 1, 1,-3,-3, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 2, 2, 0, 0, 2, 2, 1, 2, 0, 0, 0, 1 },
			{-3, 0, 2, 0, 2, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0, 1 }, 
			{ 1, 0, 2, 0, 2, 0,-3, 2, 2, 0, 0, 2, 2, 2, 0, 1 },
			{ 1, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 }, 
			{-3, 0, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 1 }, 
			{-3, 0, 2, 0,-3, 0, 0, 0, 2, 0, 0, 0, 0, 2,-3, 1 },
			{-8, 0,-3, 0, 0, 0, 2, 0, 0, 0,-3, 2, 2, 2, 1, 1 },			
			{-3,-3,-3,-3, 2, 1, 2, 2, 2, 2,-3, 2, 2, 1, 1, 1 },
			{-3,-3,-3,-3,-3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
		},{ 
			{-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3 }, 
			{-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2,-3 },
			{-3, 0,-3, 2,-3,-3, 2,-3, 2, 0, 0,-9, 2, 2, 1,-3 }, 
			{-3, 0,-3,-3,-3,-3,-3,-3, 2, 2, 2, 2, 2, 2, 2,-3 },
			{-3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2,-3 }, 
			{-3, 1, 2, 0, 2, 0, 2, 2, 2, 2,-3, 2,-0, 2, 2,-3 }, 
			{-3, 0, 0, 0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 2,-8, 0 },
			{-3, 0, 2, 2, 2, 0, 2, 2, 2, 0, 1, 1, 1, 1, 1, 0 },			
			{-3, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0 },
			{-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3 } 
		},{ 
			{-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3 }, 
			{-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2,-3 },
			{-3, 0,-3, 2,-3,-3, 2,-3, 2, 0, 0,-9, 2, 2, 1,-3 }, 
			{-3, 0,-3,-3,-3,-3,-3,-3, 2, 2, 2, 2, 2, 2, 2,-3 },
			{-3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2,-3 }, 
			{-3, 1, 2, 0, 2, 0, 2, 2, 2, 2,-3, 2,-0, 2, 2,-3 }, 
			{-3, 0, 0, 0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 2,-8, 0 },
			{-3, 0, 2, 2, 2, 0, 2, 2, 2, 0, 1, 1, 1, 1, 1, 0 },			
			{-3, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0 },
			{-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3 } 
		}};
	}
	public void draw(GraphicsContext gc) {
		for (int x = 0; x <  FIELD[FIELD_NUMBER][0].length; x++) {
			for (int y = 0; y <  FIELD[FIELD_NUMBER].length; y++) {
				gc.drawImage(RenderableHolder.grass, x*SIZE, y*SIZE);
				if( FIELD[FIELD_NUMBER][y][x] == -8 ||  FIELD[FIELD_NUMBER][y][x] == -9) {
					gc.drawImage(RenderableHolder.path, x*SIZE, y*SIZE);
					gc.translate(x*SIZE + 25, y*SIZE + 25);
					gc.rotate(deg);
					gc.drawImage(RenderableHolder.portal, -25, -25);
					gc.rotate(-deg);
					gc.translate(-(x*SIZE + 25), -(y*SIZE + 25));
					deg += 1.5;
				}else if( FIELD[FIELD_NUMBER][y][x] == 0) {
					gc.drawImage(RenderableHolder.path, x*SIZE, y*SIZE);
				}else if( FIELD[FIELD_NUMBER][y][x] == -3) {
					gc.drawImage(RenderableHolder.rock, x*SIZE, y*SIZE);
				}else if( FIELD[FIELD_NUMBER][y][x] == 1) {
					gc.drawImage(RenderableHolder.tree, x*SIZE, y*SIZE);
				}else if( FIELD[FIELD_NUMBER][y][x] == 4) {
					
				}
			}
		}
	}
	public int getZ() {
		return -9999;
	}
	
	public void tick() {
		
	}
	
	public int getField(int x, int y) {
		try {
			y -= 60;
			return FIELD[FIELD_NUMBER][(int)(y/SIZE)][(int)(x/SIZE)];
		}catch(Exception e) {
			return 1;
		}
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public boolean isVisible() {
		return true;
	}
	
	public int getFIELD_NUMBER() {
		return FIELD_NUMBER;
	}
	
	public void setFIELD_NUMBER(int fIELD_NUMBER) {
		FIELD_NUMBER = fIELD_NUMBER;
	}
	
	public int[][][] getFIELD() {
		return FIELD;
	}
	
	
}
