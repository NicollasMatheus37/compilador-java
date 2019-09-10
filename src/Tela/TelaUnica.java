package Tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaUnica extends JFrame {

	private JPanel contentPane;
	private JList pilhaDerivação;
	private JList pilhaToken;
	private JLabel lblConsole;
	private JLabel lblAreaDeCdigo;
	private JScrollPane scrollPaneAreaDeCodigo;
	private JTextArea areaDeCodigo;
	private JScrollPane scrollPaneConsole;
	private JTextArea textArea_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUnica frame = new TelaUnica();
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
	public TelaUnica() {
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
				//Ação
			}
		});
		contentPane.setLayout(null);
//		------------------------------------------------------------------
		contentPane.add(btnCompilar);
		
//		---------------------------------Lists-----------------------------------------
		pilhaDerivação = new JList();
		pilhaDerivação.setBounds(756, 31, 175, 192);
		contentPane.add(pilhaDerivação);
		
		pilhaToken = new JList();
		pilhaToken.setBounds(756, 262, 175, 281);
		contentPane.add(pilhaToken);
		
//		----------------------------------Labels---------------------------------------
		JLabel lblPilhaDeTokens = new JLabel("                Pilha de Tokens");
		lblPilhaDeTokens.setBounds(756, 234, 175, 25);
		lblPilhaDeTokens.setBackground(SystemColor.windowBorder);
		contentPane.add(lblPilhaDeTokens);
		
		JLabel lblPilha = new JLabel("                 Pilha de Deriva\u00E7\u00E3o");
		lblPilha.setBounds(756, 11, 175, 20);
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
		
		textArea_1 = new JTextArea();
		scrollPaneConsole.setViewportView(textArea_1);
	}
}
