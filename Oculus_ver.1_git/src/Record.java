// 02. ���� ��� Ȯ�� Ķ����

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Record extends JFrame implements ActionListener
{
	String [] days = {"��","��","ȭ","��","��","��","��"};		 // ���Ͽ� �ش��ϴ� ���ڿ� �迭 ����
	int year,month,day,todays,memoday=0;	
	Font f;
	Color bc,fc;
	Calendar today;
	Calendar cal;
	JButton btnBefore,btnAfter;
	JButton[] calBtn = new JButton[49]; 				// �� ���� 49�Ϸ� ����
	JLabel thing;
	JLabel time;
	JLabel Explain;
	JPanel panWest; 						// �޷��� �� �κ�
	JPanel panSouth;						// �޷��� �Ʒ��� ����
	JPanel panNorth; 						// �޷��� ���� ����
	JTextField txtMonth,txtYear;
	JTextField txtTime;

	BorderLayout bLayout= new BorderLayout();     

	int target; 							// ��ǥġ

	FileReader fr = null;
	BufferedReader br = null;

	String line;
	String[] str;
	int year_ck;
	int date;
	int setmonth;
	int c;
	public static double Date[][] = new double [12][31];   // 
	public static int Target[][] = new int [12][31];   // ���

	public Record(){
		for(int i =0; i<12; i++){					// ��� �ʱ�ȭ
			for(int j =0; j<31; j++){
				Date[i][j] = 0;
			}
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// �ݱ� ��ư�� ������ �� �ش� â�� ����
	}

	public void run(){


		today = Calendar.getInstance(); 				// ���� �ý����� �ð� ������ ��� Calendar Ŭ���� ��ü�� ����,����Ʈ�� Ÿ�� �� �� �������� ����� �޷��� ������
		cal = new GregorianCalendar();					// GregorianCalendar ��ü ����
		year = today.get(Calendar.YEAR);				// �⵵ ����
		month = today.get(Calendar.MONTH)+1;				// �� ����, 1���� ���� 0�̹Ƿ� +1 

		// ���� �Է�
		try{
			fr = new FileReader("log\\Info.txt");
			br = new BufferedReader(fr);

			while((line = br.readLine()) != null){  			// �� �پ� �о��
				str = line.split(",");   				// ��ǥ�� �и�
				year_ck = Integer.parseInt(str[0]);

				if(year_ck == year){				//��ϵ� ������ ���ض��
					setmonth = Integer.parseInt(str[1]); 			// �̹� �޸� ���
					date = Integer.parseInt(str[2]);   			// int������ ��ȯ�Ͽ� ���� : ù ��° ���ڴ� ��¥, �� ��° ���ڴ� 
					Date[setmonth-1][date-1] = Double.parseDouble(str[3]);  // �迭�� 0���� ����

					for(int i =0; i<12; i++){
						for(int j =0; j<31; j++){

							//��޳�����
							if(Date[i][j]<26 && Date[i][j]>0)		// ����� 0 �ʰ� 26 �̸��̸�
								Target[i][j] = 1;			// ��ǥġ�� 1
							else if(Date[i][j]<51 && Date[i][j]>=26)	// ����� 26 �̻� 51 �̸��̸�
								Target[i][j] = 2;			// ��ǥġ�� 2
							else if(Date[i][j]<76 && Date[i][j]>=51)	// ����� 51 �̻� 76 �̸��̸�
								Target[i][j] = 3;			// ��ǥġ�� 3
							else if(Date[i][j]>=76)				// ����� 76 �̻��̸�
								Target[i][j] = 4;			// ��ǥġ�� 4
							else if(Date[i][j]==0)				// ����� 0�̸�
								Target[i][j] = 0;			// ��ǥġ�� 0
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
		}catch(IOException e){System.out.println("�����Է½���");}


		panNorth = new JPanel();					// JPanel ��ü�� �����Ͽ� panNorth�� ����
		panNorth.add(btnBefore = new JButton("Before"));		// "Before" ��ư�� panNorth�� ����    
		panNorth.add(txtYear = new JTextField(year+"��"));		// �⵵�� ��Ÿ���� �ؽ�Ʈ�ʵ带 panNorth�� ����
		panNorth.add(txtMonth = new JTextField( month+"��",3));		// ���� ��Ÿ���� �ؽ�Ʈ�ʵ带 panNorth�� ����
		txtYear.setEnabled(false); 					// �ٲܼ� ���� ����
		txtMonth.setEnabled(false);					// �ٲܼ� ���� ����
		panNorth.add(btnAfter = new JButton("After"));			// "After" ��ư�� panNorth�� ����
		f=new Font("Sherif",Font.BOLD,18); 				// ��Ʈ ����
		txtYear.setFont(f);						// txtYear �ؽ�Ʈ�ʵ忡 ��Ʈ ����
		txtMonth.setFont(f);       					// txtMonth �ؽ�Ʈ�ʵ忡 ��Ʈ ����
		add(panNorth,"North"); 						// panNorth�� ���� ����

		panSouth = new JPanel();					// JPanel ��ü�� �����Ͽ� panSouth�� ����
		panSouth.add(Explain = new JLabel("���� : 1 ~ 25%, ��Ȳ : 26 ~ 50%, �Ķ� : 51 ~ 75%, �ʷ� : 76 ~ 100%"));  	// ���ڿ� ���̺� ������Ʈ ����
		f=new Font("Sherif",Font.BOLD,12); 				// ��Ʈ ����
		Explain.setFont(f);						// Explain ���̺� ��Ʈ ����
		add(panSouth,"South");						// panSouth�� �Ʒ��� ����

		// �޷¿� ���� �ش��ϴ� �κ�
		panWest = new JPanel(new GridLayout(7,7));			// 7X7 ���ҷ� ������Ʈ ��ġ
		f=new Font("Sherif",Font.BOLD,12);				// ��Ʈ ����
		gridInit();
		calSet();
		hideInit();
		add(panWest,"Center"); 						// panWest�� ����� ����

		btnBefore.addActionListener(this);
		btnAfter.addActionListener(this);       
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Calendar");						// â�̸��� "Calendar"
		setBounds(300,300,445,350);					// (300,300) ���� 445, ���� 350 ũ��� ��ġ ��Ŵ
		setLocationRelativeTo(null);					// �ݱ� ��ư�� ������ �� �ش� â�� ����
		setVisible(true);						// â�� ���̰� ��
	}

	//�޷� ����
	public void calSet(){
		cal.set(Calendar.YEAR,year);					// �⵵ ����
		cal.set(Calendar.MONTH,(month-1));				// �� ����
		cal.set(Calendar.DATE,1);					// �� ����
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			// ���� ������
		/*
		 * get �� set �� ���� �ʵ�ġ��, ������ ��Ÿ���ϴ�.
		 * �� �ʵ��� ����,SUNDAY,MONDAY,TUESDAY,WEDNESDAY
		 * ,THURSDAY,FRIDAY, �� SATURDAY �� �˴ϴ�. 
		 * get()�޼ҵ��� ���� ������ ���ڷ� ��ȯ
		 */
		int j=0;
		int hopping=0;

		//�Ͽ���, ����� ��ư ���� ���� ����
		calBtn[0].setForeground(new Color(255,0,0));			//�Ͽ��� "��"
		calBtn[6].setForeground(new Color(0,0,255));			//����� "��"
		for(int i=cal.getFirstDayOfWeek();i<dayOfWeek;i++){  j++;  } 	//ù���� �Ͽ���(1)���� �� ���� ���� ���ϱ��� j++
		/*
		 * �Ͽ��Ϻ��� �״��� ù���� ���ϱ��� ��ĭ���� �����ϱ� ���� 
		 */
		hopping=j;
		for(int kk=0;kk<hopping;kk++){
			calBtn[kk+7].setText(""); //+7�� ������ �� ���ٿ� ������ ��� ������ �ƴұ�?
		}

		//���� ������(1)�� ���� �ְ��� �̿��Ͽ� for�� ������
		for(int i=cal.getMinimum(Calendar.DAY_OF_MONTH);
				i<=cal.getMaximum(Calendar.DAY_OF_MONTH);i++){
			cal.set(Calendar.DATE,i);
			if(cal.get(Calendar.MONTH) !=month-1){ //���� ��ġ���������� break
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
			 * ������ ���� �������� ����ؾ� �ϴ� ������ ���� ��ư�� ������ ���ϰ�
			 * �ε����� 0���� �����̴� -1�� ���� ������ ������ ���ְ�
			 * ��ư�� ������ �������ش�. 
			 */
			calBtn[i+6+hopping].setText((i)+""); //""�� ���̴� ������ i�� ���ڷ� �ٲٱ� ���ؼ�

			for(j=0; j<12; j++){
				if(month == j+1){
					//��޿� ���� ���� ������
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
			this.txtYear.setText(year+"��");
			this.txtMonth.setText(month+"��");                   
		}                   
		else if(ae.getSource() == btnAfter){
			this.panWest.removeAll();
			calInput(1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year+"��");
			this.txtMonth.setText(month+"��");                                       
		}
		//��¥�� ��������
		else if(Integer.parseInt(ae.getActionCommand()) >= 1 && 
				Integer.parseInt(ae.getActionCommand()) <=31){
			day = Integer.parseInt(ae.getActionCommand());
			//��ư�� ��� �� 1,2,3.... ���ڸ� ���������� ��ȯ�Ͽ� Ŭ���� ��¥�� �ٲ��ش�.
			System.out.println(+year+"-"+month+"-"+day);
			calSet();
		}      
	}//end actionperformed()

	public void hideInit(){
		for(int i = 0 ; i < calBtn.length;i++){
			if((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false);
			//���� ������ ���� ������ ��ư�� ��Ȱ��ȭ ��Ų��. 
		}//end for
	}//end hideInit()

	public void gridInit(){
		//jPanel3�� ��ư ���̱�
		for(int i = 0 ; i < days.length;i++) //0<=i<7
		{
			panWest.add(calBtn[i] = new JButton(days[i]));
			calBtn[i].setContentAreaFilled(false); //��ư�� ������ ĥ���� ���� ����, ��,ȭ,��,��,�ݿ��� ���� ���� x
			calBtn[i].setBorderPainted(false);
		}	
		for(int i = days.length ; i < 49;i++){                
			panWest.add(calBtn[i] = new JButton(""));                   
			calBtn[i].addActionListener(this);
		}              
	}//end gridInit()

	//�� ���̾ƿ� ����
	public void panelInit(){
		GridLayout gridLayout1 = new GridLayout(7,7);
		panWest.setLayout(gridLayout1);   
	}//end panelInit()

	//��,�⵵ �ű��
	public void calInput(int gap){
		month+=(gap);		
		if (month<=0){
			month = 12;
			//year  =year- 1; //�⵵�� �ű�� �������� �迭�� Ŀ���Ƿ� ����
		}else if (month>=13){
			month = 1;
			//year =year+ 1;
		}		
	}//end calInput()	
}//end class
