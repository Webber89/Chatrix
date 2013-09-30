package server;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame
{

    private JPanel contentPane;
    private final ButtonGroup buttonGroup_1 = new ButtonGroup();
    private JTextField textField;
    private final ButtonGroup buttonGroup_3 = new ButtonGroup();
    ServerController sc = new ServerController();

    
    /**
     * Create the frame.
     */
    public ServerGUI()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 700, 400);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setTitle("Server");
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JPanel panel = new JPanel();
	panel.setBounds(10, 11, 263, 157);
	panel.setBorder(BorderFactory.createLineBorder(Color.gray));
	contentPane.add(panel);
	panel.setLayout(null);
	
	JButton btnNewButton = new JButton("Start Server");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    sc.createServer("TCP", false, 7000);
		}
	});
	btnNewButton.setBounds(43, 126, 91, 23);
	panel.add(btnNewButton);
	
	JToggleButton rdbtnTcp = new JToggleButton("TCP");
	rdbtnTcp.setBounds(107, 11, 68, 23);
	panel.add(rdbtnTcp);
	buttonGroup_1.add(rdbtnTcp);
	rdbtnTcp.setSelected(true);
	
	JToggleButton rdbtnUdp = new JToggleButton("UDP");
	rdbtnUdp.setBounds(185, 11, 68, 23);
	panel.add(rdbtnUdp);
	buttonGroup_1.add(rdbtnUdp);
	
	textField = new JTextField();
	textField.setBounds(107, 76, 68, 20);
	panel.add(textField);
	textField.setColumns(10);
	
	JLabel lblPort = new JLabel("Port");
	lblPort.setBounds(10, 79, 46, 14);
	panel.add(lblPort);
	
	JLabel lblConnectionType = new JLabel("Connection type");
	lblConnectionType.setBounds(10, 15, 87, 14);
	panel.add(lblConnectionType);
	
	JLabel lblWanlocal = new JLabel("WAN / LAN");
	lblWanlocal.setBounds(10, 46, 61, 14);
	panel.add(lblWanlocal);
	
	JToggleButton tglbtnWan = new JToggleButton("Wan");
	buttonGroup_3.add(tglbtnWan);
	tglbtnWan.setBounds(107, 42, 68, 23);
	panel.add(tglbtnWan);
	
	JToggleButton tglbtnPrivate = new JToggleButton("Local");
	buttonGroup_3.add(tglbtnPrivate);
	tglbtnPrivate.setBounds(185, 42, 68, 23);
	panel.add(tglbtnPrivate);
	setVisible(true);
    }
}
