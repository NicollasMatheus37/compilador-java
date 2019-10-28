package shared;

public class Erros {

	private String titulo;
	private String mensagem;
	private int linha;
	
	public Erros() {
	}
	
	public Erros(String mensagem, int linha) {
		super();
		this.mensagem = mensagem;
		this.linha = linha;
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
