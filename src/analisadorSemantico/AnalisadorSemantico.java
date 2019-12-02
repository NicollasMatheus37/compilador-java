package analisadorSemantico;

import java.util.ArrayList;
import java.util.Stack;

import shared.Token;

public class AnalisadorSemantico {
	
	private VariableTable variableTable =  new VariableTable();
	private Stack<Token> identifierStack = new Stack<Token>();
	private Stack<String> expressionsTypes = new Stack<String>();
	private String lastProc = "";
	private String type;
	private String category;
	private Integer level = 0;
	
	// flags 
	private Boolean isStating = true;
	private Boolean isCallingProc = false;
	private Boolean isCommand = false;
	private Boolean isExpression = false;
	private Boolean isDefParam = false;
	
	public String anylize() {
		String response =  "";
		
		if((identifierStack.size() > 0) && isStating) {
			if(!category.isEmpty() && category.equals("CONST")) {
				type = "INTEGER";
			}
			response = addOnVariableTable();
		}
		
		return response;
	}
	
	private String addOnVariableTable() {
		String resp = "";
		
		while (!identifierStack.isEmpty()) {
			Token ident = identifierStack.pop();
			String response = variableTable.add(formatVariable(ident), level);
			
			Variable temp = formatVariable(ident);
			

			if(isDefParam) {
				Variable var = variableTable.getVar(lastProc, 0);
				var.addParam(formatVariable(ident).getType());
			}

			System.out.println(temp.getCategory() + ", " + temp.getName() + ", " + temp.getType() +", " + temp.getLevel());
			if(response.equalsIgnoreCase("ERRO")) {
				resp =  "identificador ja declarado";
				break;
			} 
		}	
//		clearParameters();
		return resp;
	}
	
	public void clearParameters() {
		this.identifierStack.clear();
		type = null;
		category = null;
	}
	
	public String searchOnIdentTable(String name) {
		if(variableTable.containsVariable(name, level)) {
			if(isExpression) {
				Variable var = variableTable.getVar(name, level);
				expressionsTypes.add(var.getType());
			}
			
			if(isCallingProc) {
				Variable var = variableTable.getVar(name, level);
				Variable last = variableTable.getVar(lastProc, 0);
			
				if((last.getParams()).contains(var.getType())) {
					return "";
				} else {
					return "tipo de parametro incorreto";
				}
			}
			
			return "";
		} else {
			return"Identificador não declarado";	
		}
	}
	
	public String checkExpressionTypes() {
		String response ="";
		if(isExpression) {
			int i,j = 0;
			while(!expressionsTypes.isEmpty()) {
				String temp = expressionsTypes.pop();
				if(!expressionsTypes.isEmpty()) {
					String temp2 = expressionsTypes.pop();
					if(!temp.equals(temp2)) {
						response ="expressão com tipos diferentes";
						break;
					}
				}
			}
//			
		}
		return response;
	}
	
	public void deleteAllByLevel(Integer lvl) {
		variableTable.deleteByLevel(lvl);
	}
	
	private Variable formatVariable(Token token) {
		return new Variable(token.getValor(), this.type, this.level, this.category);
	}
	
	public void setIsStating(Boolean isStt) {
		this.isStating = isStt;
	}
	
	public Boolean getIsStating() {
		return this.isStating;
	}
	
	public void setIsCommand(Boolean isStt) {
		this.isCommand = isStt;
	}
	
	public Boolean getIsCommand() {
		return this.isCommand;
	}
	
	public void setIsExpression(Boolean isStt) {
		this.isExpression = isStt;
	}
	
	public Boolean getIsExpression() {
		return this.isExpression;
	}
	
	public void setIsDefParam(Boolean isStt) {
		this.isDefParam = isStt;
	}
	
	public Boolean getIsDefParam() {
		return this.isDefParam;
	}
	
	public void setIsCallingProc(Boolean isStt) {
		this.isCallingProc = isStt;
	}
	
	public Boolean getIsCallingProc() {
		return this.isCallingProc;
	}
	
	public void setType(String tp){
		this.type = tp;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
	public void setLastProc(String last) {
		this.lastProc = last;
	}
	
	public void pushOnIdentifierStack(Token identifier){
		
		this.identifierStack.add(identifier);
	}
	
}