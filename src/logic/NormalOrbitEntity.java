package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import com.sun.javafx.image.AlphaType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sharedObject.RenderableHolder;

public class NormalOrbitEntity extends OrbitEntity{
	int deg;
	double x1;
	double y1;
	private int dimension;
	
	public NormalOrbitEntity(int x, int y, int orbitRange, int posiotion) {
		this.dimension = 20;
		this.x = x-dimension/2;
		this.y = y-dimension/2;
		this.speed = 3;
		this.orbitRange = orbitRange;
		this.locationX = this.x+orbitRange*Math.sin(Math.toRadians(posiotion));
		this.locationY = this.y-orbitRange*Math.cos(Math.toRadians(posiotion));;
		x1 = locationX-this.x;
		y1 = locationY-this.y;
		this.deg = 0;
		this.damage = 10;
	}

	@Override
	public void tick() {
		orbit();
	}

	@Override
	public void draw(GraphicsContext gc) {
		this.cX = locationX + dimension/2;
		this.cY = locationY + dimension/2;
		
		gc.setFill(Color.BLUE);
		gc.fillOval(locationX, locationY, dimension, dimension);
		gc.drawImage(RenderableHolder.normalOrbitEntity, locationX, locationY);
	

	}

	@Override
	protected Shape getBound() {
		return new Circle(cX, cY, dimension/2);
	}
	
	public void orbit() {
		deg+= speed;
		double angle = Math.toRadians(deg);

		//APPLY ROTATION
		double newx1 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
		double newy1 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

		//TRANSLATE BACK
		locationX = (int)Math.ceil(newx1) + this.x;
		locationY = (int)Math.ceil(newy1) + this.y;
		if(deg > 360) deg = 0;
		
}

}
