// 07. 홍채 운동 : 명암 트레이닝

import java.io.*;

public class Brightness implements Runnable{
	HowToUse htu3;
	
	public static void setBrightness(int brightness)  throws IOException {
		//Creates a powerShell command that will set the brightness to the requested value (0-100), after the requested delay (in milliseconds) has passed. 
			String s = String.format("$brightness = %d;", brightness)
				+ "$delay = 0;"
				+ "$myMonitor = Get-WmiObject -Namespace root\\wmi -Class WmiMonitorBrightnessMethods;"
				+ "$myMonitor.wmisetbrightness($delay, $brightness)";
			String command = "powershell.exe  " + s;
		
		// Executing the command
			Process powerShellProcess = Runtime.getRuntime().exec(command);
			powerShellProcess.getOutputStream().close();

		//Report any error messages
			String line;

			BufferedReader stderr = new BufferedReader(new InputStreamReader(
					powerShellProcess.getErrorStream()));
			line = stderr.readLine();
		
		if (line != null)
		{
			System.err.println("Standard Error:");
			
			do
			{
				System.err.println(line);
			} while ((line = stderr.readLine()) != null);

		}
		stderr.close();
	}

	public void run(){
		htu3 = new HowToUse(3);						//운동에 대한 설명서
		int k = -1;
		
		//for(k = 0; k < 1; k++){   // 디버깅용
		for(k = 0; k < 10; k++){   // 열 번 반복 후 종료
			try{
				try{
					setBrightness(0);
					Thread.sleep(5000);
					setBrightness(100);
					Thread.sleep(3500);
				}catch(IOException e){
					System.err.println(e);
					return;
				}
			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}
		}
		try{
			setBrightness(80);   // 중간으로 돌려준다.
			htu3.finish();
			return;
		}catch(IOException e){
			System.err.println(e);
			return;
		}
	}
}