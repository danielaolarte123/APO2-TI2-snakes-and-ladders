package model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1;
	private String nickname;
	private int pos;
	private int row;
	private int column;
	private int  movements;
	private User left;
	private User right;
	private User parent;
	

	public User(String nickname, int pos, int row, int column, int movements) {
		this.setNickname(nickname);
		this.setPos(pos);
		this.row = row;
		this.column = column;
		this.movements = movements;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getMovements() {
		return movements;
	}

	public void setMirror(int movements) {
		this.movements = movements;
	}

	public User getLeft() {
		return left;
	}

	public void setLeft(User left) {
		this.left = left;
	}

	public User getRight() {
		return right;
	}

	public void setRight(User right) {
		this.right = right;
	}

	public User getParent() {
		return parent;
	}
	
	public void setParent(User parent) {
		this.parent = parent;
	}

	public String getData() {
		String n = nickname + " " + row + " " + column + " " + movements + " " + pos;
		return n;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
}
