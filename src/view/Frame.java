package view;

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

public class Frame extends JFrame {

	private JPanel contentPane;
	private JList list;
	private JList list_1;
	private JLabel lblConsole;
	private JLabel lblAreaDeCdigo;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JScrollPane scrollPane_1;
	private JTextArea textArea_1;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
//		-----------------------Button-----------------------------
		JButton btnCompilar = new JButton("Compilar");
		btnCompilar.setBounds(484, 5, 131, 22);
		btnCompilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Ação
			}
		});
		contentPane.setLayout(null);
//		------------------------------------------------------------------
		contentPane.add(btnCompilar);
		
//		---------------------------------Lists-----------------------------------------
		list = new JList();
		list.setBounds(625, 31, 175, 192);
		contentPane.add(list);
		
		list_1 = new JList();
		list_1.setBounds(625, 249, 175, 189);
		contentPane.add(list_1);
		
//		----------------------------------Labels---------------------------------------
		JLabel lblPilhaDeTokens = new JLabel("                Pilha de Tokens");
		lblPilhaDeTokens.setBounds(625, 224, 175, 25);
		lblPilhaDeTokens.setBackground(SystemColor.windowBorder);
		contentPane.add(lblPilhaDeTokens);
		
		JLabel lblPilha = new JLabel("                 Pilha ####");
		lblPilha.setBounds(625, 7, 175, 20);
		contentPane.add(lblPilha);
		
		lblConsole = new JLabel("   Console:");
		lblConsole.setBounds(10, 331, 131, 20);
		contentPane.add(lblConsole);
		
		lblAreaDeCdigo = new JLabel(" \u00C1rea de c\u00F3digo:");
		lblAreaDeCdigo.setBounds(10, 5, 131, 25);
		contentPane.add(lblAreaDeCdigo);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 605, 289);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 349, 605, 89);
		contentPane.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
	}
}
