package analizadorLexico;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.Stack;

import shared.Token;
import view.Erros;


public class AnalizadorLexico {
	
	private Token token = new Token();
	private ManipuladorArquivos interpretador = new ManipuladorArquivos();
	private SimbolosTerminais terminais = new SimbolosTerminais();
	private Stack<Token> tokenStack = new Stack<Token>();
	private Stack<Erros> erros = new Stack<Erros>();
	private Retorno retorno = new Retorno();
	private Reader reader;
	private BufferedReader bufferedReader;
	
	boolean isComentario = false, isLiteral = false, isPalavra = false, isNumero = false, isUltimoCaracter = false;
	int linhaComentario = 0, linhaLiteral = 0, tamComentario = 0;
	
	int numLinha = 0;
	String linha = "";
	
	int codigo = 0;

	public Retorno Analizar(String texto) throws FileNotFoundException, IOException {
		
		erros.clear();
		tokenStack.clear();
		
//		System.out.println("texto: " + texto);

		String textoArquivo = (texto);
		Reader reader = new StringReader(textoArquivo);
		
		bufferedReader = new BufferedReader(reader);

		String palavra = new String();
		
		while((linha = bufferedReader.readLine()) != null) {
			
			numLinha++;
			
			char caracteres[] = linha.toCharArray();
			
			//tratamento de erro literais com mais de uma linha
			if(isLiteral) {
				insertOnErrorStack("Não fechamento de literal na linha ", linhaLiteral);
			}
			
			for(int i = 0; i < caracteres.length; i++) {
				
				char charAtual = caracteres[i];
				char charProx = ' ';
				
				try {
					charProx = caracteres [i + 1];
				} catch (Exception e) {
					isUltimoCaracter = true;
				}
				
				//verifica se existe caracter com acentuacao fora do bloco de cometarios ou literais
				if(!isLiteral && !isComentario && caracterComAcentuacao(charAtual)) {
					insertOnErrorStack("Caracter inválido na linha ", numLinha);
				}
				
				//comentarios
				if(charAtual == '(' && charProx == '*' && !isComentario) { //inicia comentário
					isComentario = true;
					linhaComentario = numLinha;
					i++;
					continue;
				} else if(charAtual == '*' && charProx == ')' && isComentario) { //fecha bloco de comentario
					i += 2;
					isComentario = false;
					continue;
				} else if(isComentario) { //adiciona o bloco de comentário
					tamComentario++;
					continue;
				}
				
				// verificação de literais
				if(charAtual == '\'' && !isLiteral) { //inicia bloco de literais
					isLiteral = true;
					linhaLiteral = numLinha;
					palavra += charAtual;
				} else if(charAtual != '\'' && isLiteral) { //adiciona no bloco de literais
					palavra += charAtual;
				} else if(charAtual == '\'' && isLiteral) { //fecha bloco de literais
					palavra += charAtual;
					isLiteral = false;
					insertOnTokenStack(48, palavra, numLinha);
					palavra = "";
				}
				
				if(!isLiteral) {
					//iniciacao de palavras
					
					// verificação de simbolos terminais
					if((codigo = getSimbolosSecundarios(charAtual, charProx)) != 0) { //verifica se é simbolo composto
						insertOnTokenStack(codigo, "" + charAtual + charProx, numLinha);
						palavra = "";
						i++;
					} else if((codigo = getSimboloPrimario(charAtual)) != 0) { //verifica se é simbolo primario
						insertOnTokenStack(codigo, "" + charAtual, numLinha);
						palavra = "";
					}
					
					//iniciado com simbolo '-' ou iniciado com número - isNumero = true;
					if(charAtual == '-' && isNumero(charProx)) { //inicia bloco de numeros quando valor for simbolo '-'
						isNumero = true;
						palavra += charAtual;
						i++;
					}
					if(isNumero(charAtual)) { //inicia bloco de numeros
						isNumero = true;
						palavra += charAtual;
					}
					if(isNumero) {
						try {
							while(isNumero(caracteres[i + 1])) {
								if(charAtual == '.') { //verifica ponto flutuante
									insertOnErrorStack("Numeros de ponto flutuante na linha ", numLinha);
								}
								palavra += caracteres[i + 1];
								i++;
							}
							
							if(Double.parseDouble(palavra) > -32768 && Double.parseDouble(palavra) < 32768) { //verifica escala de inteiros da linguagem
								insertOnTokenStack(26, palavra, numLinha);
								palavra = "";
								isNumero = false;
								
							} else if(Double.parseDouble(palavra) <= -32768 || Double.parseDouble(palavra) >= 32768) { //cria erro de ponto flutuante
								insertOnErrorStack("Inteiro fora dos valores aceitos na linha ", numLinha);
								
							}
						} catch(Exception e) {}
						
					}
					
					if(!isNumero) { //nao e numero
						// montagem de palavra
						if(!isPalavra && isLetra(charAtual)) {
							isPalavra = true;
							palavra += charAtual;
						}
						if(isPalavra) {
							try {
								while(isLetra(caracteres[i + 1]) || isNumero(caracteres[i + 1])) {
									palavra += caracteres[i + 1];
									i++;
									if(palavra.length() > 30) {
										insertOnErrorStack("String fora do limite de caracteres na linha ", numLinha);
									}
								}
							} catch(Exception e) {}
							
							if((codigo = getPalavraReservada(palavra)) != 0) {
								insertOnTokenStack(codigo, palavra, numLinha);
								palavra = "";
								isPalavra = false;
							} else {
								insertOnTokenStack(25, palavra, numLinha);
								palavra = "";
								isPalavra = false;
							}
						}
					}
				}
			}
		}
		
		if(isComentario) {
			insertOnErrorStack("Erro de nao fechamento de comentario na linha ", linhaComentario);
		}
		if(isLiteral) {
			insertOnErrorStack("Erro de nao fechamento de literal na linha ", linhaLiteral);
		}
		
		if(!erros.empty()) {
			retorno.setHasError(true);
			retorno.setErrorStack(erros);
		} else {
			retorno.setTokenStack(tokenStack);
		}
		
		return retorno;
		
	}
	
	private void insertOnTokenStack(int codigo, String palavra, int numLinha) {
		Token tok = new Token();
				tok.setCodigo(codigo)
				.setValor(palavra)
				.setNumLinha(numLinha);
		tokenStack.add(tok);
		Token token = tokenStack.peek();
	}
	
	private void insertOnErrorStack(String mensagem, int numLinha) {
		erros.push(
				(new Erros())
					.setMensagem(mensagem)
					.setLinha(numLinha));
	}
	
	private int getSimboloPrimario(char caracter) {
		return terminais.getSimboloPrimario(caracter);
	}
	
	private int getSimbolosSecundarios(char caracter, char proxCaracter) {
		return terminais.getSimbolosSecundarios(caracter, proxCaracter);
	}
	
	private int getPalavraReservada(String palavra) {
		return terminais.getPalavraReservada(palavra);
	}
	
	private boolean isLetra(char caracter) {
		if (caracter >= 'a' && caracter <= 'z' || caracter >= 'A' && caracter <= 'Z' || caracter == '_') {
			return true;
		}
		return false;
	}
	
	private boolean isNumero(char caracter) {
		if(caracter >= '0' && caracter <= '9') {
			return true;
		}
		return false;
	}
	
	private boolean caracterComAcentuacao(char caracter) {
		String acentos = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ";
		if(acentos.contains("" + caracter)) {
			return true;
		}
		return false;
	}
}
