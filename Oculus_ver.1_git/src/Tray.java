//Ʈ����

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.util.Calendar;


import javax.swing.JOptionPane;

public class Tray implements ActionListener, ItemListener{
	private SystemTray tray;						//Ʈ���� ����
	private PopupMenu popup;						//�˾� �޴� ����
	private MenuItem Open, Close; 					//���� �ݱ� �޴� ����
	private CheckboxMenuItem Wait;					//�ڵ����� ���θ� ���� üũ�ڽ�
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
		if(SystemTray.isSupported()){					//���� os���� ��밡������ Ȯ��
			popup = new PopupMenu();
			Open = new MenuItem("Open");				//���� �޴� �߰�
			Close = new MenuItem("Exit");				//�ݱ� �޴� �߰�
			Wait = new CheckboxMenuItem("�ڵ�����", true);	//�ڵ����� �޴� �߰�

			//�׼� ������ �ޱ�
			Open.addActionListener(this);
			Close.addActionListener(this);
			Wait.addItemListener(this);

			//�˾��� �޴� �ޱ�
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

	//�޴��� ������ �� �߻��ϴ� �̺�Ʈ ����
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == Open){
			restart();	
			tray.remove(icon);						//Ʈ���̸� �������� Ʈ���� ������ ����
		} else if(ae.getSource() == Close){
			showMessage("����", "�����Ͻðڽ��ϱ�?");		//Ʈ������ exit�� ������ ��� �޽��� â �߰� �ϱ�
		}
	}
	
	//�ڵ����� �������� true, false�� ���� �� ��� ����
	public void itemStateChanged(ItemEvent e){
		//if(e.getSource() == Wait){
		if(e.getStateChange() == ItemEvent.SELECTED){
			Thread Waiting = new Thread(new Waiting());						//Ʈ���̷� ���������� �ÿ� �����ð��� �Ǹ� �ڵ����� �����ϰ� ��
			Waiting.start();		//������ ����
		}	else if(e.getStateChange() == ItemEvent.DESELECTED){			//üũ�ڽ� ���������� Waiting ������ ����
			Waiting.finish();
		}
	}

	//exit ��ư�� ������ �� ��Ÿ���� â ����
	private void showMessage(String title, String message){
		int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		
		//����ڰ� "��"�� "�ƴϿ�"�� ���� ���� ���̾�α� â�� ���� ���
		if(result == JOptionPane.CLOSED_OPTION) {				
		}
		
		//����ڰ� "��"�� ������ ���
		else if(result == JOptionPane.YES_OPTION) {
			System.out.println("Oculus ����");
			System.exit(0);
		}
		
		//����ڰ� "�ƴϿ�"�� ������ ���
		else {
		}
	}

	//���� �ٽ� �����ϱ�
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
