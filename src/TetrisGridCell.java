import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class TetrisGridCell {
	private Rectangle cell;
	private Color color = Color.GRAY;
	public TetrisGridCell(int x, int y){
		cell = new Rectangle(x, y, 20, 20);
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		g2.fill(cell);
		g2.setColor(Color.DARK_GRAY);
		g2.draw(cell);
	}
}
