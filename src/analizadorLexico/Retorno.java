package analizadorLexico;

import java.util.Stack;

public class Retorno {

	public boolean isError = false;
	public Stack<Erros> errorStack;
	public Stack<Token> tokenStack;
	
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public Stack<Erros> getErrorStack() {
		return errorStack;
	}
	public void setErrorStack(Stack<Erros> errorStack) {
		this.errorStack = errorStack;
	}
	public Stack<Token> getTokenStack() {
		return tokenStack;
	}
	public void setTokenStack(Stack<Token> tokenStack) {
		this.tokenStack = tokenStack;
	}
	
	
}
