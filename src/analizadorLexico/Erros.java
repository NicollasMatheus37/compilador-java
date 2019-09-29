package analizadorLexico;

public class Erros {

	private String titulo;
	private String mensagem;
	private int linha;
	
	public String getTitulo() {
		return titulo;
	}
	public Erros setTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}
	public String getMensagem() {
		return mensagem;
	}
	public Erros setMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	public int getLinha() {
		return linha;
	}
	public Erros setLinha(int linha) {
		this.linha = linha;
		return this;
	}
	
	
}
