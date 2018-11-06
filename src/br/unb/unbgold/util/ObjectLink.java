package br.unb.unbgold.util;

public class ObjectLink {
	String desc;
	String link;
	
	
	public ObjectLink(String desc, String link) {
		super();
		this.desc = desc;
		this.link = link;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
