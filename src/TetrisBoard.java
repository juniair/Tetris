import java.awt.Color;


public class TetrisBoard {
	private static class Location{
		private int row;
		private int col;
		public Location(int row, int col){
			this.row = row;
			this.col = col;
		}
	}
	
	private Color[][] cells;
	private int width;
	private int height;
	private Tetromino currTetromino;
	private Location currLocation;
	public TetrisBoard(int width, int height){
		this.width = width;
		this.height = height;
		cells = new Color[height][width];
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++)
				cells[row][col] = Color.GRAY;
	}
	public void clear(){
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++)
				cells[row][col] = Color.GRAY;
	}
	public Color[][] getCells(){
		return cells;
	}
	private boolean isValidRow(int row){
		return row>=0&&row<height;
	}
	private boolean isValidCol(int col){
		return col>=0&&col<width;
	}
	private boolean isValidLocation(int row, int col){
		return isValidRow(row)&&isValidCol(col);
	}
	private boolean isEmptyRow(int row){
		for(int col=0; col<width; col++){
			if(cells[row][col]!=Color.GRAY) return false;
		}
		return true;	
	}
	private boolean isFullRow(int row){
		for(int col=0; col<width; col++){
			if(cells[row][col]==Color.GRAY) return false;
		}
		return true;	
	}
	
	private boolean canMove(Location newLoc){
		byte[][] tetrominoBlock = currTetromino.getCurrentBlock();
		for(int row=0; row<tetrominoBlock.length; row++){
			int r = newLoc.row+row;
			for(int col=0; col<tetrominoBlock[0].length; col++){
				int c = newLoc.col+col;
				if(tetrominoBlock[row][col]==1){
					if(!isValidLocation(r,c) || cells[r][c]!=Color.GRAY) return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean moveShapeDown(){	
		return move(new Location(currLocation.row+1, currLocation.col));
	}
	public void moveShapeLeft(){
		move(new Location(currLocation.row, currLocation.col-1)); 
	}
	public void moveShapeRight(){
		move(new Location(currLocation.row, currLocation.col+1)); 	
	}
	public void rotateShape(){
		eraseBlock();
		currTetromino.rotate();
		if(!canMove(currLocation)){
			currTetromino.rotateBack();
		}
		applyBlock();
	}
	public void hardDropShape(){
		while(moveShapeDown());
	}
	
	private boolean move(Location newLocation){
		boolean moved = false;
		eraseBlock();
		if(canMove(newLocation)){
			currLocation = newLocation;
			moved = true;
		}
		applyBlock();
		return moved;
	}
	
	// 게임 종료 판반 메소드
	public boolean GameOver(){
		for(int i = 0; i<10; i++){
			if(cells[0][i] != Color.GRAY)
			{
				return true;
			}
		}
		return false;
	}

	
	private void applyBlock(){
		byte[][] tetrominoBlock = currTetromino.getCurrentBlock();
		int r = 0, c = 0;
		for(int row=0; row<tetrominoBlock.length; row++){
			r = row+currLocation.row;
			for(int col=0; col<tetrominoBlock[0].length; col++){
				c = col+currLocation.col;
				if(tetrominoBlock[row][col]==1&&isValidLocation(r,c))
					cells[r][c] = currTetromino.getColor();
			}
		}
	}
	
	private void eraseBlock(){
		byte[][] tetrominoBlock = currTetromino.getCurrentBlock();
		for(int row=0; row<tetrominoBlock.length; row++){
			int r = row+currLocation.row;
			for(int col=0; col<tetrominoBlock[0].length; col++){
				int c = col+currLocation.col;
				if(tetrominoBlock[row][col]==1&&isValidLocation(r,c))
					cells[r][c] = Color.GRAY;
			}
		}
	}
	
	private Color[] generateNewRow(){
		Color[] newRow = new Color[width];
		for(int col=0; col<width; col++)
			newRow[col] = Color.GRAY;
		return newRow;
	}
	
	/*private void removeRow(int delRow){
		for(int row=delRow; row>=1; row--){
			cells[row] = cells[row-1];
			if(isEmptyRow(row-1)){
				cells[row-1] = generateNewRow();
				break;
			}
		}
	}*/
	
	private void removeRow(int delRow){
		for(int row=delRow; row>=1; row--){
			for(int col=0; col<width; col++){
				cells[row][col] = cells[row-1][col];
			}
			if(isEmptyRow(row-1)) break;
		}
	}
	
	public int removeFullRow(){
		int numberOfRemovedRows = 0;
		for(int row=height-1; row>=0; row--){
			if(isEmptyRow(row)) break;
			else if(isFullRow(row)){
				removeRow(row);
				++row;
				++numberOfRemovedRows;
			}
		}
		return numberOfRemovedRows;
	}
	
	
	public boolean insertTetromino(Tetromino tetromino){
		currTetromino = tetromino;
		int startCol = (width-currTetromino.getWidth())/2;
		currLocation = new Location(0,startCol);
		if(canMove(currLocation)){
			applyBlock();
			return true;
		}
		return false;
	}
}
