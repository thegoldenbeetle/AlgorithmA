import java.awt.*;
import javax.swing.JComponent;

public class Canvas extends JComponent {

	private static final long serialVersionUID = 1L;
	private Graphics2D g2d;
	private Point coordStart, coordFinish;

	public static final int CANVAS_WIDTH = 700;
	public static final int CANVAS_HEIGHT = 500;
	public static final int POINT_SIZE = 10;

	public void drawMap(Map _map) {
		coordStart = null;
		coordFinish = null;
		for (int i = 0; i < _map.getHeight(); i++)
			for (int j = 0; j < _map.getWidth(); j++) {
				if (!_map.isExist(new Point(j, i))) {
					drawPoint(new Point(j * POINT_SIZE, i * POINT_SIZE),
							Color.black);
				} else {
					drawPoint(new Point(j * POINT_SIZE, i * POINT_SIZE),
							Color.white);
				}
			}

	}

	public void drawPoint(Point newcoord, Color color) {
		g2d = (Graphics2D) super.getGraphics();
		g2d.setPaint(color);
		g2d.fillRect(newcoord.x + 1, newcoord.y + 1, POINT_SIZE - 1,
				POINT_SIZE - 1);
	}

	public Point setStartFinish(Point newcoord, boolean Type, Point coord) {

		if (coord == null) {
			coord = new Point();
		} else {
			drawPoint(coord, Color.white);
		}

		coord.x = newcoord.x - (newcoord.x % POINT_SIZE);
		coord.y = newcoord.y - (newcoord.y % POINT_SIZE);
		drawPoint(coord, (Type) ? Color.blue : new Color(7, 210, 7));

		return coord;
	}

	public void setStart(int x, int y) {
		coordStart = setStartFinish(new Point(x, y), true, coordStart);
	}

	public void setFinish(int x, int y) {
		coordFinish = setStartFinish(new Point(x, y), false, coordFinish);
	}

	public void paintComponent(Graphics g) {

		super.paintComponents(g);
		g2d = (Graphics2D) g;

		g2d.setPaint(Color.white);
		g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		g2d.setPaint(Color.black);
		g2d.drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

		for (int i = 0; i < CANVAS_WIDTH; i += POINT_SIZE) {
			g2d.setPaint(Color.black);
			g2d.drawLine(i, 0, i, CANVAS_HEIGHT);
		}

		for (int i = 0; i < CANVAS_HEIGHT; i += POINT_SIZE) {
			g2d.setPaint(Color.black);
			g2d.drawLine(0, i, CANVAS_WIDTH, i);
		}
	}

}
