// 04. 목표 설정

import java.awt.*;
import java.awt.event.*;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;

import javax.swing.*;

public class Goal extends JFrame implements Runnable{
	Container contentPane;

	JRadioButton[] FDSet= new JRadioButton[2];
	JRadioButton[] FMSet = new JRadioButton[2];

	int yet [] = new int[2];

	Goal(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void run(){
		setTitle("Setting");

		contentPane = getContentPane();
		contentPane.setLayout(null); //배치관리자 삭제

		ButtonGroup GFD = new ButtonGroup();
		ButtonGroup GFM = new ButtonGroup();

		FDSet[0]=new JRadioButton("9회");
		FDSet[1]=new JRadioButton("15회");
		FDSet[0].addItemListener(new SetFDListener());
		FDSet[1].addItemListener(new SetFDListener());

		GFD.add(FDSet[0]);
		GFD.add(FDSet[1]);

		if(MainFrame.FD == 9)
			FDSet[0].setSelected(true); //초기조건 : 3회
		else
			FDSet[1].setSelected(true);


		FMSet[0]=new JRadioButton("9회");
		FMSet[1]=new JRadioButton("15회");
		FMSet[0].addItemListener(new SetFMListener());
		FMSet[1].addItemListener(new SetFMListener());
		GFM.add(FMSet[0]);
		GFM.add(FMSet[1]);

		if(MainFrame.FM ==9)
			FMSet[0].setSelected(true); //초기조건 : 3회
		else
			FMSet[1].setSelected(true);

		//확인 버튼을 눌렀을 때
		JButton ok = new JButton("확인");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.FM = yet[0];
				MainFrame.FD = yet[1];

				Save.SaveNow();
				
				dispose();				
			}
		});

		//취소 버튼을 눌렀을 때
		JButton cancel = new JButton("취소");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});

		//항목 설정
		JLabel Eye1 = new JLabel("Follow Me");
		JLabel Eye2 = new JLabel("Fifteen Dots");

		//사이즈 세팅
		Eye1.setSize(70,20);
		Eye2.setSize(70,20);
		ok.setSize(75,30);
		cancel.setSize(75,30);

		FDSet[0].setSize(60,20);
		FMSet[0].setSize(70,20);
		FDSet[1].setSize(60,20);
		FMSet[1].setSize(70,20);

		Eye1.setLocation(120,80);
		Eye2.setLocation(310,80);
		ok.setLocation(140,200);
		cancel.setLocation(250,200);

		FMSet[0].setLocation(85,110);
		FMSet[1].setLocation(155,110);

		FDSet[0].setLocation(285,110);
		FDSet[1].setLocation(345,110);

		contentPane.add(Eye1);
		contentPane.add(Eye2);
		contentPane.add(ok);
		contentPane.add(cancel);

		contentPane.add(FMSet[0]);
		contentPane.add(FDSet[0]);
		contentPane.add(FMSet[1]);
		contentPane.add(FDSet[1]);

		setSize(500, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);				//창 크기를 못 늘리게 고정
	}

	//횟수 버튼 리스너
	class SetFMListener implements ItemListener {
		public void itemStateChanged(ItemEvent e){	
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //선택 해제된 경우 그냥 리턴

			if(FMSet[0].isSelected())
				yet[0] = 9;
			else 
				yet[0] = 15;
		}
	}

	class SetFDListener implements ItemListener {
		public void itemStateChanged(ItemEvent e){	
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //선택 해제된 경우 그냥 리턴

			if(FDSet[0].isSelected())
				yet[1] = 9;
			else 
				yet[1] = 15;
		}
	}
}
