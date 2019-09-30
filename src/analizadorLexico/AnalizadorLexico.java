package analizadorLexico;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.Stack;


public class AnalizadorLexico {
	
	Token token = new Token();
	ManipuladorArquivos interpretador = new ManipuladorArquivos();
	SimbolosTerminais terminais = new SimbolosTerminais();
	Stack<Token> tokenStack = new Stack<Token>();
	Stack<Erros> erros = new Stack<Erros>();
	Retorno retorno = new Retorno();
	Reader reader;
	BufferedReader bufferedReader;
	
	boolean isComentario = false, isLiteral = false, isPalavra = false, isNumero = false, isUltimoCaracter = false;
	int linhaComentario = 0, linhaLiteral = 0, tamComentario = 0;
	
	int numLinha = 0;
	String linha = "";
	
	int codigo = 0;
	
	public AnalizadorLexico() {
	}

	public Retorno Analizar(String texto) throws FileNotFoundException, IOException {
		
		erros.clear();
		tokenStack.clear();
		
		System.out.println("texto: " + texto);

		String textoArquivo = (texto);
		Reader reader = new StringReader(textoArquivo);
		
		bufferedReader = new BufferedReader(reader);

		String palavra = new String();
		
		while((linha = bufferedReader.readLine()) != null) {
			
			numLinha++;
			
			char caracteres[] = linha.toCharArray();
			
			//tratamento de erro dos literais
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
				
				System.out.println(isUltimoCaracter ? "1" : "0");
				
				// acentuacao
				if(!isLiteral && !isComentario && caracterComAcentuacao(charAtual)) {
					insertOnErrorStack("Caracter inválido na linha ", numLinha);
				}
				
				//comentarios
				if(charAtual == '(' && charProx == '*' && !isComentario) {
					isComentario = true;
					linhaComentario = i;
					continue;
				} else if(charAtual == '*' && charProx == ')' && isComentario) {
					i += 2;
					isComentario = false;
					continue;
				} else if(isComentario) {
					tamComentario++;
					continue;
				}
				
				// verificação de literais
				if(charAtual == '\'' && !isLiteral) {
					isLiteral = true;
					linhaLiteral = numLinha;
					palavra += charAtual;
					continue;
				} else if(charAtual != '\'' && isLiteral) {
					palavra += charAtual;
					continue;
				} else if(charAtual == '\'' && isLiteral) {
					palavra += charAtual;
					insertOnTokenStack(48, palavra, numLinha); //insere literal
					continue;
				}
				
				//verifica simbolo '-'
				if(charAtual == '-' && isNumero(charProx)) {
					isNumero = true;
					palavra += charAtual + charProx;
					i++;
					continue;
				}
				
				// verificação de numeros
				if(!isPalavra && isNumero(charAtual)) {
					isNumero = true;
					palavra += charAtual;
					continue;
				} else if(isNumero && isNumero(charAtual)) {
					palavra += charAtual;
					continue;
				} else if(isNumero && !isNumero(charAtual)) {
					if(charAtual == '.') {
						insertOnErrorStack("Numeros de ponto flutuante na linha ", numLinha);
					} else if(Double.parseDouble(palavra) > -32768 && Double.parseDouble(palavra) < 32768) {
						insertOnTokenStack(26, palavra, numLinha);
						isNumero = false;
						continue;						
					} else if(Double.parseDouble(palavra) <= -32768 || Double.parseDouble(palavra) >= 32768) {
						insertOnErrorStack("Inteiro fora dos valores aceitos na linha ", numLinha);
					}
				}
				
				// verificação de simbolos terminais
				if((codigo = getSimbolosSecundarios(charAtual, charProx)) != 0) {
					String valor = "";
					insertOnTokenStack(codigo, palavra, numLinha);
					i++;
					continue;
				}
				else if((codigo = getSimboloPrimario(charAtual)) != 0) {
					String valor = "";
					token.setCodigo(codigo)
						.setValor(valor += charAtual)
						.setNumLinha(i);
					tokenStack.push(token);
					continue;
				}
				
				// montagem de palavra
				if(!isPalavra && isLetra(charAtual)) {
					System.out.println("começa a palavra");
					isPalavra = true;
					palavra += charAtual;
					continue;
				} else if(isPalavra && (isLetra(charAtual) || isNumero(charAtual))) {
					System.out.println("adiciona na palavra");
					palavra += charAtual;
					continue;
				} else if(isPalavra && !isLetra(charAtual) && !isNumero(charAtual)) {
					System.out.println("termina a palavra");
					System.out.println("vê se é palavra reservada" + getPalavraReservada(palavra));
					if((codigo = getPalavraReservada(palavra)) != 0) {
						System.out.println("palavra reservada");
						insertOnTokenStack(codigo, palavra, numLinha);
						continue;
					} else {
						System.out.println("identificador");
						insertOnTokenStack(25, palavra, numLinha);
						continue;
					}
				}

				if(isUltimoCaracter) {
					System.out.println('a');
				}
			}
		}
		
		if(!erros.empty()) {
			retorno.setError(true);
			retorno.setErrorStack(erros);
		} else {
			retorno.setTokenStack(tokenStack);
		}
		
		System.out.println(tokenStack.size());
		Stack<Token> stack = retorno.getTokenStack();
		for(Token token : stack) {
			System.out.println(token);
		}
		
		return retorno;
		
	}
	
	private void insertOnTokenStack(int codigo, String palavra, int numLinha) {
		token.setCodigo(codigo)
			.setValor(palavra)
			.setNumLinha(numLinha);
		tokenStack.push(token);
		palavra = "";
	}
	
	private void insertOnErrorStack(String mensagem, int numLinha) {
		erros.push(
				(new Erros())
					.setMensagem("Numeros de ponto flutuante na linha ")
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
