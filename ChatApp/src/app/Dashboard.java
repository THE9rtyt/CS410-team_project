package app;


import javax.swing.*;

import ui.ChatPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JPanel {
    private String userType;
    private ChatManager CM;
    private ChatPanel chatPanel;
    private JPanel askUser;          // Panel to ask user type
    private JPanel dashboardPanel;   // Panel for dashboard UI

    public Dashboard(ChatManager cm, ChatPanel ChatPanel) {
        this.CM = cm;
        this.chatPanel = ChatPanel;
        setLayout(new BorderLayout(0, 0));
        
        // Initialize "Ask User" panel with buttons
        askUser = new JPanel();
        JButton btnMod = new JButton("Moderator");
        JButton btnRegister = new JButton("Register");
        askUser.add(btnMod);
        askUser.add(btnRegister);
        
        // Add askUser panel at the top
        add(askUser, BorderLayout.NORTH);

        // Create dashboardPanel but keep it hidden initially
        dashboardPanel = new JPanel(new BorderLayout(0, 0));
        dashboardPanel.setVisible(false);

        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        dashboardPanel.add(buttonPanel, BorderLayout.WEST); // Add buttons to dashboard panel

        JTextArea messageQueueArea = new JTextArea(5, 20);
        messageQueueArea.setEditable(false);
        dashboardPanel.add(new JScrollPane(messageQueueArea), BorderLayout.EAST);

        // Add dashboard panel but keep it hidden
        add(dashboardPanel, BorderLayout.CENTER);

      
        messageQueueArea.setText(cm.queueToString());

        // Action listener for "Approve" and "Reject" buttons
        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Chat nextMessage = cm.popQueue();
                if (nextMessage != null) {
                    cm.addChat(nextMessage);
                    messageQueueArea.setText(cm.queueToString());
                    chatPanel.updateText();
                }
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cm.popQueue();  
                messageQueueArea.setText(cm.queueToString());  // Update queued messages textarea
            }
        });

       
        btnMod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userType = "Moderator";
                askUser.setVisible(false);      
                dashboardPanel.setVisible(true); // Show the dashboard
            }
        });

        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userType = "User";
                askUser.setVisible(false);     
                dashboardPanel.setVisible(false); // Keep the dashboard hidden
            }
        });
    }
}