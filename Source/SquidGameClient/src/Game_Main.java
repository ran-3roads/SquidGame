// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

public class Game_Main extends JFrame {
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	private Image login = tk.getImage("login.png");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_Main frame = new Game_Main();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game_Main() {
		setTitle("Squid Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(222, 184, 79));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(login, 220, 0, 400, 400, this);
				setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
				super.paintComponent(g);
			}
		};
		background.setSize(600,400);
		contentPane.add(background);

		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(12, 59, 82, 33);
		contentPane.add(lblNewLabel);

		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(101, 59, 116, 33);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(12, 120, 82, 33);
		contentPane.add(lblIpAddress);

		txtIpAddress = new JTextField();
		txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtIpAddress.setText("127.0.0.1");
		txtIpAddress.setColumns(10);
		txtIpAddress.setBounds(101, 120, 116, 33);
		contentPane.add(txtIpAddress);

		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(12, 203, 82, 33);
		contentPane.add(lblPortNumber);

		txtPortNumber = new JTextField();
		txtPortNumber.setText("30000");
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setColumns(10);
		txtPortNumber.setBounds(101, 203, 116, 33);
		contentPane.add(txtPortNumber);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(12, 243, 205, 38);
		contentPane.add(btnConnect);
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
		txtUserName.addActionListener(action);
		txtIpAddress.addActionListener(action);

	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = txtIpAddress.getText().trim();
			String port_no = txtPortNumber.getText().trim();
			QueWindow que = new QueWindow(username, ip_addr, port_no);
			setVisible(false);
		}
	}
}
