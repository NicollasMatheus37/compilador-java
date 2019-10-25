package analisadorSintatico;

import java.util.Stack;

import analizadorLexico.Retorno;
import analizadorLexico.SimbolosTerminais;
import shared.Token;
import view.Erros;

public class AnalisadorSintatico {
/*--------------------------------Variaveis de classe-----------------------------*/
	
	//------------	tabelas  
	private TabelaParsing tabelaDerivacoes = new TabelaParsing();
	private SimbolosNaoTerminais naoTerminais = new SimbolosNaoTerminais();
	private Terminais terminais = new Terminais();
	private Retorno retorno = new Retorno();
	//----------	pilhas
	private Stack<Token> derivadas = new Stack <Token>();
	private Stack<Erros> pilhaErros = new Stack<Erros>();
	private Stack<Token> pilhaLexica = new Stack<Token>();
	private Stack<Token> pilhaSintatica = new Stack<Token>();

/*------------------------------------Metodos--------------------------------------*/
	
	/* funcao responsavel pela analise sintatica */ 
	public void analiseSintatica(Stack<Token> tokenStack) {
		
		this.pilhaLexica = tokenStack;
		this.pilhaSintatica.add(new Token(52,"PROGRAMA"));
		
		//processa enquanto a pilha sintatica n estiver vazia
		while(!pilhaSintatica.isEmpty()) {

			Token valorEntrada = new Token();
			Token valorSintatico = new Token();
			
			//atribui valores a serem analisados
			try {
				valorEntrada = pilhaLexica.peek();
				valorSintatico = pilhaSintatica.peek();
				
			} catch (Exception e) {
				
				pilhaErros.add((new Erros("ERRO----> Esperado(a) ' " + valorSintatico.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));
				break;
			}
			
			System.out.println(valorEntrada.getValor() + "-----" + valorSintatico.getValor());
		
			//se valor inicial atribui primeiira derivação sintatica
			if((valorSintatico.getCodigo() == 52) && (valorEntrada.getCodigo() == 1)) {
				
				// retira token inicial e coleta sua derivacao
				this.pilhaSintatica.pop();
				String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
				
				// atribui as derivacoes a uma pilha temporaria e depois a pilha principal				
				for(String derivacao : derivacoes) {
					if(!derivacao.equals("NULL")) {
						derivadas.add(formataDerivacao(derivacao));						
					}
				}
				
				addPilhaSintatica(derivadas);
				derivadas.clear();
				
			} else /* se nao é posicao inicial */{
				
				//se x(topo da pilha sintatica) é terminal
				if(valorSintatico.getCodigo() < 52) {
					
					// se derivacao igual a token lexico remove ambos 
					if(valorSintatico.getCodigo() == valorEntrada.getCodigo()) {
						pilhaLexica.pop();
						pilhaSintatica.pop();
						
					} else /* se nao erro */{ 
						pilhaErros.add((new Erros("ERRO----> Esperado(a) ' " + valorSintatico.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());	
						break;
					}
					
					
				} else /* se x(topo da pilha sintaica) é nao-terminal */{
					
					// se existir derivacao com os codigos do topo das pilhas sintatica e lexica
					if(tabelaDerivacoes.containsKey(valorSintatico.getCodigo(), valorEntrada.getCodigo())) {
						
						// retira token inicial e coleta sua derivacao
						this.pilhaSintatica.pop();
						String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
						
						// atribui as derivacoes a uma pilha temporaria e depois a pilha principal
						for(String derivacao : derivacoes) {
							if(!derivacao.equals("NULL")) {
								derivadas.add(formataDerivacao(derivacao));						
							}
						}
						
						addPilhaSintatica(derivadas);
						derivadas.clear();
						
					} else /* caso nao exista derivacao erro */{
						pilhaErros.add((new Erros("ERRO-------> Não e permitido ' " + valorEntrada.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());
						break;
					}
				}

			}
		}
	}
	
	//metodo para o debug
	public Retorno debugNext(Stack<Token> tokenStack, Stack<Token> derivStack ) {
		
		this.pilhaLexica = tokenStack;
		this.pilhaSintatica = derivStack;
		
		

			Token valorEntrada = new Token();
			Token valorSintatico = new Token();
			
			//atribui valores a serem analisados
			try {
				valorEntrada = pilhaLexica.peek();
				valorSintatico = pilhaSintatica.peek();
				
			} catch (Exception e) {
				
				pilhaErros.add((new Erros("ERRO----> Esperado(a) ' " + valorSintatico.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));

				retorno.setHasError(true);
				retorno.setErrorStack(pilhaErros);
			}
			
			System.out.println(valorEntrada.getValor() + "-----" + valorSintatico.getValor());
		
			//se valor inicial atribui primeiira derivação sintatica
			if((valorSintatico.getCodigo() == 52) && (valorEntrada.getCodigo() == 1)) {
				
				// retira token inicial e coleta sua derivacao
				this.pilhaSintatica.pop();
				String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
				
				// atribui as derivacoes a uma pilha temporaria e depois a pilha principal				
				for(String derivacao : derivacoes) {
					if(!derivacao.equals("NULL")) {
						derivadas.add(formataDerivacao(derivacao));						
					}
				}
				
				addPilhaSintatica(derivadas);
				retorno.setSintaticStack(pilhaSintatica);
				retorno.setTokenStack(pilhaLexica);
				return retorno;
				
			} else /* se nao é posicao inicial */{
				
				//se x(topo da pilha sintatica) é terminal
				if(valorSintatico.getCodigo() < 52) {
					
					// se derivacao igual a token lexico remove ambos 
					if(valorSintatico.getCodigo() == valorEntrada.getCodigo()) {
						pilhaLexica.pop();
						pilhaSintatica.pop();
						
						retorno.setSintaticStack(pilhaSintatica);
						retorno.setTokenStack(pilhaLexica);
						return retorno;
					
					} else /* se nao erro */{ 
						pilhaErros.add((new Erros("ERRO----> Esperado(a) ' " + valorSintatico.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());	
						retorno.setHasError(true);
						retorno.setErrorStack(pilhaErros);
						return retorno;
					}
					
					
				} else /* se x(topo da pilha sintaica) é nao-terminal */{
					
					// se existir derivacao com os codigos do topo das pilhas sintatica e lexica
					if(tabelaDerivacoes.containsKey(valorSintatico.getCodigo(), valorEntrada.getCodigo())) {
						
						// retira token inicial e coleta sua derivacao
						this.pilhaSintatica.pop();
						String[] derivacoes = tabelaDerivacoes.getDerivação(valorSintatico.getCodigo(), valorEntrada.getCodigo());
						
						// atribui as derivacoes a uma pilha temporaria e depois a pilha principal
						for(String derivacao : derivacoes) {
							if(!derivacao.equals("NULL")) {
								derivadas.add(formataDerivacao(derivacao));						
							}
						}
						
						addPilhaSintatica(derivadas);
						retorno.setTokenStack(pilhaSintatica);
						retorno.setTokenStack(pilhaLexica);
						return retorno;
						
					} else /* caso nao exista derivacao erro */{
						pilhaErros.add((new Erros("ERRO-------> Não e permitido ' " + valorEntrada.getValor() + " ' | linha ->" + valorEntrada.getNumLinha())));
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());
						
						retorno.setHasError(true);
						retorno.setErrorStack(pilhaErros);
						return retorno;
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
	
	/*  forma token da derivacao  */
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
