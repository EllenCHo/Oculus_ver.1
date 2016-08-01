// 00. Main Page

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

class MainFrame extends JFrame implements WindowListener{
	public static int Year = -1;
	public static int Month = -1;
	public static int Day = -1;
	public static int FM = -1;   	// Follow Me 목표 횟수
	public static int FMC = -1;   	// Follow Me 카운트
	public static int FD = -1;   	// Fifteen Dots 목표 횟수
	public static int FDC = -1;   	// Fifteen Dots 카운트
	
	
	private JLabel date;   			 // 현재 날짜, 시각
	private JButton record;    		 // 월간/주간 목표 그래프
	private JButton graph;   		 // 오늘 목표달성치(%)
	private JButton goal;   		 // 목표 설정
	private JButton exercise1;  	 // 운동 1 : Follow Me
	private JButton exercise2;  	 // 운동 2 : 15 dots
	private JButton exercise3;  	 // 운동 3 : Brightness
	private JButton reference;  	 // 개발자 및 앱 소개
	
	private Calendar cal;
	
	String line;					//달력을 가져올 변수
	String str[];					//기록을 가져올 변수
	
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
    	new Tray();											//닫았을 때 트레이로 돌아감
    	DateJLabel.stop();									//시계 쓰레드 종료
    	Thread Waiting = new Thread(new Waiting());			//트레이로 돌려보냈을 시에 지정시간이 되면 자동으로 실행하게 함
		Waiting.start();
    }
    public void windowDeactivated(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    	Thread Waiting = new Thread(new Waiting());		    //창을 내렸을 경우 지정시간이 되면 운동이 랜덤으로 실행되도록 설정 
    	Waiting.start();
    }
    public void windowDeiconified(WindowEvent e) {
    	Waiting.finish();									//창을 다시 올리면 웨이팅 종료
    }
    public void windowOpened(WindowEvent e) {
    	Waiting.finish();									//트레이에서 돌아오면 웨이팅 종료
    }
	
	MainFrame(){
		setTitle("Oclulus");
		setSize(750, 1000);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(this);
		cal = Calendar.getInstance();   				   //달력 초기화

		//시계 생성
		date = new JLabel("현재 날짜시각", null, SwingConstants.CENTER);
		
		//기록 버튼 생성과 버튼 색상 설정
		record = new JButton("월간 기록 확인");
		record.setFont(new Font("Gothic", Font.BOLD, 20));
		record.setBackground(new Color(93,93,93));
		record.setForeground(Color.white);
		record.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//그래프 버튼 생성과 버튼 색상 설정
		graph = new JButton("오늘 목표달성율(%)");
		graph.setFont(new Font("Gothic", Font.BOLD, 20));
		graph.setBackground(new Color(93,93,93));
		graph.setForeground(Color.white);
		graph.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//설정 버튼 생성과 버튼 색상 설정
		goal = new JButton("설정");
		goal.setFont(new Font("Gothic", Font.BOLD, 20));
		goal.setBackground(new Color(93,93,93));
		goal.setForeground(Color.white);
		goal.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//Follow Me 버튼 생성과 버튼 색상 설정
		exercise1 = new JButton("Follow Me");
		exercise1.setFont(new Font("Arial", Font.BOLD, 20));
		exercise1.setBackground(new Color(93,93,93));
		exercise1.setForeground(Color.white);
		exercise1.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//Fifteen Dots 버튼 생성과 버튼 색상 설정
		exercise2 = new JButton("Fifteen Dots");
		exercise2.setFont(new Font("Gothic", Font.BOLD, 20));
		exercise2.setBackground(new Color(93,93,93));
		exercise2.setForeground(Color.white);
		exercise2.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//Brightness 버튼 생성과 버튼 색상 설정
		exercise3 = new JButton("Brightness");
		exercise3.setFont(new Font("Gothic", Font.BOLD, 20));
		exercise3.setBackground(new Color(93,93,93));
		exercise3.setForeground(Color.white);
		exercise3.setUI(new StyledButtonUI());   // customize the button with your own look
		
		//소개 버튼 생성과 버튼 색상 설정
		reference = new JButton("가천대학교 의용생체공학과 소개");
		reference.setFont(new Font("Gothic", Font.BOLD, 20));
		reference.setBackground(new Color(93,93,93));
		reference.setForeground(Color.white);
		reference.setUI(new StyledButtonUI());   // customize the button with your own look
		
		Container mContainer = getContentPane();
		mContainer.setLayout(new GridLayout(4, 2, 30, 30));				//레이아웃 관리자 설정
		
		//버튼 추가
		mContainer.add(date);
		mContainer.add(record);
		mContainer.add(graph);
		mContainer.add(goal);
		mContainer.add(exercise1);
		mContainer.add(exercise2);
		mContainer.add(exercise3);
		mContainer.add(reference);
		
		//기록에 저장된 내용으로 월, 일, 목표횟수, 카운트 세팅
		try{
			DateJLabel.fr = new FileReader("log\\Today.txt");   // 파일 입력 스트림 생성
			DateJLabel.br = new BufferedReader(DateJLabel.fr);   // 버퍼 파일 입력 스트림 생성, 입력 효율 향상
			
			line = DateJLabel.br.readLine();
			for(int i = 0; i < 7; i++){
				str = line.split(",");
			}
			
			Year = Integer.parseInt(str[0]);
			Month = Integer.parseInt(str[1]);
			Day = Integer.parseInt(str[2]);
			FM = Integer.parseInt(str[3]);
			FMC = Integer.parseInt(str[4]);
			FD = Integer.parseInt(str[5]);
			FDC = Integer.parseInt(str[6]);
			
			DateJLabel.br.close();   // 파일 입출력 스트림을 닫고 시스템 자원 해제
			DateJLabel.fr.close();
		
			
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
	}

	public void runRecord(){
		record.addActionListener(new ActionListener() {   // Record 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				Record record = new Record();
				record.run();
			}
		});
	}

	public void runGraph(){
		graph.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Graph graph = new Graph();
			}
		});
	}

	public void runGoal(){
		goal.addActionListener(new ActionListener() {   // Goal 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				Thread goal = new Thread(new Goal());
				goal.start();
			}
		});
	}
	
	public void runExercise1(){
		exercise1.addActionListener(new ActionListener() {   // Follow Me 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){	
				Thread followMe = new Thread(new FollowMe());
				followMe.start();
			}
		});
	}
	
	public void runExercise2(){
		exercise2.addActionListener(new ActionListener() {   // Dots 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				Thread dots = new Thread(new Dots());
				dots.start();
			}
		});
	}
	
	public void runExercise3(){
		exercise3.addActionListener(new ActionListener() {   // Brightness 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				Thread brightness = new Thread(new Brightness());
				brightness.start();
			}
		});
	}
	
	public void runReference(){
		reference.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Reference reference = new Reference();
			}
		});
	}
	
	
	public void setDate(JLabel date){this.date = date;}
	public void setRecord(){return;}
	public void setGraph(){return;}
	public void setGoal(){return;}
	public void setExercise1(){return;}
	public void setExercise2(){return;}
	public void setExercise3(){return;}
	public void setReference(){return;}

	public JLabel getDate(){return date;}
	public JButton getRecord(){return record;}
	public JButton getGraph(){return graph;}
	public JButton getGoal(){return goal;}
	public JButton getExercise1(){return exercise1;}
	public JButton getExercise2(){return exercise2;}
	public JButton getExercise3(){return exercise3;}
	public JButton getReference(){return reference;}
}

