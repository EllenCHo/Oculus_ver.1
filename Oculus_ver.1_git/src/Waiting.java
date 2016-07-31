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
			
			//일정 시간(50분)이 되면 운동 중 하나가 자동실행
			if(cal.get(Calendar.MINUTE) == 50 ){
	    		switch(cal.get(Calendar.HOUR_OF_DAY)){						//시간에 따라 운동별로 시작
	    		
	    		case 0:
	    			Thread followMe1 = new Thread(new FollowMe());
					followMe1.start();
					break;
					
	    		case 1:
	    			Thread dots1 = new Thread(new Dots());
					dots1.start();
					break;
					
	    		case 2:
	    			Thread brightness1 = new Thread(new Brightness());
					brightness1.start();
					break;
					
	    		case 8:
	    			Thread followMe2 = new Thread(new FollowMe());
					followMe2.start();
					break;
					
	    		case 11:
	    			Thread dots2 = new Thread(new Dots());
					dots2.start();
					break;
					
	    		case 13:
	    			Thread brightness2 = new Thread(new Brightness());
					brightness2.start();
					break;
					
	    		case 15:
	    			Thread followMe3 = new Thread(new FollowMe());
					followMe3.start();
					break;
					
	    		case 17:
	    			Thread dots3 = new Thread(new Dots());
					dots3.start();
					break;
					
	    		case 19:
	    			Thread brightness3 = new Thread(new Brightness());
					brightness3.start();
					break;
					
	    		case 21:
	    			Thread followMe4 = new Thread(new FollowMe());
					followMe4.start();
					break;
					
	    		case 22:
	    			Thread dots4 = new Thread(new Dots());
					dots4.start();
					break;
					
	    		case 23:
	    			Thread brightness4 = new Thread(new Brightness());
					brightness4.start();
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
