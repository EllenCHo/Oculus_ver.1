// 02. 월간 기록 확인 캘린더

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Record extends JFrame implements ActionListener
{
	String [] days = {"일","월","화","수","목","금","토"};		 // 요일에 해당하는 문자열 배열 선언
	int year,month,day,todays,memoday=0;	
	Font f;
	Color bc,fc;
	Calendar today;
	Calendar cal;
	JButton btnBefore,btnAfter;
	JButton[] calBtn = new JButton[49]; 				// 일 수를 49일로 설정
	JLabel thing;
	JLabel time;
	JLabel Explain;
	JPanel panWest; 						// 달력의 일 부분
	JPanel panSouth;						// 달력의 아래쪽 영역
	JPanel panNorth; 						// 달력의 위쪽 영역
	JTextField txtMonth,txtYear;
	JTextField txtTime;

	BorderLayout bLayout= new BorderLayout();     

	int target; 							// 목표치

	FileReader fr = null;
	BufferedReader br = null;

	String line;
	String[] str;
	int year_ck;
	int date;
	int setmonth;
	int c;
	public static double Date[][] = new double [12][31];   // 
	public static int Target[][] = new int [12][31];   // 등급

	public Record(){
		for(int i =0; i<12; i++){					// 기록 초기화
			for(int j =0; j<31; j++){
				Date[i][j] = 0;
			}
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// 닫기 버튼을 눌렀을 때 해당 창만 종료
	}

	public void run(){


		today = Calendar.getInstance(); 				// 현재 시스템의 시간 정보를 얻는 Calendar 클래스 객체를 생성,디폴트의 타임 존 및 로케일을 사용해 달력을 가져옴
		cal = new GregorianCalendar();					// GregorianCalendar 객체 생성
		year = today.get(Calendar.YEAR);				// 년도 정보
		month = today.get(Calendar.MONTH)+1;				// 월 정보, 1월의 값이 0이므로 +1 

		// 파일 입력
		try{
			fr = new FileReader("log\\Info.txt");
			br = new BufferedReader(fr);

			while((line = br.readLine()) != null){  			// 한 줄씩 읽어옴
				str = line.split(",");   				// 쉼표로 분리
				year_ck = Integer.parseInt(str[0]);

				if(year_ck == year){				//기록된 연도가 올해라면
					setmonth = Integer.parseInt(str[1]); 			// 이번 달만 기록
					date = Integer.parseInt(str[2]);   			// int형으로 변환하여 저장 : 첫 번째 숫자는 날짜, 두 번째 숫자는 
					Date[setmonth-1][date-1] = Double.parseDouble(str[3]);  // 배열은 0부터 시작

					for(int i =0; i<12; i++){
						for(int j =0; j<31; j++){

							//등급나누기
							if(Date[i][j]<26 && Date[i][j]>0)		// 기록이 0 초과 26 미만이면
								Target[i][j] = 1;			// 목표치는 1
							else if(Date[i][j]<51 && Date[i][j]>=26)	// 기록이 26 이상 51 미만이면
								Target[i][j] = 2;			// 목표치는 2
							else if(Date[i][j]<76 && Date[i][j]>=51)	// 기록이 51 이상 76 미만이면
								Target[i][j] = 3;			// 목표치는 3
							else if(Date[i][j]>=76)				// 기록이 76 이상이면
								Target[i][j] = 4;			// 목표치는 4
							else if(Date[i][j]==0)				// 기록이 0이면
								Target[i][j] = 0;			// 목표치는 0
						}
					}
				}else {
					for(int i =0; i<12; i++){
						for(int j =0; j<31; j++){
							Target[i][j] = 0;	
						}
					}
				}
			}
		}catch(IOException e){System.out.println("파일입력실패");}


		panNorth = new JPanel();					// JPanel 객체를 생성하여 panNorth에 저장
		panNorth.add(btnBefore = new JButton("Before"));		// "Before" 버튼을 panNorth에 삽입    
		panNorth.add(txtYear = new JTextField(year+"년"));		// 년도를 나타내는 텍스트필드를 panNorth에 삽입
		panNorth.add(txtMonth = new JTextField( month+"월",3));		// 월을 나타내는 텍스트필드를 panNorth에 삽입
		txtYear.setEnabled(false); 					// 바꿀수 없게 고정
		txtMonth.setEnabled(false);					// 바꿀수 없게 고정
		panNorth.add(btnAfter = new JButton("After"));			// "After" 버튼을 panNorth에 삽입
		f=new Font("Sherif",Font.BOLD,18); 				// 폰트 설정
		txtYear.setFont(f);						// txtYear 텍스트필드에 폰트 적용
		txtMonth.setFont(f);       					// txtMonth 텍스트필드에 폰트 적용
		add(panNorth,"North"); 						// panNorth을 위에 삽입

		panSouth = new JPanel();					// JPanel 객체를 생성하여 panSouth에 저장
		panSouth.add(Explain = new JLabel("빨강 : 1 ~ 25%, 주황 : 26 ~ 50%, 파랑 : 51 ~ 75%, 초록 : 76 ~ 100%"));  	// 문자열 레이블 컴포넌트 생성
		f=new Font("Sherif",Font.BOLD,12); 				// 폰트 설정
		Explain.setFont(f);						// Explain 레이블에 폰트 적용
		add(panSouth,"South");						// panSouth를 아래에 삽입

		// 달력에 날에 해당하는 부분
		panWest = new JPanel(new GridLayout(7,7));			// 7X7 분할로 컴포넌트 배치
		f=new Font("Sherif",Font.BOLD,12);				// 폰트 설정
		gridInit();
		calSet();
		hideInit();
		add(panWest,"Center"); 						// panWest를 가운데에 삽입

		btnBefore.addActionListener(this);
		btnAfter.addActionListener(this);       
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Calendar");						// 창이름은 "Calendar"
		setBounds(300,300,445,350);					// (300,300) 가로 445, 세로 350 크기로 위치 시킴
		setLocationRelativeTo(null);					// 닫기 버튼을 눌렀을 때 해당 창만 종료
		setVisible(true);						// 창을 보이게 함
	}

	//달력 설정
	public void calSet(){
		cal.set(Calendar.YEAR,year);					// 년도 지정
		cal.set(Calendar.MONTH,(month-1));				// 월 지정
		cal.set(Calendar.DATE,1);					// 일 지정
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			// 요일 가져옴
		/*
		 * get 및 set 를 위한 필드치로, 요일을 나타냅니다.
		 * 이 필드의 값은,SUNDAY,MONDAY,TUESDAY,WEDNESDAY
		 * ,THURSDAY,FRIDAY, 및 SATURDAY 가 됩니다. 
		 * get()메소드의 의해 요일이 숫자로 반환
		 */
		int j=0;
		int hopping=0;

		//일요일, 토요일 버튼 숫자 색깔 설정
		calBtn[0].setForeground(new Color(255,0,0));			//일요일 "일"
		calBtn[6].setForeground(new Color(0,0,255));			//토요일 "토"
		for(int i=cal.getFirstDayOfWeek();i<dayOfWeek;i++){  j++;  } 	//첫주인 일요일(1)부터 그 달의 시작 요일까지 j++
		/*
		 * 일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해 
		 */
		hopping=j;
		for(int kk=0;kk<hopping;kk++){
			calBtn[kk+7].setText(""); //+7인 이유는 그 윗줄에 요일을 썼기 때문이 아닐까?
		}

		//월의 최저값(1)과 월의 최고값을 이용하여 for문 돌리기
		for(int i=cal.getMinimum(Calendar.DAY_OF_MONTH);
				i<=cal.getMaximum(Calendar.DAY_OF_MONTH);i++){
			cal.set(Calendar.DATE,i);
			if(cal.get(Calendar.MONTH) !=month-1){ //달이 일치하지않으면 break
				break;
			}

			todays=i;
			if(memoday==1){
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
			calBtn[i+6+hopping].setText((i)+""); //""을 붙이는 이유는 i를 문자로 바꾸기 위해서

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
		//날짜를 눌렀을때
		else if(Integer.parseInt(ae.getActionCommand()) >= 1 && 
				Integer.parseInt(ae.getActionCommand()) <=31){
			day = Integer.parseInt(ae.getActionCommand());
			//버튼의 밸류 즉 1,2,3.... 문자를 정수형으로 변환하여 클릭한 날짜를 바꿔준다.
			System.out.println(+year+"-"+month+"-"+day);
			calSet();
		}      
	}//end actionperformed()

	public void hideInit(){
		for(int i = 0 ; i < calBtn.length;i++){
			if((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false);
			//일이 찍히지 않은 나머지 버튼을 비활성화 시킨다. 
		}//end for
	}//end hideInit()

	public void gridInit(){
		//jPanel3에 버튼 붙이기
		for(int i = 0 ; i < days.length;i++) //0<=i<7
		{
			panWest.add(calBtn[i] = new JButton(days[i]));
			calBtn[i].setContentAreaFilled(false); //버튼의 배경색을 칠할지 말지 결정, 월,화,수,목,금에는 배경색 지정 x
			calBtn[i].setBorderPainted(false);
		}	
		for(int i = days.length ; i < 49;i++){                
			panWest.add(calBtn[i] = new JButton(""));                   
			calBtn[i].addActionListener(this);
		}              
	}//end gridInit()

	//팬 레이아웃 설정
	public void panelInit(){
		GridLayout gridLayout1 = new GridLayout(7,7);
		panWest.setLayout(gridLayout1);   
	}//end panelInit()

	//월,년도 옮기기
	public void calInput(int gap){
		month+=(gap);		
		if (month<=0){
			month = 12;
			//year  =year- 1; //년도를 옮긴는 것이지만 배열이 커지므로 삭제
		}else if (month>=13){
			month = 1;
			//year =year+ 1;
		}		
	}//end calInput()	
}//end class
