
import java.awt.*;
import javax.swing.*;

/**
 * 개발자 및 학교 & 학과 소개
 * @author YoungEun
 */

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
		setResizable(false);									
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
	
	/* 이미지 가져옴 */
	class MyPanel extends JPanel{
		ImageIcon icon = new ImageIcon("image\\Ref.jpg");		
		Image img = icon.getImage();							

		/* 이미지 그리기 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0, getWidth(), getHeight(),this);	
		}
	}
}
