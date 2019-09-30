package analizadorLexico;

import java.util.Stack;

public class Retorno {

	private boolean hasError = false;
	private Stack<Erros> errorStack;
	private Stack<Token> tokenStack;
	
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
	
	
}
