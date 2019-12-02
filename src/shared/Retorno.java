package shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import analisadorSemantico.Variable;

public class Retorno {

	private boolean hasError = false;
	private Stack<Erros> errorStack;
	private Stack<Token> tokenStack;
	private Stack<Token> sintaticStack;
	private Map<String, Variable>  variableTable = new HashMap<String, Variable>();
	
	public Map<String, Variable> getVariableTable() {
		return variableTable;
	}

	public void setVariableTable(Map<String, Variable> variableTable) {
		this.variableTable = variableTable;
	}

	public Stack<Token> getSintaticStack() {
		return sintaticStack;
	}

	public void setSintaticStack(Stack<Token> sintaticStack) {
		this.sintaticStack = sintaticStack;
	}

	public boolean gethasError() {
		return hasError;
	}
	
	public void setHasError(boolean isError) {
		this.hasError = isError;
	}
	
	public Stack<Erros> getErrorStack() {
		Stack<Erros> invertedStack = new Stack<Erros>();
		try {
			while(!this.errorStack.isEmpty()) {
				invertedStack.add(errorStack.pop());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return invertedStack;
	}
	
	public void setErrorStack(Stack<Erros> errorStack) {
		this.errorStack = errorStack;
	}
	
	public Stack<Token> getTokenStack() {
		Stack<Token> invertedStack = new Stack<Token>();
		try {
			while(!this.tokenStack.isEmpty()) {
				invertedStack.add(tokenStack.pop());
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return invertedStack;
	}
	
	public void setTokenStack(Stack<Token> tokenStack) {
		this.tokenStack = tokenStack;
	}
	
	public Stack<Token> getNoRevertTokenStack() {
		return tokenStack;
	}
}
