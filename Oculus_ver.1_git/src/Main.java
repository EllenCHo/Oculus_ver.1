// 00. Main Page

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

class MainFrame extends JFrame implements WindowListener{
	public static int Month = -1;
	public static int Day = -1;
	public static int FM = -1;   // Follow Me 목표 횟수
	public static int FMC = -1;   // Follow Me 카운트
	public static int FD = -1;   // Fifteen Dots 목표 횟수
	public static int FDC = -1;   // Fifteen Dots 카운트
	
	
	private JLabel date;   // 현재 날짜, 시각
	private JButton record;   // 월간/주간 목표 그래프
	private JButton graph;   // 오늘 목표달성치(%)
	// private JPanel graph;
	private JButton goal;   // 목표 설정
	private JButton exercise1;   // 운동 1 : Follow Me
	private JButton exercise2;   // 운동 2 : 15 dots
	private JButton exercise3;   // 운동 3 : Brightness
	private JButton reference;   // 개발자 및 앱 소개
	
	private Calendar cal;
	
	String line;
	String str[];
	
	// public static FollowMe finFollowMe;
	// public static Dots finDots;
	
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
    	new Tray();		//닫았을 때 트레이로 돌아감
    	DateJLabel.stop();
    	Thread Waiting = new Thread(new Waiting());			//트레이로 돌려보냈을 시에 지정시간이 되면 자동으로 실행하게 함
		Waiting.start();
    	//System.exit(0);
    }
    public void windowDeactivated(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    	// runRun();
		// Thread waiting = new Thread(new Waiting());
		// waiting.start();
    }
    public void windowDeiconified(WindowEvent e) {
    	Waiting.finish();
    }
    public void windowOpened(WindowEvent e) {}
	
	MainFrame(){
		setTitle("Oclulus");
		setSize(750, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		cal = Calendar.getInstance();   // 다른 거 망가질까봐 억지로 초기화 함

		date = new JLabel("현재 날짜시각", null, SwingConstants.CENTER);
		record = new JButton("월간 기록 확인");
		record.setFont(new Font("Gothic", Font.BOLD, 20));
		record.setBackground(new Color(93,93,93));
		record.setForeground(Color.white);
		record.setUI(new StyledButtonUI());   // customize the button with your own look
		graph = new JButton("오늘 목표달성율(%)");
		// graph = new JLabel("오늘 목표달성율(%)", null, SwingConstants.CENTER);
		graph.setFont(new Font("Gothic", Font.BOLD, 20));
		graph.setBackground(new Color(93,93,93));
		graph.setForeground(Color.white);
		graph.setUI(new StyledButtonUI());   // customize the button with your own look
		goal = new JButton("설정");
		goal.setFont(new Font("Gothic", Font.BOLD, 20));
		goal.setBackground(new Color(93,93,93));
		goal.setForeground(Color.white);
		goal.setUI(new StyledButtonUI());   // customize the button with your own look
		exercise1 = new JButton("Follow Me");
		exercise1.setFont(new Font("Arial", Font.BOLD, 20));
		exercise1.setBackground(new Color(93,93,93));
		exercise1.setForeground(Color.white);
		exercise1.setUI(new StyledButtonUI());   // customize the button with your own look
		exercise2 = new JButton("Fifteen Dots");
		exercise2.setFont(new Font("Gothic", Font.BOLD, 20));
		exercise2.setBackground(new Color(93,93,93));
		exercise2.setForeground(Color.white);
		exercise2.setUI(new StyledButtonUI());   // customize the button with your own look
		exercise3 = new JButton("Brightness");
		exercise3.setFont(new Font("Gothic", Font.BOLD, 20));
		exercise3.setBackground(new Color(93,93,93));
		exercise3.setForeground(Color.white);
		exercise3.setUI(new StyledButtonUI());   // customize the button with your own look
		reference = new JButton("가천대학교 의용생체공학과 소개");
		reference.setFont(new Font("Gothic", Font.BOLD, 20));
		reference.setBackground(new Color(93,93,93));
		reference.setForeground(Color.white);
		reference.setUI(new StyledButtonUI());   // customize the button with your own look
		
		Container mContainer = getContentPane();
		mContainer.setLayout(new GridLayout(4, 2, 30, 30));
		mContainer.add(date);
		mContainer.add(record);
		mContainer.add(graph);
		mContainer.add(goal);
		mContainer.add(exercise1);
		mContainer.add(exercise2);
		mContainer.add(exercise3);
		mContainer.add(reference);
		
		//기록에 저장된 내용으로 세팅
		try{
			DateJLabel.fr = new FileReader("log\\Today.txt");   // 파일 입력 스트림 생성
			DateJLabel.br = new BufferedReader(DateJLabel.fr);   // 버퍼 파일 입력 스트림 생성, 입력 효율 향상
			
			line = DateJLabel.br.readLine();
			for(int i = 0; i < 6; i++){
				str = line.split(",");
			}
			Month = Integer.parseInt(str[0]);
			Day = Integer.parseInt(str[1]);
			FM = Integer.parseInt(str[2]);
			FMC = Integer.parseInt(str[3]);
			FD = Integer.parseInt(str[4]);
			FDC = Integer.parseInt(str[5]);
			
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
				// Thread record = new Thread(new Record());
				// record.start();
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
				// new HowToUse(1);	
				// new FollowMe();		
				Thread followMe = new Thread(new FollowMe());
				followMe.start();
			}
		});
	}
	
	public void runExercise2(){
		exercise2.addActionListener(new ActionListener() {   // Dots 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				// new HowToUse(2);
				// new Dots();
				Thread dots = new Thread(new Dots());
				dots.start();
			}
		});
	}
	
	public void runExercise3(){
		exercise3.addActionListener(new ActionListener() {   // Brightness 버튼 : 리스너 & 스레드
			public void actionPerformed(ActionEvent e){
				// new HowToUse(3);
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
	
	/*
	public void runRun(){

    		}
    	}
	}
	*/
	
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
		
		// SyncroObject syncro1 = new SyncroObject();
		// SyncroObject syncro2 = new SyncroObject();

		Calendar now = Calendar.getInstance();   // 현재 날짜와 시간 정보를 가져온다.
		
		Save.SaveDay();			//날짜가 지나면 기록에 저장
/*		
		try{
			//하루가 넘어갔을 경우 카운트를 초기화하고 기록 저장
			if(now.get(Calendar.DAY_OF_MONTH) != MainFrame.Day){
				DateJLabel.fw = new FileWriter("log\\Info.txt", true);   // 파일 출력 스트림 생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				// if((cal.get(Calendar.DAY_OF_MONTH)) == 1)   // 새로운 달로 넘어갔을 때
				DateJLabel.bw.write(String.format("%d,%d,%.2f\r\n", MainFrame.Month, MainFrame.Day, (double)100 * (MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt에 오늘 기록 입력
				DateJLabel.bw.flush();
				// else
				// bw.write(String.format("%d,%d,%f\r\n", cal.get(Calendar.MONTH + 1), (cal.get(Calendar.DAY_OF_MONTH) - 1), (double)(MainFrame.FMC + MainFrame.FDC) / (MainFrame.FM + MainFrame.FD)));   // Info.txt에 오늘 기록 입력

				// mFrame.FM = 9;
				MainFrame.FMC = 0;
				// mFrame.FD = 9;
				MainFrame.FDC = 0;

				DateJLabel.fw = new FileWriter("log\\Today.txt");   // 파일 출력 스트림 생성
				DateJLabel.bw = new BufferedWriter(DateJLabel.fw);   // 버퍼 파일 출력 스트림 생성, 출력 효율 향상

				DateJLabel.bw.write(String.format("%d,%d,%d,0,%d,0", now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), MainFrame.FM, MainFrame.FD));
				DateJLabel.bw.flush();

				DateJLabel.br.close();   // 파일 입출력 스트림을 닫고 시스템 자원 해제
				DateJLabel.fr.close();
				DateJLabel.bw.close();
				DateJLabel.fw.close();
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(1);
		}
		*/
		
		Thread dateJLabel = new Thread(new DateJLabel(now, mFrame.getDate()));
		// DateJLabel dateJLabel = new DateJLabel(now, mFrame.getDate());
		// mFrame.setDate(dateJLabel.getDate());
		dateJLabel.start();
		
		// Record record = new Record();
		// record.run();
		
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