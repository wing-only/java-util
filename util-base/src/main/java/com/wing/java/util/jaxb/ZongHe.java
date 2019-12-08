package com.wing.java.util.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class ZongHe {

	String age;
	String Height;
	String weight;

	public ZongHe() {
		super();
	}

	public ZongHe(String age, String height, String weight) {
		super();
		this.age = age;
		Height = height;
		this.weight = weight;
	}

	@XmlAttribute
	public String getAge() {
		return age;
	}

	public String getHeight() {
		return Height;
	}

	public String getWeight() {
		return weight;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setHeight(String height) {
		Height = height;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "ZongHe [age=" + age + ", Height=" + Height + ", weight="
				+ weight + "]";
	}

}
