package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import analisadorSintatico.AnalisadorSintatico;
import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ManipuladorArquivos;
import shared.Erros;
import shared.Retorno;
import shared.Token;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import analisadorSemantico.Variable;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JLabel lblConsole;
	private JLabel lblAreaDeCdigo;
	private JScrollPane scrollPaneAreaDeCodigo;
	private JTextArea areaDeCodigo;
	private JScrollPane scrollPaneConsole;
	private JTextArea consoleTextArea;
	private DefaultListModel defaultListModel = new DefaultListModel();
	private String fontCode;
	private JButton btnSalvar;
	private JButton btnCompilar;
	private JButton btnAbrirArquivo;
	private JButton btnStop;
	private JButton btnDebug;
	private JButton btnNext;
	
	private Stack<Stack> debugBackTokenStack = new Stack<Stack>();
	private Stack<Stack> debugBackSintacStack = new Stack<Stack>();
	private Stack<Map<String, Variable>> debugBackVariableStack =  new Stack<Map<String,Variable>>();
	
	private Map<String, Variable>  variableTable = new HashMap<String, Variable>();
	private Stack<Token> sintaticStack = new Stack<Token>();
	private Stack<Token> tokenStack;
	private Stack<Erros> errorStack = new Stack<Erros>();
	
	private AnalizadorLexico lexico = new AnalizadorLexico();
	private AnalisadorSintatico sintatico = new AnalisadorSintatico();
	private Retorno retorno;
	private JTable tableTokens;
	private JTable tableDeriv;
	private JTable tableVariaveis;
	private DefaultTableModel modelToken, modelDeriv, modelVariaveis; 
	private JScrollPane scrollPane;
	private JLabel lblPilhaDeDerivaes;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setTitle("Compilador");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1065, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblConsole = new JLabel("   Console:");
		lblConsole.setBounds(10, 381, 146, 29);
		contentPane.add(lblConsole);
		
		lblAreaDeCdigo = new JLabel(" \u00C1rea de c\u00F3digo:");
		lblAreaDeCdigo.setBounds(10, 5, 131, 25);
		contentPane.add(lblAreaDeCdigo);
		
		scrollPaneAreaDeCodigo = new JScrollPane();
		scrollPaneAreaDeCodigo.setBounds(10, 31, 736, 341);
		contentPane.add(scrollPaneAreaDeCodigo);
		
		areaDeCodigo = new JTextArea();
		scrollPaneAreaDeCodigo.setViewportView(new NumeredBorder().getBorder(areaDeCodigo));
		
		scrollPaneConsole = new JScrollPane();
		scrollPaneConsole.setEnabled(false);
		scrollPaneConsole.setBounds(10, 411, 736, 132);
		contentPane.add(scrollPaneConsole);
		
		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		scrollPaneConsole.setViewportView(consoleTextArea);
		
		
