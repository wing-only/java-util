package com.wing.java.util.jaxb;

public class Hobby {

	private String name;
	private String location;

	public Hobby() {
		super();
	}

	public Hobby(String name) {
		super();
		this.name = name;
	}
	
	

	public Hobby(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Hobby [name=" + name + "]";
	}

}
