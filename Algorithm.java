import java.util.*;
import java.awt.Point;

public class Algorithm {

	/**
	 * aStar Algorithm
	 * 
	 */
	public Algorithm(Map map) {
		_map = map;
		G = new int[_map.getWidth()][_map.getHeight()];
		F = new int[_map.getWidth()][_map.getHeight()];
	}

	public void generateMap() {
		_map.generate();
	}
	
	public Map getMap() {
		return _map;
	}

	public Vector<Point> aStar(Point start, Point end) {
		Vector<Point> closeset = new Vector<>();
		Point[][] fromset = new Point[_map.getWidth()][_map.getHeight()];
		Queue<Point> openset = new PriorityQueue<>(_map.getWidth()
				* _map.getHeight(), fieldComparator);
		G[start.x][start.y] = 0;
		F[start.x][start.y] = G[start.x][start.y]
				+ setHeuristicFunction(start, end);
		openset = includeOpenSet(openset, start);
		while (!openset.isEmpty()) {

			boolean better_result = false;
			Point curr = takeMin(openset);
			if (curr.equals(end)) {
				return reconstructPath(fromset, start, end);
			}

			openset = removeOpenSet(openset, curr);
			closeset = includeCloseSet(closeset, curr);
			Vector<Point> neighbours = findNeighbours(curr);

			for (Point neighbour : neighbours) {

				if (closeset.contains(neighbour))
					continue;

				int tentativeScore = G[curr.x][curr.y]
						+ _map.getWay(curr, neighbour);
				if (!openset.contains(neighbour)) {

					better_result = true;
				} else {
					if (tentativeScore < G[neighbour.x][neighbour.y])
						better_result = true;
					else
						better_result = false;

				}
				if (better_result) {
					fromset[neighbour.x][neighbour.y] = includeFromSet(curr);
					G[neighbour.x][neighbour.y] = tentativeScore;
					F[neighbour.x][neighbour.y] = G[neighbour.x][neighbour.y]
							+ setHeuristicFunction(neighbour, end);
					if (!openset.contains(neighbour))
						openset = includeOpenSet(openset, neighbour);
					else {
						openset = removeOpenSet(openset, neighbour);
						openset = includeOpenSet(openset, neighbour);
					}
				}
			}

		}
		return null;
	}

	protected Vector<Point> reconstructPath(Point[][] fromset, Point start,
			Point end) {
		Vector<Point> pathset = new Vector<>();

		Point curr = end;
		pathset.add(curr);
		while (curr != start) {
			curr = fromset[curr.x][curr.y];
			pathset.add(curr);

		}
		Collections.reverse(pathset);
		return pathset;

	}

	/**
	 * Setting HeuristicFunction
	 * 
	 * @param cell
	 * @param end
	 */
	protected Integer setHeuristicFunction(Point cell, Point end) {
		return Math.abs(cell.x - end.x) + Math.abs(cell.y - end.y);
	}

	/**
	 * Include in closeset elemets
	 * 
	 * @param closeset
	 * @param current
	 * @return
	 */
	protected Vector<Point> includeCloseSet(Vector<Point> closeset,
			Point current) {
		closeset.add(current);
		return closeset;
	}

	/**
	 * remove element from openset
	 * 
	 * @param openset
	 * @param current
	 * @return
	 */

	protected Queue<Point> removeOpenSet(Queue<Point> openset, Point current) {
		openset.remove(current);
		return openset;
	}

	protected Queue<Point> includeOpenSet(Queue<Point> openset, Point current) {
		openset.add(current);
		return openset;
	}

	protected Point includeFromSet(Point current) {
		return current;
	}

	protected Point takeMin(Queue<Point> openset) {
		Point curr = openset.peek();
		return curr;
	}

	/**
	 * Creation vector of Neighbours
	 * 
	 * @param current
	 * @return
	 */
	protected Vector<Point> findNeighbours(Point current) {
		Vector<Point> finded = new Vector<>();
		boolean parametr1 = (current.x + 1 <= _map.getWidth() - 1);
		boolean parametr2 = (current.y + 1 <= _map.getHeight() - 1);
		boolean parametr3 = (current.x - 1 >= 0);
		boolean parametr4 = (current.y - 1 >= 0);

		if (parametr1)
			if (_map.isExist(new Point(current.x + 1, current.y)))
				finded.add(new Point(current.x + 1, current.y));
		if (parametr2)
			if (_map.isExist(new Point(current.x, current.y + 1)))
				finded.add(new Point(current.x, current.y + 1));
		if (parametr3)
			if (_map.isExist(new Point(current.x - 1, current.y)))
				finded.add(new Point(current.x - 1, current.y));
		if (parametr4)
			if (_map.isExist(new Point(current.x, current.y - 1)))
				finded.add(new Point(current.x, current.y - 1));
		return finded;
	}

	protected Map _map;
	private static int[][] G;
	private static int[][] F;

	public static Comparator<Point> fieldComparator = new Comparator<Point>() {

		@Override
		public int compare(Point p1, Point p2) {

			return (int) (F[p1.x][p1.y] - F[p2.x][p2.y]);

		}
	};


}
