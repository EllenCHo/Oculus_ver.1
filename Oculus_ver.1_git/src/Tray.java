//트레이

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.util.Calendar;


import javax.swing.JOptionPane;

public class Tray implements ActionListener, ItemListener{
	private SystemTray tray;
	private PopupMenu popup;
	private MenuItem Open, Close; 
	private CheckboxMenuItem Wait;		//wait은 나중(7.10)에 추가한것
	private TrayIcon icon;

	public Tray(){
		try {
			setup();
		} catch(AWTException awte){
			System.out.println("Error TRAY");
			System.out.println(awte.toString());
		}
	}

	//트레이 설정
	public void setup() throws AWTException{
		if(SystemTray.isSupported()){				//현재 os에서 사용가능한지 확인
			popup = new PopupMenu();
			Open = new MenuItem("Open");
			Close = new MenuItem("Exit");
			Wait = new CheckboxMenuItem("자동실행", true);

			//액션 리스너 달기
			Open.addActionListener(this);
			Close.addActionListener(this);
			Wait.addItemListener(this);


			popup.add(Open);
			popup.add(Wait);
			popup.add(Close);


			//트레이아이콘 설정
			Image image = Toolkit.getDefaultToolkit().getImage("image\\logo.png");
			icon = new TrayIcon(image, "Oculus", popup);
			icon.setImageAutoSize(true);
			//트레이 아이콘 근처로 팝업창 뜨게하기
			icon.displayMessage("Oculus", "트레이로 이동합니다.", MessageType.INFO);

			//더블클릭했을 시에 메인 창이 뜨도록 함.(의문점 : MousePressed를 이용했을 때는 실행이 안됨, 더블클릭시에 실행됨)
			icon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tray.remove(icon);			//트레이를 열었을때 트레이 아이콘 삭제
					restart();
				}
			});
			tray = SystemTray.getSystemTray();
			tray.add(icon);
		}
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == Open){
			restart();	
			tray.remove(icon);			//트레이를 열었을때 트레이 아이콘 삭제
		} else if(ae.getSource() == Close){
			showMessage("종료", "종료하겠습니다.");
			System.out.println("Oclus 종료");
			System.exit(0);
		}
	}

	public void itemStateChanged(ItemEvent e){
		//if(e.getSource() == Wait){
		if(e.getStateChange() == ItemEvent.SELECTED){
			Thread Waiting = new Thread(new Waiting());			//트레이로 돌려보냈을 시에 지정시간이 되면 자동으로 실행하게 함
			Waiting.start();		//쓰레드 실행
		}	else if(e.getStateChange() == ItemEvent.DESELECTED){			//체크박스 해제했을때 Waiting 쓰레드 종료
			Waiting.finish();
		}
	}


	private void showMessage(String title, String message){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	//창 다시 시작하기
	public void restart(){
		MainFrame mFrame = new MainFrame();

		Calendar now = Calendar.getInstance();   // 현재 날짜와 시간 정보를 가져온다.

		Save.SaveDay();

		Thread dateJLabel = new Thread(new DateJLabel(now, mFrame.getDate()));
		dateJLabel.start();

		mFrame.runRecord();
		mFrame.runGraph();
		mFrame.runGoal();
		mFrame.runExercise1();
		mFrame.runExercise2();
		mFrame.runExercise3();
		mFrame.runReference();
	}

}
