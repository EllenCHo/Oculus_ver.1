import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * 기록을 저장하고 불러오는 클래스
 * @author Sol
 * @see DateJLabel
 *
 */
public class Save {
	/**
	 * Today에 저장된 정보로 MainFrame의 월, 일, FM, FMC, FD, FMC를 설정한다.
	 * 만약 log 폴더에 Today.txt가 없다면 Today.txt를 만들고 초기값(목표치 = 0)을 저장한다.
	 * 폴더가 없다면 log 폴더 생성한다.
	 * @param Calendar cal - 현재 날짜와 시간 정보를 가져올 변수
	 * @param String str[] - 기록을 가져올 변수
	 */
	public static void Load(){
		Calendar cal = Calendar.getInstance(); 
		String line; 
		String str[] = {};
		
		try {
			DateJLabel.fr = new FileReader("log\\Today.txt"); 
			DateJLabel.br = new BufferedReader(DateJLabel.fr); 

			line = DateJLabel.br.readLine();
			for (int i = 0; i < 7; i++) {
				str = line.split(",");
			}

			MainFrame.year = Integer.parseInt(str[0]);
			MainFrame.month = Integer.parseInt(str[1]);
			MainFrame.day = Integer.parseInt(str[2]);
			MainFrame.FM = Integer.parseInt(str[3]); 
			MainFrame.FMC = Integer.parseInt(str[4]);
			MainFrame.FD = Integer.parseInt(str[5]); 
			MainFrame.FDC = Integer.parseInt(str[6]);

			DateJLabel.br.close();
			DateJLabel.fr.close();

		}
		catch (java.io.FileNotFoundException e) {
			MainFrame.FM = 9;
			MainFrame.FD = 9;
			MainFrame.year = cal.get(Calendar.YEAR);			
			MainFrame.month = cal.get(Calendar.MONTH) + 1;			
			MainFrame.day = cal.get(Calendar.DAY_OF_MONTH);			
			MainFrame.FMC = 0;			
			MainFrame.FDC = 0;
						
			try {
				FileWriter fw = null;
				BufferedWriter bw = null;
				File dsFile = new File("log\\Today.txt");
				if(!dsFile.exists()){
					File dsDir = new File("log"); 
					dsDir.mkdirs();
					dsDir = null;
				}

				dsFile = null;				
				fw = new FileWriter("log\\Today.txt"); 
				bw = new BufferedWriter(fw);
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
	
	/**
	 * 연도, 월, 일과 현재 설정된 목표값과 카운트 값을 Today.txt에 저장한다.
	 */
	public static synchronized void SaveNow() {
		try {
			DateJLabel.fw = new FileWriter("log\\Today.txt"); 
			DateJLabel.bw = new BufferedWriter(DateJLabel.fw);
			
			DateJLabel.bw.write(String.format("%d,%d,%d,%d,%d,%d,%d", MainFrame.year, MainFrame.month, MainFrame.day,
					MainFrame.FM, MainFrame.FMC, MainFrame.FD, MainFrame.FDC));
			DateJLabel.bw.flush();

			DateJLabel.bw.close();
			DateJLabel.fw.close();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}
	
	/**
	 * 하루가 지났다면 연도, 월, 일, 목표 달성치를 Info.txt에 저장한다.
	 * 하루가 지났기 때문에 Today.txt에 달성치 값을 저장한다.
	 * 만약 log 폴더에 Info.txt가 없다면 Info.txt를 만들고 달성치가 0인 값을 저장한다.
	 * 폴더가 없다면 log 폴더 생성한다
	 * @param Calendar now - 현재 날짜와 시간 정보를 가져올 변수
	 */
	public static void SaveDay() {
		Calendar now = Calendar.getInstance(); 

		try {
			if (now.get(Calendar.DAY_OF_MONTH) != MainFrame.day) {
				DateJLabel.fw = new FileWriter("log\\Info.txt", true); 
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw); 

				DateJLabel.bw.write(String.format("%d,%d,%d,%.2f\r\n", MainFrame.year, MainFrame.month, MainFrame.day,
						(double) 100 * (MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));
				DateJLabel.bw.flush();

				MainFrame.FMC = 0;
				MainFrame.FDC = 0;

				DateJLabel.fw = new FileWriter("log\\Today.txt"); 
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw); 

				// Today 기록을 초기화해서 기록
				DateJLabel.bw.write(String.format("%d,%d,%d,%d,0,%d,0", now.get(Calendar.YEAR),
						now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
				DateJLabel.bw.flush();

				DateJLabel.br.close(); 
				DateJLabel.fr.close();
				DateJLabel.bw.close();
				DateJLabel.fw.close();
			}
		} catch (java.io.FileNotFoundException e) { 
			try {
				FileWriter fw = null;
				BufferedWriter bw = null;
				File dsFile = new File("log\\Info.txt");		
				if(!dsFile.exists()){							
					File dsDir = new File("log"); 				
					dsDir.mkdirs();
					dsDir = null;
				}
				
				dsFile = null;				
				fw = new FileWriter("log\\Info.txt"); 
				bw = new BufferedWriter(fw);
				
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
