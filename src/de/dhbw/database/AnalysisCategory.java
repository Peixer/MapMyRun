package de.dhbw.database;

public class AnalysisCategory {

	public AnalysisCategory(int id, String name, String imageName, String format) {
		setId(id);
		setName(name);
		setImageName(imageName);
		setFormat(format);
		
	}
	
	private int id;
	private String name;
	private String imageName;
	private String format;
	
	
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
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
