// 01. 현재 날짜와 시각 표시

import java.util.*;
import java.awt.*;
import java.io.*;

import javax.swing.*;

public class DateJLabel implements Runnable{
	private JLabel date;							// 시간을 나타낼 Label
	private Calendar cal;							// 달력 받아올 변수
	String dayOfWeek;			

	public static FileReader fr;
	public static BufferedReader br;
	public static FileWriter fw;
	public static BufferedWriter bw;
	public static boolean flag = true;

	public static void stop(){						// 창을 닫았을 시에 쓰레드 종료
		flag = false;
	}

	DateJLabel(Calendar now, JLabel date){					
		this.cal = now;
		this.date = date;
		date.setFont(new Font("Arial", Font.BOLD, 20));			// 폰트 설정
		this.dayOfWeek = null;
		flag = true;
	}

	public void run(){
		
		//시간, 분, 초를 십의 단위와 일의 단위로 나눠서 저장
		int h = cal.get(Calendar.HOUR_OF_DAY), h10 = h/10, h1 = h%10;
		int m = cal.get(Calendar.MINUTE), m10 = m/10, m1 = m%10;
		int s = cal.get(Calendar.SECOND), s10 = s/10, s1 = s%10;
		
		//요일은 숫자로 표시되기때문에 요일명으로 설정하기 위해 스위치문 사용
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

				if(s == 60){								// 1분 지날때마다 시간 갱신
					cal = Calendar.getInstance();
					h = cal.get(Calendar.HOUR_OF_DAY); h10 = h/10; h1 = h%10;
					m = cal.get(Calendar.MINUTE); m10 = m/10; m1 = m%10;
					s = cal.get(Calendar.SECOND); s10 = s/10; s1 = s%10;
				}
				if(m == 60){								// 1시간이 지나면 시간 올리기
					h++;
					m = 0;
				}
				
				h10 = h/10; h1 = h%10;
				m10 = m/10; m1 = m%10;
				s10 = s/10; s1 = s%10;
				
				if(h == 24){								//하루가 지나면 다시 요일명 설정하고 카운트 초기화
					switch(cal.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SUNDAY : dayOfWeek = "Sun."; break;
					case Calendar.MONDAY : dayOfWeek = "Mon."; break;
					case Calendar.TUESDAY : dayOfWeek = "Tues."; break;
					case Calendar.WEDNESDAY : dayOfWeek = "Wed."; break;
					case Calendar.THURSDAY : dayOfWeek = "Thur."; break;
					case Calendar.FRIDAY: dayOfWeek = "Fri."; break;
					case Calendar.SATURDAY : dayOfWeek = "Sat."; break;
					}

					//카운트 초기화
					MainFrame.FMC = 0;	
					MainFrame.FDC = 0;
				}
			}while(flag);	
	}
}
