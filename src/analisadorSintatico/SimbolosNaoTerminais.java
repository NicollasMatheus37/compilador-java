package analisadorSintatico;

import java.util.HashMap;
import java.util.Map;

public class SimbolosNaoTerminais {

	private Map<String, Integer> simbolosNaoTerminais = new HashMap<String, Integer>();
	
	public SimbolosNaoTerminais() {
		this.iniciaHashMapNaoTerminais();
	}
	
	public Integer getNaoTerminal(String palavra) {
		if(simbolosNaoTerminais.containsKey(palavra)) {
			return simbolosNaoTerminais.get(palavra);
		}
		return null;
	}
	
	public Boolean containsKey(String key) {
		return simbolosNaoTerminais.containsKey(key);
	}
	
	private void iniciaHashMapNaoTerminais() {
		simbolosNaoTerminais.put("PROGRAMA", 52);
		simbolosNaoTerminais.put("BLOCO", 53);
		simbolosNaoTerminais.put("DCLROT", 54);
		simbolosNaoTerminais.put("LID", 55);
		simbolosNaoTerminais.put("REPIDENT", 56);
		simbolosNaoTerminais.put("DCLCONST", 57);
		simbolosNaoTerminais.put("LDCONST", 58);
		simbolosNaoTerminais.put("DCLVAR", 59);
		simbolosNaoTerminais.put("LDVAR", 60);
		simbolosNaoTerminais.put("TIPO", 61);
		simbolosNaoTerminais.put("DCLPROC", 62);
		simbolosNaoTerminais.put("DEFPAR", 63);
		simbolosNaoTerminais.put("CORPO", 64);
		simbolosNaoTerminais.put("REPCOMANDO", 65);
		simbolosNaoTerminais.put("COMANDO", 66);
		simbolosNaoTerminais.put("RCOMID", 67);
		simbolosNaoTerminais.put("RVAR", 68);
		simbolosNaoTerminais.put("PARAMETROS", 69);
		simbolosNaoTerminais.put("REPPAR", 70);
		simbolosNaoTerminais.put("ELSEPARTE", 71);
		simbolosNaoTerminais.put("VARIAVEL", 72);
		simbolosNaoTerminais.put("VARIAVEL1", 73);
		simbolosNaoTerminais.put("REPVARIAVEL", 74);
		simbolosNaoTerminais.put("REPVARIAVEL", 74);
		simbolosNaoTerminais.put("ITEMSAIDA", 75);
		simbolosNaoTerminais.put("REPITEM", 76);
		simbolosNaoTerminais.put("EXPRESSAO", 77);
		simbolosNaoTerminais.put("REPEXPSIMP", 78);
		simbolosNaoTerminais.put("EXPSIMP", 79);
		simbolosNaoTerminais.put("REPEXP", 80);
		simbolosNaoTerminais.put("TERMO", 81);
		simbolosNaoTerminais.put("REPTERMO", 82);
		simbolosNaoTerminais.put("FATOR", 83);
		simbolosNaoTerminais.put("CONDCASE", 84);
		simbolosNaoTerminais.put("CONTCASE", 85);
		simbolosNaoTerminais.put("RPINTEIRO", 86);
		simbolosNaoTerminais.put("SEMEFEITO", 87);
	}
}
