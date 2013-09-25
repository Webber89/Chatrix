package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.Server;
import client.Client;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatrixLauncher
{

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();
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
	frame.setBounds(500, 250, 400, 225);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.getContentPane().setBackground(Color.WHITE);

	 try
	{
	    UIManager.setLookAndFeel(
	                UIManager.getCrossPlatformLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e1)
	{
	    e1.printStackTrace();
	}
	
	panel = new JPanel();
	panel.setBackground(Color.WHITE);
	panel.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mouseClicked(MouseEvent arg0)
	    {
		new Server();
		frame.dispose();
	    }

	    @Override
	    public void mouseEntered(MouseEvent arg0)
	    {
		panel.removeAll();
		try
		{
		    BufferedImage myPicture3 = ImageIO.read(new File(
			    "resources/redeye.png"));
		    JLabel picLabel3 = new JLabel(new ImageIcon(myPicture3));
		    panel.add(picLabel3);
		    frame.revalidate();

		} catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }
		@Override
		public void mouseExited(MouseEvent e) {
		    panel.removeAll();
			try
			{
			    BufferedImage myPicture3 = ImageIO.read(new File(
				    "resources/matrix.png"));
			    JLabel picLabel3 = new JLabel(new ImageIcon(myPicture3));
			    panel.add(picLabel3);
			    frame.revalidate();

			} catch (IOException e1)
			{
			    e1.printStackTrace();
			}
		}
	});
	
	panel.setBounds(26, 31, 139, 130);

	JPanel panel_1 = new JPanel();
	panel_1.setBounds(203, 31, 139, 130);
	panel_1.setBackground(Color.WHITE);
	panel_1.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mouseClicked(MouseEvent arg0)
	    {
		new Client();
		frame.dispose();
	    }
	});

	try
	{

	    BufferedImage myPicture = ImageIO.read(new File(
		    "resources/matrix.png"));
	    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	    panel.add(picLabel);
	    frame.getContentPane().add(panel);

	    BufferedImage myPicture1 = ImageIO.read(new File(
		    "resources/chatrix.png"));
	    JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
	    panel_1.add(picLabel1);
	    frame.getContentPane().add(panel_1);

	} catch (IOException e)
	{
	}

    }
}
