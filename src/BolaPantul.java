/**
 * @(#)BolaPantul.java
 *
 * BolaPantul Applet application
 *
 * @author nObeLLeon
 * @version 1.00 2020/6/6
 ***********************************************  Program Successful  *******************************************************************
 */
 
import java.awt.*;
import java.applet.*;

class BolaPantul0 {
	
	int width, height;
	static final int diameter = 30;
	double p, q, pinc, qinc;
	Color warna;
	Graphics g;
	
	public BolaPantul0(int w, int h, int p, int q, double pinc, double qinc, Color c){
		
		width = w;
		height = h;
		this.p = p;
		this.q = q;
		this.pinc = pinc;
		this.qinc = qinc;
		
	}
	
	public void move() {
		
		p += pinc;
		q += qinc;
		
		// Ketika bola mengenai tepi applet, bola tersebut akan dipantulkan kembali
		if(p < 0 || p > width-diameter){
			pinc =- pinc;
			p += pinc;
			
		}
		if(q < 0 || q > height - diameter){
			qinc =- qinc;
			q += qinc;
		}
	}

	public void paint(Graphics gr) {
		
		g = gr;
		g.setColor(warna);
		
		// Koordinat dalam fillOval harus bertipe integer, harus didefinisikan secara
		// eksplisit dari double ke integer
		g.fillOval((int)p, (int)q, diameter, diameter); 
		
	}
}

public class BolaPantul extends Applet implements Runnable{
	
	Thread lari;
	Image Buffer;
	Graphics gBuffer;
	BolaPantul0 bola[];
	static final int MAX = 9; // Jumlah bola 9
	
	public void init(){
		
		Buffer = createImage(size().width, size().height);
		gBuffer = Buffer.getGraphics();
		
		bola = new BolaPantul0[MAX];
		
		int w = size().width;
		int h = size().height;
		
		// Bola-bola ini mempunyai koordinat awal yg berbeda & warna yg berbeda
		// disertai dengan peningkatan kecepatan & perubahan arah
		
		bola[0] = new BolaPantul0(w, h, 50, 20, 1.5, 2.0, Color.red);
		bola[1] = new BolaPantul0(w, h, 60, 100, 2.0, -3.0, Color.green);
		bola[2] = new BolaPantul0(w, h, 15, 70, -2.0, -2.5, Color.blue);
		bola[3] = new BolaPantul0(w, h, 150, 60, -2.7, -2.0, Color.orange);
		bola[4] = new BolaPantul0(w, h, 210, 30, 2.2, -3.5, Color.yellow);
		bola[5] = new BolaPantul0(w, h, 30, 40, 2.5, 3.7, Color.gray);
		bola[6] = new BolaPantul0(w, h, 330, 70, 3.2, -4.5, Color.lightGray);  
		bola[7] = new BolaPantul0(w, h, 20, 90, 1.3, -4.5, Color.CYAN);
        bola[8] = new BolaPantul0(w, h, 230, 50, 3.5, -4.5, Color.MAGENTA);
		
	}
	
	public void start(){
		
		if (lari == null){
			lari = new Thread (this);
			lari.start();
		}
	}
	
	public void stop(){
		
		if (lari != null){
			lari.stop();
			lari = null;
		}
	}
	
	public void run(){
		
		while(true){
			// waktu tunda sebesar 15ms
			try{lari.sleep(15);}
			catch(Exception e){}
			
			// Menggerakkan bola
			for(int i=0; i<MAX; i++)
				bola[i].move();
			
			repaint();
		}
	}
	
	public void update(Graphics g){
		
		paint(g);
	}
	
	public void paint(Graphics g){
		
		// Membuat latar belakang berwarna hijau
		gBuffer.setColor(Color.green);
		gBuffer.fillRect(0, 0, size().width, size().height);
		gBuffer.setColor(Color.yellow);    
			for(int p=0; p<size().width; p+=50)
				gBuffer.drawLine(p, 0, p, size().height);
			
			for(int q=0; q<size().height; q+=50)
				gBuffer.drawLine(0, q, size().width, q);
				
			// Mewarnai bola
			for(int i=0; i<MAX; i++)
				bola[i].paint(gBuffer);
			
			g.drawImage(Buffer, 0, 0, this);
				
	}
} 