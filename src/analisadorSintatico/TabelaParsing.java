package analisadorSintatico;

import java.util.HashMap;
import java.util.Map;

public class TabelaParsing {
	
	private Map<String, String> tabelaParsing = new HashMap<String, String>();

	
	public TabelaParsing() {
		this.iniciaTabelaParsing();
	}

	public Boolean containsKey(Integer chave1, Integer chave2) {
		return tabelaParsing.containsKey(formatarChave(chave1, chave2));
	}
	
	public String[] getDeriva��o(Integer chave1, Integer chave2) {
		String chave = formatarChave(chave1,chave2);
		
		if(tabelaParsing.containsKey(chave)) {
			String[] derivacoes = (tabelaParsing.get(chave).toString()).split("&");
			return derivacoes;
		}
		return null;
	}
	
	private String formatarChave(Integer chave1, Integer chave2) {
		String chave = chave1.toString() + "," + chave2.toString();
		return chave;
	}
	
	public void iniciaTabelaParsing() {
		tabelaParsing.put("52,1", "PROGRAM&IDENTIFICADOR&;&BLOCO&.");
		tabelaParsing.put("53,2", "DCLROT&DCLCONST&DCLVAR&DCLPROC&CORPO");
		tabelaParsing.put("53,3", "DCLROT&DCLCONST&DCLVAR&DCLPROC&CORPO");
		tabelaParsing.put("53,4", "DCLROT&DCLCONST&DCLVAR&DCLPROC&CORPO");
		tabelaParsing.put("53,5", "DCLROT&DCLCONST&DCLVAR&DCLPROC&CORPO");
		tabelaParsing.put("53,6", "DCLROT&DCLCONST&DCLVAR&DCLPROC&CORPO");
		tabelaParsing.put("54,2", "LABEL&LID&;");
		tabelaParsing.put("54,3", "NULL");
		tabelaParsing.put("54,4", "NULL");
		tabelaParsing.put("54,5", "NULL");
		tabelaParsing.put("54,6", "NULL");
		tabelaParsing.put("55,25", "IDENTIFICADOR&REPIDENT");
		tabelaParsing.put("56,39", "NULL");
		tabelaParsing.put("56,46", ",&IDENTIFICADOR&REPIDENT");
		tabelaParsing.put("56,47", "NULL");
		tabelaParsing.put("57,3", "CONST&IDENTIFICADOR&=&INTEIRO&;&LDCONST");
		tabelaParsing.put("57,4", "NULL");
		tabelaParsing.put("57,5", "NULL");
		tabelaParsing.put("57,6", "NULL");
		tabelaParsing.put("58,4", "NULL");
		tabelaParsing.put("58,5" ,"NULL");
		tabelaParsing.put("58,6","NULL");
		tabelaParsing.put("58,25","IDENTIFICADOR&=&INTEIRO&;&LDCONST");
		tabelaParsing.put("59,4","VAR&LID&:&TIPO&;&LDVAR");
		tabelaParsing.put("59,5","NULL");
		tabelaParsing.put("59,6","NULL");
		tabelaParsing.put("60,5","NULL");
		tabelaParsing.put("60,6","NULL");
		tabelaParsing.put("60,25","LID&:&TIPO&;&LDVAR");
		tabelaParsing.put("61,8","INTEGER");
		tabelaParsing.put("61,9","ARRAY&[&INTEIRO&..&INTEIRO&]&OF&INTEGER");
		tabelaParsing.put("62,5","PROCEDURE&IDENTIFICADOR&DEFPAR&;&BLOCO&;&DCLPROC");
		tabelaParsing.put("62,6","NULL");
		tabelaParsing.put("63,36","(&LID&:&INTEGER&)");
		tabelaParsing.put("63,39","NULL");
		tabelaParsing.put("64,6","BEGIN&COMANDO&REPCOMANDO&END");
		tabelaParsing.put("65,7","NULL");
		tabelaParsing.put("65,47",";&COMANDO&REPCOMANDO");
		tabelaParsing.put("66,6","CORPO");
		tabelaParsing.put("66,7","NULL");
		tabelaParsing.put("66,11","CALL&IDENTIFICADOR&PARAMETROS");
		tabelaParsing.put("66,12","GOTO&IDENTIFICADOR");
		tabelaParsing.put("66,13","IF&EXPRESSAO&THEN&COMANDO&ELSEPARTE");
		tabelaParsing.put("66,15","NULL");
		tabelaParsing.put("66,16","WHILE&EXPRESSAO&DO&COMANDO");
		tabelaParsing.put("66,18","REPEAT&COMANDO&UNTIL&EXPRESSAO");
		tabelaParsing.put("66,19","NULL");
		tabelaParsing.put("66,20","READLN&(&VARIAVEL&REPVARIAVEL&)");
		tabelaParsing.put("66,21","WRITELN&(&ITEMSAIDA&REPITEM&)");
		tabelaParsing.put("66,25","IDENTIFICADOR&RCOMID");
		tabelaParsing.put("66,27","FOR&IDENTIFICADOR&:=&EXPRESSAO&TO&EXPRESSAO&DO&COMANDO");
		tabelaParsing.put("66,29","CASE&EXPRESSAO&OF&CONDCASE&END");
		tabelaParsing.put("66,47","NULL");
		tabelaParsing.put("67,34","RVAR&:=&EXPRESSAO");
		tabelaParsing.put("67,38","RVAR&:=&EXPRESSAO");
		tabelaParsing.put("67,39",":&COMANDO" );
		tabelaParsing.put("68,34","[&EXPRESSAO&]");
		tabelaParsing.put("68,38","NULL");
		tabelaParsing.put("69,15","NULL");
		tabelaParsing.put("69,7","NULL");
		tabelaParsing.put("69,19","NULL");
		tabelaParsing.put("69,36","(&EXPRESSAO&REPPAR&)");
		tabelaParsing.put("69,47","NULL");
		tabelaParsing.put("70,37","NULL");
		tabelaParsing.put("70,46",",&EXPRESSAO&REPPAR");
		tabelaParsing.put("71,7","NULL");
		tabelaParsing.put("71,15","ELSE&COMANDO");
		tabelaParsing.put("71,19","NULL");
		tabelaParsing.put("71,47","NULL");
		tabelaParsing.put("72,25","IDENTIFICADOR&VARIAVEL1");
		tabelaParsing.put("73,7","NULL");
		tabelaParsing.put("73,10","NULL");
		tabelaParsing.put("73,14","NULL");
		tabelaParsing.put("73,15","NULL");
		tabelaParsing.put("73,17","NULL");
		tabelaParsing.put("73,19","NULL");
		tabelaParsing.put("73,22","NULL");
		tabelaParsing.put("73,23","NULL");
		tabelaParsing.put("73,28","NULL");
		tabelaParsing.put("73,30","NULL");
		tabelaParsing.put("73,31","NULL");
		tabelaParsing.put("73,32","NULL");
		tabelaParsing.put("73,33","NULL");
		tabelaParsing.put("73,34","[&EXPRESSAO&]");
		tabelaParsing.put("73,35","NULL");
		tabelaParsing.put("73,37","NULL");
		tabelaParsing.put("73,40","NULL");
		tabelaParsing.put("73,41","NULL");
		tabelaParsing.put("73,42","NULL");
		tabelaParsing.put("73,43","NULL");
		tabelaParsing.put("73,44","NULL");
		tabelaParsing.put("73,45","NULL");
		tabelaParsing.put("73,46","NULL");
		tabelaParsing.put("73,47","NULL");
		tabelaParsing.put("74,37","NULL");
		tabelaParsing.put("74,46",",&VARIAVEL&REPVARIAVEL");
		tabelaParsing.put("75,24","EXPRESSAO");
		tabelaParsing.put("75,25","EXPRESSAO");
		tabelaParsing.put("75,26","EXPRESSAO");
		tabelaParsing.put("75,30","EXPRESSAO");
		tabelaParsing.put("75,31","EXPRESSAO");
		tabelaParsing.put("75,36","EXPRESSAO");
		tabelaParsing.put("75,48","LITERAL");
		tabelaParsing.put("76,37","NULL");
		tabelaParsing.put("76,46",",&ITEMSAIDA&REPITEM");
		tabelaParsing.put("77,24","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("77,25","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("77,26","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("77,30","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("77,31","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("77,36","EXPSIMP&REPEXPSIMP");
		tabelaParsing.put("78,7","NULL");
		tabelaParsing.put("78,10","NULL");
		tabelaParsing.put("78,14","NULL");
		tabelaParsing.put("78,15","NULL");
		tabelaParsing.put("78,17","NULL");
		tabelaParsing.put("78,19","NULL");
		tabelaParsing.put("78,28","NULL");
		tabelaParsing.put("78,35","NULL");
		tabelaParsing.put("78,37","NULL");
		tabelaParsing.put("78,40","=&EXPSIMP");
		tabelaParsing.put("78,41",">&EXPSIMP");
		tabelaParsing.put("78,42",">=&EXPSIMP");
		tabelaParsing.put("78,43","<&EXPSIMP");
		tabelaParsing.put("78,44","<=&EXPSIMP");
		tabelaParsing.put("78,45","<>&EXPSIMP");
		tabelaParsing.put("78,46","NULL");
		tabelaParsing.put("78,47","NULL");
		tabelaParsing.put("79,24","TERMO&REPEXP");
		tabelaParsing.put("79,25","TERMO&REPEXP");
		tabelaParsing.put("79,26","TERMO&REPEXP");
		tabelaParsing.put("79,30","+&TERMO&REPEXP");
		tabelaParsing.put("79,31","-&TERMO&REPEXP");
		tabelaParsing.put("79,36","TERMO&REPEXP");
		tabelaParsing.put("80,7","NULL");
		tabelaParsing.put("80,10","NULL");
		tabelaParsing.put("80,14","NULL");
		tabelaParsing.put("80,15","NULL");
		tabelaParsing.put("80,17","NULL");
		tabelaParsing.put("80,19","NULL");
		tabelaParsing.put("80,22","OR&TERMO&REPEXP");
		tabelaParsing.put("80,28","NULL");
		tabelaParsing.put("80,30","+&TERMO&REPEXP");
		tabelaParsing.put("80,31","-&TERMO&REPEXP");
		tabelaParsing.put("80,35","NULL");
		tabelaParsing.put("80,37","NULL");
		tabelaParsing.put("80,40","NULL");
		tabelaParsing.put("80,41","NULL");
		tabelaParsing.put("80,42","NULL");
		tabelaParsing.put("80,43","NULL");
		tabelaParsing.put("80,44","NULL");
		tabelaParsing.put("80,45","NULL");
		tabelaParsing.put("80,46","NULL");
		tabelaParsing.put("80,47","NULL");
		tabelaParsing.put("81,24","FATOR&REPTERMO");
		tabelaParsing.put("81,25","FATOR&REPTERMO");
		tabelaParsing.put("81,26","FATOR&REPTERMO");
		tabelaParsing.put("81,36","FATOR&REPTERMO");
		tabelaParsing.put("82,7","NULL");
		tabelaParsing.put("82,10","NULL");
		tabelaParsing.put("82,14","NULL");
		tabelaParsing.put("82,15","NULL");
		tabelaParsing.put("82,17","NULL");
		tabelaParsing.put("82,19","NULL");
		tabelaParsing.put("82,22","NULL");
		tabelaParsing.put("82,23","AND&FATOR&REPTERMO");
		tabelaParsing.put("82,28","NULL");
		tabelaParsing.put("82,30","NULL");
		tabelaParsing.put("82,31","NULL");
		tabelaParsing.put("82,32","*&FATOR&REPTERMO");
		tabelaParsing.put("82,33","/&FATOR&REPTERMO");
		tabelaParsing.put("82,35","NULL");
		tabelaParsing.put("82,37","NULL");
		tabelaParsing.put("82,40","NULL");
		tabelaParsing.put("82,41","NULL");
		tabelaParsing.put("82,42","NULL");
		tabelaParsing.put("82,43","NULL");
		tabelaParsing.put("82,44","NULL");
		tabelaParsing.put("82,45","NULL");
		tabelaParsing.put("82,46","NULL");
		tabelaParsing.put("82,47","NULL");
		tabelaParsing.put("83,24","NOT&FATOR");
		tabelaParsing.put("83,25","VARIAVEL");
		tabelaParsing.put("83,26","INTEIRO");
		tabelaParsing.put("83,36","(&EXPRESSAO&)");
		tabelaParsing.put("84,26","INTEIRO&RPINTEIRO&:&COMANDO&CONTCASE");
		tabelaParsing.put("85,7","NULL");
		tabelaParsing.put("85,47",";&CONDCASE");
		tabelaParsing.put("86,39","NULL");
		tabelaParsing.put("86,46",",&INTEIRO&RPINTEIRO");
	}
}