// 01. ���� ��¥�� �ð� ǥ��

import java.util.*;
import java.awt.*;
import java.io.*;

import javax.swing.*;

public class DateJLabel implements Runnable{
	private JLabel date;							// �ð��� ��Ÿ�� Label
	private Calendar cal;							// �޷� �޾ƿ� ����
	String dayOfWeek;			

	public static FileReader fr;
	public static BufferedReader br;
	public static FileWriter fw;
	public static BufferedWriter bw;
	public static boolean flag = true;

	public static void stop(){						// â�� �ݾ��� �ÿ� ������ ����
		flag = false;
	}

	DateJLabel(Calendar now, JLabel date){					
		this.cal = now;
		this.date = date;
		date.setFont(new Font("Arial", Font.BOLD, 20));			// ��Ʈ ����
		this.dayOfWeek = null;
		flag = true;
	}

	public void run(){
		
		//�ð�, ��, �ʸ� ���� ������ ���� ������ ������ ����
		int h = cal.get(Calendar.HOUR_OF_DAY), h10 = h/10, h1 = h%10;
		int m = cal.get(Calendar.MINUTE), m10 = m/10, m1 = m%10;
		int s = cal.get(Calendar.SECOND), s10 = s/10, s1 = s%10;
		
		//������ ���ڷ� ǥ�õǱ⶧���� ���ϸ����� �����ϱ� ���� ����ġ�� ���
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
						+ h10 + h1 + ":" + m10 + m1 + ":" + s10 + s1);
				s++;							

				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					System.err.println(e);
					return;
				}

				if(s == 60){								// 1�� ���������� �ð� ����
					cal = Calendar.getInstance();
					h = cal.get(Calendar.HOUR_OF_DAY); h10 = h/10; h1 = h%10;
					m = cal.get(Calendar.MINUTE); m10 = m/10; m1 = m%10;
					s = cal.get(Calendar.SECOND); s10 = s/10; s1 = s%10;
				}
				if(m == 60){								// 1�ð��� ������ �ð� �ø���
					h++;
					m = 0;
				}
				
				h10 = h/10; h1 = h%10;
				m10 = m/10; m1 = m%10;
				s10 = s/10; s1 = s%10;
				
				if(h == 24){								//�Ϸ簡 ������ �ٽ� ���ϸ� �����ϰ� ī��Ʈ �ʱ�ȭ
					switch(cal.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SUNDAY : dayOfWeek = "Sun."; break;
					case Calendar.MONDAY : dayOfWeek = "Mon."; break;
					case Calendar.TUESDAY : dayOfWeek = "Tues."; break;
					case Calendar.WEDNESDAY : dayOfWeek = "Wed."; break;
					case Calendar.THURSDAY : dayOfWeek = "Thur."; break;
					case Calendar.FRIDAY: dayOfWeek = "Fri."; break;
					case Calendar.SATURDAY : dayOfWeek = "Sat."; break;
					}

					//ī��Ʈ �ʱ�ȭ
					MainFrame.FMC = 0;	
					MainFrame.FDC = 0;
				}
			}while(flag);	
	}
}
