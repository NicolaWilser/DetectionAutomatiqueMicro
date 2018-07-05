package gui;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;


import controllers.SystemBuilder;


public class Main extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5951095275124787751L;
	private SystemBuilder systemb;
	
	
	public Main(){
		this.setTitle("SwingApplication");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				
				systemb.destroySystem();
				System.exit(0);
			}
			});

	//	systemb = new SystemBuilder();
		
		MainBoard brd = new MainBoard(null,null,null);
		
		Container pan = this.getContentPane();
		pan.add(brd);
	}
	
	
	
	public static void main(String arg[]){
		Main wind = new Main();
		wind.pack();
		wind.setVisible(true);
		
	}
	
	
	
}
