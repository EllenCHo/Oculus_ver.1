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
			
			//���� �ð�(50��)�� �Ǹ� � �� �ϳ��� �ڵ�����
			if(cal.get(Calendar.MINUTE) == 50 ){
	    		switch(cal.get(Calendar.HOUR_OF_DAY)){						//�ð��� ���� ����� ����
	    		
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
