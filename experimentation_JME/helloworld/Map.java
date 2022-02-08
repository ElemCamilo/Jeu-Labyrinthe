package helloworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.jme3.app.SimpleApplication;

public class Map {

	private int w;
	private int h;
	private Cell[][] area;
	private SimpleApplication app;

	public Map(int w, int h, SimpleApplication app) {
		this.w = w;
		this.h = h;
		this.app = app;

		this.area = new Cell[w][h];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				this.area[j][i] = new Cell(j, i, this.app);
			}
		}
	}

	public void update() {
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				this.area[j][i].updateCell();
			}
		}
	}

	public Cell[] getNeigbors(int x, int y) {
		Cell[] neigbors = new Cell[4];

		if (y == this.h - 1) {
			neigbors[0] = null;
		} else {
			neigbors[0] = this.area[x][y + 1];
		}

		if (y == 0) {
			neigbors[1] = null;
		} else {
			neigbors[1] = this.area[x][y - 1];
		}

		if (x == this.w - 1) {
			neigbors[2] = null;
		} else {
			neigbors[2] = this.area[x + 1][y];
		}

		if (x == 0) {
			neigbors[3] = null;
		} else {
			neigbors[3] = this.area[x - 1][y];
		}

		return neigbors;
	}

	public void addWall(int x, int y, String direction) {
		setWall(x, y, direction, true);
	}

	public void dropWall(int x, int y, String direction) {
		setWall(x, y, direction, false);
	}

	private void setWall(int x, int y, String direction, boolean b) {
		Cell[] neigbors = this.getNeigbors(x, y);

		switch (direction) {
		case "north":
			this.area[x][y].setWall(direction, b);
			if (neigbors[0] != null) {
				neigbors[0].setWall("south", b);
			}

			break;
		case "south":
			this.area[x][y].setWall(direction, b);
			if (neigbors[1] != null) {
				neigbors[1].setWall("north", b);
			}

			break;
		case "east":
			this.area[x][y].setWall(direction, b);
			if (neigbors[2] != null) {
				neigbors[2].setWall("weast", b);
			}

			break;
		case "weast":
			this.area[x][y].setWall(direction, b);
			if (neigbors[3] != null) {
				neigbors[3].setWall("east", b);
			}

			break;

		default:
			break;
		}
	}

	public void generate(int seed) {
		Boolean[][] visited = new Boolean[this.w][this.h];
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				visited[i][j] = false;
			}
		}

		Random generator = new Random(seed);

		int x = (int) (generator.nextDouble() * this.w);
		int y = (int) (generator.nextDouble() * this.h);

		walk(this.area[x][y], visited, generator);

	}

	public Boolean[][] walk(Cell cell, Boolean[][] visited, Random generator) {
		visited[cell.getX()][cell.getY()] = true;

		Cell[] neigbors = getNeigbors(cell.getX(), cell.getY());
		List<Integer> num_neigbors = new ArrayList();
		for (int i = 0; i < 4; i++) {
			num_neigbors.add(i);
		}

		Collections.shuffle(num_neigbors, generator);

		for (int i : num_neigbors) {
			if (neigbors[i] != null) {
				int other_x = neigbors[i].getX();
				int other_y = neigbors[i].getY();

				if (!visited[other_x][other_y]) {

					if (neigbors[i].getX() == cell.getX() + 1) {
						this.dropWall(cell.getX(), cell.getY(), "east");
					} else if (neigbors[i].getX() == cell.getX() - 1) {
						this.dropWall(cell.getX(), cell.getY(), "weast");
					} else if (neigbors[i].getY() == cell.getY() + 1) {
						this.dropWall(cell.getX(), cell.getY(), "north");
					} else if (neigbors[i].getY() == cell.getY() - 1) {
						this.dropWall(cell.getX(), cell.getY(), "south");
					}

					visited = this.walk(neigbors[i], visited, generator);
				}
			}
		}

		return visited;
	}

}
