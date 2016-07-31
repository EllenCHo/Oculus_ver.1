//���� ���� ����ȭ

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Save {
	public static synchronized void SaveNow(){				
		try{
			DateJLabel.fw = new FileWriter("log\\Today.txt");   // ���� ��� ��Ʈ�� ����
			DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // ���� ���� ��� ��Ʈ�� ����, ��� ȿ�� ���

			//��� ����
			DateJLabel.bw.write(String.format("%d,%d,%d,%d,%d,%d", MainFrame.Month, MainFrame.Day, MainFrame.FM, MainFrame.FMC, MainFrame.FD, MainFrame.FDC));
			DateJLabel.bw.flush();

			DateJLabel.bw.close();   // ���� ����� ��Ʈ���� �ݰ� �ý��� �ڿ� ����
			DateJLabel.fw.close();
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
	}
	
	
	public static void SaveDay(){
		Calendar now = Calendar.getInstance();   // ���� ��¥�� �ð� ������ �����´�.
		
		try{
			if(now.get(Calendar.DAY_OF_MONTH) != MainFrame.Day){
				DateJLabel.fw = new FileWriter("log\\Info.txt", true);   // ���� ��� ��Ʈ�� ����
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   	 // ���� ���� ��� ��Ʈ�� ����, ��� ȿ�� ���

				// Info.txt�� ���� ��� �Է�
				DateJLabel.bw.write(String.format("%d,%d,%.2f\r\n", MainFrame.Month, MainFrame.Day, (double)100 * (MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   
				DateJLabel.bw.flush();									 //���ۿ� ���� �� ���
	
				MainFrame.FMC = 0;
				MainFrame.FDC = 0;

				DateJLabel.fw = new FileWriter("log\\Today.txt");   	 // ���� ��� ��Ʈ�� ����
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   	 // ���� ���� ��� ��Ʈ�� ����, ��� ȿ�� ���

				//Today ����� �ʱ�ȭ�ؼ� ���
				DateJLabel.bw.write(String.format("%d,%d,%d,0,%d,0", now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
				DateJLabel.bw.flush();

				DateJLabel.br.close();   // ���� ����� ��Ʈ���� �ݰ� �ý��� �ڿ� ����
				DateJLabel.fr.close();
				DateJLabel.bw.close();
				DateJLabel.fw.close();
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
	}
	
}
