package analisadorSintatico;

import java.util.Stack;

import analizadorLexico.SimbolosTerminais;
import analizadorLexico.Token;

public class AnalisadorSintatico {
//------------	tabelas  
	private TabelaParsing tabelaDerivacoes = new TabelaParsing();
	private SimbolosNaoTerminais naoTerminais = new SimbolosNaoTerminais();
	private Terminais terminais = new Terminais();
	
//------------	variaveis de classe 
	private Stack<Token> pilhaLexica = new Stack<Token>();
	private Stack<Token> pilhaSintatica = new Stack<Token>();
	private Token valorEntrada = new Token();
	private Token valorSintatico = new Token();
	
//------------	construtor 
	public void analisadorSintatico(Stack<Token> tokenStack) {
		this.pilhaLexica = tokenStack;
		this.pilhaSintatica.add(new Token(52,"PROGRAMA"));
	}

	
//------------	funcao responsavel pela analise sintatica 
	public void analiseSintatica() {
		
		
		//processa enquanto a pilha sintatica n estiver vazia
		while(!pilhaSintatica.isEmpty()) {
			
			//atribui valores a serem analisados
			valorEntrada = pilhaLexica.peek();
			valorSintatico = pilhaSintatica.peek();
		
			//se valor inicial atribui primeiira derivação sintatica
			if((valorSintatico.getCodigo() == 52) && (valorEntrada.getCodigo() == 1)) {
				
				pilhaSintatica.pop();
				String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
				
				for(String derivacao : derivacoes) {
					pilhaSintatica.add(formataDerivacao(derivacao));
				}
				
			} else {
				
				//se x(topo da pilha sintatica) é terminal
				if(valorEntrada.getCodigo() < 52) {
					
					if(valorSintatico.getCodigo() == valorEntrada.getCodigo()) {
						pilhaLexica.pop();
						pilhaSintatica.pop();
						
					} else { 
						System.out.println("erroooooou");
						break;
					}
					
					
				} else {
					
					if(tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo())!= null) {
						
						pilhaSintatica.pop();
						String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
						
						for(String derivacao : derivacoes) {
							pilhaSintatica.add(formataDerivacao(derivacao));
						}		
					} else {
						System.out.println("erroooooou");
						break;
					}
				}

			}
		}
	}
	
//------------------------ funcoes auxiliares --------------------------------
	
	private Token formataDerivacao(String deriv) {
		Integer codigo;
		
		   try {
			   if(naoTerminais.getNaoTerminal(deriv) != 0) {
				   	codigo = naoTerminais.getNaoTerminal(deriv); 
				   	return new Token(codigo, deriv);
				   	
			   } else {
				   codigo = terminais.getTerminal(deriv);
				   return new Token(codigo, deriv);
			   }
			   
		   } catch (Exception e) {
			   // ERROOOOOOOU
		   	}
		
		return null;
	}
	
}
