package logic;

import javafx.scene.canvas.GraphicsContext;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class Field implements IRenderable {
	private boolean destroyed;
	private int fieldNumber;
	public static final int SIZE = 50;
	private int[][][] fieldMap;
	private double deg;
	
	public Field() {
		this.deg = 0;
		this.destroyed = false;
		this.fieldNumber = 0;
		this.fieldMap = new int[][][]{{
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
		for (int x = 0; x <  fieldMap[fieldNumber][0].length; x++) {
			for (int y = 0; y <  fieldMap[fieldNumber].length; y++) {
				gc.drawImage(RenderableHolder.grass, x*SIZE, y*SIZE);
				if( fieldMap[fieldNumber][y][x] == -8 ||  fieldMap[fieldNumber][y][x] == -9) {
					gc.drawImage(RenderableHolder.path, x*SIZE, y*SIZE);
					gc.translate(x*SIZE + 25, y*SIZE + 25);
					gc.rotate(deg);
					gc.drawImage(RenderableHolder.portal, -25, -25);
					gc.rotate(-deg);
					gc.translate(-(x*SIZE + 25), -(y*SIZE + 25));
					deg += 1.5;
				}else if( fieldMap[fieldNumber][y][x] == 0) {
					gc.drawImage(RenderableHolder.path, x*SIZE, y*SIZE);
				}else if( fieldMap[fieldNumber][y][x] == -3) {
					gc.drawImage(RenderableHolder.rock, x*SIZE, y*SIZE);
				}else if( fieldMap[fieldNumber][y][x] == 1) {
					gc.drawImage(RenderableHolder.tree, x*SIZE, y*SIZE);
				}else if( fieldMap[fieldNumber][y][x] == 4) {
					
				}
			}
		}
	}
	public int getZ() {
		return -9999;
	}
	
	public void tick() {
		
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public boolean isVisible() {
		return true;
	}
	
	public int getFieldNumber() {
		return fieldNumber;
	}
	
	public void setFieldNumber(int fIELD_NUMBER) {
		fieldNumber = fIELD_NUMBER;
	}
	
	public int[][][] getFieldMap() {
		return fieldMap;
	}
	
	
}
