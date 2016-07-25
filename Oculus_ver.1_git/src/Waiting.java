// MainFrame 비활성화 시 자동으로 운동 띄움.

import java.util.Calendar;

public class Waiting implements Runnable{
	private Calendar cal;
	static boolean flag;
	
	Waiting(){
		cal = Calendar.getInstance();
		flag = false;
	}
	
	public static void finish(){
		flag = true;
	}
	
	public void run(){
		System.out.println("Waiting 실행 중");
		
		while(true){
			cal = Calendar.getInstance();		//현재 시간 갱신
			
			//운동 별로 쓰레드 생성
			Thread followMe = new Thread(new FollowMe());
			Thread dots = new Thread(new Dots());
			Thread brightness = new Thread(new Brightness());
			
			//일정 시간(50분)이 되면 운동 중 하나가 자동실행
			if(cal.get(Calendar.MINUTE) == 50 ){
	    		switch(cal.get(Calendar.HOUR_OF_DAY)){						//시간에 따라 운동별로 시작
	    		case 0:
					followMe.start();
					break;
					
	    		case 1:
					dots.start();
					break;
					
	    		case 2:
					brightness.start();
					break;
					
	    		case 8:
					followMe.start();
					break;
					
	    		case 11:
					dots.start();
					break;
					
	    		case 13:
					brightness.start();
					break;
					
	    		case 15:
					followMe.start();
					break;
					
	    		case 17:
					dots.start();
					break;
					
	    		case 19:
					brightness.start();
					break;
					
	    		case 21:
					followMe.start();
					break;
					
	    		case 22:
					dots.start();
					break;
					
	    		case 23:
					brightness.start();
					break;
	    		}
			}
			
			try{
				Thread.sleep(60000);   // 1분마다 실행
				if(flag == true){
					System.out.println("Waiting 종료");
					return;
				}
			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}
		}
	}
}
