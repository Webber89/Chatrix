package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientLogin {

	private static JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private static ClientController controller = ClientController.getInstance();
	private static JLabel statusLbl;
	private JButton btnRegister;

	/**
	 * Create the application.
	 */
	public ClientLogin() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Logon");
		frame.setBounds(100, 100, 221, 215);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(29, 49, 59, 14);
		frame.getContentPane().add(lblUser);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(29, 81, 72, 14);
		frame.getContentPane().add(lblPassword);

		textField = new JTextField();
		textField.setBounds(100, 46, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statusLbl.setText("Logging in..");
					controller.login(textField.getText(), new String(
							passwordField.getPassword()));
			}
		});
		btnLogin.setBounds(97, 109, 89, 23);
		frame.getContentPane().add(btnLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(100, 78, 86, 20);
		frame.getContentPane().add(passwordField);

		statusLbl = new JLabel("Please enter credentials");
		statusLbl.setBounds(29, 11, 231, 14);
		frame.getContentPane().add(statusLbl);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(100, 143, 89, 23);
		frame.getContentPane().add(btnRegister);
	}
	
		public static void startClient() {
			new ClientGUI(controller);
			frame.dispose();
		}
		
		public static void showErrorMessage(String message) {
			statusLbl.setText(message);
		}
}
