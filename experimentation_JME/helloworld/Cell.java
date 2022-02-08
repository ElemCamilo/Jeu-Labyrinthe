package helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Cell {
	private int x;
	private int y;
	private Boolean wall_north = true;
	private Boolean wall_south = true;
	private Boolean wall_east = true;
	private Boolean wall_weast = true;
	private Box[][] area;
	private SimpleApplication app;

	public Cell(int x, int y, SimpleApplication app) {
		this.x = x;
		this.y = y;
		this.app = app;

		initCell();

	}

	public void updateCell() {
		Boolean is_wall;

		if (!this.wall_north) {
			this.setColor(1, 3, ColorRGBA.Blue);
			this.setColor(2, 3, ColorRGBA.Blue);
		} else {
			this.setColor(1, 3, ColorRGBA.Red);
			this.setColor(2, 3, ColorRGBA.Red);
		}
		
		if (!this.wall_south) {
			this.setColor(1, 0, ColorRGBA.Blue);
			this.setColor(2, 0, ColorRGBA.Blue);

		} else {
			this.setColor(1, 0, ColorRGBA.Red);
			this.setColor(2, 0, ColorRGBA.Red);
		}
		
		if (!this.wall_east) {
			this.setColor(3, 1, ColorRGBA.Blue);
			this.setColor(3, 2, ColorRGBA.Blue);
		} else {
			this.setColor(3, 1, ColorRGBA.Red);
			this.setColor(3, 2, ColorRGBA.Red);
		}
		
		if (!this.wall_weast) {
			this.setColor(0, 1, ColorRGBA.Blue);
			this.setColor(0, 2, ColorRGBA.Blue);
		} else {
			this.setColor(0, 1, ColorRGBA.Red);
			this.setColor(0, 2, ColorRGBA.Red);
		}

	}
	
	private void setColor(int x, int y, ColorRGBA color) {
		Box b = this.area[x][y];
		Geometry geom = new Geometry("Box", b);
		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		geom.setMaterial(mat);
		this.app.getRootNode().attachChild(geom);
	}

	private void initCell() {
		this.area = new Box[4][4];
		Boolean is_wall;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				is_wall = (i == 0) || (i == 3) || (j == 0) || (j == 3);
				this.area[j][i] = create_case(is_wall);
				this.area[j][i].updateGeometry(new Vector3f(this.x + j * 0.25F -25, this.y + i * 0.25F - 13, -50), 0.1F, 0.1F,
						0.1F);
			}
		}
	}

	private Box create_case(Boolean is_wall) {
		Box b = new Box(0.25F, 0.25F, 0.25F);
		Geometry geom = new Geometry("Box", b);
		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

		if (Boolean.TRUE.equals(is_wall)) {
			mat.setColor("Color", ColorRGBA.Red);
		} else {
			mat.setColor("Color", ColorRGBA.Blue);
		}
		geom.setMaterial(mat);
		this.app.getRootNode().attachChild(geom);
		return b;
	}

	public void setWall(String direction, boolean b) {

		switch (direction) {
		case "north":
			this.wall_north = b;

			break;
		case "south":
			this.wall_south = b;

			break;
		case "east":
			this.wall_east = b;

			break;
		case "weast":
			this.wall_weast = b;

			break;

		default:
			break;
		}

	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

}
