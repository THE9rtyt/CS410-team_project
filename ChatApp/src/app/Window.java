package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.ChatPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private ChatPanel contentPane;
	private ChatManager cm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
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
	public Window() {
		cm = new ChatManager();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new ChatPanel(cm);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.addKeyListener(contentPane);
		contentPane.setFocusable(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	}

}
