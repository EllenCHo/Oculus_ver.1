import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * 동기화를 이용해서 Log, Today 저장
 * @author Sol
 * @see DateJLabel
 *
 */
public class Save {

	
	public static void Load(){
		Calendar cal = Calendar.getInstance(); // 현재 날짜와 시간 정보를 가져온다.;
		String line; // 달력을 가져올 변수
		String str[] = {}; // 기록을 가져올 변수
		// 기록에 저장된 내용으로 월, 일, 목표횟수, 카운트 세팅
		
		try {
			DateJLabel.fr = new FileReader("log\\Today.txt"); // 파일 입력 스트림 생성
			DateJLabel.br = new BufferedReader(DateJLabel.fr); // 버퍼 파일 입력 스트림
																// 생성, 입력 효율 향상

			line = DateJLabel.br.readLine();
			for (int i = 0; i < 7; i++) {
				str = line.split(",");
			}

			MainFrame.Year = Integer.parseInt(str[0]);
			MainFrame.Month = Integer.parseInt(str[1]);
			MainFrame.Day = Integer.parseInt(str[2]);
			MainFrame.FM = Integer.parseInt(str[3]); //목표치
			MainFrame.FMC = Integer.parseInt(str[4]); //실제한 것
			MainFrame.FD = Integer.parseInt(str[5]); //목표치
			MainFrame.FDC = Integer.parseInt(str[6]); //실제한 것

			DateJLabel.br.close(); // 파일 입출력 스트림을 닫고 시스템 자원 해제
			DateJLabel.fr.close();

		}
		catch (java.io.FileNotFoundException e) { //Today.txt가 없을 경우에 대한 처리가 없어서 추가한 부분
			//TODO 2016.08.01 dsaint31 Today.txt 초기 파일 생성이 필요. 오늘 날짜를 읽어들여서 처리하게 추가할 것.
			MainFrame.FM = 9; //TODO dsaint31 2016.08.01 목표치 초기화 필요.
			MainFrame.FD = 9; //TODO dsaint31 2016.08.01 목표치 초기화 필요.
			MainFrame.Year = cal.get(Calendar.YEAR);			
			MainFrame.Month = cal.get(Calendar.MONTH) + 1;			
			MainFrame.Day = cal.get(Calendar.DAY_OF_MONTH);			
			MainFrame.FMC = 0;			
			MainFrame.FDC = 0;
						
			try {
				FileWriter fw = null;
				BufferedWriter bw = null;
				File dsFile = new File("log\\Today.txt");
				if(!dsFile.exists()){
					File dsDir = new File("log"); 
					dsDir.mkdirs();
					//dsDir.delete();
					dsDir = null;
				}
				//dsFile.delete();
				dsFile = null;				
				fw = new FileWriter("log\\Today.txt"); // 파일 출력 스트림
				// 생성
				bw = new BufferedWriter(fw); // 버퍼 파일 출력
				// 스트림 생성,
				// 출력 효율 향상

				//Today 기록을 초기화해서 기록
				bw.write(String.format("%d,%d,%d,%d,0,%d,0", cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
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
