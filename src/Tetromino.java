import java.awt.Color;


public class Tetromino {
	private Color color;
	private byte[][][] block;
	private int rotateIndex  = 0;
	public Tetromino(byte[][][] block, Color color){
		this.block = block;
		this.color = color;
	}
	public int getWidth(){
		return block[0].length;
	}
	public byte[][] getCurrentBlock(){
		return block[rotateIndex];
	}
	public Color getColor(){
		return color;
	}
	public void rotateBack(){
		rotateIndex = rotateIndex-1;
		if(rotateIndex==-1) rotateIndex = block.length-1;
	}
	public void rotate(){
		rotateIndex = (rotateIndex+1) % block.length;
	}
}
