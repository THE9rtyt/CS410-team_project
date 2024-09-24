package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.ChatManager;
import app.User;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ChatManager cm;
	private JTextField messageBox;
	private JTextPane chatBox;
	private User u;
	private String last_str;
	/**
	 * Create the panel.
	 */
	public ChatPanel(ChatManager cm) {
		this.cm = cm;
		setLayout(new BorderLayout(0, 0));
		
		messageBox = new JTextField();
		add(messageBox, BorderLayout.SOUTH);
		messageBox.setColumns(10);
		
		chatBox = new JTextPane();
		JScrollPane scroll = new JScrollPane(chatBox);
		chatBox.setText(cm.toString());
		add(scroll, BorderLayout.CENTER);
		last_str = new String(this.cm.toString());
		messageBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cm.addChat(messageBox.getText());
				messageBox.setText("");
				revalidate();
				repaint();
			}
			
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		updateText();
	}
	
	
	public void updateText () {
		String str = cm.toString();
		if (!str.equals(last_str)) {
			chatBox.setText(str);
			last_str = new String(str);
			revalidate();
			repaint();
		}
	}


}
