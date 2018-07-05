
import java.awt.event.WindowEvent;
import controllers.SystemBuilder;
import ij.IJ;
import ij.plugin.PlugIn;
import ij.plugin.frame.PlugInFrame;


public class Olympus_3D extends PlugInFrame implements PlugIn{

		public static final long serialVersionUID = 1234;
		private SystemBuilder systemb;
		
		public Olympus_3D() {
			super("My Plugin Frame");
		}
		
		
		
		public void run(String arg) {
			this.setTitle("SwingApplication");
			systemb = new SystemBuilder();
			this.add(systemb.buildSystem());
			this.pack();
			this.setVisible(true);
		}
		
		@Override
		public void windowClosing(WindowEvent e) {

				
				boolean rep = IJ.showMessageWithCancel("", "close?");
			
		    	if (rep) {
		    		systemb.destroySystem();
		    		super.windowClosing(e);
		    	}
		    		
		 }
		
		
	}

