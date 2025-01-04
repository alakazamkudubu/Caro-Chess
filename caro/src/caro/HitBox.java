package caro;

public class HitBox {
	private int sizeX;
	private int sizeY;
	private int centerX;
	private int centerY;
	private int clickX;
	private int clickY;
	private boolean isX;
	private boolean occupied;
	
	public HitBox(int centerX, int centerY, int sizeX, int sizeY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public boolean isX() {
		return isX;
	}
	
	public boolean isSameType(HitBox node) {
		if (isX == node.isX()) {
			return true;
		}
		return false;
	}
	
	public void setIsX() {
		this.isX = true;
	}
	
	public void setIsO() {
		this.isX = false;
	}
	
	public void setClickX(int clickX) {
		if (occupied) {
			return;
		}
		this.clickX = clickX;
	}
	
	public void setClickY(int clickY) {
		if (occupied) {
			return;
		}
		this.clickY = clickY;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean isValidGameClick() {
		if (clickX >= centerX - (sizeX/2) && clickX <= centerX + (sizeX/2) && clickY >= centerY - (sizeY/2) && clickY <= centerY + (sizeY/2) && !occupied) {
			this.occupied = true;
			return true;
		}
		setClickX(0);
		setClickY(0);
		return false;
	}
	
	public boolean isValidClick() {
		if (clickX >= centerX - (sizeX/2) && clickX <= centerX + (sizeX/2) && clickY >= centerY - (sizeY/2) && clickY <= centerY + (sizeY/2)) {
			return true;
		}
		setClickX(0);
		setClickY(0);
		return false;
	}
	
	
}
