package analizadorLexico;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;


public class AnalizadorLexico {
	
	Token token = new Token();
	Interpretador interpretador = new Interpretador();
	SimbolosTerminais terminais = new SimbolosTerminais();
	Stack<Token> tokenStack = new Stack<Token>();
	Stack<Erros> erros = new Stack<Erros>();
	Retorno retorno = new Retorno();
	Reader reader;
	BufferedReader bufferedReader;
	
	boolean isComentario = false, isLiteral = false, isNegativo = false, isPalavra = false, isNumero = false;
	int linhaComentario = 0, linhaLiteral = 0, tamComentario = 0;
	
	int numLinha = 0;
	String linha = "";
	
	int codigo = 0;
	
	public AnalizadorLexico() {
	}

	public Retorno Analizar() throws FileNotFoundException, IOException {
		erros.clear();
		tokenStack.clear();

		String textoArquivo = (interpretador.lerArquivo()).toString();
		Reader texto = new StringReader(textoArquivo);
		
		bufferedReader = new BufferedReader(texto);
		
		while((linha = bufferedReader.readLine()) != null) {
			
			String palavra = new String();
			
			numLinha++;
			
			char caracteres[] = linha.toCharArray();
			
			//tratamento de erro dos literais
			if(isLiteral) {
				erros.push(
						(new Erros())
							.setTitulo("Erro léxico")
							.setMensagem("Literal com mais de uma linha"));
			}
			
			
			for(int i = 0; i < caracteres.length; i++) {
				
				char charAtual = caracteres[i];
				char charProx = ' ';
				
				try {
					charProx = caracteres [i + 1];
				} catch (Exception e) {
					e.getStackTrace();
				}
				
				//comentarios
				if(charAtual == '(' && charProx == '*' && !isComentario) {
					isComentario = true;
					linhaComentario = i;
					continue;
				}
				if(charAtual == '*' && charProx == ')' && isComentario) {
					i += 2;
					isComentario = false;
					continue;
				}
				if(isComentario) {
					tamComentario++;
					continue;
				}
				
				
				// verificação de literais
				if(charAtual == '\'' && !isLiteral) {
					isLiteral = true;
					linhaLiteral = i;
					palavra += charAtual;
					continue;
				} else if(charAtual != '\'' && isLiteral) {
					palavra += charAtual;
					continue;
				} else if(charAtual == '\'' && isLiteral) {
					palavra += charAtual;
					token.setCodigo(48) // codigo de literal
						.setValor(palavra)
						.setNumLinha(i);
					tokenStack.push(token);
					palavra = "";
					continue;
				}
				
				//verifica simbolo '-'
				if(charAtual == '-' && isNumero(charProx)) {
					isNegativo = true;
					palavra += charAtual + charProx;
					i++;
					continue;
				} else if(isNegativo && isNumero(charAtual)) {
					palavra += charAtual;
					continue;
				} else if(isNegativo && !isNumero(charAtual)) {
					token.setCodigo(26) // codigo de inteiro
						.setValor(palavra)
						.setNumLinha(i);
					tokenStack.push(token);
					isNegativo = false;
					palavra = "";
					continue;
				}
				
				// verificação de numeros
				if(isNumero(charAtual)) {
					isNumero = true;
					palavra += charAtual;
					continue;
				} else if(isNumero && isNumero(charAtual)) {
					palavra += charAtual;
					continue;
				} else if(isNumero && !isNumero(charAtual)) {
					if(Double.parseDouble(palavra) > -32768 && Double.parseDouble(palavra) < 32768) {
						token.setCodigo(26) // codigo de inteiro
						.setValor(palavra)
						.setNumLinha(i);
						tokenStack.push(token);
						isNegativo = false;
						palavra = "";
						continue;						
					} else {
						// error
					}
				}
				
				// verificação de simbolos terminais
				if((codigo = getSimbolosSecundarios(charAtual, charProx)) != 0) {
					String valor = "";
					token.setCodigo(codigo)
						.setValor(valor += charAtual+charProx)
						.setNumLinha(i);
					tokenStack.push(token);
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
					isPalavra = true;
					palavra += charAtual;
					continue;
				} else if(isPalavra && (isLetra(charAtual) || isNumero(charAtual))) {
					palavra += charAtual;
					continue;
				} else if(isPalavra && !(isLetra(charAtual) || isNumero(charAtual))) {
					
					if((codigo = getPalavraReservada(palavra)) != 0) {
						token.setCodigo(codigo)
							.setValor(palavra)
							.setNumLinha(i);
						tokenStack.push(token);
						palavra = "";
						continue;
					} else {
						token.setCodigo(25) // codigo identificador
							.setValor(palavra)
							.setNumLinha(i);
						tokenStack.push(token);
						palavra = "";
						continue;
					}
				}
			}
		}
		
		if(!erros.empty()) {
			retorno.setError(true);
			retorno.setErrorStack(erros);
		} else {
			retorno.setTokenStack(tokenStack);
		}
		
		return retorno;
		
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
}
