// 08. ������ �� �б� & �а� �Ұ�

import java.awt.*;
import javax.swing.*;

public class Reference extends JFrame{
	Container c;						// �����̳� ���� ����
	
	public Reference(){
		setTitle("�Ұ�");							// â �̸��� "�Ұ�"
		c = getContentPane();					// �����ӿ� ����� ����Ʈ���� �˾Ƴ�
		MyPanel panel = new MyPanel();			// MyPanel ��ü ����
		c.add(panel, BorderLayout.CENTER);		// panel�� BorderLayout�� �߾� ���� ��ġ

		setSize(1084, 1125);					// â ũ�� ����
		setLocationRelativeTo(null);			// ȭ���� ����� â�� ���
		setVisible(true);   					// â�� ���̰� ��
		setResizable(false);					// â ũ�⸦ �� �ø��� ����
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// �ݱ� ��ư�� ������ �� �ش� â�� ����
	}
	
	class MyPanel extends JPanel{
		ImageIcon icon = new ImageIcon("image\\Ref.jpg");		// ImageIcon ��ü ����
		Image img = icon.getImage();

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0, getWidth(), getHeight(),this);	// �̹��� �׸���, �̹����� (0,0) ��ǥ���� ������ �ش� �̹����� �ʺ�� ���̸�ŭ ���
		}
	}
}

