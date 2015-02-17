package org.karamja.editor;

public class DrawString {
	
	private String string;
	private int x;
	private int y;

	public DrawString(String s, int x, int y){
		this.string = s;
		this.x = x;
		this.y = y;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
