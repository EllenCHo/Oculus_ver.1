import javax.swing.*;
import java.awt.*;

/**
 * 선택된 운동 종류에 해당하는 설명 이미지를 가져옴
 * @author YoungEun
 */

public class HowToUse extends JFrame{
	Container c;											
	ImageIcon icon;										
	
	/**
	 * @param ex - 운동 종류 (1: FollowME, 2: FifteenDots, 3: Brightness)
	 */
	public HowToUse(int ex){								
			
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
		
		setTitle("How To Use");								
		c = getContentPane();								
		MyPanel panel = new MyPanel();						
		c.add(panel, BorderLayout.CENTER);					
		
		setSize(1043, 471);									
		setLocationRelativeTo(null);						
		setVisible(true);									
		setResizable(false);			 					
		
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
