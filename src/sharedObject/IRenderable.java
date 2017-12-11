package sharedObject;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	public void tick();
	public void draw(GraphicsContext gc);
	public int getZ();
	public boolean isDestroyed();
	public boolean isVisible();
}
