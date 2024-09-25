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


class UpdateThread extends Thread {
	private Dashboard db;
	
	public UpdateThread (Dashboard db) {
		this.db = db;
	}
	
	public void run () {
		while (true) {
			db.updateText();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


public class ChatApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private ChatPanel contentPane;
	private ChatManager cm;
	private Dashboard dbpanel;
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300); 
		contentPane = new ChatPanel(cm, cc);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setFocusable(true);
		setContentPane(contentPane);
		cm.setPanel(contentPane);
		
		dbpanel = new Dashboard(cm, contentPane);
        dbpanel.setFocusable(true);
		add(dbpanel, BorderLayout.NORTH);
        
		UpdateThread updatet = new UpdateThread(dbpanel);
		updatet.start();
		

		
	}

}
