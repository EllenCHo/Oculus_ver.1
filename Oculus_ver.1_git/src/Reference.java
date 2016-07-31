// 08. ������ �� �б� & �а� �Ұ�

import java.awt.*;
import javax.swing.*;

public class Reference extends JFrame{
	Container c;
	
	public Reference(){
		setTitle("�Ұ�");
		c = getContentPane();
		MyPanel panel = new MyPanel();
		c.add(panel, BorderLayout.CENTER);

		setSize(1084, 1125);
		setLocationRelativeTo(null);
		setVisible(true);   
		setResizable(false);				//â ũ�⸦ �� �ø��� ����
		
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
