//파일 저장 동기화

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.Calendar;

public class Save {
	public static synchronized void SaveNow(){
		try{
			DateJLabel.fw = new FileWriter("log\\Today.txt");   // 파일 출력 스트림 생성
			DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

			DateJLabel.bw.write(String.format("%d,%d,%d,%d,%d,%d", MainFrame.Month, MainFrame.Day, MainFrame.FM, MainFrame.FMC, MainFrame.FD, MainFrame.FDC));
			DateJLabel.bw.flush();

			DateJLabel.bw.close();   // 파일 입출력 스트림을 닫고 시스템 자원 해제
			DateJLabel.fw.close();
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
	}
	
	/*
	public void SaveDay(){
		Calendar now = Calendar.getInstance();   // 현재 날짜와 시간 정보를 가져온다.
		
		try{
			if(now.get(Calendar.DAY_OF_MONTH) != mFrame.Day){
				DateJLabel.fw = new FileWriter("log\\Info.txt", true);   // 파일 출력 스트림 생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				// if((cal.get(Calendar.DAY_OF_MONTH)) == 1)   // 새로운 달로 넘어갔을 때
				DateJLabel.bw.write(String.format("%d,%d,%.2f\r\n", MainFrame.Month, MainFrame.Day, (double)100 * (MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt에 오늘 기록 입력
				DateJLabel.bw.flush();
				// else
				// bw.write(String.format("%d,%d,%f\r\n", cal.get(Calendar.MONTH + 1), (cal.get(Calendar.DAY_OF_MONTH) - 1), (double)(MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt에 오늘 기록 입력

				// mFrame.FM = 9;
				mFrame.FMC = 0;
				// mFrame.FD = 9;
				mFrame.FDC = 0;

				DateJLabel.fw = new FileWriter("log\\Today.txt");   // 파일 출력 스트림 생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				DateJLabel.bw.write(String.format("%d,%d,%d,0,%d,0", now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
				DateJLabel.bw.flush();

				DateJLabel.br.close();   // 파일 입출력 스트림을 닫고 시스템 자원 해제
				DateJLabel.fr.close();
				DateJLabel.bw.close();
				DateJLabel.fw.close();
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
	}
	*/
}
