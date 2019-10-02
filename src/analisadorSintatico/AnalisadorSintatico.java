package analisadorSintatico;

import java.util.Stack;

import analizadorLexico.SimbolosTerminais;
import analizadorLexico.Token;

public class AnalisadorSintatico {
/*--------------------------------Variaveis de classe-----------------------------*/
	
	//------------	tabelas  
	private TabelaParsing tabelaDerivacoes = new TabelaParsing();
	private SimbolosNaoTerminais naoTerminais = new SimbolosNaoTerminais();
	private Terminais terminais = new Terminais();
	
	//----------	pilhas
	private Stack<Token> pilhaLexica = new Stack<Token>();
	private Stack<Token> pilhaSintatica = new Stack<Token>();

/*------------------------------------Metodos--------------------------------------*/
	
	/* -----------------construtor------------------- */ 
	public AnalisadorSintatico(Stack<Token> tokenStack) {
		this.pilhaLexica = tokenStack;
		this.pilhaSintatica.add(new Token(52,"PROGRAMA"));
	}

	
	/* funcao responsavel pela analise sintatica */ 
	public void analiseSintatica() {
		
		
		//processa enquanto a pilha sintatica n estiver vazia
		while(!pilhaSintatica.isEmpty()) {
			
			//atribui valores a serem analisados
			Stack <Token> derivadas = new Stack <Token>();
			Token valorEntrada = pilhaLexica.peek();
			Token valorSintatico = pilhaSintatica.peek();
			
			System.out.println(valorEntrada.getValor() + "-----" + valorSintatico.getValor());
		
			//se valor inicial atribui primeiira deriva��o sintatica
			if((valorSintatico.getCodigo() == 52) && (valorEntrada.getCodigo() == 1)) {
				
				// retira token inicial e coleta sua derivacao
				this.pilhaSintatica.pop();
				String[] derivacoes = tabelaDerivacoes.getDeriva��o(valorSintatico.getCodigo(), valorEntrada.getCodigo());
				
				// atribui as derivacoes a uma pilha temporaria e depois a pilha principal				
				for(String derivacao : derivacoes) {
					if(!derivacao.equals("NULL")) {
						derivadas.add(formataDerivacao(derivacao));						
					}
				}
				
				addPilhaSintatica(derivadas);
				derivadas.clear();
				
			} else /* se nao � posicao inicial */{
				
				//se x(topo da pilha sintatica) � terminal
				if(valorSintatico.getCodigo() < 52) {
					
					// se derivacao igual a token lexico remove ambos 
					if(valorSintatico.getCodigo() == valorEntrada.getCodigo()) {
						pilhaLexica.pop();
						pilhaSintatica.pop();
						
					} else /* se nao erro */{ 
						System.out.println("Erro sintatico");
						break;
					}
					
					
				} else /* se x(topo da pilha sintaica) � nao-terminal */{
					
					// se existir derivacao com os codigos do topo das pilhas sintatica e lexica
					if(tabelaDerivacoes.containsKey(valorSintatico.getCodigo(), valorEntrada.getCodigo())) {
						
						// retira token inicial e coleta sua derivacao
						this.pilhaSintatica.pop();
						String[] derivacoes = tabelaDerivacoes.getDeriva��o(valorSintatico.getCodigo(), valorEntrada.getCodigo());
						
						// atribui as derivacoes a uma pilha temporaria e depois a pilha principal
						for(String derivacao : derivacoes) {
							if(!derivacao.equals("NULL")) {
								derivadas.add(formataDerivacao(derivacao));						
							}
						}
						
						addPilhaSintatica(derivadas);
						derivadas.clear();
						
					} else /* caso nao exista derivacao erro */{
						System.out.println("erro de deriva�ao");
						break;
					}
				}

			}
		}
	}
	
/*------------------------ funcoes auxiliares --------------------------------*/
	
	/*  add pilha temporaria na pilha-sintatica  */
	private void addPilhaSintatica(Stack<Token> stack) {
		while(!stack.isEmpty()) {
			pilhaSintatica.add(stack.pop());
		}
	}
	
	/*  tranforma a string da derivacao em token  */
	private Token formataDerivacao(String deriv) {
		int codigo;
		
		if(naoTerminais.containsKey(deriv)) {
			codigo = naoTerminais.getNaoTerminal(deriv); 
			return new Token(codigo, deriv);
	   	
		} else {
			codigo = terminais.getTerminal(deriv);
			return new Token(codigo, deriv);
		}
	}	
}
