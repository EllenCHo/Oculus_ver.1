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
		//boolean x = true;
		long time = System.currentTimeMillis();
		
		while(true){
			if(cal.get(Calendar.MINUTE) == 49 && time == 0 /*cal.get(Calendar.SECOND) >= 0 && cal.get(Calendar.SECOND) <= 2*/){
	    		switch(cal.get(Calendar.HOUR_OF_DAY)){
	    		case 0:
					Thread followMe0 = new Thread(new FollowMe());
					followMe0.start();
					//finish();
					break;
	    		case 1:
					Thread dots1 = new Thread(new Dots());
					dots1.start();
					//finish();
					break;
	    		case 2:
					Thread brightness2 = new Thread(new Brightness());
					brightness2.start();
					//finish();
					break;
	    		case 8:
					Thread followMe9 = new Thread(new FollowMe());
					followMe9.start();
					//finish();
					break;
	    		case 11:
					Thread dots11 = new Thread(new Dots());
					dots11.start();
					//finish();
					break;
	    		case 13:
					Thread brightness13 = new Thread(new Brightness());
					brightness13.start();
					//finish();
					break;
	    		case 15:
					Thread followMe15 = new Thread(new FollowMe());
					followMe15.start();
					//finish();
					break;
	    		case 17:
					Thread dots17 = new Thread(new Dots());
					dots17.start();
					//finish();
					break;
	    		case 19:
					Thread brightness19 = new Thread(new Brightness());
					brightness19.start();
					//finish();
					break;
	    		case 21:
					Thread followMe21 = new Thread(new FollowMe());
					followMe21.start();
					//finish();
					break;
	    		case 22:
					Thread dots22 = new Thread(new Dots());
					dots22.start();
					//finish();
					break;
	    		case 23:
					Thread brightness23 = new Thread(new Brightness());
					brightness23.start();
					//finish();
					break;
	    		}
				// x = false;
			}
			
			try{
				Thread.sleep(1000);   // 1초마다 실행
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
