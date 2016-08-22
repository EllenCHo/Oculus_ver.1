
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 월간 기록 확인 캘린더
 * @author YoungEun
 */

public class Record extends JFrame implements ActionListener
{
	String [] days = {"일","월","화","수","목","금","토"};		 		
	int year,month,day,todays,memoDay=0;	
	Font f;
	Color bc,fc;
	Calendar today;
	Calendar cal;
	JButton btnBefore,btnAfter;
	JButton[] calBtn = new JButton[49]; 							
	JLabel thing;
	JLabel time;
	JLabel Explain;
	JPanel panWest; 												
	JPanel panSouth;												
	JPanel panNorth; 											
	JTextField txtMonth,txtYear;
	JTextField txtTime;

	BorderLayout bLayout= new BorderLayout();    				

	int target; 											

	FileReader fr = null;											
	BufferedReader br = null;								

	String line;
	String[] str;
	int date;
	int setMonth;
	int c;
	public static double Date[][] = new double [12][31];    		
	public static int Target[][] = new int [12][31];				
	
	/**
	 * 기록 초기화
	 */
	public Record(){											
		for(int i =0; i<12; i++){
			for(int j =0; j<31; j++){
				Date[i][j] = 0;
			}
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}

	/**
	 * 파일입력
	 * 등급 나누기
	 * @throws IOException
	 */
	public void run(){
		
		try{
			fr = new FileReader("log\\Info.txt");					
			br = new BufferedReader(fr);							

			while((line = br.readLine()) != null){  					
				str = line.split(",");   							

				setMonth = Integer.parseInt(str[0]); 				
				date = Integer.parseInt(str[1]);   						 
				Date[setMonth-1][date-1] = Double.parseDouble(str[2]); 

				for(int i =0; i<12; i++){
					for(int j =0; j<31; j++){
						
						if(Date[i][j]<26 && Date[i][j]>0)				
							Target[i][j] = 1;						
						else if(Date[i][j]<51 && Date[i][j]>=26)	
							Target[i][j] = 2;							
						else if(Date[i][j]<76 && Date[i][j]>=51)		
							Target[i][j] = 3;							
						else if(Date[i][j]>=76)						
							Target[i][j] = 4;							
						else if(Date[i][j]==0)							
							Target[i][j] = 0;						
					}
				}
			}
		}catch(IOException e){System.out.println("파일입력실패");}		

		today = Calendar.getInstance(); 								
		cal = new GregorianCalendar();									
		year = today.get(Calendar.YEAR);								
		month = today.get(Calendar.MONTH)+1;						
		
		
		panNorth = new JPanel();									
		panNorth.add(btnBefore = new JButton("Before"));				  
		panNorth.add(txtYear = new JTextField(year+"년"));				
		panNorth.add(txtMonth = new JTextField( month+"월",3));		
		txtYear.setEnabled(false); 									
		txtMonth.setEnabled(false);									
		panNorth.add(btnAfter = new JButton("After"));					
		f=new Font("Sherif",Font.BOLD,18); 								
		txtYear.setFont(f);											
		txtMonth.setFont(f);       										
		add(panNorth,"North"); 										
		
		panSouth = new JPanel();									
		panSouth.add(Explain = new JLabel("빨강 : 1 ~ 25%, 주황 : 26 ~ 50%, 파랑 : 51 ~ 75%, 초록 : 76 ~ 100%"));  
		f=new Font("Sherif",Font.BOLD,12); 							
		Explain.setFont(f);											
		add(panSouth,"South");											
		
		// 달력에 날에 해당하는 부분
		panWest = new JPanel(new GridLayout(7,7));					
		f=new Font("Sherif",Font.BOLD,12);							
		gridInit();
		calSet();
		hideInit();
		add(panWest,"Center"); 							

		btnBefore.addActionListener(this);
		btnAfter.addActionListener(this);       
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Calendar");									
		setBounds(300,300,445,350);									
		setLocationRelativeTo(null);						
		setVisible(true);								
	}

	/**
	 * 달력 설정
	 */
	public void calSet(){
		cal.set(Calendar.YEAR,year);									// 년도 지정
		cal.set(Calendar.MONTH,(month-1));								// 월 지정
		cal.set(Calendar.DATE,1);										// 일 지정
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);					// 요일 가져옴
		/*
		 * get 및 set 를 위한 필드치로, 요일을 나타냅니다.
		 * 이 필드의 값은,SUNDAY,MONDAY,TUESDAY,WEDNESDAY
		 * ,THURSDAY,FRIDAY, 및 SATURDAY 가 됩니다. 
		 * get()메소드의 의해 요일이 숫자로 반환
		 */
		int j=0;
		int hopping=0;

		//일요일, 토요일 버튼 숫자 색깔 설정
		calBtn[0].setForeground(new Color(255,0,0));				
		calBtn[6].setForeground(new Color(0,0,255));					
		for(int i=cal.getFirstDayOfWeek(); i<dayOfWeek; i++){  j++;  } 	
		
		/*
		 * 일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해 
		 */
		hopping=j;
		for(int kk=0; kk<hopping; kk++){
			calBtn[kk+7].setText(""); 								
		}

		for(int i=cal.getMinimum(Calendar.DAY_OF_MONTH); i<=cal.getMaximum(Calendar.DAY_OF_MONTH); i++){    
			cal.set(Calendar.DATE,i);								
			
			if(cal.get(Calendar.MONTH) !=month-1){ 					
				break;
			}

			todays=i;
			
			if(memoDay==1){
				calBtn[i+6+hopping].setForeground(new Color(0,255,0));                 
			}
			
			else{
				calBtn[i+6+hopping].setForeground(new Color(0,0,0));	
			}
			/*
			 * 요일을 찍은 다음부터 계산해야 하니 요일을 찍은 버튼의 갯수를 더하고
			 * 인덱스가 0부터 시작이니 -1을 해준 값으로 연산을 해주고
			 * 버튼의 색깔을 변경해준다. 
			 */
			calBtn[i+6+hopping].setText((i)+""); 					

			for(j=0; j<12; j++){
				if(month == j+1){
					
					//등급에 따라 색깔 나누기
					switch(Target[j][i-1]){
					case 1 :
						calBtn[i+6+hopping].setBackground(Color.red);		
						break;

					case 2 :
						calBtn[i+6+hopping].setBackground(Color.orange);
						break;

					case 3 :
						calBtn[i+6+hopping].setBackground(Color.blue);	
						break;

					case 4 :
						calBtn[i+6+hopping].setBackground(Color.green);	
						break;

					default :
						break;
					}
				}
			}
		}//for
	}//end Calset()

