import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

public class Game_View extends JFrame {

	static Toolkit tk = Toolkit.getDefaultToolkit();
	static Image usr = tk.getImage("leftUser.png");

	static Image usr0 = usr;
	static Image usr1 = usr;
	static Image usr2 = usr;
	static Image usr3 = usr;

	static Image playground = tk.getImage("playground.jpg");
	static Image punisher = tk.getImage("back.png");
	static Image pink = tk.getImage("pink.png");
	static Image other = tk.getImage("other.png");
	static Image attack = tk.getImage("punch.png");
	static Image panelImage = null;

	public static String sign; // 무궁화 꽃이 피었습니다
	public static boolean moveOK; //green light인지 red light 인지 확인해주는 boolean
	private boolean GameStatus = true;

	static int myuser;

	public static QueWindow qww;

	Image buffImg = null;
	Graphics buffer = null;

	public JButton ready;
	public JButton exit;

	static int xpos;
	static int ypos;

	public static Stage stg = new Stage();

	private Graphics gc;

	private boolean up = false, down = false, left = false, right = false, space = false;
	private boolean moveUsr = false;
	private static boolean punch = false;

	public static boolean life = true;

	private static String UserName;

	public static int aX = 10, aY = 820, bX = 110, bY = 820, cX = 210, cY = 820, dX = 310, dY = 820;
	public static boolean aL = true, bL = true, cL = true, dL = true;// 각유저들의 목숨 상태 확인
	public static boolean aP = false, bP = false, cP = false, dP = false;//각 유저들의 공격 유무 확인

	public Game_View(String username, QueWindow view, int xPos, int yPos) {
		// 프레임의 대한 설정.
		xpos = xPos;
		ypos = yPos;
		UserName = username;
		qww = view;

		setTitle(UserName); // 프레임 제목 설정.
		setSize(400, 900); // 프레임의 크기 설정.
		setResizable(false); // 프레임의 크기 변경 못하게 설정.

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임의 x버튼 누르면 종료;
		Container g = getContentPane();
		g.setLayout(null);
		setLocationRelativeTo(null);

		stg.setBounds(0, 0, 400, 900);
		add(stg);
		MyKeyAdapater cma = new MyKeyAdapater();
		addKeyListener(cma);
		gc = stg.getGraphics();
		setFocusable(true);

		setVisible(true); // 프레임 보이기;

		runUser urs = new runUser();
		urs.start();

		younghee yh = new younghee();
		yh.start();
	}
	//방향키 이벤트처리
	private class MyKeyAdapater extends KeyAdapter {
		// TODO Auto-generated method stub
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveUsr = true;
				up = true;
				break;
			case KeyEvent.VK_DOWN:
				moveUsr = true;
				down = true;
				break;
			case KeyEvent.VK_LEFT:
				moveUsr = true;
				left = true;
				break;
			case KeyEvent.VK_RIGHT:
				moveUsr = true;
				right = true;
				break;
			case KeyEvent.VK_SPACE:
				space = true;
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveUsr = false;
				up = false;
				break;
			case KeyEvent.VK_DOWN:
				moveUsr = false;
				down = false;
				break;
			case KeyEvent.VK_LEFT:
				moveUsr = false;
				left = false;
				break;
			case KeyEvent.VK_RIGHT:
				moveUsr = false;
				right = false;
				break;
			case KeyEvent.VK_SPACE: //뒤에서 다른 유저들을 미는 space키 이벤트
				space = false;
				punch = false;
				SendMyPos(xpos, ypos, life, punch);
				setPos();
				break;
			}
		}

	}
