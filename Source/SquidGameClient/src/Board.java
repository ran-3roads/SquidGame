import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.JButton;

public class Board extends JFrame{
	public String UserName;
	public int Rank;
	private static Board boardview;
	public QueWindow qww;

	
	public static String comment;
	public static StringBuilder sb = new StringBuilder();
	
	static Image buffImg1 = null;
	static Graphics buffer1 = null;
	
	public Board(String username,int rank,QueWindow view){
		UserName=username;
		Rank=rank;
		boardview=this;
		qww=view;
		
		sb.append(UserName+"님 "+Rank+"등");
		comment = sb.toString();

		
		setTitle("Ranking Board"); // 프레임 제목 설정.
	    setSize(360, 190); // 프레임의 크기 설정.
	    setResizable(false);
	    
		Container gc = getContentPane();
		gc.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Ranking");
		lblNewLabel.setBounds(157, 0, 100, 50);
		getContentPane().add(lblNewLabel);
		
		
		JButton exit = new JButton("exit");
		exit.setBounds(124, 115, 117, 29);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		

		getContentPane().add(exit);
		
		setVisible(true); // 프레임 보이기;
			

	}
	public void paint(Graphics g) {		
		g.setFont(new Font("Ariel", Font.BOLD,30));
		g.setColor(Color.black);
		g.drawString(comment, 120, 100);
	}	
    
}
