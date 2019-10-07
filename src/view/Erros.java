package view;

public class Erros {

	private String titulo;
	private String mensagem;
	private int linha;
	
	public Erros() {
		super();
	}
	public Erros(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public Erros setTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}
	public String getTitulo() {
		return titulo;
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
