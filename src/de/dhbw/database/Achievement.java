package de.dhbw.database;

public class Achievement {
	
	private int id;
	private String name;
	private String description;
	private String imageName;
	
	private String requiredUnit;
	private int requiredNumber;
	
	private boolean achieved;
	
	/*
	 	Code to get the ID from a String (http://stackoverflow.com/questions/9481334/how-to-replace-r-drawable-somestring)
	 	String mDrawableName = "myimg";
		int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
	 */
	
	public Achievement() {
		// default contructor
	}
	
	public Achievement(int id, String name, String description, String imageName, String requiredUnit, int requiredNumber) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageName = imageName;
		this.requiredUnit = requiredUnit;
		this.requiredNumber = requiredNumber;
	}
	
	public Achievement(String name, String description, String imageName, String requiredUnit, int requiredNumber) {
		this.id = -1;
		this.name = name;
		this.description = description;
		this.imageName = imageName;
		this.requiredUnit = requiredUnit;
		this.requiredNumber = requiredNumber;
	}
	
	public Achievement(String name, String description, String imageName, String requiredUnit, int requiredNumber, boolean achieved) {
		this.id = -1;
		this.name = name;
		this.description = description;
		this.imageName = imageName;
		this.requiredUnit = requiredUnit;
		this.requiredNumber = requiredNumber;
		this.achieved = achieved;
	}

	public String toString()
	{
		String string = "ID: "+id+", Name: "+name+", Description: "+description+", ImageName: "
							+imageName+", Requirement: "+requiredNumber+" "+requiredUnit+", Achieved: "+achieved;
		return string;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getRequiredUnit() {
		return requiredUnit;
	}

	public void setRequiredUnit(String requiredUnit) {
		this.requiredUnit = requiredUnit;
	}

	public int getRequiredNumber() {
		return requiredNumber;
	}

	public void setRequiredNumber(int requiredNumber) {
		this.requiredNumber = requiredNumber;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	
}
