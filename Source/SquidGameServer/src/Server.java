import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Server extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의

	private boolean GameStart = false;

	public int dieRank = 5; // 유저가 사망시에 등수를 깎아서 부여함
	public int winRank = 0;// 유저가 골인시 등수를 더해서 부여함

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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
	public Server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성

					ServerQue new_user = new ServerQue(client_socket);

					UserVec.add(new_user);

					new_user.start();
					AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(Action msg) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		textArea.append("code = " + msg.code + "\n");
		textArea.append("id = " + msg.UserName + "\n");
		textArea.append("data = " + msg.data + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// Server Que 대기창
	class ServerQue extends Thread {
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector user_vc;
		public int xPos, yPos;

		public String UserName = "";
		public int MAX = 4;
		public int player;
		public int usrRank = 0;
		public int coin = 1;

		public ServerQue(Socket client_socket) {

			this.client_socket = client_socket;
			this.user_vc = UserVec;

			try {
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

			} catch (Exception e) {
				AppendText("error");
			}
		}
		// user가 죽었을 때 rank를 처리함.
		public synchronized void usrDie(String username) {
			if (coin == 1) {//coin은 중복으로 rank를 삭제하는 것을 방지하기 위해
				coin--;
				dieRank--;
				usrRank = dieRank;
				AppendText("사용자 " + "[" + username + "] 사망," + usrRank + "등입니다.");
			}
		}
		// user가 골인했을 때 rank를 처리함.
		public synchronized void usrWin(String username) {
			if (coin == 1) {
				coin--;
				winRank++;
				usrRank = winRank;
				AppendText("사용자 " + "[" + username + "] 골인!," + usrRank + "등입니다.");
			}
		}

		public synchronized void Exit() {
			UserVec.removeElement(this); // exit한 현재 객체를 벡터에서 지운다
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
			int i = user_vc.size() - 1;
			Integer.toString(i);
			StartAll(Integer.toString(i));
		}
		//초기 유저들의 정보를 init 할때 사용한다.
		public synchronized void StartOne(String msg) {
			try {
				Action obcm = new Action("SERVER", "101", msg);
				obcm.xPos = xPos;
				obcm.yPos = yPos;
				obcm.player = player;

				oos.writeObject(obcm);

			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}
		//초기 유저들의 좌표를 설정한다.
		public synchronized void initPosOne() {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				if (user == this) {
					user.xPos = 10 + 100 * i;
					user.yPos = 820;
					user.player = i;
				}
			}
		}
		//초기 유저들의 정보를 init 할때 사용한다.
		public synchronized void StartAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				user.StartOne(str);
			}
		}
		//초기 유저들의 정보를 init 할때 사용한다.
		public void Wait() {
			int i = user_vc.size();
			AppendText("새로운 참가자 " + UserName + " 입장.");
			Integer.toString(i);
			StartAll(Integer.toString(i));
		}
		//초기 유저들의 정보를 init 할때 사용한다.
		public void StartOneObject(Object ob) {
			try {
				oos.writeObject(ob);
			} catch (IOException e) {
				AppendText("oos.writeObject(ob) error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit();
			}
		}

		// Client 유저 위치 표시
		public synchronized void userAllSetting() {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				user.userOneSetting();
			}
		}
		//초기 유저들의 정보를 init 할때 사용한다.
		public synchronized void userOneSetting() {
			try {
				Action obcm = new Action("SERVER", "102", UserName);
				obcm.player = player;
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
					Exit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}
		//유저들의 좌표,목숨,공격유무에 대해서 표시해준다.
		public synchronized void writeOneStage(int x, int y, int p, boolean l,boolean a) {
			try {
				Action obcm = new Action("SERVER", "400", UserName);
				obcm.xPos = x;
				obcm.yPos = y;
				obcm.player = p;
				obcm.life = l;
				obcm.punch=a;
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
					Exit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// Client 화면유저들 위치 표시
		public synchronized void writeAllStage(int x, int y, int p, boolean l,boolean a) {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				user.writeOneStage(x, y, p, l,a);
			}
		}
		//rank를 보여준다.
		public synchronized void showOne() {
			try {
				Action obcm = new Action("SERVER", "999", "Rank");
				obcm.rank = usrRank;
				obcm.UserName = UserName;
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
					Exit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}
		//rank를 보여준다.
		public synchronized void ShowRanking() {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				user.showOne();
			}
		}
		// 다른 유저가 밀었을 시에 처리해준다.
		public synchronized void killMessage(int dieP) {
			for (int i = 0; i < user_vc.size(); i++) {
				ServerQue user = (ServerQue) user_vc.elementAt(i);
				if(user.player==dieP) {
					user.killOne();	
					AppendText(i+"죽임");
				}
			}
		}
		// 다른 유저가 밀었을 시에 처리해준다.
		public synchronized void killOne() {
			try {
				Action obcm = new Action("SERVER", "445", "youDie");
				obcm.xPos=xPos;
				obcm.yPos=yPos;
				obcm.player=player;
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
					Exit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Exit(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					Action cm = null;
					if (socket == null)
						break;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if (obcm == null)
						break;
					if (obcm instanceof Action) {
						cm = (Action) obcm;
						AppendObject(cm);
					} else
						continue;

					if (cm.code.matches("100")) {
						UserName = cm.UserName;
						player=cm.player;
						initPosOne();
						Wait();
						if (user_vc.size() == 4) {
							GameStart = true;
							userAllSetting();
						}
					} else if (cm.code.matches("300")) {// user move
						xPos=cm.xPos;
						yPos=cm.yPos;
						player=cm.player;
						writeAllStage(cm.xPos, cm.yPos, cm.player, cm.life,cm.punch);
					} else if (cm.code.matches("600")) {//user die
						usrDie(cm.UserName);
					} else if (cm.code.matches("700")) {//user goal in
						usrWin(cm.UserName);
					} else if (cm.code.matches("800")) {// 게임 종료됐고 랭크를 요청함.
						ShowRanking();
					}else if(cm.code.matches("444")) {//cm.player를 죽였다.
						killMessage(cm.player);
					}

				} catch (IOException e) { // IO이셉션으로 바꿀 생각
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Exit();
						break;
					} catch (Exception ee) {
						break;
					}
				}
			} // while
		}// run
	}

}
