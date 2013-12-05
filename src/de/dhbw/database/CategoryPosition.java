package de.dhbw.database;

public class CategoryPosition {

	public CategoryPosition() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryPosition(int position, int categoryId)
	{
		setPosition(position);
		setCategoryId(categoryId);
	}
	
	private int position;
	private int categoryId;
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
