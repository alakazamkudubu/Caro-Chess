package caro;

public class caromain {
	public static void main(String[] args) {
		Board gameBoard = new Board();
		
		HitBox[] gridHitBox = new HitBox[(gameBoard.getNumColumn()-1) * (gameBoard.getNumRow()- gameBoard.getDashboardHeight()/gameBoard.getGridSize()- 1)];
		int hitBoxIndex = 0;
		for (int i = 1; i < gameBoard.getNumColumn(); i++) {
			for (int j = 1 + (gameBoard.getDashboardHeight()/gameBoard.getGridSize()); j < gameBoard.getNumRow(); j++) {
				gridHitBox[hitBoxIndex] = new HitBox(gameBoard.getGridSize() * i, gameBoard.getGridSize() * j, gameBoard.getGridHitBoxSize(), gameBoard.getGridHitBoxSize());
				hitBoxIndex++;
			}
		}
		
		HitBox[] dashboardHitBox = new HitBox[3];
		for (int i = 0; i < 3; i++) {
			dashboardHitBox[i] = new HitBox(gameBoard.getDashboardButtonWidth()/2 + gameBoard.getDashboardButtonWidth()*i, gameBoard.getDashboardButtonHeight()/2, gameBoard.getDashboardButtonWidth(), gameBoard.getDashboardButtonHeight());
		}
		
		HitBox[] menuHitBox = new HitBox[4];
		for (int i = 0; i < 3; i++) {
			menuHitBox[i] = new HitBox(gameBoard.getPanelWidth()/2, gameBoard.getMenuHeight()/2 + gameBoard.getMenuButtonHeight()/2 + i*gameBoard.getMenuButtonHeight(), gameBoard.getMenuButtonWidth(), gameBoard.getMenuButtonHeight());
		}
		// Close Menu button
		menuHitBox[3] = new HitBox(gameBoard.getPanelWidth()/2+gameBoard.getMenuWidth()/2+gameBoard.getGridSize()/2,gameBoard.getPanelHeight()/2-gameBoard.getMenuHeight()/2-gameBoard.getGridSize()/2,gameBoard.getGridSize(),gameBoard.getGridSize());
		
		HitBox[] playerMenuHitBox = new HitBox[3];
		for (int i = 0; i < 3; i++) {
			playerMenuHitBox[i] = new HitBox(gameBoard.getPanelWidth()/2,gameBoard.getPanelHeight()/2 - gameBoard.getPlayerMenuButtonHeight()/2 + i*gameBoard.getPlayerMenuButtonHeight(),gameBoard.getPlayerMenuButtonWidth(),gameBoard.getPlayerMenuButtonHeight());
		}
		
		GetGameClick gameListener = new GetGameClick(gridHitBox,dashboardHitBox,menuHitBox,playerMenuHitBox,gameBoard);
		gameBoard.getPanel().addMouseListener(gameListener);
		
	}
}
