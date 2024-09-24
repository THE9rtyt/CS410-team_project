package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import app.ChatManager;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ChatManager cm;
	private JTextField messageBox;
	private JTextPane chatBox;
	/**
	 * Create the panel.
	 */
	public ChatPanel(ChatManager cm) {
		this.cm = cm;
		setLayout(new BorderLayout(0, 0));
		
		messageBox = new JTextField();
		add(messageBox, BorderLayout.SOUTH);
		messageBox.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(cm.toString());
		add(textPane, BorderLayout.CENTER);
		
		messageBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cm.addChat(messageBox.getText());
				messageBox.setText("");
				textPane.setText(cm.toString());
			}
			
		});
	}
	
	public void updateText () {
		chatBox.setText(cm.toString());
	}


}
