package com.wing.java.util.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "xml")
public class User {

	private Integer id;
	private String name;
	private Integer age;
	private Study study;
	private List<Hobby> hobbys = new ArrayList<Hobby>();

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@XmlElementWrapper(name = "hobbys")
	@XmlElements(value = { @XmlElement(name = "hobby", type = Hobby.class) })
	public List getHobbys() {
		return hobbys;
	}

	public void setHobbys(List hobbys) {
		this.hobbys = hobbys;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", study=" + study + ", hobbys=" + hobbys + "]";
	}

	public static void main(String[] args) throws Exception {
		
		//bean -> xml
		 ZongHe zongHe = new ZongHe("12", "1.87", "134");
		 Study study = new Study("81", "82", "90");
		 study.setZongHe(zongHe);
		 Hobby hobby1 = new Hobby("游泳", "河水");
		 Hobby hobby2 = new Hobby("跑步", "操场");
		 List<Hobby> hobbys = new ArrayList<Hobby>();
		 hobbys.add(hobby1);
		 hobbys.add(hobby2);
		 User user = new User();
		 user.setId(10001);
		 user.setName("张三");
		 user.setAge(13);
		 user.setStudy(study);
		 user.setHobbys(hobbys);
		
		 String xml = JaxbUtil.toXml(User.class, user);
		 System.err.println(xml);

		
		//xml -> bean
//		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><xml><age>13</age><hobbys><hobby><name>游泳</name></hobby><hobby><name>跑步</name></hobby></hobbys><id>10001</id><name>张三</name><study><chinese>81</chinese><english>90</english><math>82</math><zongHe age=\"12\"><height>1.87</height><weight>134</weight></zongHe></study></xml>";
//		User user = JaxbUtil.fromXml(User.class, xml);
//		System.err.println(user);

	}

}