//게임진행 쓰레드
	class runUser extends Thread {
		public void run() {
			try {
				while (GameStatus) {
					keyProcess();
					//영희의 앞뒤 모습 처리
					if (moveOK)
						punisher = tk.getImage("back.png");
					else
						punisher = tk.getImage("front.png");
					setPos();// 유저들의 좌표 refresh
					sleep(50);
					//유저들이 역동적으로 움직이는 모습 구현 처리
					if (aY % 4 == 0)
						usr0 = tk.getImage("leftUser.png");
					else
						usr0 = tk.getImage("rightUser.png");
					if (bY % 4 == 0)
						usr1 = tk.getImage("leftUser.png");
					else
						usr1 = tk.getImage("rightUser.png");
					if (cY % 4 == 0)
						usr2 = tk.getImage("leftUser.png");
					else
						usr2 = tk.getImage("rightUser.png");
					if (dY % 4 == 0)
						usr3 = tk.getImage("leftUser.png");
					else
						usr3 = tk.getImage("rightUser.png");
					stg.repaint();
					setPos();
					if (moveOK == false) {
						if (moveUsr == true) {//redlight 일때 유저 움직이면 사망 처리
							life = false;
							setPos();
							SendMyPos(xpos, ypos, life, punch);
							sleep(50);
							Die(UserName);// 사망처리
						}

						sleep(30);
						if (!aL && !bL && !cL && !dL) {// 유저들이 사망 or골인 처리 되었을때 게임 종료 처리
							sleep(100);
							GameStatus = false;
							setPos();
							GameOver();
						}
					}
					if (ypos <= 20) {
						// 여기에 점수랑 유저 name을 board로 보내야함.
						sleep(50);
						ypos = 20;
						life = false;
						setPos();
						SendMyPos(xpos, ypos, life, punch);
						winner(UserName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
//영희의 무궁화꽃이 피었습니다 쓰레드
	class younghee extends Thread {
		int delay1;
		int delay2;
		int count = 8;

		public void run() {
			while (life) {
				try {
					delay1 = 100 + 70 * count;
					delay2 = 300 + 70 * count;
					moveOK = true;
					sign = "무";
					sleep(delay1);
					sign = "무궁";
					sleep(delay1);
					sign = "무궁화";
					sleep(delay1);
					sign = "무궁화 꽃";
					sleep(delay1);
					sign = "무궁화 꽃이";
					sleep(delay1);
					sign = "무궁화 꽃이 피었";
					sleep(delay1);
					sign = "무궁화 꽃이 피었습니";
					sleep(delay1);
					sign = "무궁화 꽃이 피었습니다!";
					moveOK = false;
					sleep(delay2);
					sleep(50);
					count--;
					if (count == 0)
						count = 5;
				} catch (InterruptedException e) {
					return;
				}
				if (ypos <= 20)
					sign = "GOAL IN!!";
				else
					sign = "Game Over";
			}
		}
	}
// 유저들의 좌표 update refresh
	public static void setPos() {
		aX = qww.aX;
		aY = qww.aY;
		aL = qww.aL;
		aP = qww.aP;
		bX = qww.bX;
		bY = qww.bY;
		bL = qww.bL;
		bP = qww.bP;
		cX = qww.cX;
		cY = qww.cY;
		cL = qww.cL;
		cP = qww.cP;
		dX = qww.dX;
		dY = qww.dY;
		dL = qww.dL;
		dP = qww.dP;
		//유저 사망시
		if (!aL && aY > 20)
			usr0 = tk.getImage("die.png");
		else if (!aL && aY <= 20)
			usr0 = tk.getImage("other.png");
		if (!bL && bY > 20)
			usr1 = tk.getImage("die.png");
		else if (!bL && bY <= 20)
			usr1 = tk.getImage("other.png");
		if (!cL && cY > 20)
			usr2 = tk.getImage("die.png");
		else if (!cL && cY <= 20)
			usr2 = tk.getImage("other.png");
		if (!dL && dY > 20)
			usr3 = tk.getImage("die.png");
		else if (!dL && dY <= 20)
			usr3 = tk.getImage("other.png");
	}
// 게임종료
	public void GameOver() {
		Action cm = new Action(UserName, "800", "GameOver");
		qww.SendObject(cm);
	}
//유저 골인
	public void winner(String username) {
		Action cm = new Action(UserName, "700", "user winner!");
		cm.UserName = UserName;
		qww.SendObject(cm);

	}
// 유저 사망
	public void Die(String username) {
		Action cm = new Action(UserName, "600", "user Die");
		cm.UserName = UserName;
		cm.player = qww.myuser;
		qww.SendObject(cm);
	}
// 유저가 다른 유저에 의해 사망
	public static void Murder(String username) {
		life = false;
		SendMyPos(xpos, ypos, life, punch);
		setPos();
		if (!aL)
			usr0 = tk.getImage("die.png");
		if (!bL)
			usr1 = tk.getImage("die.png");
		if (!cL)
			usr2 = tk.getImage("die.png");
		if (!dL)
			usr3 = tk.getImage("die.png");
		sign = "Game Over";
		stg.repaint();
		Action cm = new Action(UserName, "600", "user Die");
		cm.UserName = UserName;
		cm.player = qww.myuser;
		qww.SendObject(cm);

	}
// 자신의 좌표를 서버에 전송
	public static void SendMyPos(int xpos, int ypos, boolean Lif, boolean Punch) {
		Action cm = new Action(UserName, "300", "move");
		cm.xPos = xpos;
		cm.yPos = ypos;
		cm.player = qww.myuser;
		cm.life = Lif;
		cm.punch = Punch;
		qww.SendObject(cm);
	}

	public void keyProcess() {
		if (up && ypos - 10 > 0 && life == true) {
			ypos -= 3;
			SendMyPos(xpos, ypos, true, punch);
		}
		if (down && ypos + 20 < 900 && life == true) {
			ypos += 3;
			SendMyPos(xpos, ypos, true, punch);
		}
		if (left && xpos > 0 && life == true) {
			xpos -= 3;
			SendMyPos(xpos, ypos, true, punch);
		}
		if (right && xpos + 30 < 400 && life == true) {
			xpos += 3;
			SendMyPos(xpos, ypos, true, punch);
		}
		if (space && life) {
			punch = true;
			setPos();
			SendMyPos(xpos, ypos, true, punch);
			if (!moveOK)
				checkAttack(xpos, ypos);
		}
	}
// redlight 일 때 다른 유저를 뒤에서 밀경우 공격범위이 인지 체크 해주는 함수 .
	public void checkAttack(int xpos, int ypos) {
		Action cm = new Action(UserName, "444", "kill");
		setPos();
		boolean xOk = false, yOk = false;
		if (qww.myuser == 0) {

			if (bL) {
				if (xpos - 20 <= bX && xpos + 20 >= bX)
					xOk = true;
				if (ypos - 40 <= bY && ypos + 20 >= bY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 1;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (cL) {
				if (xpos - 20 <= cX && xpos + 20 >= cX)
					xOk = true;
				if (ypos - 40 <= cY && ypos + 20 >= cY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 2;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (dL) {
				if (xpos - 20 <= dX && xpos + 20 >= dX)
					xOk = true;
				if (ypos - 40 <= dY && ypos + 20 >= dY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 3;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
		} else if (qww.myuser == 1) {

			if (aL) {
				if (xpos - 20 <= aX && xpos + 20 >= aX)
					xOk = true;
				if (ypos - 40 <= aY && ypos + 20 >= aY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 0;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (cL) {

				if (xpos - 20 <= cX && xpos + 20 >= cX)
					xOk = true;
				if (ypos - 40 <= cY && ypos + 20 >= cY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 2;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (dL) {
				if (xpos - 20 <= dX && xpos + 20 >= dX)
					xOk = true;
				if (ypos - 40 <= dY && ypos + 20 >= dY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 3;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
		} else if (qww.myuser == 2) {
			if (aL) {
				if (xpos - 20 <= aX && xpos + 20 >= aX)
					xOk = true;
				if (ypos - 40 <= aY && ypos + 20 >= aY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 0;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (bL) {

				if (xpos - 20 <= bX && xpos + 20 >= bX)
					xOk = true;
				if (ypos - 40 <= bY && ypos + 20 >= bY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 1;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (dL) {

				if (xpos - 20 <= dX && xpos + 20 >= dX)
					xOk = true;
				if (ypos - 40 <= dY && ypos + 20 >= dY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 3;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
		} else {
			if (aL) {

				if (xpos - 20 <= aX && xpos + 20 >= aX)
					xOk = true;
				if (ypos - 40 <= aY && ypos + 20 >= aY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 0;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (bL) {
				if (xpos - 20 <= bX && xpos + 20 >= bX)
					xOk = true;
				if (ypos - 40 <= bY && ypos + 20 >= bY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 1;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
			if (cL) {
				if (xpos - 20 <= cX && xpos + 20 >= cX)
					xOk = true;
				if (ypos - 40 <= cY && ypos + 20 >= cY)
					yOk = true;
				if (xOk && yOk) {
					cm.player = 2;
					qww.SendObject(cm);
				}
				xOk = false;
				yOk = false;
			}
		}
	}

}