
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
//import java.util.*;

/**
 * 오늘의 목표달성치
 * @author YoungEun
 * percent1 = FollowMe 달성률, percent2 = Fifteen Dots 달성률
 */

class Graph extends JFrame{
	Container c;
	double percent1 = 100 * (double)MainFrame.FMC/MainFrame.FM;				 
	double percent2 = 100 * (double)MainFrame.FDC/MainFrame.FD;			

	/**
	 * 그래프의 위치와 크기 설정
	 */
	Graph(){	 															
		setTitle("오늘 목표달성률(%)");											
		c = getContentPane();												
		c.setLayout(null);													

		JLabel FM = new JLabel("Follow Me");								
		JLabel CD = new JLabel("Fifteen Dots");								

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
		setResizable(false);										
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
	}

	public class CircleGraph extends JPanel{
		int width, height;											
		double value;													
		float r;														
		double angle;												
		Color lineColor;											
		Color arcColor;												

		Ellipse2D.Float outCircle;										
		Ellipse2D.Float outFillCircle;								

		Arc2D.Float inArc;												

		int stringX, stringY;											

		public CircleGraph(double value){							
			this(100,100,value, 1);
		}
		
		/**
		 * 운동별 그래프 설정
		 * @param ex - 운동종류 (1 = FollowMe, 2 = Fifteen Dots)
		 */
		public CircleGraph(int width, int height, double value, int ex){ 	
																			
			this.width = width;
			this.height = height;
			this.r = (width > height)? width/2f: height/2f;
			this.lineColor = Color.black;								
																			
			if(ex==1){												
				this.arcColor = new Color(255, 0, 0, 50); 			
			}
			else{														
				this.arcColor = new Color(0, 0, 255, 50); 			
			}

			this.outCircle = new Ellipse2D.Float(1,1,2*r,2*r);			
			this.outFillCircle = new Ellipse2D.Float((int)outCircle.getX()+1,(int)outCircle.getY()+1,(int)outCircle.getWidth()-1,(int)outCircle.getHeight()-1);	// 그래프 채우기 객체 생성, outcircle보다 직경이 작아야지 외곽선이 보임

			setValue(value);									
		}

		/**
		 * 퍼센트 값 설정(0이하여도 0으로 되게 설정, 100넘어도 100으로 되게 설정)
		 * @param value - 퍼센트 값
		 */
		public void setValue(double value){
			if(value > 100) value=100;							
			if(value < 0) value = 0;								

			this.value = value;

			this.angle = 3.6 * value;									
			inArc = new Arc2D.Float(2,2,2*r-1,2*r-1,0,(int)angle,2);		
			repaint();													
		}

		public double getValue(){
			return this.value;
		}

		public void paintComponent(Graphics g){	
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D)g;	

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

			g2.setStroke(new BasicStroke(1));						

			g2.draw(outCircle);										

			g.setColor(arcColor);									
			
			g2.fill(inArc);										

			FontMetrics fm = g.getFontMetrics();
			int offX = fm.stringWidth(value+"%") /3;

			stringX = (int)(Math.cos(Math.toRadians(angle/2d))*(r/2)) + (int)r - offX;				
			stringY = (int)(Math.sin(Math.toRadians(angle/2d))*(r/2)) * -1 + (int)r + 5;		

			g.setColor(Color.black);								

			g2.drawString(String.format("%.2f", value)+"%",stringX,stringY);
		}
		public Dimension getPreferredSize(){						
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
