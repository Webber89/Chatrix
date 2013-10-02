package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.ServerGUI;
import client.ClientGUI;
import javax.swing.JLabel;

public class ChatrixLauncher
{

    private JFrame frame;
    JPanel panel;

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
	frame.setBounds(500, 250, 400, 246);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.getContentPane().setBackground(Color.WHITE);

	try
	{
	    UIManager.setLookAndFeel(UIManager
		    .getCrossPlatformLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e1)
	{
	    e1.printStackTrace();
	}

	JButton serverButton = new JButton();
	serverButton.setBorder(null);
	serverButton.setMargin(new Insets(0, 0, 0, 0));
	try
	{
	    Image img = ImageIO.read(new File("resources/matrix.png"));
	    serverButton.setIcon(new ImageIcon(img));
	} catch (IOException ex)
	{
	}
	serverButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		new ServerGUI();
		frame.dispose();
	    }
	});
	serverButton.setBackground(Color.WHITE);
	serverButton.setBounds(33, 66, 139, 130);

	JButton clientButton = new JButton();
	clientButton.setBorder(null);
	clientButton.setMargin(new Insets(0, 0, 0, 0));
	try
	{
	    Image img = ImageIO.read(new File("resources/chatrix.png"));
	    clientButton.setIcon(new ImageIcon(img));
	} catch (IOException ex)
	{
	}
	clientButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		new ClientGUI();
		frame.dispose();
	    }
	});
	clientButton.setBounds(210, 66, 139, 130);
	clientButton.setBackground(Color.WHITE);

	frame.getContentPane().add(serverButton);
	frame.getContentPane().add(clientButton);
	
	JLabel lblChoose = new JLabel("Choose:");
	lblChoose.setBounds(33, 11, 68, 14);
	frame.getContentPane().add(lblChoose);
	
	JLabel lblServer = new JLabel("Server");
	lblServer.setBounds(76, 53, 68, 14);
	frame.getContentPane().add(lblServer);
	
	JLabel lblClient = new JLabel("Client");
	lblClient.setBounds(260, 53, 61, 14);
	frame.getContentPane().add(lblClient);
    }
}
