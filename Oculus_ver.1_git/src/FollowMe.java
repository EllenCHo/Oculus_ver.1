// 05. �ȱ��̵��� � : �����̴� �� Ʈ���̴�

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class FollowMe extends JFrame implements Runnable, WindowListener{
	public static int spset = 3;		//�� ���ǵ� ����Ʈ ��(����)
	public static int szset = 0;		//�� ũ�� ����Ʈ ��(��)
	Setting setting;					//����â
	Container contentPane;
	boolean flag;	
	JButton btn; 						//�� ���� ��ư
	HowToUse htu1;						//���� ����
	
	Color color = new Color(206,247,110);							//��׶��� ���� ����
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); 	//��üȭ�� ������ ��������

	String [] sptext = {"��", "��", "��", "����"};
	String [] sztext = {"��", "��"};
	
	int speed1[]= {res.width/350, res.width/250, res.width/150};			 					//âũ�⿡ ���� �� ���ǵ�(��, ��, ��)
	int speed2[] = {res.width/350, res.width/250, res.width/200, res.width/50};					//âũ�⿡ ���� ���� �� ���ǵ�
	int size[] = {res.width/15, res.width/20};													//��ǻ�� â ũ�� ���� ������� �� ũ��(��, ��)

	//�������̽��� ��������Ƿ� �� �����ε��ؾ���
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		finish();									//â ������ ī��Ʈ�����ʰ� ����
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

	FollowMe() {
		super("FolloFollow Me");
		contentPane = getContentPane();
		contentPane.setLayout(null); 								//��ġ������ ����
		contentPane.setBackground(color);							//��׶��� ���� ����
		DrawCircle panel = new DrawCircle();					//���� �����̴� �г� ����
		panel.setSize(res.width-100,res.height); 					//���� ����
		panel.setBackground(color);									//�г� ��׶��� ���� ����
		contentPane.add(panel); 									// �г��� ����Ʈ�ҿ� ����
		this.addWindowListener(this);

		flag = false;		

		btn = new JButton("�޴�"); 									//��ư �����

		btn.setSize(50,50);											//��ư ������ ����
		btn.setLocation(res.width-70,30);							//��ư ��ġ ����
		contentPane.add(btn);

		btn.addActionListener(new ActionListener() {				//��ư ���콺 ������ �߰�
			public void actionPerformed(ActionEvent e){
				setting.setVisible(true);							//���� â ���̱�
			}
		});

		setting = new Setting(this, "����");							//����â ����
		setting.setLocationRelativeTo(null);						//����â�� ȭ�� �߾ӿ� �������� ����

		setSize(res.width, res.height);								//����� ũ�⸸ŭ â ũ�� ����
		setVisible(true);

		htu1 = new HowToUse(1);										//��� ���� ����

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	void finish(){
		flag = true;
	}

	public void run(){
		int k = -10;
		while(true){
			try{
				//Thread.sleep(100);   // ������
				Thread.sleep(1000);   // 60�� �� ����

				if(flag == true){
					htu1.finish();			//���� �ݱ�
					dispose();				//Follow Me â �ݱ�
					return;
				}
			}catch(InterruptedException e){
				System.err.println(e);
				return;
			}

			k++;
			
			if(k == -5)
				htu1.finish();			//5�ʰ� ������ ���� ������� �ϱ�

			if(k == 60){				//��� ������ ��� ī��Ʈ
				MainFrame.FMC++;
				if(MainFrame.FMC > MainFrame.FM) MainFrame.FMC = MainFrame.FM;		//ī��Ʈ�� ������ ��ǥ�� �� ���� ���ϵ��� ����
				Save.SaveNow();			//��� ����
				
				dispose();				//Follow Me â �ݱ�
				return;
			}
		}
	}

	public class DrawCircle extends JPanel {						//���� �����̴� �г��� ������ Ŭ����
		private int xPos=200; 				//x ��ǥ
		private int yPos=100; 				//y ��ǥ

		int speedX; 						//x��ǥ���� ���ǵ�
		int speedY; 						//y��ǥ���� ���ǵ�
		int speedx;							//x��ǥ�� ���� ���ǵ� ����
		int speedy;							//y��ǥ�� ���� ���ǵ� ����

		ImageIcon icon = new ImageIcon("image\\RYAN.png");			//�� �̹��� ����
		Image img = icon.getImage();

		boolean play = true;

		public DrawCircle() {
			speedX = speedY = 10;			//�ʱ� ���ǵ� 10
			try {
				new Thread(){
					public void run(){
						while(play){
							Random rd = new Random();
							//Random.nextInt():int���� ������ �߻�
							//Random.nextInt(int n) : 0�� (n-1)������ int���� ������ �߻��Ѵ�.

							int x_dir = rd.nextInt(2);  			//0~1 ���� �߻� (���ǵ带 ������ ���� ����� ���� �������� �ϱ�����)
							int y_dir = rd.nextInt(2);

							int x_rnd = rd.nextInt(4);				//0~3 ���� �߻� (���� ���ǵ带 ����� ��� ���ǵ带 �ٲٱ� ����)
							int y_rnd = rd.nextInt(4);
							
							if(spset <3){							//��, ��, ���� ����� ���
								speedx= speed1[spset];
								speedy= speed1[spset];
							}

							if(spset ==3){							//������ ����� ���
								speedx = speed2[x_rnd];
								speedy = speed2[y_rnd];
							}

							xPos = xPos + speedX; 					//x��ǥ �̵�
							yPos = yPos + speedY; 					//y��ǥ �̵�

							if(xPos + size[szset] >=DrawCircle.this.getWidth() ){
								//�г� â�� ���� ������Ƿ� x��ǥ�� �������� ������ �ӵ��� ������ ����
								speedX = -speedx;
								speedY = (y_dir == 0 ? speedy : -speedy);	//y_dir�� 0�̸� y������ �״��
							}

							if( yPos+ size[szset] >=DrawCircle.this.getHeight()){
								//�г� â�� ���� ������Ƿ� y��ǥ�� �Ʒ������� ������ �ӵ��� ������ ����
								speedY = -speedy;
								speedX = (x_dir == 0 ? speedx : -speedx);	//x_dir�� 0�̸� x������ �״��
							}

							if(xPos < 0){
								//�г� â�� ���� ������Ƿ� x��ǥ�� ���������� ������ �ӵ��� ����� ����
								speedX = speedx;
								speedY = (y_dir == 0 ? speedy : -speedy);
							}

							if(yPos < 0){
								//�г� â�� ���� ������Ƿ� y��ǥ�� �������� ������ �ӵ��� ����� ����
								speedY = speedy;
								speedX = (x_dir == 0 ? speedx : -speedx);
							}

							repaint(); 										//���� �����̱⶧���� �ٽ� �׸���
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

		public void paintComponent(Graphics g) {									//�� �׸���
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;

			g.drawString("�� ũ�� : "+sztext[szset], res.width-170, 50);				//���� ���� ������ �г� ���� ���ʿ� �׸���
			g.drawString("�� �ӵ� : "+sptext[spset], res.width-170, 70);

			g2d.drawImage(img, xPos, yPos, size[szset], size[szset], this);			//x, y ��ġ�� size ��ŭ�� �̹��� �׸���
		}
	}
}


class Setting extends JDialog {												//���� Ŭ����
	Container cp;

	int sp=3;				//�� ���ǵ� �ʱⰪ : ����
	int sz=0;				//�� ũ�� �ʱⰪ : ��

	JLabel csize = new JLabel("�� ũ��");
	JLabel cspeed = new JLabel("�� �ӵ�");

	JRadioButton [] speed = new JRadioButton[4];		//�� ���ǵ� ���� ��ư ����
	JRadioButton [] size = new JRadioButton[2];			//�� ũ�� ���̵� ��ư ����

	String [] sptext = {"��", "��", "��", "����"};
	String [] sztext = {"��", "��"};

	public Setting(JFrame frame, String title) {
		super(frame, title);

		cp = getContentPane();
		cp.setLayout(null);

		
		ButtonGroup gspeed = new ButtonGroup();			//�ӵ� ���� �׷�
		for(int i=0; i<speed.length; i++){
			speed[i] = new JRadioButton(sptext[i]);		//���� ��ư�� �ؽ�Ʈ �߰�
			gspeed.add(speed[i]);						//��ư �׷쿡 �߰�
			speed[i].addItemListener(new SpListener()); //��ư ���õƴ��� �˱� ���� ������ �ޱ�
		}
		speed[sp].setSelected(true); 					//�ʱ����� : ����


		ButtonGroup gsize = new ButtonGroup();			//�� ũ�� ���� �׷�
		for(int i=0; i<size.length; i++){
			size[i] = new JRadioButton(sztext[i]);		//���� ��ư�� �ؽ�Ʈ �߰�
			gsize.add(size[i]);							//��ư �׷쿡 �߰�
			size[i].addItemListener(new SzListener());	//��ư ���õƴ��� �˱� ���� ������ �ޱ�
		}
		size[sz].setSelected(true); 					//�ʱ����� : ��


		//Ȯ�� ��ư
		JButton ok = new JButton("Ȯ��");
		ok.setBackground(new Color(93,93,93));
		ok.setForeground(Color.white);
		
		//��ư UI ����
		ok.setUI(new StyledButtonUI());
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FollowMe.spset = sp;			//���õ� �� ���ǵ�� ����
				FollowMe.szset = sz;			//���õ� �� ������� ����
				setVisible(false);				
			}
		});
		
		//��� ��ư
		JButton cancel = new JButton("���");
		cancel.setBackground(new Color(93,93,93));
		cancel.setForeground(Color.white);
		
		//��ư UI ����
		cancel.setUI(new StyledButtonUI());
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);				
			}
		});


		//�� ũ�� �ؽ�Ʈ�� ���� ��ư ��ġ����
		csize.setLocation(135,20);
		csize.setSize(200,30);
		size[0].setLocation(55,50);
		size[0].setSize(75,30);
		size[1].setLocation(185,50);
		size[1].setSize(75,30);

		//�� �ӵ� �ؽ�Ʈ�� ���� ��ư ��ġ����
		cspeed.setLocation(135,120);
		cspeed.setSize(200,30);
		for(int i=0; i<speed.length; i++){
			speed[i].setLocation(20+70*i,150);
			speed[i].setSize(60,30);
		}

		//Ȯ�� ��� ��ư ��ġ ����
		ok.setLocation(55,220);
		ok.setSize(75,30);
		cancel.setLocation(185,220);
		cancel.setSize(75,30);

		//��ư �߰�
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
		setResizable(false);				//â ũ�⸦ �� �ø��� ����
	}

	class SpListener implements ItemListener {						//�� ���ǵ� ��ư ������
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //���� ������ ��� �׳� ����
			
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

	class SzListener implements ItemListener {						//�� ������ ��ư ������
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; //���� ������ ��� �׳� ����
			
			if(size[0].isSelected())
				sz = 0;
			else
				sz = 1;
		}
	}
}

class StyledButtonUI extends BasicButtonUI {						//��ư UI���� �޼ҵ�

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