package testUnit;


import hardware.cam.pvcam.PVCAM;
import hardware.cam.pvcam.PVCAMDriver;
import hardware.cam.pvcam.PVParam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TempPanel extends JFrame implements Runnable, ChangeListener, ActionListener{
	private static final long serialVersionUID = 5590927680353069205L;
	private PVCAMDriver cam;
	private int hcam;
	private JLabel temp;
	private JSpinner settemp;
	private boolean pause,stop;
	private JToggleButton pauseBut;
	
	private void construct(){
		this.setTitle("Temperature");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {

				// detruire tache
				System.out.println("bye bye");
				stop = true;
			//	System.exit(0);
				
			}
			});

		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		temp = new JLabel("0.0   °c");
		temp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		int[] mm = minMaxTmp();
		SpinnerModel model = new SpinnerNumberModel(0,mm[0],mm[1],1);
		settemp = new JSpinner(model);
		settemp.addChangeListener(this);
		pan.add(new JLabel("Current tmp: "));
		
		pan.add(temp);
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		pan.add(new JLabel("Set tmp: "));
		pan.add(settemp);
		pauseBut = new JToggleButton("Pause");
		pauseBut.addActionListener(this);
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		pan.add(pauseBut);
		this.getContentPane().add(pan);
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void run() {
		while (stop == false){
			if (pause == false){
				double tempr = cam.pl_get_param(hcam, PVParam.PARAM_TEMP, PVParam.ATTR_CURRENT, PVParam.TYPE_INT16);
				tempr = tempr / 100;
				temp.setText(String.valueOf(tempr));
			}
		}
		
	}

	public TempPanel(PVCAM pvcam){
		
		cam = pvcam.getCam();
		hcam = pvcam.getHcam();
		pause = false;
		stop = false;
		construct();
	}
	
	

	private int[] minMaxTmp(){
		int[] mm = new int[2];
		mm[0] = (int) (cam.pl_get_param(hcam, PVParam.PARAM_TEMP_SETPOINT, PVParam.ATTR_MIN, PVParam.TYPE_INT16) / 100);
		mm[1] = (int) (cam.pl_get_param(hcam, PVParam.PARAM_TEMP_SETPOINT, PVParam.ATTR_MAX, PVParam.TYPE_INT16) / 100);
		return mm;
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		int val = ((Integer) settemp.getModel().getValue()).intValue();
		val = val * 100;
		pause = true;
		cam.pl_set_param(hcam, PVParam.PARAM_TEMP_SETPOINT,val,PVParam.TYPE_INT16);
		pause = false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		pause = pauseBut.isSelected();
		
	}
	
}
