
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 모양체 운동 : 15점 트레이닝
 * @author YoungEun
 */

public class Dots extends JFrame implements Runnable, WindowListener{
	int row = 0;
	String line;
	String[] str;
	boolean flag;
	
	/* 버튼이 나타나는 순서 */
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
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); 		

	HowToUse htu2;													

	public void windowActivated(WindowEvent e) {}					
	public void windowClosed(WindowEvent e) {}
	/* 창을 닫았을 때 실행 */
	public void windowClosing(WindowEvent e) {
		finish();														
	}
	public void windowDeactivated(WindowEvent e) {}						
	public void windowDeiconified(WindowEvent e) {}						
	public void windowIconified(WindowEvent e) {}						
	public void windowOpened(WindowEvent e) {}						


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

		/* 캐릭터 이미지 */
		icon = new ImageIcon("image\\heart.png");				

		contentPane = getContentPane();									
		contentPane.setLayout(new GridLayout(3, 5, 5, 5)); 				
		contentPane.setBackground(color);							
		this.addWindowListener(this);

		flag = false;

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 5; j++){
				btn[i][j] = new JButton(icon);
				btn[i][j].setSize(res.width/15, res.width/15);		

				// 버튼 정렬
				btn[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				btn[i][j].setVerticalAlignment(SwingConstants.CENTER);

				// 버튼 디자인 정리
				btn[i][j].setBorderPainted(false); 						
				btn[i][j].setFocusPainted(false);					
				btn[i][j].setContentAreaFilled(false);						

				contentPane.add(btn[i][j]);								
			}
		}


		setSize(res.width, res.height);			// 모니터 크기만큼 창 크기 설정						
		setVisible(true);												

		htu2 = new HowToUse(2);											

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
	}

	void finish(){
		flag = true;
	}

	public void run(){
		int k = -4;

		while(true){
			try{
				//Thread.sleep(1000);   							
				Thread.sleep(2000);   				// 2초씩 넘어감							

				if(flag == true){
					htu2.finish();										
					dispose();											
					return;
				}

			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}

			k++;

			/* 2초가 지난후 설명이 사라지게 하기 */
			if(k == -1)
				htu2.finish();										

			if(k > 0){
				setOff();												
				setOn(row, order);

				row++;
			}

			if(row == 33){
				MainFrame.FDC++;
				if(MainFrame.FDC > MainFrame.FD) MainFrame.FDC = MainFrame.FD;
				Save.SaveNow();						// 기록 저장									

				dispose();											
				return;
			}
		}
	}
}
