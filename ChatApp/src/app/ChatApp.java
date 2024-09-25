package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import network.ChatClient;
import network.ChatServer;
import ui.ChatPanel;

public class ChatApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private ChatPanel contentPane;
	private ChatManager cm;
	private Dashboard db;
	private ChatClient cc;
	private ChatServer cs;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatApp frame = new ChatApp();
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
	public ChatApp() {
		cm = new ChatManager();
		db = new Dashboard();
		
		cs = new ChatServer(9999);
		System.out.println(cs.getServerIP());
		cs.start();
		cc = new ChatClient(9999, cs.getServerIP(), "kellen");
		cc.start();
		cs.registerId(0);
		System.out.println("client done");
		cc.setChatManager(cm);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new ChatPanel(cm, cc, cc.getUserName());
		add(db, BorderLayout.NORTH);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setFocusable(true);
		setContentPane(contentPane);
		cm.setPanel(contentPane);
		
	}

}