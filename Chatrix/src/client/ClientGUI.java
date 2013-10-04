package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextArea inputField;
	private JTextArea msgWall;
	JList<String> roomList;
	JList<String> userList;
	private ClientController controller;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ClientGUI(ClientController controller) {
		this.controller = controller;
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
		msgWall.append("Line 11111111111111111111\n");
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
		contentPane.add(sendButton);

		String[] rooms = { "Public", "Room 1" };
		String[] users = { "Admin", "User 1" };

		roomList = new JList(rooms);
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

		userList = new JList(users);
		userList.setBounds(500, 216, 174, 126);
		contentPane.add(userList);
		setVisible(true);

	}

	public void sendMsg() {
		controller.send(inputField.getText() + "\n");
		inputField.setText("");
	}

	public void joinRoom() {
		System.out.println(roomList.getSelectedValue());
		// Call method in clientController which accepts a room, user session ID
		// and
	}

}
