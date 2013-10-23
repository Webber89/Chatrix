package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private static ClientGUI gui = new ClientGUI();
	private JPanel contentPane;
	private JTextArea inputField;
	private JTextArea msgWall;
	JList<String> roomList;
	JList<String> userList;
	private DefaultListModel<String> userListModel;
	private DefaultListModel<String> roomListModel;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ClientGUI() {
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

		msgWall = new JTextArea();
		msgWall.setBounds(30, 50, 442, 213);
		msgWall.setEditable(false);
		msgWall.setWrapStyleWord(true);
		msgWall.setLineWrap(true);
		contentPane.add(msgWall);

		inputField = new JTextArea();
		inputField.setBounds(30, 274, 341, 76);
		inputField.setWrapStyleWord(true);
		inputField.setLineWrap(true);
		contentPane.add(inputField);

		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				sendMsg();
			}
		});
		sendButton.setBounds(390, 274, 82, 76);
		getRootPane().setDefaultButton(sendButton);
		contentPane.add(sendButton);

		roomListModel = new DefaultListModel();
		roomList = new JList(roomListModel);
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() >= 2) {
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

	public void sendMsg() {

		if (roomList.getSelectedValue() == null) {
			roomList.setSelectedIndex(0);
		}
		String currentRoom = roomList.getSelectedValue();
		ClientController.getInstance().send(currentRoom, inputField.getText());
		// msgWall.append(inputField.getText() + "\n");
		inputField.setText("");
	}

	public void joinRoom() {
		System.out.println(roomList.getSelectedValue());
		// Call method in clientController which accepts a room, user session ID
		// and
	}

	public void updateUsers(List<String> users) {
		userListModel.removeAllElements();
		for (String s : users) {
			userListModel.addElement(s);
		}
	}

	public void updateRooms(Collection<String> rooms) {
		roomListModel.removeAllElements();
		for (String s : rooms) {
			roomListModel.addElement(s);
		}
	}

	public static ClientGUI getInstance() {
		return gui;
	}

	public void AddMessage(String user, String message, String timestamp) {
		msgWall.append("(" + timestamp + ") " + user + " says: " + message
				+ "\n");
	}

}
