import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TetrisFrame extends JFrame {
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source==startButton){
				reset();
				pauseButton.setEnabled(true);
				mainGrid.setFocusable(true);
				startNewTetrimino();
				mainGrid.requestFocus();
			}
			else if(source==pauseButton){
				if(!pauseFlag){
					startButton.setEnabled(false);
					speedTimer.cancel();
					pauseFlag = true;
				}
				else{
					startButton.setEnabled(true);
					pauseFlag = false;
					speedTimer = new Timer();
					speedTimer.schedule(new ShapeDownTask(),500,DropSpeed);
					mainGrid.requestFocus();
					
				}
			}
		}
	}
	private class KeyboardListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			repaint();
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
				gameBoard.moveShapeLeft();
				mainGrid.setColors(gameBoard);
	            mainGrid.repaint();
	            player.play("Move.wav");
			}
	        else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
	        	gameBoard.moveShapeRight();
	        	mainGrid.setColors(gameBoard);
	        	mainGrid.repaint();
	        	player.play("Move.wav");
	        }
	        else if (e.getKeyCode() == KeyEvent.VK_UP){
	        	gameBoard.rotateShape();
	        	mainGrid.setColors(gameBoard);
	        	mainGrid.repaint();
	        	player.play("Rotate.wav");
	        }
	        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
	        	gameBoard.moveShapeDown();
	        	mainGrid.setColors(gameBoard);
	            mainGrid.repaint();
	            player.play("Move.wav");
	        }
	        else if (e.getKeyCode() == KeyEvent.VK_SPACE){
	        	gameBoard.hardDropShape();
	            mainGrid.setColors(gameBoard);
	            mainGrid.repaint();
	            score += 4;
	            player.play("HardDrop.wav");
	        }
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	
	private class ShapeDownTask extends TimerTask{
		public int computeScore(int numberOfRemovedRows){
			switch(numberOfRemovedRows){
			case 1: 
				player.play("SingleLineClear.wav");
				return 40*(level+1)+4;
			case 2:
				player.play("DoubleLineClear.wav");
				return 100*(level+1)+4;
			case 3: 
				player.play("TripleLineClear.wav");
				return 300*(level+1)+4;
			case 4: 
				return 1200*(level+1)+4;
			default: return 4;
			}
		}
		// 레벨 계산
		public void computeLevel(int score){
			if( (level-1) != score/(1200*level)){
				level = ((score/(1200*level))+1);
			}
		}
		
		// 속도계산
		public void computeDropSpeed(int level){
			DropSpeed = 1000 - ((level/3)^(level-1));
			if(DropSpeed < 0 ) DropSpeed = 50;
		}
		@Override
		public void run() {
			//repaint();
			if(!gameBoard.moveShapeDown()){
				if(gameBoard.GameOver())
				{
					JOptionPane.showMessageDialog(null, "게임을 종료하겠습니다.", "GameOver", JOptionPane.CLOSED_OPTION);
					System.exit(0);
				}
				else{
					int removedLines = gameBoard.removeFullRow();
					numberOfLines += removedLines;
					score += computeScore(removedLines);
					computeLevel(score);
					computeDropSpeed(level);
					setStateData();
					tetrominoFactory.changeBlock();
					startNewTetrimino();
				}
			}
			else{
				mainGrid.setColors(gameBoard);
				mainGrid.repaint();
			}
			repaint();
		}
	}
	
	private JPanel mainPanel = new JPanel();
	private JPanel homePanel = new JPanel();
	private JPanel statePanel = new JPanel();
	private JButton startButton = new JButton("start");
	private JButton pauseButton = new JButton("pause");
	private JTextField levelField = new JTextField(6);
	private JTextField scoreField = new JTextField(6);
	private JTextField lineField = new JTextField(6);
	private JLabel levelLabel = new JLabel("level");
	private JLabel scoreLabel = new JLabel("score");
	private JLabel lineLabel = new JLabel("line");
	
	private TetrisGrid mainGrid = new TetrisGrid(10,22);
	private TetrisGrid nextGrid = new TetrisGrid(6,6);
	
	private TetrisBoard gameBoard = new TetrisBoard(10,22);
	private TetrisBoard nextBoard = new TetrisBoard(6,6);
	
	private TetrominoFactory tetrominoFactory = new TetrominoFactory();
	private Timer speedTimer = new Timer();
	private AudioPlayer player = new AudioPlayer();
	
	
	private int level = 1;
	private int score = 0;
	private long DropSpeed = 00;
	private int numberOfLines = 0;
	private boolean pauseFlag = false;
	
	public TetrisFrame(){
		setTitle("Java Tetris");
		setSize(400, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setView();
	}
	
	private void setHomePanel(){
		ActionListener listener = new ButtonListener();
		startButton.addActionListener(listener);
		pauseButton.addActionListener(listener);
		
		homePanel.setLayout(new FlowLayout());
		homePanel.add(startButton);
		homePanel.add(pauseButton);
		pauseButton.setEnabled(false);
		add(homePanel, BorderLayout.NORTH);
	}
	
	private void initTextFields(JTextField field){
		field.setMaximumSize(new Dimension(240, 60));
		field.setEditable(false);
		field.setHorizontalAlignment(JTextField.RIGHT);
	}
	
	private void setStatePanel(){
		statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.PAGE_AXIS));
		statePanel.add(Box.createVerticalStrut(20));
		statePanel.add(nextGrid);
		statePanel.add(Box.createVerticalStrut(20));
		statePanel.add(levelLabel);
		statePanel.add(levelField);
		statePanel.add(Box.createVerticalStrut(10));
		statePanel.add(scoreLabel);
		statePanel.add(scoreField);
		statePanel.add(Box.createVerticalStrut(10));
		statePanel.add(lineLabel);
		statePanel.add(lineField);
		statePanel.add(Box.createVerticalStrut(100));
		
		initTextFields(levelField);
		initTextFields(scoreField);
		initTextFields(lineField);
	}
	
	private void setMainPanel(){
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(Box.createHorizontalStrut(20));
		mainPanel.add(mainGrid);
		mainPanel.add(Box.createHorizontalStrut(20));
		mainPanel.add(statePanel);
		mainPanel.add(Box.createHorizontalStrut(20));
		mainGrid.addKeyListener(new KeyboardListener());
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setStateData(){
		levelField.setText(level+"");
		scoreField.setText(score+"");
		lineField.setText(numberOfLines+"");
	}
	
	private void setView(){
		setHomePanel();
		setStatePanel();
		setMainPanel();
		setStateData();
	}

	private void reset(){
		numberOfLines = 0;
		score = 0;
		level = 1;
		DropSpeed = 1000;
		setStateData();
		gameBoard.clear();
		nextBoard.clear();
		mainGrid.setColors(gameBoard);
		nextGrid.setColors(nextBoard);
		repaint();
		mainGrid.repaint();
		nextGrid.repaint();
		tetrominoFactory.start();
		speedTimer.cancel();
		speedTimer = new Timer();
		speedTimer.schedule(new ShapeDownTask(),500,DropSpeed);
	}
	
	private void startNewTetrimino(){
		gameBoard.insertTetromino(tetrominoFactory.getCurrent());
		nextBoard.clear();
		nextBoard.insertTetromino(tetrominoFactory.getNext());
		nextBoard.moveShapeDown();
		mainGrid.setColors(gameBoard);
		nextGrid.setColors(nextBoard);
		repaint();
		mainGrid.repaint();
		nextGrid.repaint();
	}
}



















