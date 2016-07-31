// 06. ���ü � : 15�� Ʈ���̴�

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dots extends JFrame implements Runnable, WindowListener{
	int row = 0;
	String line;
	String[] str;
	boolean flag;
	
	//��ư�� ��Ÿ���� ����
	int[][] order = {
			{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4},
			{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4},
			{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4},
			{0, 0, 2}, {0, 1, 3}, {0, 2, 4},
			{1, 2, 4}, {1, 1, 3}, {1, 0, 2},
			{2, 0, 2}, {2, 1, 3}, {2, 2, 4},
			{0, 2}, {0, 1, 3}, {0, 0, 4},
			{1, 2}, {1, 1, 3}, {1, 0, 4},
			{2, 2}, {2, 1, 3}, {2, 0, 4},
	};

	public JButton btn[][] = new JButton[3][5];

	ImageIcon icon;					
	Container contentPane;

	Color color = new Color(206,247,110);

	//http://b-jay.tistory.com/123
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); 	//��üȭ�� ������ ��������

	HowToUse htu2;

	public void windowActivated(WindowEvent e) {}			// �����찡 Ȱ��ȭ �� �� ȣ��
	public void windowClosed(WindowEvent e) {}			// �����찡 ������ ������ �� ȣ��
	public void windowClosing(WindowEvent e) {
		finish();						// â ������ ī��Ʈ�����ʰ� ����
	}
	public void windowDeactivated(WindowEvent e) {}			// �����찡 ��Ȱ��ȭ�� �� ȣ��
	public void windowDeiconified(WindowEvent e) {}			// �����찡 �����ܿ��� ���� ũ��� �� �� ȣ��
	public void windowIconified(WindowEvent e) {}			// �����찡 ������ȭ(�ּ�ȭ)�� �� ȣ��
	public void windowOpened(WindowEvent e) {}			// �����찡 ���� �� ȣ��


	public void setOn(int i, int[][] j){
		for(int a =1 ; a <j[row].length; a++)
			btn[j[i][0]][j[i][a]].setVisible(true);
	}

	public void setOff(){
		for(int a = 0; a<btn.length; a++)
			for(int b = 0; b < btn[a].length; b++)
				btn[a][b].setVisible(false);
	}

	Dots() {
		super("Fifteen Dots");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		icon = new ImageIcon("image\\RYAN_resize.png");		// ĳ���� �̹���

		contentPane = getContentPane();				// 
		contentPane.setLayout(new GridLayout(3, 5, 5, 5)); 	// ��ġ������ ����
		contentPane.setBackground(color);			// 
		this.addWindowListener(this);

		flag = false;

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 5; j++){
				btn[i][j] = new JButton(icon);
				btn[i][j].setSize(res.width/15, res.width/15);		// ����� �ػ󵵿� ���� ��ư ������ ����

				// ��ư ����
				btn[i][j].setHorizontalAlignment(SwingConstants.CENTER);	//
				btn[i][j].setVerticalAlignment(SwingConstants.CENTER);		// 

				// ��ư ������ ����
				btn[i][j].setBorderPainted(false); 				// ��ư ��輱 ����
				btn[i][j].setFocusPainted(false);				// ��Ŀ��(�����ߴ� ��ư ǥ��) ����
				btn[i][j].setContentAreaFilled(false);				// ��ư���� ��� ����

				contentPane.add(btn[i][j]);					// ����Ʈ�ҿ� ��ư ����
			}
		}


		setSize(res.width, res.height);							// ũ�� ����
		setVisible(true);								// â�� ���̰� ��

		htu2 = new HowToUse(2);								// ��� ���� ����

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				// �ݱ� ��ư�� ������ �� �ش� â�� ����
	}

	void finish(){
		flag = true;
	}

	public void run(){
		int k = -4;

		while(true){
			try{
				//Thread.sleep(1000);   				// ������
				Thread.sleep(2000);   					// 2�ʾ� �Ѿ

				if(flag == true){
					htu2.finish();					// 
					dispose();					// ������ ����
					return;
				}

			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}

			k++;

			if(k == -1)
				htu2.finish();						// 2�ʰ� ������ ������ ������� �ϱ�

			if(k > 0){
				setOff();						// ��ư ��� ����
				setOn(row, order);

				row++;
			}

			if(row == 33){
				MainFrame.FDC++;
				if(MainFrame.FDC > MainFrame.FD) MainFrame.FDC = MainFrame.FD;
				Save.SaveNow();

				dispose();						// ������ ����
				return;
			}
		}
	}
}
