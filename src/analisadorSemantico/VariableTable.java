package analisadorSemantico;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariableTable {

	private Map<String, Variable>  variableTable = new HashMap<String, Variable>();
	
	public String add(Variable variable,Integer level) {
		if(containsVariable(variable.getName(), level)) {
			return "ERRO";
		} else {
			variableTable.put(variable.getName()+"_"+level, variable);
			return "sucesso";
		}
		
	}
	
	public Variable getVar(String var, Integer level) {
		return variableTable.get(var+"_"+level);
	}
	
	public void delete(String key,Integer level) {
		variableTable.remove(key+"_"+level);
	}
	
	public void deleteByLevel(Integer level) {
		ArrayList<String> aux = new ArrayList<String>();
		
		for (Map.Entry<String, Variable> variable : variableTable.entrySet()) {
			if(variable.getValue().getLevel() == level) {
				aux.add(variable.getKey());
			}
		}

		variableTable.keySet().removeAll(aux);
	}
	
	public boolean containsVariable(String key, Integer level) {
		
		if(variableTable.containsKey(key+"_"+level)) {
				return true;
			
		} else {
			return false;
			
		}
		
	}
}
