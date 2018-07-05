package gui;

import hardware.shutter.ShutterDriver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controllers.ServiceProvider;


public class MainBoard extends JPanel{

private static final long serialVersionUID = 5350911515127744315L;
private JButton opensh, closesh,acquier, cancel,saveb;
private JCheckBox externalCnt, saveOption;
private JToggleButton liveBut, thresBut;
private JLabel pathl;
private ShutterDriver shutter;
private LivePanel panel;
private ServiceProvider cntrl;

private JSpinner thershold, exposure;
private JProgressBar localBar, globalBar;



private class shutterListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JButton but = (JButton) e.getSource();
		if (but.equals(opensh)){
			shutter.openShutter();
		} else {
			shutter.closeShutter();
		}	
	}
}

private class ToggleButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		JToggleButton butt = (JToggleButton)e.getSource();
		if (butt.equals(liveBut)){
			if (butt.isSelected()){
				panel.start();
			} else {
				panel.stop();
			}
		}
		if (butt.equals(thresBut)){
			panel.activateThreshold(butt.isSelected());
		}	
	}
}

private void setPath(){
	File path = null;
	JFileChooser chooser = new JFileChooser(); 
	chooser.setCurrentDirectory(new java.io.File("."));
	chooser.setDialogTitle("choose Dir");
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	chooser.setAcceptAllFileFilterUsed(false);

	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
	    path  =  chooser.getSelectedFile();
	    pathl.setText(path.getAbsolutePath());
	}
}


private void startAcqInit(){
	if (shutter.getDeviceStatus()){
		shutter.closeShutter();
		
	}
	if (panel.isRunnig()){
		liveBut.setSelected(false);
		panel.stop();
	}
}

private class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		JButton butt = (JButton) e.getSource();
		if (butt.equals(acquier)){
			if (saveOption.isSelected()){
				if (pathl.getText().equalsIgnoreCase("not set")){
					setPath();
				}
			}
			
			startAcqInit();  /************************* updated******************************/
			
			cntrl.aqcuiereAllData(localBar,globalBar,saveOption.isSelected(),pathl.getText());
		}
		if (butt.equals(saveb)){
			setPath();
		}
		if (butt.equals(cancel)){
			cntrl.stopEmergency();	
		}
	}
		
}
	

private class SpinnerListener implements ChangeListener{
	public void stateChanged(ChangeEvent e) {

		JSpinner spinner = (JSpinner) e.getSource();
		SpinnerModel model = spinner.getModel();
		int val = ((Integer) model.getValue()).intValue();
		
		if (spinner.equals(exposure)){
			panel.setExposure(val);
		} else {
			panel.setThreshold(val);
		}
		
	}
	
}



public MainBoard(ShutterDriver shd, LivePanel pnl, ServiceProvider dm) {
	shutter = shd;
	panel = pnl;
	cntrl = dm;
	
	this.add(makeShutterControl());
	this.add(Box.createRigidArea(new Dimension(2,10)));
	this.add(getLiveControlPanel());
	this.add(Box.createRigidArea(new Dimension(2,10)));
	this.add(mainControlPanel());
	this.add(Box.createRigidArea(new Dimension(2,10)));
	this.add(progressPanel());
	this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
	}

private JPanel progressPanel(){
	localBar = new JProgressBar();
	globalBar = new JProgressBar();
	
	localBar.setStringPainted(true);
	globalBar.setStringPainted(true);
	
	localBar.setBorder(new CompoundBorder(new EmptyBorder(0,4,3,4), localBar.getBorder()));
	globalBar.setBorder(new CompoundBorder(new EmptyBorder(0,4,0,4), localBar.getBorder()));
	JPanel panel = new JPanel();
	panel.add(new JLabel("Global Progress"));
	panel.add(globalBar);
	panel.add(new JLabel("Current Task Progress"));
	panel.add(localBar);
	panel.setBorder(new CompoundBorder ( new EmptyBorder(0,4,2,4), BorderFactory.createLineBorder(Color.black)));
	panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
	
	return panel;
	
}

