import javax.swing.*;
import java.awt.*;

public class HowToUse extends JFrame{
	Container c;							// �����̳� ���� ����
	ImageIcon icon;							// �̹��� ������ ���� ����
	
	public HowToUse(int ex){					// �������� 'ex'�� � ���� (1: FollowME, 2: 15��, 3: Brightness)
		
		//������� ���� ���� �ٲٱ�	
		switch(ex){
		case 1:							// FollowMe ���� ��
			icon = new ImageIcon("image\\use1.jpg");		// �̹����� 'use1'
			break;
		case 2:							// 15 ���� ��
			icon = new ImageIcon("image\\use2.jpg");		// �̹����� 'use2'
			break;
		case 3:							// Brightness ���� ��
			icon = new ImageIcon("image\\use3.jpg");		// �̹����� 'use3'
			break;
		default :
			break;
		}
		
		setTitle("How To Use");					// â�̸��� "How To Use"
		c = getContentPane();					// �����ӿ� ����� ����Ʈ���� �˾Ƴ�
		MyPanel panel = new MyPanel();				// MyPaenl ��ü ����
		c.add(panel, BorderLayout.CENTER);			// panel�� BorderLayout�� �߾� ���� ��ġ
		
		setSize(1043, 471);					// ���� ������ ����
		setLocationRelativeTo(null);				// ȭ���� ����� â�� ���
		setVisible(true);					// â�� ���̰� ��
		setResizable(false);			 		// â ũ�⸦ �� �ø��� ����
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// �ݱ� ��ư�� ������ �� �ش� â�� ����
	}

	public boolean finish(){				
		dispose();
		return true;
	}
	
	class MyPanel extends JPanel{
		Image img = icon.getImage();				

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0, getWidth(), getHeight(),this);	// �̹��� �׸���, �̹����� (0,0) ��ǥ���� ������ �ش� �̹����� �ʺ�� ���̸�ŭ ��� 
		}
	}
}
