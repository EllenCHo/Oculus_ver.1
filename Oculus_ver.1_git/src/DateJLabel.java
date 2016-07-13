// 01. ���� ��¥�� �ð� ǥ��

import java.util.*;
import java.awt.*;
import java.io.*;

import javax.swing.*;

public class DateJLabel implements Runnable{
	// javax.swing.Timer Time;
	// public static boolean dayCount;   // ��¥ �������� ���� : true�� �������Ƿ� ���� �ʱ�ȭ, false�� �� ����
	private JLabel date;
	private Calendar cal;
	String dayOfWeek;
	String line;
	String[] str;

	public static FileReader fr;
	public static BufferedReader br;
	public static FileWriter fw;
	public static BufferedWriter bw;
	public static boolean flag = true;
	// PrintWriter pw = new PrintWriter(fw);
	// String ampm;
	// SyncroObject syncro1;
	// SyncroObject syncro2;

	public static void stop(){				//â�� �ݾ��� �ÿ� ������ ����
		flag = false;
	}

	DateJLabel(Calendar now, JLabel date){
		// this.dayCount = false;
		this.cal = now;
		this.date = date;
		date.setFont(new Font("Arial", Font.BOLD, 20));
		this.dayOfWeek = null;
		flag = true;
		// this.ampm = null;
		// this.syncro1 = syncro1;
		// this.syncro2 = syncro2;
	}

	public void run(){
		// Time = new javax.swing.Timer(1000, date);

		int h = cal.get(Calendar.HOUR_OF_DAY), h10 = h/10, h1 = h%10;
		int m = cal.get(Calendar.MINUTE), m10 = m/10, m1 = m%10;
		int s = cal.get(Calendar.SECOND), s10 = s/10, s1 = s%10;
		/*
      	time = ""; 
		time += (h < 10) ? "0" + h : h;
		time += ":";
		time += (m < 10) ? "0" + m : m;
		time += ":"; 
		time += (s < 10) ? "0" + s : s; 
		 */
		/*
	    if(cal.get(Calendar.AM_PM) == Calendar.AM){ampm = "AM";}
	    else{ampm = "PM";}
		 */
		switch(cal.get(Calendar.DAY_OF_WEEK)){
		case Calendar.SUNDAY : dayOfWeek = "Sun."; break;
		case Calendar.MONDAY : dayOfWeek = "Mon."; break;
		case Calendar.TUESDAY : dayOfWeek = "Tues."; break;
		case Calendar.WEDNESDAY : dayOfWeek = "Wed."; break;
		case Calendar.THURSDAY : dayOfWeek = "Thur."; break;
		case Calendar.FRIDAY: dayOfWeek = "Fri."; break;
		case Calendar.SATURDAY : dayOfWeek = "Sat."; break;
		}
	
			do{
				date.setText("" + cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH) + " (" + dayOfWeek + ") "
						+ /*ampm + " "*/ + h10 + h1 + ":" + m10 + m1 + ":" + s10 + s1);
				// cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
				s++;

				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					System.err.println(e);
					return;
				}

				if(s == 60){
					// m++;
					// s = 0;
					cal = Calendar.getInstance();
					h = cal.get(Calendar.HOUR_OF_DAY); h10 = h/10; h1 = h%10;
					m = cal.get(Calendar.MINUTE); m10 = m/10; m1 = m%10;
					s = cal.get(Calendar.SECOND); s10 = s/10; s1 = s%10;
				}
				if(m == 60){
					h++;
					m = 0;
					/*
			    if(cal.get(Calendar.AM_PM) == Calendar.AM){ampm = "AM";}
			    else{ampm = "PM";}
					 */
				}
				h10 = h/10; h1 = h%10;
				m10 = m/10; m1 = m%10;
				s10 = s/10; s1 = s%10;
				/*
			// if(h == 23 && m == 59 && s == 59){
			if(cal.get(Calendar.DAY_OF_MONTH)!= MainFrame.Day){
				try{
					fw = new FileWriter("log\\Info.txt", true);   // ���� ��� ��Ʈ�� ����
					bw = new BufferedWriter(fw);   // ���� ���� ��� ��Ʈ�� ����, ��� ȿ�� ���

					// if((cal.get(Calendar.DAY_OF_MONTH)) == 1)   // ���ο� �޷� �Ѿ�� ��
					bw.write(String.format("%d,%d,%.2f\r\n", MainFrame.Month, MainFrame.Day, (double)100*(MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt�� ���� ��� �Է�
					bw.flush();
					// else
					// bw.write(String.format("%d,%d,%f\r\n", cal.get(Calendar.MONTH + 1), (cal.get(Calendar.DAY_OF_MONTH) - 1), (double)(MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt�� ���� ��� �Է�

					bw.close();   // ���� ����� ��Ʈ���� �ݰ� �ý��� �ڿ� ����
					fw.close();		

					fw = new FileWriter("log\\Today.txt");   // ���� ��� ��Ʈ�� ����
					bw = new BufferedWriter(fw);   // ���� ���� ��� ��Ʈ�� ����, ��� ȿ�� ���

					bw.write(String.format("%d,%d,9,0,9,0", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));   // Today.txt �ʱ�ȭ
					bw.flush();


					//line = DateJLabel.br.readLine();
					//for(int i = 0; i < 5; i++){
					//	str = line.split(",");
					//}

					//MainFrame.Day = Integer.parseInt(str[0]);
					//MainFrame.FM = Integer.parseInt(str[1]);
					//MainFrame.FMC = Integer.parseInt(str[2]);
					//MainFrame.FD = Integer.parseInt(str[3]);
					//MainFrame.FDC = Integer.parseInt(str[4]);

					br.close();   // ���� ����� ��Ʈ���� �ݰ� �ý��� �ڿ� ����
					fr.close();
					bw.close();
					fw.close();					
				}catch(IOException e){
					System.err.println(e);
					System.exit(1);
				}	
			}
				 */
				if(h == 24){
					switch(cal.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SUNDAY : dayOfWeek = "Sun."; break;
					case Calendar.MONDAY : dayOfWeek = "Mon."; break;
					case Calendar.TUESDAY : dayOfWeek = "Tues."; break;
					case Calendar.WEDNESDAY : dayOfWeek = "Wed."; break;
					case Calendar.THURSDAY : dayOfWeek = "Thur."; break;
					case Calendar.FRIDAY: dayOfWeek = "Fri."; break;
					case Calendar.SATURDAY : dayOfWeek = "Sat."; break;
					}
					// dayCount = true;   // ��¥ �ٲ� ǥ��

					MainFrame.FM = 9;
					MainFrame.FMC = 0;
					MainFrame.FD = 9;
					MainFrame.FDC = 0;
				}
			}while(flag);
		
	}
}