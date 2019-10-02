package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import analisadorSintatico.AnalisadorSintatico;
import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Erros;
import analizadorLexico.ManipuladorArquivos;
import analizadorLexico.Retorno;
import analizadorLexico.Token;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class Frame extends JFrame {

	private JPanel contentPane;
	private JList pilhaDerivação;
	private JList pilhaToken;
	private JLabel lblConsole;
	private JLabel lblAreaDeCdigo;
	private JScrollPane scrollPaneAreaDeCodigo;
	private JTextArea areaDeCodigo;
	private JScrollPane scrollPaneConsole;
	private JTextArea consoleTextArea;
	private DefaultListModel defaultListModel = new DefaultListModel();
	private String fontCode;
	private JButton btnSalvar;
	private JScrollPane scrollPaneListaToken;
	private JScrollPane scrollPane;

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
		setBounds(100, 100, 947, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
//		-----------------------Button-----------------------------
		JButton btnCompilar = new JButton("Compilar");
		btnCompilar.setBackground(SystemColor.menu);
		btnCompilar.setBounds(615, 6, 131, 22);
		btnCompilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				defaultListModel.clear();
				consoleTextArea.setText("");
				fontCode = areaDeCodigo.getText();
				AnalizadorLexico lexico = new AnalizadorLexico();
				
				try {
					
					Retorno retorno = lexico.Analizar(fontCode);
					retorno.gethasError();
					Stack<Token> tokenStack = retorno.getTokenStack();
					Stack<Erros> errorStack = retorno.getErrorStack();
					
					if(!retorno.gethasError()) {
						AnalisadorSintatico sintatico = new AnalisadorSintatico(tokenStack);
						sintatico.analiseSintatica();
					}
					
					if(retorno.gethasError()) {
						Erros erros = errorStack.pop();
						String erro = erros.getMensagem() + erros.getLinha();
						
						consoleTextArea.setText(erro);
					} else {						
						defaultListModel.addElement("CODIGO | VALOR | LINHA");
						while(!tokenStack.isEmpty()) {
							Token token = tokenStack.pop();
							String tokenString = token.getCodigo() + " | "
									+ token.getValor() + " | "
									+ token.getNumLinha();
							defaultListModel.addElement(tokenString);
						}
						pilhaToken.setModel(defaultListModel);
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.setLayout(null);
		
		JButton btnAbrirArquivo = new JButton("Abrir Arquivo");
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
		btnAbrirArquivo.setBounds(487, 6, 118, 23);

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
						String fileName = JOptionPane.showInputDialog("Informe o nome do arquivo");
						
						try {
							new ManipuladorArquivos().GravaArquivo(fontCode, fileName,chooser.getSelectedFile().getPath());
							
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
		btnSalvar.setBounds(388, 6, 89, 23);
//		------------------------------------------------------------------
		contentPane.add(btnSalvar);
		contentPane.add(btnAbrirArquivo);
		contentPane.add(btnCompilar);
		
//		----------------------------------Labels---------------------------------------
		JLabel lblPilhaDeTokens = new JLabel("                Pilha de Tokens");
		lblPilhaDeTokens.setBounds(756, 234, 175, 25);
		lblPilhaDeTokens.setBackground(SystemColor.windowBorder);
		contentPane.add(lblPilhaDeTokens);
		
		JLabel lblPilha = new JLabel("           Pilha de Deriva\u00E7\u00E3o");
		lblPilha.setBounds(756, 10, 175, 20);
		contentPane.add(lblPilha);
		
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
		scrollPaneConsole.setViewportView(consoleTextArea);
		
		scrollPaneListaToken = new JScrollPane();
		scrollPaneListaToken.setBounds(756, 257, 175, 286);
		contentPane.add(scrollPaneListaToken);
		
		pilhaToken = new JList();
		scrollPaneListaToken.setViewportView(pilhaToken);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(756, 31, 175, 207);
		contentPane.add(scrollPane);
		
//		---------------------------------Lists-----------------------------------------
		pilhaDerivação = new JList();
		scrollPane.setViewportView(pilhaDerivação);
		
	}
}
