package app;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import network.*;
import ui.ChatPanel;


public class Dashboard extends JPanel {
    private ChatManager cm;
    private ChatPanel chatPanel;
    private JPanel askUser;          // Panel to ask user type
    private JPanel dashboardPanel;   // Panel for dashboard UI
    private JPanel askConnect;
    private JTextField IpTextField;
    private JTextField usernameTextField;
    private ChatServer cs;
    private ChatClient cc;
    public JTextArea messageQueueArea;
    public JTextField clientQueueField;
    
    private ArrayList<String> clientList;
    
    
    public Dashboard(ChatManager cm, ChatPanel ChatPanel) {
    	this.cm = cm;
        this.chatPanel = ChatPanel;
        clientList = new ArrayList<>();
        initialize();
    }
    
    
    public void initialize() {
        setLayout(new BorderLayout(0, 0));
        
        // Initialize "Ask User" panel with buttons
        askUser = new JPanel();
        JLabel ask = new JLabel("Choose User type: ");
        JButton btnMod = new JButton("Moderator");
        JButton btnRegister = new JButton("Register");
        askUser.add(ask);
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
        

        messageQueueArea = new JTextArea(5, 20);
        messageQueueArea.setEditable(false);
        dashboardPanel.add(new JScrollPane(messageQueueArea), BorderLayout.EAST);

        // Add dashboard panel but keep it hidden
        add(dashboardPanel, BorderLayout.CENTER);

      
        messageQueueArea.setText(cm.queueToString());

        // Action listener for "Approve" and "Reject" buttons
        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cm.setQueue(cs.getQueuedMessages());
                Chat nextMessage = cm.popQueue();
                if (nextMessage != null) {
                    System.out.println(nextMessage.author);
                    messageQueueArea.setText(cm.queueToString());
                    chatPanel.updateText();
                    try {
						cs.sendMessage(nextMessage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cm.setQueue(cs.getQueuedMessages());
                cm.popQueue();  
                messageQueueArea.setText(cm.queueToString());  // Update queued messages textarea
            }
        });
 
       //listeners to clicking mod or reg
        btnMod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	allowConnection();
            }
        });
        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	initializeAskConnect();
                askUser.setVisible(false);     
                askConnect.setVisible(true);
            }
        });
    }
    
    public void allowConnection() {
        askUser.setVisible(false);
        createModeratorDashboard();
        cs = new ChatServer(9999, this);
		cs.start();
		JLabel IPLabel = new JLabel("Server Ip: " + cs.getServerIP());
		dashboardPanel.add(IPLabel, BorderLayout.SOUTH);
		
		cc = new ChatClient(9999, cs.getServerIP(), "admin");
		cc.setChatManager(cm);
		cc.start();
		chatPanel.setChatClient(cc);
        dashboardPanel.setVisible(true); // Show the dashboard
    }
    
    public void createModeratorDashboard() {
    	 dashboardPanel = new JPanel(new BorderLayout(0, 0));
         dashboardPanel.setVisible(true);
         
         JPanel buttonPanel = new JPanel();
         JButton approveButton = new JButton("Approve");
         JButton rejectButton = new JButton("Reject");
         buttonPanel.add(approveButton);
         buttonPanel.add(rejectButton);
         dashboardPanel.add(buttonPanel, BorderLayout.WEST); // Add buttons to dashboard panel
         

         messageQueueArea = new JTextArea(6,10);
         messageQueueArea.setEditable(false);
         dashboardPanel.add(new JScrollPane(messageQueueArea), BorderLayout.CENTER);

         messageQueueArea.setText(cm.queueToString());
         
         
         //create a register or remove user jpanel;
         JPanel clientQueueDash = new JPanel();
         
         JPanel clientbuttonPanel = new JPanel();
         JButton registerButton = new JButton("Register");
         JButton removeButton = new JButton("Remove");
         clientbuttonPanel.add(registerButton);
         clientbuttonPanel.add(removeButton);
         clientQueueDash.add(clientbuttonPanel, BorderLayout.WEST);

         // Client queue area (assuming it's a list or display of clients)
         clientQueueField = new JTextField();
         clientQueueField.setEditable(false);
         clientQueueField.setColumns(10);
         clientQueueDash.add(clientQueueField, BorderLayout.CENTER);
         dashboardPanel.add(clientQueueDash, BorderLayout.NORTH);
        
         // Add dashboard panel but keep it hidden
         add(dashboardPanel, BorderLayout.CENTER);

         registerButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	 if (clientList.size() != 0) {
            		cs.register(clientList.get(0));
            		if (clientList.size() > 1) {
            			clientList.remove(0);
                      	clientQueueField.setText(clientList.get(0)); 
            		}
            		else {
            			clientList.remove(0);
            			clientQueueField.setText("");
            		}
            	 }
             }
         });
         
         removeButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	if (clientList.size() != 0){
             		if (clientList.size() > 1) {
             			clientList.remove(0);
             			clientQueueField.setText(clientList.get(0));  
             		}else {
             			clientList.remove(0);
             			clientQueueField.setText("");
             		}
            	}
             }
         });
         
      // Action listener for "Approve" and "Reject" buttons
         approveButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             	cm.setQueue(cs.getQueuedMessages());
                 Chat nextMessage = cm.popQueue();
                 System.out.println(nextMessage.author);
                 if (nextMessage != null) {
                     messageQueueArea.setText(cm.queueToString());
                     chatPanel.updateText();
                     try {
 						cs.sendMessage(nextMessage);
 					} catch (IOException e1) {
 						// TODO Auto-generated catch block
 						e1.printStackTrace();
 					}
                 }
             }
         });

         rejectButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             	cm.setQueue(cs.getQueuedMessages());
                 cm.popQueue();  
                 messageQueueArea.setText(cm.queueToString());  // Update queued messages textarea
             }
         });
    }
    
    public void initializeAskConnect() {
    	JPanel askConnectPanel = new JPanel();
        JLabel enterIPLabel = new JLabel("Enter IP: ");
        IpTextField = new JTextField();
        IpTextField.setColumns(8);
        
        JLabel askUsernameLabel = new JLabel("Enter Username: ");
        usernameTextField = new JTextField();
        usernameTextField.setColumns(8);
        
        
        KeyAdapter textAreaEnter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER && !IpTextField.getText().equals("") && !usernameTextField.getText().equals("")) {
            
    				cc = new ChatClient(9999, IpTextField.getText(), usernameTextField.getText());
    		
            		cc.setChatManager(cm);
            		cc.start();
            		chatPanel.setChatClient(cc);
            		askConnect.setVisible(false);
            	}
 
            }
        };
        
        IpTextField.addKeyListener(textAreaEnter);
        usernameTextField.addKeyListener(textAreaEnter);
        
        askConnectPanel.add(enterIPLabel);
        askConnectPanel.add(IpTextField);
        askConnectPanel.add(askUsernameLabel);
        askConnectPanel.add(usernameTextField);
        
        this.askConnect = askConnectPanel;
        add(askConnect, BorderLayout.NORTH);
        
    }
 
    
    
    
    
    public void notifyNewConnection(String client) {
    	clientList.add(client);
      	clientQueueField.setText(clientList.get(0));
    }

    public void updateText () {
    	if (cs != null) {
    		cm.setQueue(cs.getQueuedMessages());
    		messageQueueArea.setText(cm.queueToString());  // Update queued messages textarea
    		revalidate();
    		repaint();
    	}
    }
}