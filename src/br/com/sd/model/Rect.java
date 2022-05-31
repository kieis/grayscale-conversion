package br.com.sd.model;

import java.io.Serializable;

public class Rect implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
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
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rect getRect() {
		return new Rect(getX(), getY(), getWidth(), getHeight());
	}
	
	public void setRect(Rect rect) {
		setX(rect.getX());
		setY(rect.getY());
		setWidth(rect.getWidth());
		setHeight(rect.getHeight());
	}
	
	public boolean isEmpty() {
		return (getX() == 0 && getY() == 0 && getWidth() == 0 && getHeight() == 0);
	}
	
	public Rect() {
		setX(0);
		setY(0);
		setWidth(0);
		setHeight(0);
	}
	
	public Rect(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public Rect(Rect rect) {
		setRect(rect.getRect());
	}
}