	public void actionPerformed(ActionEvent ae){         
		if(ae.getSource() == btnBefore){
			this.panWest.removeAll();
			calInput(-1);
			gridInit();
			panelInit();               
			calSet();
			hideInit();
			this.txtYear.setText(year+"년");
			this.txtMonth.setText(month+"월");                   
		}                   
		else if(ae.getSource() == btnAfter){
			this.panWest.removeAll();
			calInput(1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year+"년");
			this.txtMonth.setText(month+"월");                                       
		}
		
		else if(Integer.parseInt(ae.getActionCommand()) >= 1 && Integer.parseInt(ae.getActionCommand()) <=31){			
			day = Integer.parseInt(ae.getActionCommand());
			
			System.out.println(+year+"-"+month+"-"+day);			
			calSet();
		}      
	}//end actionperformed()

	public void hideInit(){
		for(int i = 0 ; i < calBtn.length;i++){
			if((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false); 
		}//end for
	}//end hideInit()

	public void gridInit(){
		
		//jPanel3에 버튼 붙이기
		for(int i = 0 ; i < days.length;i++) 
		{
			panWest.add(calBtn[i] = new JButton(days[i]));
			calBtn[i].setContentAreaFilled(false); 		
			calBtn[i].setBorderPainted(false);
		}	
		for(int i = days.length ; i < 49;i++){                
			panWest.add(calBtn[i] = new JButton(""));                   
			calBtn[i].addActionListener(this);
		}              
	}//end gridInit()

	/**
	 * 팬 레이아웃 설정
	 */
	public void panelInit(){
		GridLayout gridLayout1 = new GridLayout(7,7);	
		panWest.setLayout(gridLayout1);   			
	}//end panelInit()

	/**
	 * 월, 년도 옮기기
	 */
	public void calInput(int gap){	
		month+=(gap);						
		if (month<=0){
			month = 12;
			//year  =year- 1; 						
		}else if (month>=13){
			month = 1;
			//year =year+ 1;
		}		
	}//end calInput()	
}//end class
