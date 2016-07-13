//Ʈ����

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.util.Calendar;


import javax.swing.JOptionPane;

public class Tray implements ActionListener, ItemListener{
	private SystemTray tray;
	private PopupMenu popup;
	private MenuItem Open, Close; 
	private CheckboxMenuItem Wait;		//wait�� ����(7.10)�� �߰��Ѱ�
	private TrayIcon icon;

	public Tray(){
		try {
			setup();
		} catch(AWTException awte){
			System.out.println("Error TRAY");
			System.out.println(awte.toString());
		}
	}

	//Ʈ���� ����
	public void setup() throws AWTException{
		if(SystemTray.isSupported()){				//���� os���� ��밡������ Ȯ��
			popup = new PopupMenu();
			Open = new MenuItem("Open");
			Close = new MenuItem("Exit");
			Wait = new CheckboxMenuItem("�ڵ�����", true);

			//�׼� ������ �ޱ�
			Open.addActionListener(this);
			Close.addActionListener(this);
			Wait.addItemListener(this);


			popup.add(Open);
			popup.add(Wait);
			popup.add(Close);


			//Ʈ���̾����� ����
			Image image = Toolkit.getDefaultToolkit().getImage("image\\logo.png");
			icon = new TrayIcon(image, "Oculus", popup);
			icon.setImageAutoSize(true);
			//Ʈ���� ������ ��ó�� �˾�â �߰��ϱ�
			icon.displayMessage("Oculus", "Ʈ���̷� �̵��մϴ�.", MessageType.INFO);

			//����Ŭ������ �ÿ� ���� â�� �ߵ��� ��.(�ǹ��� : MousePressed�� �̿����� ���� ������ �ȵ�, ����Ŭ���ÿ� �����)
			icon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tray.remove(icon);			//Ʈ���̸� �������� Ʈ���� ������ ����
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
			tray.remove(icon);			//Ʈ���̸� �������� Ʈ���� ������ ����
		} else if(ae.getSource() == Close){
			showMessage("����", "�����ϰڽ��ϴ�.");
			System.out.println("Oclus ����");
			System.exit(0);
		}
	}

	public void itemStateChanged(ItemEvent e){
		//if(e.getSource() == Wait){
		if(e.getStateChange() == ItemEvent.SELECTED){
			Thread Waiting = new Thread(new Waiting());			//Ʈ���̷� ���������� �ÿ� �����ð��� �Ǹ� �ڵ����� �����ϰ� ��
			Waiting.start();		//������ ����
		}	else if(e.getStateChange() == ItemEvent.DESELECTED){			//üũ�ڽ� ���������� Waiting ������ ����
			Waiting.finish();
		}
	}


	private void showMessage(String title, String message){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	//â �ٽ� �����ϱ�
	public void restart(){
		MainFrame mFrame = new MainFrame();

		Calendar now = Calendar.getInstance();   // ���� ��¥�� �ð� ������ �����´�.

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