//		-----------------------Button-----------------------------
		btnCompilar = new JButton("Compilar");
		btnCompilar.setBackground(SystemColor.menu);
		btnCompilar.setBounds(326, 5, 131, 22);
		btnCompilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				defaultListModel.clear();
				consoleTextArea.setText("");
				fontCode = areaDeCodigo.getText();
					
				if(!(areaDeCodigo.getText().isEmpty())) {
					try {
						retorno = new Retorno();
						lexico = new AnalizadorLexico();
						sintatico = new AnalisadorSintatico();
						retorno = lexico.Analizar(fontCode);
						retorno.gethasError();
						tokenStack = retorno.getTokenStack();
						errorStack = retorno.getErrorStack();
						
						if(!retorno.gethasError()) {
							
							retorno= new Retorno();
							retorno = sintatico.analiseSintatica(tokenStack);
							tokenStack = retorno.getTokenStack();
							errorStack = retorno.getErrorStack();
						}
						
						if(!errorStack.isEmpty()) {
							Erros erros = errorStack.pop();
							String erro = erros.getMensagem() + ", linha " + erros.getLinha();
							
							consoleTextArea.setText(erro);
						} else {
							consoleTextArea.setText("Compilado sem erros!!!");
						}
						
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"O campo de texto esta vazio");
				}
			}
		});
		contentPane.setLayout(null);
		
		btnAbrirArquivo = new JButton("Abrir Arquivo");
		btnAbrirArquivo.setBackground(SystemColor.menu);
		btnAbrirArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setCurrentDirectory(new File("Documentos"));

				try {
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						StringBuilder text = new ManipuladorArquivos().lerArquivo(chooser.getSelectedFile().getPath());
						String txt = text.toString();
						areaDeCodigo.setText(txt);
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAbrirArquivo.setBounds(206, 5, 118, 23);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setCurrentDirectory(new File("Documentos"));
				fontCode = areaDeCodigo.getText();

				if(fontCode.isEmpty()) {
					JOptionPane.showMessageDialog(null,"O campo de texto esta vazio");
					
				}else {
					
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						
						try {
							new ManipuladorArquivos().GravaArquivo(fontCode,chooser.getSelectedFile().getPath());
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
					}
				}

			}
		});
		btnSalvar.setBackground(SystemColor.menu);
		btnSalvar.setBounds(115, 5, 89, 23);
		
		contentPane.add(btnSalvar);
		contentPane.add(btnAbrirArquivo);
		contentPane.add(btnCompilar);
		
		btnDebug = new JButton("Debug");
		btnDebug.setBackground(SystemColor.menu);
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				debugBackSintacStack.clear();
				debugBackTokenStack.clear();
				debugBackVariableStack.clear();
				consoleTextArea.setText("");
				
				btnCompilar.setEnabled(false);
				btnDebug.setEnabled(false);
				btnStop.setEnabled(true);
				btnNext.setEnabled(true);
				tokenStack = new Stack<Token>();
				fontCode = areaDeCodigo.getText();

				sintaticStack.add(new Token(52,"PROGRAMA"));
				lexico = new AnalizadorLexico();
				sintatico = new AnalisadorSintatico();
				variableTable = new HashMap<String, Variable>();
				
				if(!(areaDeCodigo.getText().isEmpty())) {
					try {

						retorno = lexico.Analizar(fontCode);
						
						
						retorno.gethasError();
						tokenStack = retorno.getTokenStack();
						errorStack = retorno.getErrorStack();
						variableTable = retorno.getVariableTable();
						

						
						if(retorno.gethasError()) {
							Erros erros = errorStack.pop();
							String erro = erros.getMensagem() + ", linha " + erros.getLinha();
							
							consoleTextArea.setText(erro);
						} else {	
							
							Stack tempStack = (Stack) tokenStack.clone();
							System.out.println(tokenStack.size());
							
							updateLexicTable(tempStack);
							updateSintaticTable(sintaticStack);
							updateSemanticTable(variableTable);
						}
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else {
					JOptionPane.showMessageDialog(null,"O campo de texto esta vazio");
				}
			
			
			}
		});
		btnDebug.setBounds(563, 5, 80, 23);
		contentPane.add(btnDebug);
		
		btnStop = new JButton("||");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				defaultListModel.clear();
				consoleTextArea.setText("");
				btnCompilar.setEnabled(true);
				btnDebug.setEnabled(true);
				btnStop.setEnabled(false);
				btnNext.setEnabled(false);
				
				tokenStack.clear();
				sintaticStack.clear();
				variableTable = null;
				debugBackSintacStack.clear();
				debugBackTokenStack.clear();
				debugBackVariableStack.clear();
				consoleTextArea.setText("");
				modelDeriv.setRowCount(0);
				modelToken.setRowCount(0);
				modelVariaveis.setRowCount(0);
			}
		});
		btnStop.setBackground(SystemColor.menu);
		btnStop.setBounds(648, 5, 47, 23);
		contentPane.add(btnStop);
		
		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!retorno.gethasError() && !(tokenStack.isEmpty() && sintaticStack.isEmpty())) {
					retorno = new Retorno();
					debugBackTokenStack.push((Stack) tokenStack.clone());
					debugBackSintacStack.push((Stack) sintaticStack.clone());
					debugBackVariableStack.push(variableTable);
					
					retorno = sintatico.debugNext(tokenStack, sintaticStack);
					
				} else if(tokenStack.isEmpty() && sintaticStack.isEmpty()) {
					consoleTextArea.setText("Compilado sem erros!!");
					btnNext.setEnabled(false);
				}
				
				variableTable = retorno.getVariableTable();
				sintaticStack = retorno.getSintaticStack();
				tokenStack = retorno.getNoRevertTokenStack();
				errorStack = retorno.getErrorStack();
				
				if(retorno.gethasError()) {
					Erros erros = errorStack.peek();
					String erro = erros.getMensagem()  + ", linha " + erros.getLinha();
					
					consoleTextArea.setText(erro);
					btnNext.setEnabled(false);
				} else {	

					Stack tempStack = (Stack) tokenStack.clone();				
					updateLexicTable(tempStack);
					updateSintaticTable(sintaticStack);
					updateSemanticTable(variableTable);
				}
			}
		});
		btnNext.setEnabled(false);
		btnNext.setBackground(SystemColor.menu);
		btnNext.setBounds(699, 5, 47, 23);
		contentPane.add(btnNext);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(756, 356, 293, 187);
		contentPane.add(scrollPane_1);
		
		String colunas[] = { "Codigo", "Valor" , "Linha" };
		String colunasS[] = { "Codigo", "Valor" };
		String colunasSs[] = { "Nome", "Tipo", "Categoria", "Nivel" };
		
		modelToken = new DefaultTableModel(null, colunas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableTokens = new JTable();
		tableTokens.setModel(modelToken);
		tableTokens.getColumnModel().getColumn(0).setResizable(false);
		tableTokens.getColumnModel().getColumn(1).setMinWidth(23);
		scrollPane_1.setViewportView(tableTokens);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(756, 145, 293, 196);
		contentPane.add(scrollPane);
		
		modelDeriv= new DefaultTableModel(null, colunasS) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableDeriv = new JTable();
		tableDeriv.setModel(modelDeriv);
		scrollPane.setViewportView(tableDeriv);
		
		JLabel lblPilhaDeTokens = new JLabel("Pilha de Tokens");
		lblPilhaDeTokens.setBounds(861, 344, 188, 11);
		contentPane.add(lblPilhaDeTokens);
		
		lblPilhaDeDerivaes = new JLabel("Pilha de deriva\u00E7\u00F5es sintaticas");
		lblPilhaDeDerivaes.setBounds(826, 129, 223, 14);
		contentPane.add(lblPilhaDeDerivaes);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(756, 35, 293, 90);
		contentPane.add(scrollPane_2);
		
		modelVariaveis = new DefaultTableModel(null, colunasSs) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableVariaveis = new JTable();
		tableVariaveis.setModel(modelVariaveis);
		scrollPane_2.setViewportView(tableVariaveis);
		
		JLabel lblTabelaSemanticaidentificadores = new JLabel("Tabela Semantica (identificadores)");
		lblTabelaSemanticaidentificadores.setBounds(811, 10, 238, 14);
		contentPane.add(lblTabelaSemanticaidentificadores);
		
	}
	
	private void updateLexicTable(Stack tokens) { 
		 Stack tempStack = (Stack) tokens.clone();
		
		modelToken.setRowCount(0);
		 
		while(!tempStack.isEmpty()) {
			
			Token token = (Token) tempStack.pop() ;
			modelToken.addRow(new Object[] { token.getCodigo(), token.getValor(), token.getNumLinha() });
			
		}
	}
	
	private void updateSintaticTable(Stack tokens) { 
		 Stack tempStack = (Stack) tokens.clone();
		
		modelDeriv.setRowCount(0);
		 
		while(!tempStack.isEmpty()) {
			
			Token token = (Token) tempStack.pop() ;
			modelDeriv.addRow(new Object[] { token.getCodigo(), token.getValor() });
			
		}
	}
	
	private void updateSemanticTable(Map<String, Variable> vars) { 
		
		modelVariaveis.setRowCount(0);
		 
		for (Variable var : vars.values()) {
			modelVariaveis.addRow(new Object[] { var.getName(), var.getType(), var.getCategory(), var.getLevel() });
		}
			
	}
}
