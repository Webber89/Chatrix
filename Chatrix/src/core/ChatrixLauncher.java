package core;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import server.Server;

import client.Client;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatrixLauncher
{

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
	EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    ChatrixLauncher window = new ChatrixLauncher();
		    window.frame.setVisible(true);
		    
		    
		} catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public ChatrixLauncher()
    {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
	frame = new JFrame();
	frame.setBounds(100, 100, 400, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	
	JRadioButton rdbtnServer = new JRadioButton("Server");
	buttonGroup.add(rdbtnServer);
	rdbtnServer.setBounds(45, 46, 109, 23);
	frame.getContentPane().add(rdbtnServer);
	
	JRadioButton rdbtnClient = new JRadioButton("Client");
	buttonGroup.add(rdbtnClient);
	rdbtnClient.setBounds(234, 46, 109, 23);
	frame.getContentPane().add(rdbtnClient);
	
	JButton btnConnect = new JButton("Enter the Chatrix");
	btnConnect.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if (buttonGroup.getElements().nextElement().isSelected()){
			new Client();
		    }
		    else{
			new Server();
		    }
		}
	});
	btnConnect.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 17));
	btnConnect.setBounds(90, 177, 192, 73);
	frame.getContentPane().add(btnConnect);
	
    }
}
