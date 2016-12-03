import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;


public class TetrisGrid extends JPanel {
	private int width;
	private int height;
	private TetrisGridCell[][] cells; 
	public TetrisGrid(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width*20, height*20));
		cells = new TetrisGridCell[height][width];
		for(int row = 0; row<height; row++)
			for(int col = 0; col<width; col++)
				cells[row][col] = new TetrisGridCell(col*20, row*20);
	}
	public void setColors(TetrisBoard board){
		Color[][] boardCells = board.getCells();
		for(int row = 0; row<boardCells.length; row++)
			for(int col = 0; col<boardCells[0].length; col++)
				cells[row][col].setColor(boardCells[row][col]);
	}
	@Override
	public void paint(Graphics g){
		for(int row = 0; row<height; row++)
			for(int col = 0; col<width; col++)
				cells[row][col].draw(g);
	}
	
}
