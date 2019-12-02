package analisadorSemantico;

import java.awt.List;
import java.util.ArrayList;

public class Variable {

	private String name;
	private String type;
	private String category;
	private Integer level;
	private ArrayList<String> parameters = new ArrayList<String>();
	
	public Variable() {}
	
	public Variable(String name, String type, Integer level, String category) {
		super();
		this.name = name;
		this.level = level;
		this.type = type;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level  = level;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void addParam(String param) {
		this.parameters.add(param);
	}
	
	public ArrayList<String> getParams() {
		return this.parameters;
	}
	
}