private JPanel mainControlPanel(){
	//JPanel panel = new JPanel(new SpringLayout());
	
	acquier = new JButton("Start Acquiering Data");
	cancel = new JButton("Emergency Stop");
	
	saveOption = new JCheckBox("AutoSave data");
	saveb = new JButton("Browse");
	ButtonListener bls = new ButtonListener();
	
	acquier.addActionListener(bls);
	cancel.addActionListener(bls);
	saveb.addActionListener(bls);
	
	pathl = new JLabel("not set");
	pathl.setPreferredSize(new Dimension(400,25));
	pathl.setBorder(new CompoundBorder ( new EmptyBorder(0,1,0,1), BorderFactory.createLineBorder(Color.black)));
	
	JPanel l1 = new JPanel();
	JPanel l2 = new JPanel();
	JPanel l3 = new JPanel();
	l3.setLayout(new BoxLayout(l3,BoxLayout.PAGE_AXIS));
	
	l1.add(acquier);
	l1.add(Box.createRigidArea(new Dimension(50,10)));
	l1.add(cancel);
	l2.add(saveOption);
	l2.add(Box.createRigidArea(new Dimension(20,10)));
	l2.add(saveb);
	l2.add(pathl);
	
	l1.setBorder(new CompoundBorder ( new EmptyBorder(0,4,2,4), BorderFactory.createLineBorder(Color.black)));
	l2.setBorder(new CompoundBorder ( new EmptyBorder(2,4,0,4), BorderFactory.createLineBorder(Color.black)));

	l3.add(l1);
	l3.add(l2);
	
//	l3.setBorder(new CompoundBorder ( new EmptyBorder(0,4,0,4), BorderFactory.createLineBorder(Color.black)));
	return l3;
}

private JPanel makeShutterControl(){
	shutterListener listener = new shutterListener();
	JPanel panel = new JPanel();
	opensh = new JButton("Open Shutter");
	opensh.addActionListener(listener);
	closesh = new JButton("Close Shutter");
	closesh.addActionListener(listener);
	externalCnt = new JCheckBox("Activate External control");
	externalCnt.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent e) {
			boolean stc =  externalCnt.isSelected();
			opensh.setEnabled(!stc);
			closesh.setEnabled(!stc);
			shutter.setExternalControl(stc);
		}
	});
	
	panel.add(opensh);
	panel.add(closesh);
	panel.add(externalCnt);
	panel.setBorder(new CompoundBorder ( new EmptyBorder(0,4,0,4), BorderFactory.createLineBorder(Color.black)));
	
	if (shutter.getDeviceStatus() == false){
		SpringUtilities.disableIt(panel,shutter.getDeviceStatus());
	}

	return panel;
	
}

private JPanel getLiveControlPanel(){
	
	liveBut = new JToggleButton("Preview", false);
	thresBut = new JToggleButton("Threshold",false);
	
	ToggleButtonListener tgl = new ToggleButtonListener();
	liveBut.addActionListener(tgl);
	thresBut.addActionListener(tgl);

	SpinnerModel expoModel = new SpinnerNumberModel(20,5,60000,10);
	SpinnerModel thrModel = new SpinnerNumberModel(4090,1,65535,5);
	
	SpinnerListener scl = new SpinnerListener();
	thershold = new JSpinner(thrModel);
	exposure = new JSpinner(expoModel);
	
	thershold.addChangeListener(scl);
	exposure.addChangeListener(scl);
	
	
	JPanel panel = new JPanel();
	
	panel.add(liveBut);
	panel.add(new JLabel("Exposure (ms): "));
	panel.add(exposure);
	panel.add(thresBut);
	panel.add(new JLabel("Saturation: "));
	panel.add(thershold);
	panel.setBorder(new CompoundBorder ( new EmptyBorder(0,4,0,4), BorderFactory.createLineBorder(Color.black)));
	return panel;

}



}
