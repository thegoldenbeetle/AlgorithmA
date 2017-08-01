import java.util.Random;
import java.awt.Point;

public class Map {

	public final static int DEF_SIZE = 10;
	public final static int N_SEED = 30;
	public final static int N_UPDATE = 50;

	public Map() {
		this(DEF_SIZE, DEF_SIZE);
	}

	public Map(int width, int height) {
		_height = height;
		_width = width;
		defMap();
	}

	public boolean isExist(Point point) throws NullPointerException {
		return _map[point.x][point.y].exist;
	}

	public void setExist(Point point, boolean exist) {
		_map[point.x][point.y].exist = exist;
	}

	private void defMap() {
		_map = new Node[_width][_height];
		for (Node[] a : _map) {
			for (int i = 0; i < a.length; i++) {
				a[i] = new Node();
			}
		}

	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight(Point point) {
		return _map[point.x][point.y].height;
	}

	public void setHeight(Point point, int height) {
		_map[point.x][point.y].height = height;
	}

	public int getWay(Point point1, Point point2) {
		return Math.abs(getHeight(point1) - getHeight(point2)) + 1;
	}

	public void generate() {
		defMap();
		Random rand = new Random();
		int[] seedPos = new int[N_SEED];
		int numUpdates = N_UPDATE;

		for (int i = 0; i < seedPos.length; i++) {
			seedPos[i] = Math.abs(rand.nextInt() % (_height * _width));
			setExist(new Point(seedPos[i] % _width, seedPos[i] / _width), false);
		}

		for (int i = 0; i < numUpdates; i++) {
			for (int j = 0; j < seedPos.length; j++) {
				switch (Math.abs(rand.nextInt()) % 4) {

				case 0:
					seedPos[j] -= _width;
					break;
				case 1:
					seedPos[j]++;
					break;
				case 2:
					seedPos[j] += _width;
					break;
				case 3:
					seedPos[j]--;
					break;
				}

				if (!(seedPos[j] < 0 || seedPos[j] >= (_height * _width))) {
					setExist(
							new Point(seedPos[j] % _width, seedPos[j] / _width),
							false);
				}
			}
		}
	}

	private class Node {
		public Node() {
			exist = true;
			height = 1;
		}

		public boolean exist;
		public int height;
	}

	private Node[][] _map;
	private int _height, _width;
}
