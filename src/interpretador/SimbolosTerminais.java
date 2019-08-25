package interpretador;

import java.util.ArrayList;

public class SimbolosTerminais {
	
	ArrayList<String> identificadores;

	public int BuscaPalavraReservada(String token) {
		
		switch(token) {
		case "Program": return 1;
		case "Label": return 2;
		case "Const": return 3;
		case "Var": return 4;
		case "Procedure": return 5;
		case "Begin": return 6;
		case "End": return 7;
		case "Integer": return 8;
		case "Array": return 9;
		case "Of": return 10;
		case "Call": return 11;
		case "Goto": return 12;
		case "If": return 13;
		case "Then": return 14;
		case "Else": return 15;
		case "While": return 16;
		case "Do": return 17;
		case "Repeat": return 18;
		case "Until": return 19;
		case "Readln": return 20;
		case "Writeln": return 21;
		case "Or": return 22;
		case "And": return 23;
		case "Not": return 24;
		case "For": return 27;
		case "To": return 28;
		case "Case": return 29;
		case "+": return 30;
		case "-": return 31;
		case "*": return 32;
		case "/": return 33;
		case "[": return 34;
		case "]": return 35;
		case "(": return 36;
		case ")": return 37;
		case ":=": return 38;
		case ":": return 39;
		case "=": return 40;
		case ">": return 41;
		case ">=": return 42;
		case "<": return 43;
		case "<=": return 44;
		case "<>": return 45;
		case ",": return 46;
		case ";": return 47;
		case ".": return 49;
		case "..": return 50;
		case "$": return 51;
		default: return this.pegarCodigoToken(token);
		}
	}
	
	public void criarIdentificador(String identificador) {
		this.identificadores.add(identificador);
	}
	
	private int pegarCodigoToken(String token) {
		try {
			Integer.parseInt(token);
			return 26;
		} catch(NumberFormatException e) {
			if(this.verificarSeIdentificador(token)) {
				return 25;
			}
		}
		return 48;
	}
	
	private boolean verificarSeIdentificador(String token) {
		if(this.identificadores.contains(token)) {
			return true;
		}
		return false;
	}
}
