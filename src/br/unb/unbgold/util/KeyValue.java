package br.unb.unbgold.util;

public class KeyValue {
	
	Integer value;
	String label;
	public KeyValue(Integer value, String label) {
		super();
		this.value = value;
		this.label = label;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
