// 03. 오늘 목표달성치
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
//import java.util.*;

class Graph extends JFrame{
	Container c;
	double percent1 = 100 * (double)MainFrame.FMC/MainFrame.FM;		// Follow Me 퍼센트 
	double percent2 = 100 * (double)MainFrame.FDC/MainFrame.FD;		// Fifteen Dots 퍼센트

	Graph(){	 							// percent1 = FollowMe 달성률, percent2 = Fifteen Dots 달성률
		setTitle("오늘 목표달성률(%)");					// 창이름 설정
		c = getContentPane();						// 
		c.setLayout(null);

		JLabel FM = new JLabel("Follow Me");
		JLabel CD = new JLabel("Fiteen Dots");

		CircleGraph FollowMe = new CircleGraph(250, 250, percent1, 1);
		CircleGraph Card = new CircleGraph(250, 250, percent2, 2);

		FollowMe.setLocation(40,50);
		FollowMe.setSize(260,260);
		Card.setLocation(340,50);
		Card.setSize(260,260);
		FM.setLocation(135,310);
		FM.setSize(70,40);
		CD.setLocation(435,310);
		CD.setSize(70,40);

		c.add(FollowMe);
		c.add(Card);
		c.add(FM);
		c.add(CD);
		setSize(640,460);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);						//창 크기를 못 늘리게 고정
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public class CircleGraph extends JPanel{
		int width, height;						//원 가로 세로 크기
		double value;							//%값
		float r;							//반지름 길이
		double angle;							//각	
		Color lineColor;						//테두리 컬러
		Color arcColor;							//호 컬러

		Ellipse2D.Float outCircle;					//외곽 테두리
		Ellipse2D.Float outFillCircle;					//내부 원 영역

		Arc2D.Float inArc;						//호 영역

		int stringX, stringY;						//값 출력 위치

		//원크기 디폴트
		public CircleGraph(double value){
			this(100,100,value, 1);
		}
		public CircleGraph(int width, int height, double value, int ex){ 	
			//ex = 운동종류 (1 = FollowMe, 2 = Fifteen Dots
			this.width = width;
			this.height = height;
			this.r = (width > height)? width/2f: height/2f;
			this.lineColor = Color.black;
			if(ex==1){
				this.arcColor = new Color(255, 0, 0, 50); 			//알파 = 색 명암 조절
			}
			else{
				this.arcColor = new Color(0, 0, 255, 50); 			//알파 = 색 명암 조절
			}

			this.outCircle = new Ellipse2D.Float(1,1,2*r,2*r);	//Ellipse2D(x, y, 직경, 직경)
			this.outFillCircle = new Ellipse2D.Float((int)outCircle.getX()+1,(int)outCircle.getY()+1,(int)outCircle.getWidth()-1,(int)outCircle.getHeight()-1);
			//outcircle보다 직경이 작아야지 외곽선이 보임
			setValue(value);
		}

		//퍼센트값 설정 (100넘어도 100으로 되게 설정)
		public void setValue(double value){
			if(value > 100) value=100;
			if(value < 0) value = 0;

			this.value = value;

			this.angle = 3.6 * value;		//1%당 3.6도 <-(360/100)
			inArc = new Arc2D.Float(2,2,2*r-1,2*r-1,0,(int)angle,2);
			repaint();
		}

		public double getValue(){
			return this.value;
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D)g;

			//안티앨리어싱 활성화 (계단현상 제거하기. 즉 원이 사각형모양으로 삐뚤삐뚤 삐져나오는거 제거)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//선두께 1로 설정
			g2.setStroke(new BasicStroke(1));

			//외각원 그리기
			g2.draw(outCircle);

			g.setColor(arcColor);
			g2.fill(inArc);

			FontMetrics fm = g.getFontMetrics();
			int offX = fm.stringWidth(value+"%") /3;

			//삼각함수를 통하여 해당되는 각도의 x,y 좌표 구함
			stringX = (int)(Math.cos(Math.toRadians(angle/2d))*(r/2)) + (int)r - offX;
			stringY = (int)(Math.sin(Math.toRadians(angle/2d))*(r/2)) * -1 + (int)r + 5;

			//값 출력 색상
			g.setColor(Color.black);

			//값(문자열) 출력
			g2.drawString(String.format("%.2f", value)+"%",stringX,stringY);
		}
		public Dimension getPreferredSize(){	//컴포넌트의 폭과 크기 정하기
			return new Dimension(width+4,height+4);
		}
		public Dimension getMinimumSize(){
			return getPreferredSize();
		}
		public Dimension getMaximumSize(){
			return getPreferredSize();
		}
	}
}

