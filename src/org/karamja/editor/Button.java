package org.karamja.editor;


public class Button {
	
    
    private int endX;
	private int endY;
	private int startX;
	private int startY;

	public Button(int startX, int startY, int endX, int endY){
    	this.setStartX(startX);
    	this.setStartY(startY);
    	this.setEndX(endX);
    	this.setEndY(endY);
    }

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
    
}