public class Main {
	public static void main(String[] args){
		MainFrame mFrame = new MainFrame();

		Calendar now = Calendar.getInstance();   // 현재 날짜와 시간 정보를 가져온다.
		
		Save.SaveDay();							 //날짜가 지나면 기록에 저장
		
		Thread dateJLabel = new Thread(new DateJLabel(now, mFrame.getDate()));		//시계 쓰레드 시작
		dateJLabel.start();
		
		//쓰레드 생성
		mFrame.runRecord();
		mFrame.runGraph();
		mFrame.runGoal();
		mFrame.runExercise1();
		mFrame.runExercise2();
		mFrame.runExercise3();
		mFrame.runReference();
	}

	//버튼 디자인 바꾸기
	class StyledButtonUI extends BasicButtonUI {

		@Override
		public void installUI (JComponent c) {
			super.installUI(c);
			AbstractButton button = (AbstractButton) c;
			button.setOpaque(false);
			button.setBorder(new EmptyBorder(5, 15, 5, 15));
		}

		@Override
		public void paint (Graphics g, JComponent c) {
			AbstractButton b = (AbstractButton) c;
			paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
			super.paint(g, c);
		}

		private void paintBackground (Graphics g, JComponent c, int yOffset) {
			Dimension size = c.getSize();
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c.getBackground().darker());
			g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
			g.setColor(c.getBackground());
			g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
		}
	}
}