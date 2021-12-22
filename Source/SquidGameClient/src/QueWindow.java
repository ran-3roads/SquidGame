import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

public class QueWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public String now = "● ○ ○ ○ ○";
	public static String sign = new String(" ");
	public static boolean moveOK;

	public Board bd;

	private int usrCnt;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket; // 연결소켓

	public Game_View gameview;
	public Stage stg;

	private QueWindow mainview;

	String UserName, Ip_Addr, Port_No;
	public int Rank;

	static int myuser;//본인의 player number
	static int xPos;//본인 x좌표
	static int yPos;//본인 y좌표

	public int coin = 1;

	public static int aX = 10, aY = 820, bX = 110, bY = 820, cX = 210, cY = 820, dX = 310, dY = 820;//유저들의 초기좌표
	public static boolean aL = true, bL = true, cL = true, dL = true;//유저들의 좌표 정보
	public static boolean aP = false, bP = false, cP = false, dP = false;// 유저들의 공격 유무

	public QueWindow(String username, String ip_addr, String port_no) {
		UserName = username;
		mainview = this;

		setTitle("Waiting room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 230, 230);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		setVisible(true);
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			// SendMessage("/login " + UserName);
			Action obcm = new Action(UserName, "100", "Hello");
			SendObject(obcm);
			waitQue net = new waitQue();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paint(Graphics g) {
		g.setFont(new Font("Ariel", Font.BOLD, 20));
		g.drawString("큐 기다리는 중...", 50, 100);
		g.drawString(now, 70, 150);
	}

	class waitQue extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					Action cm;

					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof Action) {
						cm = (Action) obcm;
						msg = String.format("[%s]\n%s", cm.UserName, cm.data);
					} else
						continue;
					switch (cm.code) {
					case "101": // 자신의 데이터를 받아서 기록한다.
						usrCnt = Integer.parseInt(cm.data);
						xPos = cm.xPos;
						yPos = cm.yPos;
						myuser = cm.player;
						break;
					case "400"://각유저들의 좌표,목숨, 공격유무에대해 refresh 한다.
						whatXY(cm.xPos, cm.yPos, cm.player, cm.life,cm.punch);
						break;
					case "445"://각유저들의 좌표,목숨, 공격유무에대해 refresh 한후 Game_View의 Murder를 실행한다.
						whatXY(cm.xPos, cm.yPos, cm.player,false,false);
						Game_View.Murder(UserName);
						break;
					case "999":
						if (coin == 1) {//coin은 중복 적으로 board를 실행하지 않기위해 설정한다.
							coin--;
							Rank=cm.rank;
							Board bd = new Board(UserName,Rank, mainview);//게임 종료 후 랭킹 보드 창을 출력한다.
						}
						break;
					}
					//유저들이 대기 하면 큐가 늘었음을 확인 시켜준다.
					if (usrCnt == 1) {
						now = "● ○ ○ ○";
						mainview.repaint();
					} else if (usrCnt == 2) {
						now = "● ● ○ ○";
						mainview.repaint();
					} else if (usrCnt == 3) {
						now = "● ● ● ○";
						mainview.repaint();
					} else if (usrCnt == 4) {
						now = "● ● ● ●";
						mainview.repaint();
						Game_View gameview = new Game_View(UserName, mainview, xPos, yPos);
						usrCnt = 0;
						dispose();
					}

				} catch (IOException e) {
					try {
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}
	//각유저들의 좌표,목숨, 공격유무에대해 refresh 한다.
	public void whatXY(int xpos, int ypos, int player, boolean life,boolean punch) {
		if (player == 0) {
			aX = xpos;
			aY = ypos;
			aL = life;
			aP=punch;

		} else if (player == 1) {
			bX = xpos;
			bY = ypos;
			bL = life;
			bP=punch;
		} else if (player == 2) {
			cX = xpos;
			cY = ypos;
			cL = life;
			cP=punch;
		} else {
			dX = xpos;
			dY = ypos;
			dL = life;
			dP=punch;
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
		}
	}
}
