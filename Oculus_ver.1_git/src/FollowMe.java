// 05. 안구이동근 운동 : 움직이는 점 트레이닝

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class FollowMe extends JFrame implements Runnable, WindowListener{
	public static int spset = 3;		//공 스피드 디폴트 값(랜덤)
	public static int szset = 0;		//공 크기 디폴트 값(중)
	Setting setting;					//설정창
	Container contentPane;
	boolean flag;	
	JButton btn; 						//공 설정 버튼
	HowToUse htu1;						//설명서 설정
	
	Color color = new Color(206,247,110);							//백그라운드 색깔 설정
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); 	//전체화면 사이즈 가져오기

	String [] sptext = {"하", "중", "상", "랜덤"};
	String [] sztext = {"중", "소"};
	
	int speed1[]= {res.width/350, res.width/250, res.width/150};			 					//창크기에 따른 공 스피드(하, 중, 상)
	int speed2[] = {res.width/350, res.width/250, res.width/200, res.width/50};					//창크기에 따른 랜덤 공 스피드
	int size[] = {res.width/15, res.width/20};													//컴퓨터 창 크기 따른 상대적인 공 크기(중, 하)

	//인터페이스를 상속했으므로 다 오버로딩해야함
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		finish();									//창 닫으면 카운트되지않고 종료
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

	FollowMe() {
		super("FolloFollow Me");
		contentPane = getContentPane();
		contentPane.setLayout(null); 								//배치관리자 삭제
		contentPane.setBackground(color);							//백그라운드 색깔 적용
		DrawCircle panel = new DrawCircle();					//공이 움직이는 패널 생성
		panel.setSize(res.width-100,res.height); 					//범위 지정
		panel.setBackground(color);									//패널 백그라운드 색깔 적용
		contentPane.add(panel); 									// 패널을 컨텐트팬에 부착
		this.addWindowListener(this);

		flag = false;		

		btn = new JButton("메뉴"); 									//버튼 만들기

		btn.setSize(50,50);											//버튼 사이즈 지정
		btn.setLocation(res.width-70,30);							//버튼 위치 지정
		contentPane.add(btn);

		btn.addActionListener(new ActionListener() {				//버튼 마우스 리스너 추가
			public void actionPerformed(ActionEvent e){
				setting.setVisible(true);							//설정 창 보이기
			}
		});

		setting = new Setting(this, "설정");							//설정창 생성
		setting.setLocationRelativeTo(null);						//설정창이 화면 중앙에 나오도록 설정

		setSize(res.width, res.height);								//모니터 크기만큼 창 크기 설정
		setVisible(true);

		htu1 = new HowToUse(1);										//운동에 대한 설명서

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	void finish(){
		flag = true;
	}

	public void run(){
		int k = -10;
		while(true){
			try{
				//Thread.sleep(100);   // 디버깅용
				Thread.sleep(1000);   // 60초 후 종료

				if(flag == true){
					htu1.finish();			//설명서 닫기
					dispose();				//Follow Me 창 닫기
					return;
				}
			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}

			k++;
			
			if(k == -5)
				htu1.finish();			//5초가 지나면 설명문 사라지게 하기

			if(k == 60){				//운동을 다했을 경우 카운트
				MainFrame.FMC++;
				if(MainFrame.FMC > MainFrame.FM) MainFrame.FMC = MainFrame.FM;		//카운트가 설정한 목표을 더 넘지 못하도록 설정
				Save.SaveNow();			//기록 저장
				
				dispose();				//Follow Me 창 닫기
				return;
			}
		}
	}

	public class DrawCircle extends JPanel {						//공이 움직이는 패널을 포함한 클래스
		private int xPos=200; 				//x 좌표
		private int yPos=100; 				//y 좌표

		int speedX; 						//x좌표로의 스피드
		int speedY; 						//y좌표로의 스피드
		int speedx;							//x좌표의 다음 스피드 저장
		int speedy;							//y좌표의 다음 스피드 저장

		ImageIcon icon = new ImageIcon("image\\RYAN.png");			//공 이미지 설정
		Image img = icon.getImage();

		boolean play = true;

		public DrawCircle() {
			speedX = speedY = 10;			//초기 스피드 10
			try {
				new Thread(){
					public void run(){
						while(play){
							Random rd = new Random();
							//Random.nextInt():int형의 난수를 발생
							//Random.nextInt(int n) : 0과 (n-1)사이의 int형의 난수를 발생한다.

							int x_dir = rd.nextInt(2);  			//0~1 난수 발생 (스피드를 음수로 할지 양수로 할지 랜덤으로 하기위해)
							int y_dir = rd.nextInt(2);

							int x_rnd = rd.nextInt(4);				//0~3 난수 발생 (랜덤 스피드를 골랐을 경우 스피드를 바꾸기 위해)
							int y_rnd = rd.nextInt(4);
							
							if(spset <3){							//하, 중, 상을 골랐을 경우
								speedx= speed1[spset];
								speedy= speed1[spset];
							}

							if(spset ==3){							//랜덤을 골랐을 경우
								speedx = speed2[x_rnd];
								speedy = speed2[y_rnd];
							}

							xPos = xPos + speedX; 					//x좌표 이동
							yPos = yPos + speedY; 					//y좌표 이동

							if(xPos + size[szset] >=DrawCircle.this.getWidth() ){
								//패널 창의 끝에 닿았으므로 x좌표를 왼쪽으로 가도록 속도로 음수로 설정
								speedX = -speedx;
								speedY = (y_dir == 0 ? speedy : -speedy);	//y_dir이 0이면 y방향이 그대로
							}

							if( yPos+ size[szset] >=DrawCircle.this.getHeight()){
								//패널 창의 끝에 닿았으므로 y좌표를 아래쪽으로 가도록 속도로 음수로 설정
								speedY = -speedy;
								speedX = (x_dir == 0 ? speedx : -speedx);	//x_dir이 0이면 x방향이 그대로
							}

							if(xPos < 0){
								//패널 창의 끝에 닿았으므로 x좌표를 오른쪽으로 가도록 속도로 양수로 설정
								speedX = speedx;
								speedY = (y_dir == 0 ? speedy : -speedy);
							}

							if(yPos < 0){
								//패널 창의 끝에 닿았으므로 y좌표를 위쪽으로 가도록 속도로 양수로 설정
								speedY = speedy;
								speedX = (x_dir == 0 ? speedx : -speedx);
							}

							repaint(); 										//공이 움직이기때문에 다시 그리기
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {									//공 그리기
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;

			g.drawString("공 크기 : "+sztext[szset], res.width-170, 50);				//공에 대한 설정을 패널 오른 위쪽에 그리기
			g.drawString("공 속도 : "+sptext[spset], res.width-170, 70);

			g2d.drawImage(img, xPos, yPos, size[szset], size[szset], this);			//x, y 위치에 size 만큼의 이미지 그리기
		}
	}
}


class Setting extends JDialog {												//설정 클래스
	Container cp;

	int sp=3;				//공 스피드 초기값 : 랜덤
	int sz=0;				//공 크기 초기값 : 중

	JLabel csize = new JLabel("공 크기");
	JLabel cspeed = new JLabel("공 속도");

	JRadioButton [] speed = new JRadioButton[4];		//공 스피드 라디오 버튼 생성
	JRadioButton [] size = new JRadioButton[2];			//공 크기 라이도 버튼 생성

	String [] sptext = {"하", "중", "상", "랜덤"};
	String [] sztext = {"중", "소"};

	public Setting(JFrame frame, String title) {
		super(frame, title);

		cp = getContentPane();
		cp.setLayout(null);

		
		ButtonGroup gspeed = new ButtonGroup();			//속도 선택 그룹
		for(int i=0; i<speed.length; i++){
			speed[i] = new JRadioButton(sptext[i]);		//라디오 버튼에 텍스트 추가
			gspeed.add(speed[i]);						//버튼 그룹에 추가
			speed[i].addItemListener(new SpListener()); //버튼 선택됐는지 알기 위해 리스너 달기
		}
		speed[sp].setSelected(true); 					//초기조건 : 랜덤


		ButtonGroup gsize = new ButtonGroup();			//공 크기 선택 그룹
		for(int i=0; i<size.length; i++){
			size[i] = new JRadioButton(sztext[i]);		//라디오 버튼에 텍스트 추가
			gsize.add(size[i]);							//버튼 그룹에 추가
			size[i].addItemListener(new SzListener());	//버튼 선택됐는지 알기 위해 리스너 달기
		}
		size[sz].setSelected(true); 					//초기조건 : 중


		//확인 버튼
		JButton ok = new JButton("확인");
		ok.setBackground(new Color(93,93,93));
		ok.setForeground(Color.white);
		
		//버튼 UI 설정
		ok.setUI(new StyledButtonUI());
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FollowMe.spset = sp;			//선택된 공 스피드로 설정
				FollowMe.szset = sz;			//선택된 공 사이즈로 설정
				setVisible(false);				
			}
		});
		
		//취소 버튼
		JButton cancel = new JButton("취소");
		cancel.setBackground(new Color(93,93,93));
		cancel.setForeground(Color.white);
		
		//버튼 UI 설정
		cancel.setUI(new StyledButtonUI());
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);				
			}
		});


		//공 크기 텍스트와 라디오 버튼 위치지정
		csize.setLocation(135,20);
		csize.setSize(200,30);
		size[0].setLocation(55,50);
		size[0].setSize(75,30);
		size[1].setLocation(185,50);
		size[1].setSize(75,30);

		//공 속도 텍스트와 라디오 버튼 위치지정
		cspeed.setLocation(135,120);
		cspeed.setSize(200,30);
		for(int i=0; i<speed.length; i++){
			speed[i].setLocation(20+70*i,150);
			speed[i].setSize(60,30);
		}

		//확인 취소 버튼 위치 지정
		ok.setLocation(55,220);
		ok.setSize(75,30);
		cancel.setLocation(185,220);
		cancel.setSize(75,30);

		//버튼 추가
		cp.add(csize);
		cp.add(cspeed);
		cp.add(ok);
		cp.add(cancel);
		for(int i=0; i<size.length; i++){
			cp.add(size[i]);
		}
		for(int i=0; i<speed.length; i++){
			cp.add(speed[i]);
		}

		setSize(350,300);
		setVisible(true);
		setResizable(false);				//창 크기를 못 늘리게 고정
	}

	class SpListener implements ItemListener {						//공 스피드 버튼 리스너
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //선택 해제된 경우 그냥 리턴
			
			if(speed[0].isSelected())
				sp = 0;
			else if(speed[1].isSelected())
				sp = 1;
			else if(speed[2].isSelected())
				sp = 2;
			else
				sp = 3;
		}
	}

	class SzListener implements ItemListener {						//공 사이즈 버튼 리스너
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //선택 해제된 경우 그냥 리턴
			
			if(size[0].isSelected())
				sz = 0;
			else
				sz = 1;
		}
	}
}

class StyledButtonUI extends BasicButtonUI {						//버튼 UI설정 메소드

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