package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

// Abstract base class for a general Dashboard
 public class Dashboard extends JPanel{
    public String userName;
    public String userType;

    public Dashboard() {
        JPanel dashboard = new JPanel();
        
    }

    public void ModeratorDashboard() {
    	//initialize ui for dashboard
        // Approve/Reject Buttons
        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        add(buttonPanel, BorderLayout.NORTH);
     // Log area to display actions taken by the moderator
        
        JTextArea messageQueueArea = new JTextArea(5, 30);
        messageQueueArea.setEditable(false);
        add(new JScrollPane(messageQueueArea), BorderLayout.EAST);
//        messageQueueArea.append(ChatManager.chats)

        // Action listeners for buttons
        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	ChatPanel.append(messageQueueArea.getText() + "\n"); //put queued message onto chat
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//          	ChatManager.chats.remove(messageQueueArea.getText()); //remove queued message
            	// remove from actual list too
            }
        });

        setVisible(true);
    }

    public void UserDashboard() {

    }
}