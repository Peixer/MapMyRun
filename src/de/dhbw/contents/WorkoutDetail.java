package de.dhbw.contents;

public class WorkoutDetail {
	private String detailKey;
	private String detailValue;
	
	public WorkoutDetail(String detailKey, String detailValue){
		this.detailKey = detailKey;
		this.detailValue = detailValue;
	}
	
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
