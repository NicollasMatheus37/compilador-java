package shared;

public class Token {

	private int codigo;
	private String valor;
	private int numLinha;
	
	
	public Token() {
		super();
	}

	public Token(int codigo, String valor) {
//		super();
		this.codigo = codigo;
		this.valor = valor;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public Token setCodigo(int codigo) {
		this.codigo = codigo;
		return this;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public Token setValor(String valor) {
		this.valor = valor;
		return this;
	}

	public int getNumLinha() {
		return numLinha;
	}

	public void setNumLinha(int numLinha) {
		this.numLinha = numLinha;
	}
	
	
	
}
