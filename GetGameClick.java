package caro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GetGameClick implements MouseListener {
	public static final Color WIN_COLOR = new Color(4, 92, 42);
	public static final Color X_COLOR = Color.BLUE;
	public static final Color O_COLOR = Color.RED;

	private HitBox[] nodes;
	private HitBox[] buttons;
	private HitBox[] menuButtons;
	private HitBox[] playerButtons;
	private Board board;
	private Graphics2D g;
	private int turn;
	private boolean gameStart;
	private boolean gameEnd;
	
	//clicked ensures one click can only do one thing
	private boolean clicked;
	private boolean deactMainMenu;
	private boolean deactPlayerMenu;
	private boolean scoreUpdated;

	private boolean quickGame;
	private boolean scoredGame;

	private int countHorizontal = 1;
	private int countVertical = 1;
	private int countUpwardDiagonal = 1;
	private int countDownwardDiagonal = 1;
	
	private boolean player1IsX;
	private int player1Score;
	private int player2Score;
	
	private ArrayOfNodes nodeBoard;
	private ArrayOfNodes winLineHorizontal = new ArrayOfNodes();
	private ArrayOfNodes winLineVertical = new ArrayOfNodes();
	private ArrayOfNodes winLineUpwardDiagonal = new ArrayOfNodes();
	private ArrayOfNodes winLineDownwardDiagonal = new ArrayOfNodes();
	private ArrayOfNodes allMoves = new ArrayOfNodes();
	
	public GetGameClick (HitBox[] hitboxes, HitBox[] menuHitBox, HitBox[] startingMenuHitBox, HitBox[] playerMenuHitBox, Board gameBoard) {
		this.nodes = hitboxes;
		this.board = gameBoard;
		nodeBoard = new ArrayOfNodes(nodes);
		this.buttons = menuHitBox;
		this.menuButtons = startingMenuHitBox;
		this.playerButtons = playerMenuHitBox;
		this.g = board.getGraphics();
	}
	public void mouseClicked(MouseEvent e) {
		clicked = false;
		if (!deactMainMenu) {
			checkMainMenu(e);
		}
		
		if (quickGame) {
			playQuickGame(e);
		}
		
		if (scoredGame) {
			playScoredGame(e);
		}
	}
	
	public void playQuickGame (MouseEvent e) {
		if (gameStart && !gameEnd) {
			play(e);
		}
		checkDashboard(e);
	}
	
	public void playScoredGame (MouseEvent e) {
		if (!deactPlayerMenu) {
			checkPlayerMenu(e);
		}
		if (gameStart && !gameEnd && deactPlayerMenu) {
			play(e);
		}
		checkDashboard(e);
		if (gameEnd && !scoreUpdated) {
			scoreUpdated = true;
			if (player1IsX && turn%2 != 0) {
				player1Score++;
			}
			else {
				player2Score++;
			}
			board.drawScoredGameDashboard(player1IsX, player1Score, player2Score, gameEnd);
		}
	}
	
	public void checkPlayerMenu (MouseEvent e) {
		for (int i = 0; i < playerButtons.length; i++) {
			playerButtons[i].setClickX(e.getX());
			playerButtons[i].setClickY(e.getY());
			System.out.println("PLAYERRR");
			if (playerButtons[i].isValidClick() && !clicked) {
				clicked = true;
				if (i == 0) {
					player1IsX = true;
					board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
					board.drawPlayerChoice(player1IsX);
				}
				if (i == 1) {
					player1IsX = false;
					board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
					board.drawPlayerChoice(player1IsX);
				}
				if (i == 2) {
					board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
					deactPlayerMenu = true;
				}
				
			}
		}
	}
	
	public void checkMainMenu (MouseEvent e) {
		for (int i = 0; i < menuButtons.length; i++) {
			menuButtons[i].setClickX(e.getX());
			menuButtons[i].setClickY(e.getY());
			System.out.println("MAINN");
			if (menuButtons[i].isValidClick() && !clicked) {
				clicked = true;
				if (i == 0) {
					startQuickGame();
				}
				if (i == 1) {
					startScoredGame();
				}
				if (i == 2) {
					quitGame();
				}
				if (i == 3) {
					resumeGame();
				}
			}
		}
	}
	
	public void checkDashboard (MouseEvent e) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setClickX(e.getX());
			buttons[i].setClickY(e.getY());
			System.out.println("DASHBOARD");
			if (buttons[i].isValidClick() && !clicked) {
				clicked = true;
				if (i == 0 && scoredGame && gameEnd) {
					refreshGame();
					deactPlayerMenu = false;
					board.drawPlayerChoice(player1IsX);
				}
				if (i == 0 && turn != 0) {
					refreshGame();
				}
				if (i == 1) {
					openMenu();
				}
				if (i == 2) {
					quitGame();
				}
				
			}
		}
	}
	
	public void resumeGame() {
		deactMainMenu = true;
		gameStart = true;
		if (quickGame) {
			board.resetQuickGameBoard();
		}
		if (scoredGame) {
			board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
			if (!deactPlayerMenu) {
				board.drawPlayerChoice(player1IsX);
			}
		}
		drawAll(allMoves);
	}
	
	public void drawAll(ArrayOfNodes nodes) {
		HitBox[] moves = nodes.getNodes();
		if (moves == null) {
			return;
		}
		for (int i = 0; i < moves.length; i++) {
			if (moves[i].isX()) {
				drawX(moves[i],X_COLOR);
			}
			else {
				drawO(moves[i],O_COLOR);
			}
		}
		checkWin(moves[moves.length-1],nodeBoard);
	}
	
	public void refreshGame() {
		if (quickGame) {
			reset();
			gameStart = true;
			board.resetQuickGameBoard();
		}
		if (scoredGame) {
			reset();
			gameStart = true;
			board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
		}
		
	}
	
	public void openMenu() {
		deactMainMenu = false;
		gameStart = false;
		board.drawMainMenu();
	}
	
	public void startQuickGame() {
		hardReset();
		gameStart = true;
		deactMainMenu = true;
		quickGame = true;
		scoredGame = false;
		board.resetQuickGameBoard();
	}
	
	public void startScoredGame() {
		hardReset();
		gameStart = true;
		deactMainMenu = true;
		deactPlayerMenu = false;
		quickGame = false;
		scoredGame = true;
		board.resetScoredGameBoard(player1IsX,player1Score,player2Score,gameEnd);
		board.drawPlayerChoice(player1IsX);
	}
	
	public void quitGame() {
		board.getPanel().setVisible(false);
	}
	
	public void play (MouseEvent e) {
		g.setStroke(new BasicStroke(5));
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setClickX(e.getX());
			nodes[i].setClickY(e.getY());
			//Put !clicked first because if isValidGameClick is trigger it will set the node to occupied 
			if (!clicked && nodes[i].isValidGameClick()) {
				clicked = true;
				System.out.println(i);
				if (turn % 2 == 0) {
					drawX(nodes[i],X_COLOR);
					nodes[i].setIsX();
				}
				else {
					drawO(nodes[i],O_COLOR);
					nodes[i].setIsO();
				}
				allMoves.addNode(nodes[i]);
				turn++;
				if (turn == nodes.length) {
					gameEnd = true;
				}
				winLineVertical.addNode(nodes[i]);
				winLineUpwardDiagonal.addNode(nodes[i]);
				winLineDownwardDiagonal.addNode(nodes[i]);
				if (checkWin(nodes[i],nodeBoard)) {
					System.out.println("End");
					gameEnd = true;
				}
				break;
			}
		}
	}
	
	public void reset() {
		gameStart = false;
		gameEnd = false;
		scoreUpdated = false;
		turn = 0;
		countHorizontal = 1;
		countVertical = 1;
		countUpwardDiagonal = 1;
		countDownwardDiagonal = 1;
		allMoves.empty();
		winLineHorizontal.empty();
		winLineVertical.empty();
		winLineUpwardDiagonal.empty();
		winLineDownwardDiagonal.empty();
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setOccupied(false);
		}
	}
	
	public void hardReset() {
		reset();
		player1Score = 0;
		player2Score = 0;
	}
	
	public boolean isGameEnd() {
		return gameEnd;
	}
	
	public void drawX(HitBox hitbox, Color color) {
		g.setColor(color);
		g.drawLine(hitbox.getCenterX()-20, hitbox.getCenterY()-20, hitbox.getCenterX()+20, hitbox.getCenterY()+20);
		g.drawLine(hitbox.getCenterX()+20, hitbox.getCenterY()-20, hitbox.getCenterX()-20, hitbox.getCenterY()+20);
	}
	
	public void drawO(HitBox hitbox, Color color) {
		g.setColor(color);
		g.drawOval(hitbox.getCenterX()-20, hitbox.getCenterY()-20, 40, 40);
	}
	
	public void checkHorizontalForward(HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() + 50, node.getCenterY());
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countHorizontal++;
			winLineHorizontal.addNode(nodes[index]);
			checkHorizontalForward(nodes[index],board);
		}
	}
	
	public void checkHorizontalBackward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() - 50, node.getCenterY());
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countHorizontal++;
			winLineHorizontal.addNode(nodes[index]);
			checkHorizontalBackward(nodes[index],board);
		}
	}
	
	public void checkVerticalForward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX(), node.getCenterY() + 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countVertical++;
			winLineVertical.addNode(nodes[index]);
			checkVerticalForward(nodes[index],board);
		}
	}
	
	public void checkVerticalBackward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX(), node.getCenterY() - 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countVertical++;
			winLineVertical.addNode(nodes[index]);
			checkVerticalBackward(nodes[index],board);
		}
	}
	
	public void checkUpwardDiagonalForward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() + 50, node.getCenterY() - 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countUpwardDiagonal++;
			winLineUpwardDiagonal.addNode(nodes[index]);
			checkUpwardDiagonalForward(nodes[index],board);
		}
	}
	
	public void checkUpwardDiagonalBackward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() - 50, node.getCenterY() + 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countUpwardDiagonal++;
			winLineUpwardDiagonal.addNode(nodes[index]);
			checkUpwardDiagonalBackward(nodes[index],board);
		}
	}
	
	public void checkDownwardDiagonalForward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() + 50, node.getCenterY() + 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countDownwardDiagonal++;
			winLineDownwardDiagonal.addNode(nodes[index]);
			checkDownwardDiagonalForward(nodes[index],board);
		}
	}
	
	public void checkDownwardDiagonalBackward (HitBox node, ArrayOfNodes board) {
		Integer index = board.getNodeIndex(node.getCenterX() - 50, node.getCenterY() - 50);
		if (index != null && nodes[index].isSameType(node) && nodes[index].isOccupied() && node.isOccupied()) {
			countDownwardDiagonal++;
			winLineDownwardDiagonal.addNode(nodes[index]);
			checkDownwardDiagonalBackward(nodes[index],board);
		}
	}
	
	public boolean checkWinHorizontal (HitBox node, ArrayOfNodes board) {
		winLineHorizontal.addNode(node); 
		checkHorizontalForward(node,board);
		checkHorizontalBackward(node,board);
		if (countHorizontal >= 5) {
			highlightWinLine(winLineHorizontal);
			return true;
		}
		countHorizontal = 1;
		if (winLineHorizontal != null) {
			winLineHorizontal.empty();
		}
		return false;
	}
	
	public boolean checkWinVertical (HitBox node, ArrayOfNodes board) {
		winLineVertical.addNode(node);
		checkVerticalForward(node,board);
		checkVerticalBackward(node,board);
		if (countVertical >= 5) {
			highlightWinLine(winLineVertical);
			return true;
		}
		countVertical = 1;
		if (winLineVertical != null) {
			winLineVertical.empty();
		}
		return false;
	}
	
	public boolean checkWinUpwardDiagonal (HitBox node, ArrayOfNodes board) {
		winLineUpwardDiagonal.addNode(node);
		checkUpwardDiagonalForward(node,board);
		checkUpwardDiagonalBackward(node,board);
		if (countUpwardDiagonal >= 5) {
			highlightWinLine(winLineUpwardDiagonal);
			return true;
		}
		countUpwardDiagonal = 1;
		if (winLineUpwardDiagonal != null) {
			winLineUpwardDiagonal.empty();
		}
		return false;
	}
	
	public boolean checkWinDownwardDiagonal (HitBox node, ArrayOfNodes board) {
		winLineDownwardDiagonal.addNode(node);
		checkDownwardDiagonalForward(node,board);
		checkDownwardDiagonalBackward(node,board);
		if (countDownwardDiagonal >= 5) {
			highlightWinLine(winLineDownwardDiagonal);
			return true;
		}
		countDownwardDiagonal = 1;
		if (winLineDownwardDiagonal != null) {
			winLineDownwardDiagonal.empty();
		}
		return false;
	}
	
	public void highlightWinLine (ArrayOfNodes winLine) {
		g.setStroke(new BasicStroke(8));
		for (int i = 0; i < winLine.getLength(); i++) {
			if (turn % 2 != 0) {
				drawX(winLine.getNode(i),WIN_COLOR);
			}
			else {
				drawO(winLine.getNode(i),WIN_COLOR);
			}
		}
	}
	
	public boolean checkWin (HitBox node, ArrayOfNodes board) {
		if (checkWinHorizontal(node,board) || checkWinVertical(node,board) || checkWinUpwardDiagonal(node,board) || checkWinDownwardDiagonal(node,board)) {
			return true;
		}
		return false;
	}
	
	public void mousePressed(MouseEvent evt) { }
	public void mouseReleased(MouseEvent evt) { }
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
}
