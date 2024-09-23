package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import app.ChatManager;

public class ChatPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private ChatManager cm;
	private String str = "";
	/**
	 * Create the panel.
	 */
	public ChatPanel(ChatManager cm) {
		this.cm = cm;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		cm.draw(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawString(str, 0, getHeight() - 20);
		revalidate();
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_ENTER) {
			char c = e.getKeyChar();
			if (c != KeyEvent.CHAR_UNDEFINED) {
				str = str + e.getKeyChar();
			}
		} else {
			if (str != "") {
				cm.addChat(str);
				str = "";
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
