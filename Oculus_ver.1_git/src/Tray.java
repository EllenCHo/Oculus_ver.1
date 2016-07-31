//트레이

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.util.Calendar;


import javax.swing.JOptionPane;

public class Tray implements ActionListener, ItemListener{
	private SystemTray tray;						//트레이 생성
	private PopupMenu popup;						//팝업 메뉴 생성
	private MenuItem Open, Close; 					//열기 닫기 메뉴 생성
	private CheckboxMenuItem Wait;					//자동시작 여부를 위한 체크박스
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
		if(SystemTray.isSupported()){					//현재 os에서 사용가능한지 확인
			popup = new PopupMenu();
			Open = new MenuItem("Open");				//열기 메뉴 추가
			Close = new MenuItem("Exit");				//닫기 메뉴 추가
			Wait = new CheckboxMenuItem("자동실행", true);	//자동실행 메뉴 추가

			//액션 리스너 달기
			Open.addActionListener(this);
			Close.addActionListener(this);
			Wait.addItemListener(this);

			//팝업에 메뉴 달기
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

	//메뉴를 눌렀을 때 발생하는 이벤트 설정
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == Open){
			restart();	
			tray.remove(icon);						//트레이를 열었을때 트레이 아이콘 삭제
		} else if(ae.getSource() == Close){
			showMessage("종료", "종료하시겠습니까?");		//트레이의 exit를 눌렀을 경우 메시지 창 뜨게 하기
		}
	}
	
	//자동시작 아이템이 true, false가 됐을 때 기능 설정
	public void itemStateChanged(ItemEvent e){
		//if(e.getSource() == Wait){
		if(e.getStateChange() == ItemEvent.SELECTED){
			Thread Waiting = new Thread(new Waiting());						//트레이로 돌려보냈을 시에 지정시간이 되면 자동으로 실행하게 함
			Waiting.start();		//쓰레드 실행
		}	else if(e.getStateChange() == ItemEvent.DESELECTED){			//체크박스 해제했을때 Waiting 쓰레드 종료
			Waiting.finish();
		}
	}

	//exit 버튼을 눌렀을 때 나타나는 창 설정
	private void showMessage(String title, String message){
		int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		
		//사용자가 "예"나 "아니오"의 선택 없이 다이얼로그 창을 닫은 경우
		if(result == JOptionPane.CLOSED_OPTION) {				
		}
		
		//사용자가 "예"를 눌렀을 경우
		else if(result == JOptionPane.YES_OPTION) {
			System.out.println("Oculus 종료");
			System.exit(0);
		}
		
		//사용자가 "아니오"를 눌렀을 경우
		else {
		}
	}

	//메인 다시 시작하기
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
