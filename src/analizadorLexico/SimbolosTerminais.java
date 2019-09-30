package analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimbolosTerminais {

    ArrayList<String> identificadores;
    Map<Character, Integer> simbolosPrimarios = new HashMap<Character, Integer>();
    Map<String, Integer> simbolosSecundarios = new HashMap<String, Integer>();
    Map<String, Integer> palavrasReservadas = new HashMap<String, Integer>();
    
    public SimbolosTerminais() {
    	this.iniciaHashMapTerminais();
    }

    public void iniciaHashMapTerminais() {
    	
    	// simbolos primarios
        simbolosPrimarios.put('+', 30);
        simbolosPrimarios.put('-', 31);
        simbolosPrimarios.put('*', 32);
        simbolosPrimarios.put('/', 33);
        simbolosPrimarios.put('=', 40);
        simbolosPrimarios.put(',', 46);
        simbolosPrimarios.put(';', 47);
        simbolosPrimarios.put('$', 51);
        simbolosPrimarios.put('[', 34);
        simbolosPrimarios.put(']', 35);
        simbolosPrimarios.put('(', 36);
        simbolosPrimarios.put(')', 37);
        simbolosPrimarios.put(':', 39);
        simbolosPrimarios.put('>', 41);
        simbolosPrimarios.put('<', 43);
        simbolosPrimarios.put('.', 49);
        
        // simbolos secundarios
        simbolosSecundarios.put(">=", 42);
        simbolosSecundarios.put("<=", 44);
        simbolosSecundarios.put("<>", 45);
        simbolosSecundarios.put(":=", 38);
        simbolosSecundarios.put("..", 50);

        // palavras reservadas
        palavrasReservadas.put("PROGRAM", 1);
        palavrasReservadas.put("LABEL", 2);
        palavrasReservadas.put("CONST", 3);
        palavrasReservadas.put("VAR", 4);
        palavrasReservadas.put("PROCEDURE", 5);
        palavrasReservadas.put("BEGIN", 6);
        palavrasReservadas.put("END", 7);
        palavrasReservadas.put("INTEGER", 8);
        palavrasReservadas.put("ARRAY", 9);
        palavrasReservadas.put("OF", 10);
        palavrasReservadas.put("CALL", 11);
        palavrasReservadas.put("GOTO", 12);
        palavrasReservadas.put("IF", 13);
        palavrasReservadas.put("THEN", 14);
        palavrasReservadas.put("ELSE", 15);
        palavrasReservadas.put("WHILE", 16);
        palavrasReservadas.put("DO", 17);
        palavrasReservadas.put("REPEAT", 18);
        palavrasReservadas.put("UNTIL", 19);
        palavrasReservadas.put("READLN", 20);
        palavrasReservadas.put("WRITELN", 21);
        palavrasReservadas.put("OR", 22);
        palavrasReservadas.put("AND", 23);
        palavrasReservadas.put("NOT", 24);
        palavrasReservadas.put("FOR", 27);
        palavrasReservadas.put("TO", 28);
        palavrasReservadas.put("CASE", 29);
    }

    public int getSimboloPrimario(char caracter) {
    	if(simbolosPrimarios.containsKey(caracter)) {
    		return simbolosPrimarios.get(caracter);
    	}
    	return 0;
    }
    
    public int getSimbolosSecundarios(char caracter, char proxCaracter) {
    	String composto = ""; 
    	composto += caracter + proxCaracter;
    	if(simbolosSecundarios.containsKey(composto)) {
    		return simbolosSecundarios.get(composto);
    	}
    	return 0;
    }
    
    public int getPalavraReservada(String palavra) {
    	if(palavrasReservadas.containsKey(palavra)) {
    		return palavrasReservadas.get(palavra);
    	}
    	return 0;
    }
}
