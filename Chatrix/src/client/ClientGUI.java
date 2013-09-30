
package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame
{

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    

    /**
     * Create the frame.
     */
    public ClientGUI()
    {
    	try {
			new ClientConnection().runClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 700, 400);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setTitle("Client");
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblRooms = new JLabel("Rooms");
	lblRooms.setBounds(500, 25, 46, 14);
	contentPane.add(lblRooms);
	
	JLabel lblUsers = new JLabel("Users");
	lblUsers.setBounds(500, 191, 46, 14);
	contentPane.add(lblUsers);
	
	JTextArea msgWall = new JTextArea();
	msgWall.setBounds(30, 50, 442, 213);
	msgWall.setEditable(false);
	msgWall.setWrapStyleWord(true);
	msgWall.setLineWrap(true);
	msgWall.setText("Line 1111111111111111111111111111111111111111111 med word wrap som det kan ses\nLine 2 \nLine 3 \r Return");
	contentPane.add(msgWall);
	
	JButton btnNewButton = new JButton("Send");
	btnNewButton.setBounds(390, 274, 82, 76);
	contentPane.add(btnNewButton);
	
	JTextArea textArea = new JTextArea();
	textArea.setBounds(30, 274, 341, 76);
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	contentPane.add(textArea);
	
	String[] rooms = {"Public","Room 1"};
	String[] users = {"Admin","User 1"};
	
	JList<String> roomList = new JList<String>(rooms);
	roomList.setBounds(500, 54, 174, 126);
	roomList.add(new JLabel("Public"));
	contentPane.add(roomList);
	
	JList<String> userList = new JList<String>(users);
	userList.setBounds(500, 216, 174, 126);
	contentPane.add(userList);
	setVisible(true);
	
    }
}
