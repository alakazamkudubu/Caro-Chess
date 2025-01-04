package caro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Board {
	private int panelWidth = 1200;
	private int panelHeight = 1200;
	private int gridSize = 50;
	private int gridHitBoxSize = 30;
	private int numColumn = panelWidth/gridSize;
	private int numRow = panelHeight/gridSize;
	
	private int dashboardWidth = panelWidth;
	private int dashboardHeight = panelHeight/12;
	private int dashboardButtonWidth = panelWidth/6;
	private int dashboardButtonHeight = dashboardHeight;
	private Font dashboardFont = new Font("Arial", Font.BOLD, 20);
	
	private int playerWidth = panelWidth/8;
	private int scoreboardWidth = panelHeight/4;
	private Font scoreBoardFont = new Font("Arial", Font.BOLD, 50);

	private Color player1Color = Color.PINK;
	private Color player2Color = Color.MAGENTA;
	private int playerMenuWidth = panelWidth/2;
	private int playerMenuHeight = panelHeight/6;
	private int playerMenuButtonWidth = playerMenuWidth;
	private int playerMenuButtonHeight = playerMenuHeight/4;

	private Color menuBackGroundColor = new Color(99, 44, 32);
	private int menuWidth = panelWidth/3;
	private int menuHeight = panelHeight/2;
	private int menuButtonWidth = menuWidth;
	private int menuButtonHeight = menuHeight/3;
	private Font menuFont = new Font("Arial", Font.BOLD, 50);

	private Color backgroundColor = new Color(237, 208, 157);
	private Color gridLineColor = new Color(176, 114, 7);
	private DrawingPanel panel = new DrawingPanel(panelWidth,panelHeight);
	private Graphics2D g = panel.getGraphics();
	
	public Board () {
		panel.setBackground(backgroundColor);
		drawGrid();
		drawMainMenu();
	}
	public void drawMainMenu() {
		g.setStroke(new BasicStroke(5));
		g.setColor(menuBackGroundColor);
		g.fillRect(panelWidth/2-menuWidth/2-gridSize, panelHeight/2-menuHeight/2-gridSize, menuWidth+2*gridSize, menuHeight+2*gridSize);
		g.setColor(Color.ORANGE);
		g.fillRect(panelWidth/2+menuWidth/2, panelHeight/2-menuHeight/2-gridSize, gridSize, gridSize);
		g.setColor(Color.RED);
		g.drawLine(panelWidth/2+menuWidth/2+5, panelHeight/2-menuHeight/2-gridSize+5, panelWidth/2+menuWidth/2+gridSize-5, panelHeight/2-menuHeight/2-5);
		g.drawLine(panelWidth/2+menuWidth/2+gridSize-5, panelHeight/2-menuHeight/2-gridSize+5, panelWidth/2+menuWidth/2+5, panelHeight/2-menuHeight/2-5);

		g.setColor(Color.GREEN);
		g.fillRect(panelWidth/2-menuWidth/2, panelHeight/2-menuHeight/2, menuWidth, menuHeight/3);
		g.setColor(Color.BLACK);
		drawCenteredString("QUICK GAME", panelWidth/2-menuWidth/2, panelHeight/2-menuHeight/2, menuWidth, menuHeight/3, menuFont);
		g.setColor(Color.YELLOW);
		g.fillRect(panelWidth/2-menuWidth/2, panelHeight/2-menuHeight/6, menuWidth, menuHeight/3);
		g.setColor(Color.BLACK);
		drawCenteredString("BEST OF 3", panelWidth/2-menuWidth/2, panelHeight/2-menuHeight/6, menuWidth, menuHeight/3, menuFont);
		g.setColor(Color.RED);
		g.fillRect(panelWidth/2-menuWidth/2, panelHeight/2+menuHeight/6, menuWidth, menuHeight/3);
		g.setColor(Color.BLACK);
		drawCenteredString("QUIT", panelWidth/2-menuWidth/2, panelHeight/2+menuHeight/6, menuWidth, menuHeight/3, menuFont);
	}
	
	public void drawQuickGameDashboard() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dashboardWidth, dashboardHeight);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("NEW GAME",0,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		g.setColor(Color.GRAY);
		g.fillRect(dashboardButtonWidth, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("MENU",dashboardButtonWidth,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		g.setColor(Color.RED);
		g.fillRect(dashboardButtonWidth*2, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("QUIT",dashboardButtonWidth*2,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
	}
	
	public void drawScoredGameDashboard(boolean player1IsX, int player1Score, int player2Score, boolean endGame) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dashboardWidth, dashboardHeight);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		if (!endGame) {
			drawCenteredString("REPLAY ROUND",0,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		}
		else {
			drawCenteredString("NEXT ROUND",0,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		}
		g.setColor(Color.GRAY);
		g.fillRect(dashboardButtonWidth, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("MENU",dashboardButtonWidth,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		g.setColor(Color.RED);
		g.fillRect(dashboardButtonWidth*2, 0, dashboardButtonWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("QUIT",dashboardButtonWidth*2,0,dashboardButtonWidth,dashboardButtonHeight,dashboardFont);
		g.setColor(player1Color);
		g.fillRect(dashboardButtonWidth*3, 0, playerWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("PLAYER 1",dashboardButtonWidth*3,0,playerWidth,dashboardButtonHeight/2,dashboardFont);
		g.setStroke(new BasicStroke(5));
		if (player1IsX) {
			g.setColor(Color.BLUE);
			g.drawLine(dashboardButtonWidth*3+gridSize+5, dashboardButtonHeight/2-3, dashboardButtonWidth*3+2*gridSize-5, dashboardButtonHeight-13);
			g.drawLine(dashboardButtonWidth*3+2*gridSize-5, dashboardButtonHeight/2-3, dashboardButtonWidth*3+gridSize+5, dashboardButtonHeight-13);
		}
		else {
			g.setColor(Color.RED);
			g.drawOval(dashboardButtonWidth*3+gridSize+5, dashboardButtonHeight/2-3, 40, 40);
		}
		g.setColor(Color.CYAN);
		g.fillRect(dashboardButtonWidth*3+playerWidth, 0, scoreboardWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("X ALWAYS GOES FIRST!",dashboardButtonWidth*3+playerWidth,dashboardButtonHeight/2,scoreboardWidth,dashboardButtonHeight/2,dashboardFont);
		String score = String.format("%d - %d", player1Score, player2Score);
		drawCenteredString(score,dashboardButtonWidth*3+playerWidth,5,scoreboardWidth,dashboardButtonHeight/2,scoreBoardFont);
		g.setColor(player2Color);
		g.fillRect(dashboardButtonWidth*3+playerWidth+scoreboardWidth, 0, playerWidth, dashboardButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("PLAYER 2",dashboardButtonWidth*3+playerWidth+scoreboardWidth,0,playerWidth,dashboardButtonHeight/2,dashboardFont);
		if (player1IsX) {
			g.setColor(Color.RED);
			g.drawOval(dashboardButtonWidth*3+gridSize+5+playerWidth+scoreboardWidth, dashboardButtonHeight/2-3, 40, 40);
		}
		else {
			
			g.setColor(Color.BLUE);
			g.drawLine(dashboardButtonWidth*3+gridSize+5+playerWidth+scoreboardWidth, dashboardButtonHeight/2-3, dashboardButtonWidth*3+2*gridSize-5+playerWidth+scoreboardWidth, dashboardButtonHeight-13);
			g.drawLine(dashboardButtonWidth*3+2*gridSize-5+playerWidth+scoreboardWidth, dashboardButtonHeight/2-3, dashboardButtonWidth*3+gridSize+5+playerWidth+scoreboardWidth, dashboardButtonHeight-13);
		}
	}
	
	public void drawPlayerChoice(boolean player1IsX) {
		g.setColor(menuBackGroundColor);
		g.fillRect(panelWidth/2-playerMenuWidth/2-gridSize/2,panelHeight/2-playerMenuHeight/2-gridSize/2, playerMenuWidth+gridSize, playerMenuHeight+gridSize);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(panelWidth/2-playerMenuWidth/2, panelHeight/2-playerMenuHeight/2, playerMenuWidth, playerMenuHeight/4);
		g.setColor(Color.BLACK);
		drawCenteredString("PLAYER CHOICE MENU",panelWidth/2-playerMenuWidth/2, panelHeight/2-playerMenuHeight/2, playerMenuWidth, playerMenuButtonHeight,dashboardFont);

		if (player1IsX) {
			g.setColor(Color.GREEN);
			g.fillRect(panelWidth/2-playerMenuWidth/2,panelHeight/2-playerMenuHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight);
			g.setColor(Color.BLACK);
			drawCenteredString("PLAYER 1 CHOOSES X               PLAYER 2 CHOOSES O",panelWidth/2-playerMenuWidth/2,panelHeight/2-playerMenuHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight,dashboardFont);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(panelWidth/2-playerMenuWidth/2,panelHeight/2,playerMenuWidth,playerMenuButtonHeight);
			g.setColor(Color.BLACK);
			drawCenteredString("PLAYER 1 CHOOSES O               PLAYER 2 CHOOSES X",panelWidth/2-playerMenuWidth/2,panelHeight/2,playerMenuWidth,playerMenuButtonHeight,dashboardFont);
		}
		else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(panelWidth/2-playerMenuWidth/2,panelHeight/2-playerMenuHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight);
			g.setColor(Color.BLACK);
			drawCenteredString("PLAYER 1 CHOOSES X               PLAYER 2 CHOOSES O",panelWidth/2-playerMenuWidth/2,panelHeight/2-playerMenuHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight,dashboardFont);
			g.setColor(Color.GREEN);
			g.fillRect(panelWidth/2-playerMenuWidth/2,panelHeight/2,playerMenuWidth,playerMenuButtonHeight);
			g.setColor(Color.BLACK);
			drawCenteredString("PLAYER 1 CHOOSES O               PLAYER 2 CHOOSES X",panelWidth/2-playerMenuWidth/2,panelHeight/2,playerMenuWidth,playerMenuButtonHeight,dashboardFont);
		}
		g.setColor(Color.CYAN);
		g.fillRect(panelWidth/2-playerMenuWidth/2,panelHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight);
		g.setColor(Color.BLACK);
		drawCenteredString("CONFIRM",panelWidth/2-playerMenuWidth/2,panelHeight/2+playerMenuHeight/4,playerMenuWidth,playerMenuButtonHeight,dashboardFont);
		
	}
	
	public void drawGrid() {
		g.setStroke(new BasicStroke(5));
		g.setColor(gridLineColor);
		for (int i = 1; i < numColumn; i++) {
			g.drawLine(gridSize*i, 0, gridSize*i, panelHeight);
			if (i < numRow) {
				g.drawLine(0, gridSize*i, panelWidth, gridSize*i);
			}
		}
	}
	
	public void resetQuickGameBoard() {
		panel.clear();
		drawGrid();
		drawQuickGameDashboard();
	}
	
	public void resetScoredGameBoard(boolean player1IsX, int player1Score, int player2Score, boolean gameEnd) {
		panel.clear();
		drawGrid();
		drawScoredGameDashboard(player1IsX,player1Score,player2Score, gameEnd);
	}
	
	public void drawCenteredString(String text, int x, int y, int width, int height, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    x = x + (width - metrics.stringWidth(text)) / 2;
	    y = y + height/2 + metrics.getHeight()/3;
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
	
	public int getPanelWidth() {
		return panelWidth;
	}
	
	public int getPanelHeight() {
		return panelHeight;
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public int getGridHitBoxSize() {
		return gridHitBoxSize;
	}
	
	public int getNumColumn() {
		return numColumn;
	}
	
	public int getNumRow() {
		return numRow;
	}
	
	public int getDashboardWidth() {
		return dashboardWidth;
	}
	
	public int getDashboardHeight() {
		return dashboardHeight;
	}
	
	public int getDashboardButtonWidth() {
		return dashboardButtonWidth;
	}
	
	public int getDashboardButtonHeight() {
		return dashboardButtonHeight;
	}
	
	public Font getDashboardFont() {
		return dashboardFont;
	}
	
	public int getMenuWidth() {
		return menuWidth;
	}
	
	public int getMenuHeight() {
		return menuHeight;
	}
	
	public int getMenuButtonWidth() {
		return menuButtonWidth;
	}
	
	public int getMenuButtonHeight() {
		return menuButtonHeight;
	}
	
	public int getPlayerMenuWidth() {
		return playerMenuWidth;
	}
	
	public int getPlayerMenuHeight() {
		return playerMenuHeight;
	}
	
	public int getPlayerMenuButtonWidth() {
		return playerMenuButtonWidth;
	}
	
	public int getPlayerMenuButtonHeight() {
		return playerMenuButtonHeight;
	}
	
	public Font getMenuFont() {
		return menuFont;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public Color getGridLineColor() {
		return gridLineColor;
	}
	
	public DrawingPanel getPanel() {
		return panel;
	}
	
	public Graphics2D getGraphics() {
		return g;
	}
}
