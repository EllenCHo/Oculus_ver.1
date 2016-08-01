
//파일 저장 동기화

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Save {
	public static synchronized void SaveNow() {
		try {
			DateJLabel.fw = new FileWriter("log\\Today.txt"); // 파일 출력 스트림 생성
			DateJLabel.bw = new BufferedWriter(DateJLabel.fw); // 버퍼 파일 출력 스트림
																// 생성, 출력 효율 향상

			// 기록 저장
			DateJLabel.bw.write(String.format("%d,%d,%d,%d,%d,%d,%d", MainFrame.Year, MainFrame.Month, MainFrame.Day,
					MainFrame.FM, MainFrame.FMC, MainFrame.FD, MainFrame.FDC));
			DateJLabel.bw.flush();

			DateJLabel.bw.close(); // 파일 입출력 스트림을 닫고 시스템 자원 해제
			DateJLabel.fw.close();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}

	public static void SaveDay() {
		Calendar now = Calendar.getInstance(); // 현재 날짜와 시간 정보를 가져온다.

		try {
			if (now.get(Calendar.DAY_OF_MONTH) != MainFrame.Day) {
				DateJLabel.fw = new FileWriter("log\\Info.txt", true); // 파일 출력 스트림  생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw); // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				// Info.txt에 오늘 기록 입력
				DateJLabel.bw.write(String.format("%d,%d,%d,%.2f\r\n", MainFrame.Year, MainFrame.Month, MainFrame.Day,
						(double) 100 * (MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));
				DateJLabel.bw.flush(); // 버퍼에 남은 것 출력

				MainFrame.FMC = 0;
				MainFrame.FDC = 0;

				DateJLabel.fw = new FileWriter("log\\Today.txt"); // 파일 출력 스트림 생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw); // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				// Today 기록을 초기화해서 기록
				DateJLabel.bw.write(String.format("%d,%d,%d,%d,0,%d,0", now.get(Calendar.YEAR),
						now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
				DateJLabel.bw.flush();

				DateJLabel.br.close(); // 파일 입출력 스트림을 닫고 시스템 자원 해제
				DateJLabel.fr.close();
				DateJLabel.bw.close();
				DateJLabel.fw.close();
			}
		} catch (java.io.FileNotFoundException e) { //Info.txt가 없을 경우
			try {
				FileWriter fw = null;
				BufferedWriter bw = null;
				File dsFile = new File("log\\Info.txt");		//Info 파일 생성
				if(!dsFile.exists()){							//log폴더가 없을 경우
					File dsDir = new File("log"); 				//로그 폴더 생성
					dsDir.mkdirs();
					//dsDir.delete();
					dsDir = null;
				}
				//dsFile.delete();
				dsFile = null;				
				fw = new FileWriter("log\\Info.txt"); // 파일 출력 스트림
				// 생성
				bw = new BufferedWriter(fw); // 버퍼 파일 출력
				// 스트림 생성,
				// 출력 효율 향상

				//Info 기록을 초기화해서 기록
				bw.write(String.format("%d,%d,%d,0.00", now.get(Calendar.YEAR),
						now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH)));
				bw.flush();
				if(bw != null)
					bw.close();
				if(fw != null)
					fw.close();
			}
			catch (IOException te) {				
				te.printStackTrace();
				System.exit(1);
			}
			return;
		}		
		catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}

}
