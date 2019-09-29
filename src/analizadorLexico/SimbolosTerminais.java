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
    	simbolosPrimarios.put(' ', -1);
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
        palavrasReservadas.put("Program", 1);
        palavrasReservadas.put("Label", 2);
        palavrasReservadas.put("Const", 3);
        palavrasReservadas.put("Var", 4);
        palavrasReservadas.put("Procedure", 5);
        palavrasReservadas.put("Begin", 6);
        palavrasReservadas.put("End", 7);
        palavrasReservadas.put("Integer", 8);
        palavrasReservadas.put("Array", 9);
        palavrasReservadas.put("Of", 10);
        palavrasReservadas.put("Call", 11);
        palavrasReservadas.put("Goto", 12);
        palavrasReservadas.put("If", 13);
        palavrasReservadas.put("Then", 14);
        palavrasReservadas.put("Else", 15);
        palavrasReservadas.put("While", 16);
        palavrasReservadas.put("Do", 17);
        palavrasReservadas.put("Repeat", 18);
        palavrasReservadas.put("Until", 19);
        palavrasReservadas.put("Readln", 20);
        palavrasReservadas.put("Writeln", 21);
        palavrasReservadas.put("Or", 22);
        palavrasReservadas.put("And", 23);
        palavrasReservadas.put("Not", 24);
        palavrasReservadas.put("For", 27);
        palavrasReservadas.put("To", 28);
        palavrasReservadas.put("Case", 29);
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
