package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.ChatPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private ChatPanel contentPane;
	private ChatManager cm;
	private Dashboard db;

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
		db = new Dashboard();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new ChatPanel(cm);
		add(db, BorderLayout.NORTH);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.addKeyListener(contentPane);
		contentPane.setFocusable(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				contentPane.setBounds(0, 0, getWidth(), getHeight());
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				
			}

			
			
		});;
		
	}

}
