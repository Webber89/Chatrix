package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame
{

    private static ClientGUI gui = new ClientGUI();
    private JPanel contentPane;
    private JTextField inputField;
    private JTextPane msgWall;
    JList<String> roomList;
    JList<String> userList;
    private DefaultListModel<String> userListModel;
    private DefaultListModel<String> roomListModel;
    private JButton sendButton;

    /**
     * Create the frame.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private ClientGUI()
    {
	addWindowListener(new WindowAdapter()
	{
	    @Override
	    public void windowClosing(WindowEvent e)
	    {
		super.windowClosing(e);
		ClientController.getInstance().logout();
	    }
	});
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	try
	{
	    UIManager
		    .setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
	setBounds(100, 100, 700, 400);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setTitle("Client");
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JLabel lblRooms = new JLabel("Rooms");
	lblRooms.setBounds(500, 25, 76, 14);
	contentPane.add(lblRooms);

	JLabel lblUsers = new JLabel("Users");
	lblUsers.setBounds(500, 191, 76, 14);
	contentPane.add(lblUsers);

	msgWall = new JTextPane();
	DefaultCaret caret = (DefaultCaret) msgWall.getCaret();
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	msgWall.setEditable(false);
//	msgWall.setWrapStyleWord(true);
//	msgWall.setLineWrap(true);
	JScrollPane scroll = new JScrollPane(msgWall);
	scroll.setBounds(10, 10, 462, 258);
	scroll.setWheelScrollingEnabled(true);
	contentPane.add(scroll);

	inputField = new JTextField();
	inputField.setBounds(10, 274, 361, 76);
	contentPane.add(inputField);

	sendButton = new JButton("Send");
	sendButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		sendMsg();
	    }
	});
	sendButton.setBounds(390, 274, 82, 76);
	getRootPane().setDefaultButton(sendButton);
	contentPane.add(sendButton);

	roomListModel = new DefaultListModel();
	roomList = new JList(roomListModel);
	roomList.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mousePressed(MouseEvent e)
	    {
		if (e.getClickCount() >= 2)
		{
		    joinRoom();

		}
	    }
	});
	roomList.setBounds(500, 54, 174, 126);
	roomList.add(new JLabel("Public"));
	contentPane.add(roomList);

	userListModel = new DefaultListModel();
	userList = new JList(userListModel);
	userList.setBounds(500, 216, 174, 126);
	contentPane.add(userList);
	setVisible(true);

    }

    public void sendMsg()
    {

	if (!inputField.getText().equals(""))
	{

	    if (roomList.getSelectedValue() == null)
	    {
		roomList.setSelectedIndex(0);
	    }
	    String currentRoom = roomList.getSelectedValue();
	    ClientController.getInstance().send(currentRoom,
		    inputField.getText());
	    // msgWall.append(inputField.getText() + "\n");
	    inputField.setText("");
	}
    }

    public void joinRoom()
    {
	System.out.println(roomList.getSelectedValue());
	// Call method in clientController which accepts a room, user session ID
	// and
    }

    public void updateUsers(List<String> users)
    {
	userListModel.removeAllElements();
	for (String s : users)
	{
	    userListModel.addElement(s);
	}
    }

    public void updateRooms(Collection<String> rooms)
    {
	roomListModel.removeAllElements();
	for (String s : rooms)
	{
	    roomListModel.addElement(s);
	}
    }

    public static ClientGUI getInstance()
    {
	return gui;
    }

    public void addMessage(String user, String message, String timestamp)
    {
	msgWall.setCaretPosition(msgWall.getDocument().getLength());
	try
	{
	    msgWall.getDocument().insertString(msgWall.getDocument().getLength(), "(" + timestamp + ") " + user + " says: " + message
	    	
	    	+ "\n", null);
	} catch (BadLocationException e)
	{
	    e.printStackTrace();
	}
    }

    public void lostConnection()
    {
	append(Color.RED,"Connection lost - trying to reconnect");
	sendButton.setEnabled(false);
    }

    public void reconnected()
    {
	append(Color.GREEN,"Connction re-established");
	sendButton.setEnabled(true);
    }
    public void append(Color c,String text){
	StyledDocument doc = (StyledDocument) msgWall.getDocument();
	Style style = doc.addStyle("styleName", null);
	StyleConstants.setBold(style,true);
	style.addAttribute(StyleConstants.Foreground, c);
	try
	{
	    msgWall.getDocument().insertString(msgWall.getDocument().getLength(),text+"\n", style);
	} catch (BadLocationException e)
	{
	    System.out.println(e.getMessage());
	}
    }
}
