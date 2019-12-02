package analisadorSintatico;

import java.util.Stack;

import analisadorSemantico.AnalisadorSemantico;
import analizadorLexico.SimbolosTerminais;
import shared.Erros;
import shared.Retorno;
import shared.Token;

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
	
	//-------------------------------
	private AnalisadorSemantico semanticAnalyser = new AnalisadorSemantico();
	
	/*------------------------------------Metodos--------------------------------------*/
	
	/* funcao responsavel pela analise sintatica */ 
	public Retorno analiseSintatica(Stack<Token> tokenStack) {
		
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
				
				pilhaErros.add((new Erros("sperado(a)" + valorSintatico.getValor(), valorEntrada.getNumLinha())));
				retorno.setErrorStack(pilhaErros);
				break;
			}
			
//			System.out.println(valorEntrada.getValor() + "-----" + valorSintatico.getValor());
		
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
						Token lexItem = pilhaLexica.pop();
						Token sintaticItem = pilhaSintatica.pop();
						String response = new String();
						
						// casos da analise semantica
						switch(sintaticItem.getCodigo()) {
						
							//caso encontre um identificador
							case 25: 
								
								// se estiver declarando adiciona a tabela
								if(semanticAnalyser.getIsStating()) {
									semanticAnalyser.pushOnIdentifierStack(lexItem);	
									
								} else {
									// se não verifica se existe na tabela
									response = semanticAnalyser.searchOnIdentTable(valorEntrada.getValor());
									verifyError(response, valorEntrada);
								}
								
								break;
							
								//caso encontre o tipo integer/array
							case 8: case 9: 
								//adiciona os identificadores a tabela com o tipo encontrado
								semanticAnalyser.setType(valorEntrada.getValor());
								response = semanticAnalyser.anylize();
								verifyError(response, valorEntrada);
								break;
							
								//caso encontre um ';'
							case 47:
								// se estiver declarando adicioca os identificadores ja encontrados a tabela
								if(semanticAnalyser.getIsStating()) {
									response = semanticAnalyser.anylize();
									verifyError(response, valorEntrada);
								}
								if(semanticAnalyser.getIsExpression()) {
									response = semanticAnalyser.checkExpressionTypes();
									verifyError(response, valorEntrada);
								}

								semanticAnalyser.setIsCallingProc(false);
								semanticAnalyser.setIsExpression(false);
								break;
							
								// caso encontre uma categoria
							case 1: case 2: case 3: case 4: 
								semanticAnalyser.setCategory(sintaticItem.getValor());
								semanticAnalyser.setIsStating(true);
								semanticAnalyser.setIsDefParam(false);

								break;
							
								// caso encontre uma procedure
							case 5: 
								// salva a categoria e adiciona a table
								semanticAnalyser.setCategory(sintaticItem.getValor());
								semanticAnalyser.setType("PROCEDURE");
								semanticAnalyser.setIsStating(true);
								semanticAnalyser.pushOnIdentifierStack(pilhaLexica.peek());
								
								// salva key para manipular os parametros
								semanticAnalyser.setLastProc((pilhaLexica.pop()).getValor());
								response = semanticAnalyser.anylize();
								semanticAnalyser.clearParameters();
								semanticAnalyser.setLevel(1);
								semanticAnalyser.setCategory("PARAMETER");
								pilhaSintatica.pop();
								semanticAnalyser.setIsDefParam(true);
								verifyError(response, valorEntrada);
								break;
							
								//caso encontre um 'end'
							case 7:
								semanticAnalyser.setLevel(0);
								semanticAnalyser.deleteAllByLevel(1);
								semanticAnalyser.clearParameters();
								
								break;
								
								// caso encontre um call
							case 11:
//								System.out.println((pilhaLexica.peek()).getValor());
								
								response = semanticAnalyser.searchOnIdentTable(pilhaLexica.peek().getValor());
								verifyError(response, pilhaLexica.peek());
								
								if(!retorno.gethasError()) {
									semanticAnalyser.setIsCallingProc(true);
									semanticAnalyser.setLastProc((pilhaLexica.pop()).getValor());
									pilhaSintatica.pop();
								}
								break;
						}

						// se ouver erro semantico
						if(retorno.gethasError()) {
//							pilhaErros.add((new Erros(response, valorEntrada.getNumLinha())));
							break;
							
						}
						
					} else /* se nao erro */{ 
						pilhaErros.add((new Erros("valor inesperado", valorEntrada.getNumLinha())));
						retorno.setErrorStack(pilhaErros);
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());	
						break;
					}
					
					
				} else /* se x(topo da pilha sintaica) é nao-terminal */{
					
					// se existir derivacao com os codigos do topo das pilhas sintatica e lexica
					if(tabelaDerivacoes.containsKey(valorSintatico.getCodigo(), valorEntrada.getCodigo())) {
						
						// se entrar em um bloco ou corpo 
						if(valorSintatico.getCodigo() == 64 || valorSintatico.getCodigo() == 53) {
							System.out.println("toqui");
							semanticAnalyser.setIsStating(false);
							semanticAnalyser.clearParameters();
						}
						
						// caso encontre um comando ou expressão
						if(valorSintatico.getCodigo() == 77) {
							semanticAnalyser.setIsExpression(true);
						}
						
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
						pilhaErros.add((new Erros("derivação inexistente", valorEntrada.getNumLinha())));
						retorno.setErrorStack(pilhaErros);
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());
						break;
					}
				}

			}
		}
		
		return retorno;
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
				
				pilhaErros.add((new Erros("Esperado(a): " + valorSintatico.getValor(), valorEntrada.getNumLinha())));

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
						Token lexItem = pilhaLexica.pop();
						Token sintaticItem = pilhaSintatica.pop();
						String response = new String();
						
						// casos da analise semantica
						switch(sintaticItem.getCodigo()) {
						
							//caso encontre um identificador
							case 25: 
								
								// se estiver declarando adiciona a tabela
								if(semanticAnalyser.getIsStating()) {
									semanticAnalyser.pushOnIdentifierStack(lexItem);	
									
								} else {
									// se não verifica se existe na tabela
									response = semanticAnalyser.searchOnIdentTable(valorEntrada.getValor());
									verifyError(response, valorEntrada);
								}
								
								break;
							
								//caso encontre o tipo integer/array
							case 8: case 9: 
								//adiciona os identificadores a tabela com o tipo encontrado
								semanticAnalyser.setType(valorEntrada.getValor());
								response = semanticAnalyser.anylize();
								verifyError(response, valorEntrada);
								break;
							
								//caso encontre um ';'
							case 47:
								// se estiver declarando adicioca os identificadores ja encontrados a tabela
								if(semanticAnalyser.getIsStating()) {
									response = semanticAnalyser.anylize();
									verifyError(response, valorEntrada);
								}
								if(semanticAnalyser.getIsExpression()) {
									response = semanticAnalyser.checkExpressionTypes();
									verifyError(response, valorEntrada);
								}

								semanticAnalyser.setIsCallingProc(false);
								semanticAnalyser.setIsExpression(false);
								break;
							
								// caso encontre uma categoria
							case 1: case 2: case 3: case 4: 
								semanticAnalyser.setCategory(sintaticItem.getValor());
								semanticAnalyser.setIsStating(true);
								semanticAnalyser.setIsDefParam(false);

								break;
							
								// caso encontre uma procedure
							case 5: 
								// salva a categoria e adiciona a table
								semanticAnalyser.setCategory(sintaticItem.getValor());
								semanticAnalyser.setType("PROCEDURE");
								semanticAnalyser.setIsStating(true);
								semanticAnalyser.pushOnIdentifierStack(pilhaLexica.peek());
								
								// salva key para manipular os parametros
								semanticAnalyser.setLastProc((pilhaLexica.pop()).getValor());
								response = semanticAnalyser.anylize();
								semanticAnalyser.clearParameters();
								semanticAnalyser.setLevel(1);
								semanticAnalyser.setCategory("PARAMETER");
								pilhaSintatica.pop();
								semanticAnalyser.setIsDefParam(true);
								verifyError(response, valorEntrada);
								break;
							
								//caso encontre um 'end'
							case 7:
								semanticAnalyser.setLevel(0);
								semanticAnalyser.deleteAllByLevel(1);
								semanticAnalyser.clearParameters();
								
								break;
								
								// caso encontre um call
							case 11:
//								System.out.println((pilhaLexica.peek()).getValor());
								
								response = semanticAnalyser.searchOnIdentTable(pilhaLexica.peek().getValor());
								verifyError(response, pilhaLexica.peek());
								
								if(!retorno.gethasError()) {
									semanticAnalyser.setIsCallingProc(true);
									semanticAnalyser.setLastProc((pilhaLexica.pop()).getValor());
									pilhaSintatica.pop();
								}
								break;
						}

						retorno.setVariableTable(semanticAnalyser.getVariableTable());
						retorno.setSintaticStack(pilhaSintatica);
						retorno.setTokenStack(pilhaLexica);
						return retorno;
					
					} else /* se nao erro */{ 
						pilhaErros.add((new Erros("Esperado(a): " + valorSintatico.getValor(), valorEntrada.getNumLinha())));
						System.out.println(((Erros)pilhaErros.peek()).getMensagem());	
						retorno.setHasError(true);
						retorno.setErrorStack(pilhaErros);
						return retorno;
					}
					
					
				} else /* se x(topo da pilha sintaica) é nao-terminal */{
					
					// se existir derivacao com os codigos do topo das pilhas sintatica e lexica
					if(tabelaDerivacoes.containsKey(valorSintatico.getCodigo(), valorEntrada.getCodigo())) {
						
						// se entrar em um bloco ou corpo 
						if(valorSintatico.getCodigo() == 64 || valorSintatico.getCodigo() == 53) {
							System.out.println("toqui");
							semanticAnalyser.setIsStating(false);
							semanticAnalyser.clearParameters();
						}
						
						// caso encontre um comando ou expressão
						if(valorSintatico.getCodigo() == 77) {
							semanticAnalyser.setIsExpression(true);
						}
						
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
						retorno.setVariableTable(semanticAnalyser.getVariableTable());
						return retorno;
						
					} else /* caso nao exista derivacao erro */{
						pilhaErros.add((new Erros("inesperado: " + valorEntrada.getValor(), valorEntrada.getNumLinha())));
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
	
	private void verifyError(String response,Token valorEntrada) {
		if(!response.equals("")) {
			pilhaErros.add(new Erros(response, valorEntrada.getNumLinha()));
			retorno.setErrorStack(pilhaErros);
			retorno.setHasError(true);
		}	
	}
}
