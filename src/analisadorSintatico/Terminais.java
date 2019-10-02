package analisadorSintatico;

import java.util.HashMap;
import java.util.Map;

public class Terminais {

	private Map<String, Integer> tabelaTerminais = new HashMap<String, Integer>();
	
	public Terminais() {
		this.iniciaTabela();
	}
	

	public Integer getTerminal(String terminal) {
		if(tabelaTerminais.containsKey(terminal)) {
    		return tabelaTerminais.get(terminal);
    	}
		return 0;
	}
	
	public Boolean containsKey(String key) {
		return tabelaTerminais.containsKey(key);
	}
	
	private void iniciaTabela() {
		tabelaTerminais.put("IDENTIFICADOR", 25);
		tabelaTerminais.put("INTEIRO", 26);
		tabelaTerminais.put("LITERAL", 48);
		tabelaTerminais.put("+", 30);
        tabelaTerminais.put("-", 31);
        tabelaTerminais.put("*", 32);
        tabelaTerminais.put("/", 33);
        tabelaTerminais.put("=", 40);
        tabelaTerminais.put(",", 46);
        tabelaTerminais.put(";", 47);
        tabelaTerminais.put("$", 51);
        tabelaTerminais.put("[", 34);
        tabelaTerminais.put("]", 35);
        tabelaTerminais.put("(", 36);
        tabelaTerminais.put(")", 37);
        tabelaTerminais.put(":", 39);
        tabelaTerminais.put(">", 41);
        tabelaTerminais.put("<", 43);
        tabelaTerminais.put(".", 49);
        tabelaTerminais.put(">=", 42);
        tabelaTerminais.put("<=", 44);
        tabelaTerminais.put("<>", 45);
        tabelaTerminais.put(":=", 38);
        tabelaTerminais.put("..", 50);
        tabelaTerminais.put("PROGRAM", 1);
        tabelaTerminais.put("LABEL", 2);
        tabelaTerminais.put("CONST", 3);
        tabelaTerminais.put("VAR", 4);
        tabelaTerminais.put("PROCEDURE", 5);
        tabelaTerminais.put("BEGIN", 6);
        tabelaTerminais.put("END", 7);
        tabelaTerminais.put("INTEGER", 8);
        tabelaTerminais.put("ARRAY", 9);
        tabelaTerminais.put("OF", 10);
        tabelaTerminais.put("CALL", 11);
        tabelaTerminais.put("GOTO", 12);
        tabelaTerminais.put("IF", 13);
        tabelaTerminais.put("THEN", 14);
        tabelaTerminais.put("ELSE", 15);
        tabelaTerminais.put("WHILE", 16);
        tabelaTerminais.put("DO", 17);
        tabelaTerminais.put("REPEAT", 18);
        tabelaTerminais.put("UNTIL", 19);
        tabelaTerminais.put("READLN", 20);
        tabelaTerminais.put("WRITELN", 21);
        tabelaTerminais.put("OR", 22);
        tabelaTerminais.put("AND", 23);
        tabelaTerminais.put("NOT", 24);
        tabelaTerminais.put("FOR", 27);
        tabelaTerminais.put("TO", 28);
        tabelaTerminais.put("CASE", 29);
        
	}
	
}
