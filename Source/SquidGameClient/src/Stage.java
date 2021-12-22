import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Stage extends JPanel {

	Image buffImg = null;
	Graphics buffer = null;

	Stage() {
		setLayout(null);
	}

	public void paint(Graphics g) {
		buffImg = createImage(400, 900);
		buffer = buffImg.getGraphics();
		update(g);
	}

	public void update(Graphics g) {
		background();
		Green_Red();
		runUsr0();
		runUsr1();
		runUsr2();
		runUsr3();
		g.drawImage(buffImg, 0, 0, this);
	}
	// green or red 따라 이미지 처리
	public void Green_Red() {
		if (Game_View.sign.matches("Game Over")) {
			buffer.setFont(new Font("Ariel", Font.BOLD, 50));
			buffer.setColor(Color.RED);
			buffer.drawString(Game_View.sign, 70, 150);
		} else if (Game_View.sign.matches("GOAL IN!!")) {
			buffer.setFont(new Font("Ariel", Font.BOLD, 50));
			buffer.setColor(Color.BLUE);
			buffer.drawString(Game_View.sign, 70, 150);
		} else if (Game_View.sign.length() < 13) {
			buffer.setFont(new Font("Ariel", Font.BOLD, 20));
			buffer.setColor(Color.black);
			buffer.drawString(Game_View.sign, 70, 150);
		} else if (Game_View.sign.length() >= 13) {
			buffer.setFont(new Font("Ariel", Font.BOLD, 30));
			buffer.setColor(Color.RED);
			buffer.drawString(Game_View.sign, 70, 150);
		}
	}

	public void background() {
		buffer.drawImage(Game_View.playground, 0, 0, 400, 900, this);
		buffer.drawImage(Game_View.punisher, 175, 5, 30, 60, this);
		buffer.drawImage(Game_View.pink, 15, 5, 360, 60, this);
	}

	public void runUsr0() {
		if (!Game_View.aP)
			buffer.drawImage(Game_View.usr0, Game_View.aX, Game_View.aY, 30, 50, this);
		else
			buffer.drawImage(Game_View.attack, Game_View.aX, Game_View.aY-10, 30, 60, this);
	}

	public void runUsr1() {
		if (!Game_View.bP)
			buffer.drawImage(Game_View.usr1, Game_View.bX, Game_View.bY, 30, 50, this);
		else
			buffer.drawImage(Game_View.attack, Game_View.bX, Game_View.bY-10, 30, 60, this);
	}

	public void runUsr2() {
		if (!Game_View.cP)
			buffer.drawImage(Game_View.usr2, Game_View.cX, Game_View.cY, 30, 50, this);
		else
			buffer.drawImage(Game_View.attack, Game_View.cX, Game_View.cY-10, 30, 60, this);

	}

	public void runUsr3() {
		if (!Game_View.dP)
			buffer.drawImage(Game_View.usr3, Game_View.dX, Game_View.dY, 30, 50, this);
		else
			buffer.drawImage(Game_View.attack, Game_View.dX, Game_View.dY-10, 30, 60, this);

	}

}
