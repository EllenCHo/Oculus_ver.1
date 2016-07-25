// MainFrame ��Ȱ��ȭ �� �ڵ����� � ���.

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
		System.out.println("Waiting ���� ��");
		
		while(true){
			cal = Calendar.getInstance();		//���� �ð� ����
			
			//� ���� ������ ����
			Thread followMe = new Thread(new FollowMe());
			Thread dots = new Thread(new Dots());
			Thread brightness = new Thread(new Brightness());
			
			//���� �ð�(50��)�� �Ǹ� � �� �ϳ��� �ڵ�����
			if(cal.get(Calendar.MINUTE) == 50 ){
	    		switch(cal.get(Calendar.HOUR_OF_DAY)){						//�ð��� ���� ����� ����
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
				Thread.sleep(60000);   // 1�и��� ����
				if(flag == true){
					System.out.println("Waiting ����");
					return;
				}
			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}
		}
	}
}
