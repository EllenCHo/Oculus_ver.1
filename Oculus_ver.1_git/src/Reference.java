// 08. 개발자 및 학교 & 학과 소개

import java.awt.*;
import javax.swing.*;

public class Reference extends JFrame{
	Container c;
	
	public Reference(){
		setTitle("소개");
		c = getContentPane();
		MyPanel panel = new MyPanel();
		c.add(panel, BorderLayout.CENTER);

		setSize(1084, 1125);
		setLocationRelativeTo(null);
		setVisible(true);   
		setResizable(false);				//창 크기를 못 늘리게 고정
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	class MyPanel extends JPanel{
		ImageIcon icon = new ImageIcon("image\\Ref.jpg");
		Image img = icon.getImage();

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0, getWidth(), getHeight(),this);
		}
	}
}
