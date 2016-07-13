import javax.swing.*;
import java.awt.*;

public class HowToUse extends JFrame{
	Container c;
	ImageIcon icon;
	
	public HowToUse(int ex){			//ex는 운동 종류 (1: FollowME, 2: 15점, 3: Brightness)
		icon = new ImageIcon("image\\use1.jpg");
		/*
		switch(ex){
		case 1:
			icon = new ImageIcon("image\\use1.jpg");
			break;
		case 2:
			icon = new ImageIcon("image\\use2.jpg");
			break;
		case 3:
			icon = new ImageIcon("image\\use3.jpg");
			break;
		default :
				break;
		}
		*/

		setTitle("How To Use");
		c = getContentPane();
		MyPanel panel = new MyPanel();
		c.add(panel, BorderLayout.CENTER);
		
		setSize(1043, 471);
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public HowToUse(int ex, int ex2){			//ex는 운동 종류 (1: FollowME, 2: 15점, 3: Brightness)
		icon = new ImageIcon("image\\use2.jpg");
		/*
		switch(ex){
		case 1:
			icon = new ImageIcon("image\\use1.jpg");
			break;
		case 2:
			icon = new ImageIcon("image\\use2.jpg");
			break;
		case 3:
			icon = new ImageIcon("image\\use3.jpg");
			break;
		default :
				break;
		}
		*/

		setTitle("How To Use");
		c = getContentPane();
		MyPanel panel = new MyPanel();
		c.add(panel, BorderLayout.CENTER);
		
		setSize(1090, 593);
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public HowToUse(int ex, int ex2, int ex3){			//ex는 운동 종류 (1: FollowME, 2: 15점, 3: Brightness)
		icon = new ImageIcon("image\\use3.jpg");
		/*
		switch(ex){
		case 1:
			icon = new ImageIcon("image\\use1.jpg");
			break;
		case 2:
			icon = new ImageIcon("image\\use2.jpg");
			break;
		case 3:
			icon = new ImageIcon("image\\use3.jpg");
			break;
		default :
				break;
		}
		*/

		setTitle("How To Use");
		c = getContentPane();
		MyPanel panel = new MyPanel();
		c.add(panel, BorderLayout.CENTER);
		
		setSize(1135, 534);
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public boolean finish(){
		dispose();
		return true;
	}
	
	class MyPanel extends JPanel{
		Image img = icon.getImage();

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0, getWidth(), getHeight(),this);
		}
	}
}