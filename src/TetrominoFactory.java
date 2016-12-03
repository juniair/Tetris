import java.awt.Color;
import java.util.Random;


public class TetrominoFactory {
	public enum ShapeType {O_SHAPE, Z_SHAPE, S_SHAPE, L_SHAPE, J_SHAPE, T_SHAPE, I_SHAPE;
		public static ShapeType valueOf(int index){
			switch(index){
			case 0: return O_SHAPE;
			case 1: return Z_SHAPE;
			case 2: return S_SHAPE;
			case 3: return L_SHAPE;
			case 4: return J_SHAPE;
			case 5: return T_SHAPE;
			default: return I_SHAPE;
			}
		}
	};
	public static final int NUMBER_OF_SHAPES = 7;
	public static final Color[] shapeColor = {
		Color.YELLOW,
		Color.RED,
		Color.GREEN,
		Color.ORANGE,
		Color.BLUE,
		Color.MAGENTA,
		Color.CYAN
	};
	private static final byte[][][] oBlock = {
		{
			{0, 1, 1, 0},
			{0, 1, 1, 0},
			{0, 0, 0, 0}
		},
		{
			{0, 1, 1, 0},
			{0, 1, 1, 0},
			{0, 0, 0, 0}
		}
	};
	private static final byte[][][] zBlock = {
		{
			{1, 1, 0},
			{0, 1, 1},
			{0, 0, 0}
		},
		{
			{0, 0, 1},
			{0, 1, 1},
			{0, 1, 0}
		},
		{
			{0, 0, 0},
			{1, 1, 0},
			{0, 1, 1}

		},
		{
			{0, 1, 0},
			{1, 1, 0},
			{1, 0, 0}
		}
	};
	private static final byte[][][] sBlock = {
		{
			{0, 1, 1},
			{1, 1, 0},
			{0, 0, 0}
		},
		{
			{0, 1, 0},
			{0, 1, 1},
			{0, 0, 1}
		},
		{
			{0, 1, 0},
			{0, 1, 1},
			{0, 0, 1}
		},
		{
			{0, 1, 0},
			{0, 1, 1},
			{0, 0, 1}
		}
	};
	private static final byte[][][] lBlock = {
		{
			{0, 0, 1},
			{1, 1, 1},
			{0, 0, 0}
		},
		{
			{0, 1, 0},
			{0, 1, 0},
			{0, 1, 1},
		},
		{
			{0, 0, 0},
			{1, 1, 1},
			{1, 0, 0},
		},
		{
			{1, 1, 0},
			{0, 1, 0},
			{0, 1, 0}
		}
	};
	private static final byte[][][] jBlock = {
		{
			{1, 0, 0},
			{1, 1, 1},
			{0, 0, 0}
		},
		{
			{0, 1, 1},
			{0, 1, 0},
			{0, 1, 0}
		},
		{
			{0, 0, 0},
			{1, 1, 1},
			{0, 0, 1}
		},
		{
			{0, 1, 0},
			{0, 1, 0},
			{1, 1, 0}
		}
	};
	private static final byte[][][] tBlock = {
		{
			{0, 1, 0},
			{1, 1, 1},
			{0, 0, 0}
		},
		{
			{0, 1, 0},
			{0, 1, 1},
			{0, 1, 0}
		},
		{
			{0, 0, 0},
			{1, 1, 1},
			{0, 1, 0}
		},
		{
			{0, 1, 0},
			{1, 1, 0},
			{0, 1, 0}
		}
	};
	private static final byte[][][] iBlock ={
		{
			{0, 0, 0, 0},
			{1, 1, 1, 1},
			{0, 0, 0, 0},
			{0, 0, 0, 0}
		},
		{
			{0, 0, 1, 0},
			{0, 0, 1, 0},
			{0, 0, 1, 0},
			{0, 0, 1, 0}
		},
		{
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{1, 1, 1, 1},
			{0, 0, 0, 0}
		},
		{
			{0, 1, 0, 0},
			{0, 1, 0, 0},
			{0, 1, 0, 0},
			{0, 1, 0, 0}
		}		
	};
	private Tetromino currShape;
	private Tetromino nextShape;
	private final Random randomGen = new Random(System.currentTimeMillis());
	public TetrominoFactory(){
	}
	public void start(){
		currShape = getInstance(ShapeType.valueOf(randomGen.nextInt(NUMBER_OF_SHAPES)));
		nextShape = getInstance(ShapeType.valueOf(randomGen.nextInt(NUMBER_OF_SHAPES)));
	}
	public Tetromino getInstance(ShapeType type){
		switch(type){
		case O_SHAPE:
			return new Tetromino(oBlock, shapeColor[type.ordinal()]);
		case Z_SHAPE:
			return new Tetromino(zBlock, shapeColor[type.ordinal()]);
		case S_SHAPE:
			return new Tetromino(sBlock, shapeColor[type.ordinal()]);
		case L_SHAPE:
			return new Tetromino(lBlock, shapeColor[type.ordinal()]);
		case J_SHAPE:
			return new Tetromino(jBlock, shapeColor[type.ordinal()]);
		case T_SHAPE:
			return new Tetromino(tBlock, shapeColor[type.ordinal()]);
		default:
			return new Tetromino(iBlock, shapeColor[type.ordinal()]);
		}
	}
	public Tetromino getCurrent(){
		return currShape;
	}
	public Tetromino getNext(){
		return nextShape;
	}
	public void changeBlock(){
		currShape = nextShape;
		nextShape = getInstance(ShapeType.valueOf(randomGen.nextInt(NUMBER_OF_SHAPES)));
	}
}
