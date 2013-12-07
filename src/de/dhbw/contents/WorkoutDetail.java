package de.dhbw.contents;

public class WorkoutDetail {
	
	//Überschrift in der Detailauswertung für einzelne Kategorien (Dauer, Distanz etc.)
	private String detailKey;
	//Wert der einzelnen Kategorien in der Detailauswertung
	private String detailValue;
	
	//Konstruktor 
	public WorkoutDetail(String detailKey, String detailValue){
		this.detailKey = detailKey;
		this.detailValue = detailValue;
	}
	
	//Getter und Setter 
	
	public String getDetailKey() {
		return detailKey;
	}
	public void setDetailKey(String detailKey) {
		this.detailKey = detailKey;
	}
	public String getDetailValue() {
		return detailValue;
	}
	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}
	
	
}
