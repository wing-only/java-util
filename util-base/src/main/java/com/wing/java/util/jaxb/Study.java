package com.wing.java.util.jaxb;

public class Study {

	private String chinese;
	private String math;
	private String english;
	private ZongHe zongHe;

	public Study() {
		super();
	}

	public Study(String chinese, String math, String english) {
		super();
		this.chinese = chinese;
		this.math = math;
		this.english = english;
	}

	public String getChinese() {
		return chinese;
	}

	public String getMath() {
		return math;
	}

	public String getEnglish() {
		return english;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public void setMath(String math) {
		this.math = math;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public ZongHe getZongHe() {
		return zongHe;
	}

	public void setZongHe(ZongHe zongHe) {
		this.zongHe = zongHe;
	}

	@Override
	public String toString() {
		return "Study [chinese=" + chinese + ", math=" + math + ", english="
				+ english + ", zongHe=" + zongHe + "]";
	}

}
