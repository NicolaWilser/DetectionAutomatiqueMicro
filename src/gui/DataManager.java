package gui;
import hardware.stage.Oasis4i;
import ij.IJ;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import dataStruct.AcquisitionData;
import dataStruct.FormatterLab;


public class DataManager extends JPanel {

	private static final long serialVersionUID = -4027067081761732123L;

	private JButton newd, reset, setMax,setMin,getStp;
	
	private JButton add,remove,clear, save, load;

	
	private JSpinner stp, expo, expo2D, tempo, takes;
	
	private JCheckBox defExp, defExp2D;
	private JLabel cnt,min,max, curent;
	private JComboBox <String> exitation, emission, exitation2D, emission2D;
	private FormatterLab formatz = new FormatterLab(2,2);
	
	
	private AcquTree treePanel;
	private JTabbedPane mode;
	
	private Oasis4i stage;
	private LivePanel live;
	
	private AcquisitionData tempoData;
	
	
	public AcquisitionData[] getTreeWorkLine(){
		return treePanel.getWorkLine();
	}
	
	
	public DataManager(Oasis4i stage, LivePanel live){
		this.stage = stage;
		this.live = live;
		createGUI();
		
		
	}
	
	private void activeCompoments(boolean status){
		
		mode.setEnabled(status);
		
		
		
		setMax.setEnabled(status && stage.status);
		setMin.setEnabled(status && stage.status);
		getStp.setEnabled(status);
		stp.setEnabled(status);
		expo.setEnabled(status);
		expo2D.setEnabled(status);
		tempo.setEnabled(status);
		takes.setEnabled(status);
		defExp.setEnabled(status);
		defExp2D.setEnabled(status);
		exitation.setEnabled(status);
		emission.setEnabled(status);
		exitation2D.setEnabled(status);
		emission2D.setEnabled(status);
		add.setEnabled(status);
		save.setEnabled(status);
		load.setEnabled(status);
		
	}
	
private class MainListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(newd)){
				
				activeCompoments(true);
				double[] pos = stage.OI_ReadXYZ();
				
				String s = (String)JOptionPane.showInputDialog("Enter Acquisition name","AQ" + formatz.format(pos[0]) + "-" + formatz.format(pos[1]) + "-" + formatz.format(pos[2]));
				if (s == null){
					activeCompoments(false);
				} else {
					tempoData = new AcquisitionData(s,pos);
					curent.setText(s);
				}
			
			} else {

				tempo.setValue(100);
				takes.setValue(1);
				expo2D.setValue(10);
				exitation2D.setSelectedIndex(1);
				emission2D.setSelectedIndex(2);
				
				min.setText(formatz.format(0));
				max.setText(formatz.format(0));
				stp.setValue(0.25);
				cnt.setText("Bounds not set");
				expo.setValue(10);
				defExp.setSelected(true);
				exitation.setSelectedIndex(1);
				emission.setSelectedIndex(2);
				curent.setText("XX - YY - ZZ");
				activeCompoments(false);
			}
			
		}
		
}


private void calculatePlans(){
	double posMin = Double.valueOf(min.getText()).doubleValue();
	double posMax = Double.valueOf(max.getText()).doubleValue();
	
	if (posMin > posMax){
		double tmp = posMin;
		posMin = posMax;
		posMax = tmp;
	}
	
	double stepv =  ((Double) stp.getModel().getValue()).doubleValue();
	
	int res = (int) Math.floor((posMax - posMin) / stepv);
	
	if ((posMin + (res * stepv)) < posMax){
		res = res + 2;
	} else {
		res = res + 1;
	}

	cnt.setText(String.valueOf(res));
	
}



private class Listener3D implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		
		JButton src = (JButton) e.getSource();
		if (src.equals(setMin)){
			double v = stage.OI_ReadZ();
			min.setText(formatz.format(v));
			calculatePlans();
		}		
		
		if (src.equals(setMax)){
			double v = stage.OI_ReadZ();
			max.setText(formatz.format(v));
			calculatePlans();

		}	
		
		if (src.equals(getStp)){
			stp.setValue(stage.passStep);
			calculatePlans();
		}	
		
	}
	
}
private class panelManageListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src.equals(add)){
			
			
			 if(mode.getSelectedIndex() == 1){
				
				
				tempoData.setIs2d(true);
				tempoData.setRoi(live.getDrawedRoi());
				tempoData.setNum2d(((Integer)takes.getValue()).intValue());
				tempoData.setTempo(((Integer)tempo.getValue()).intValue());
				if (defExp2D.isSelected()){
					//tempoData.setExposure(cam.getExposure());
					tempoData.setExposure(live.getExposure());
				} else {
					tempoData.setExposure(((Integer)expo2D.getValue()).intValue());
				}

				tempoData.setExcitation((String)exitation2D.getSelectedItem());
				tempoData.setEmission((String)emission2D.getSelectedItem());
			} else {
				
				tempoData.setIs2d(false);
				tempoData.setRoi(live.getDrawedRoi());
				if (defExp.isSelected()){
					//tempoData.setExposure(cam.getExposure());
					tempoData.setExposure(live.getExposure());
				} else {
					tempoData.setExposure(((Integer)expo.getValue()).intValue());
				}

				tempoData.setExcitation((String)exitation.getSelectedItem());
				tempoData.setEmission((String)emission.getSelectedItem());
				
				double minv = Double.valueOf(min.getText()).doubleValue();
				double maxv = Double.valueOf(max.getText()).doubleValue();
				
				if (minv > maxv){
					IJ.showMessage("Warning","Min is bigger then Max ! values will be swapped");
					double tmp = minv;
					minv = maxv;
					maxv = tmp;
				}
				
				
				tempoData.setzMax(maxv);
				tempoData.setzMin(minv);
				tempoData.setStep(((Double)stp.getValue()).doubleValue());
			}
			
			treePanel.addAData(tempoData, true);
			curent.setText("XX - YY - ZZ");
			activeCompoments(false);
			
		}
		
		if (src.equals(remove)){
			
			treePanel.removeSelectedData();
		}

		if (src.equals(clear)){

			treePanel.clearAll();
		}
		if (src.equals(save) || src.equals(load)){
			
			JFileChooser jf = new JFileChooser();
			FileFilter ff = new FileFilter(){
				public boolean accept(File f){
					if(f.isDirectory()) return true;
					else if(f.getName().endsWith(".dat")) return true;
						else return false;
				}
				public String getDescription(){
					return "Dat files";
				}
			};
			 
			jf.removeChoosableFileFilter(jf.getAcceptAllFileFilter());
			jf.setFileFilter(ff);
			
			
			if (src.equals(save)){
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH'h'mm)");
			     Date date = new Date();
			     String nm =  dateFormat.format(date);
				AcquisitionData[] data = treePanel.getWorkLine();
				if (data.length > 0){
					jf.setSelectedFile(new File(nm + ".dat"));
					jf.showSaveDialog(null);
					File name = jf.getSelectedFile();
					try {
						final ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(name, true));
						for (int i= 0; i < data.length; i++){
							fos.writeObject(data[i]);
						}
						fos.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			} else {
				boolean rep = IJ.showMessageWithCancel("Warning", "If the origins was tempered with since the 'save' date, the data will no longer be valid... click cancel to abort");
				if (rep == true){
					jf.showOpenDialog(null);
					File name2 = jf.getSelectedFile();
					try {
						final ObjectInputStream fos = new ObjectInputStream(new FileInputStream(name2));
						Object dataObj;
						while((dataObj = fos.readObject()) != null){
							AcquisitionData data = (AcquisitionData) dataObj;
							treePanel.addAData(data, true);
						}
						fos.close();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
				
			}
		}	
	}	
}
	
private JPanel panel2D(){
		
		
		JPanel line = new JPanel(new SpringLayout());
		
		SpinnerModel halt = new SpinnerNumberModel(100,0,86400000,10);
		JLabel stpl = new JLabel("wait in between (ms): ");
		tempo = new JSpinner(halt);
		
		JLabel nbp = new JLabel("Numbers of takes: ");
		SpinnerModel tk = new SpinnerNumberModel(1,1,10000,1);
		takes = new JSpinner(tk);

		JLabel expl = new JLabel("Exposure (ms)");
		SpinnerModel mexp = new SpinnerNumberModel(20,5,60000,5);
		expo2D = new JSpinner(mexp);
		defExp2D = new JCheckBox("Current Exposure");
		defExp.setSelected(true);
		
		line.add(nbp);
		line.add(takes);
		line.add(stpl);
		line.add(tempo);
		line.add(expl);
		line.add(expo2D);
		line.add(defExp2D);
		line.add(Box.createRigidArea(new Dimension(2, 10)));

		String[] exitationFilters = { "IR", "RED", "GREEN", "BLUE"};
		String[] emissionFilters = { "IR", "RED", "GREEN", "BLUE"};

		exitation2D = new JComboBox<String>(exitationFilters);
		exitation2D.setSelectedIndex(1);
		emission2D = new JComboBox<String>(emissionFilters);
		emission2D.setSelectedIndex(2);
		JLabel eml = new JLabel("Emission Filter");
		JLabel exl = new JLabel("        Excitation Filter");
		line.add(eml);
		line.add(emission2D);
		line.add(exl);
		line.add(exitation2D);
		
		
		SpringUtilities.makeCompactGrid(line,3,4,7,7,7,7);
		line.setBorder(new CompoundBorder ( new EmptyBorder(0,4,2,4), BorderFactory.createLineBorder(Color.black)));
		return line;
	}
	
	private JPanel panel3D(){
		Listener3D list3d = new Listener3D();
		setMin = new JButton("Set Min");
		min = new JLabel(formatz.format(0));
		setMin.addActionListener(list3d);
		setMax = new JButton("Set Max");
		setMax.addActionListener(list3d);
		max = new JLabel(formatz.format(0));
		min.setBorder(BorderFactory.createLineBorder(Color.black));
		max.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel line2 = new JPanel(new SpringLayout());
		
		SpinnerModel mStepZ = new SpinnerNumberModel(0.25,0.1,30,0.1);
		JLabel stpl = new JLabel("Z step (µm)");
		stp = new JSpinner(mStepZ);
		
		stp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			   calculatePlans();
			}
        });

		
		getStp = new JButton ("get current step");
		getStp.addActionListener(list3d);
		cnt = new JLabel("Bounds not set");
		cnt.setHorizontalAlignment(JLabel.CENTER);
		cnt.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel expl = new JLabel("Exposure (ms)");
		SpinnerModel mexp = new SpinnerNumberModel(20,5,60000,5);
		expo = new JSpinner(mexp);
		defExp = new JCheckBox("Current Exposure");
		defExp.setSelected(true);
		
		line2.add(setMin);
		line2.add(min);
		line2.add(setMax);
		line2.add(max);
		
		
		line2.add(stpl);
		line2.add(stp);
		line2.add(getStp);
		line2.add(cnt);
		line2.add(expl);
		line2.add(expo);
		line2.add(defExp);
		line2.add(Box.createRigidArea(new Dimension(2, 10)));

		String[] exitationFilters = { "IR", "RED", "GREEN", "BLUE"};
		String[] emissionFilters = { "IR", "RED", "GREEN", "BLUE"};

		exitation = new JComboBox<String>(exitationFilters);
		exitation.setSelectedIndex(1);
		emission = new JComboBox<String>(emissionFilters);
		emission.setSelectedIndex(2);
		JLabel eml = new JLabel("Emission Filter");
		JLabel exl = new JLabel("        Excitation Filter");
		line2.add(eml);
		line2.add(emission);
		line2.add(exl);
		line2.add(exitation);
		SpringUtilities.makeCompactGrid(line2,4,4,7,7,7,7);
		line2.setBorder(new CompoundBorder ( new EmptyBorder(0,4,2,4), BorderFactory.createLineBorder(Color.black)));
		return line2;
	}
	
	private JPanel control(){
		JPanel panel = new JPanel();
		add = new JButton("Add");
		remove = new JButton("Remove");
		clear = new JButton("Clear all");
		save = new JButton("Save");
		load = new JButton("Load");
		
		panelManageListener clist = new panelManageListener();
		
		add.addActionListener(clist);
		remove.addActionListener(clist);
		clear.addActionListener(clist);
		
		panel.add(add);
		panel.add(Box.createRigidArea(new Dimension(40, 20)));
		panel.add(remove);
		panel.add(Box.createRigidArea(new Dimension(40, 20)));
		panel.add(clear);
		panel.add(Box.createRigidArea(new Dimension(50, 20)));
		panel.add(save);
		panel.add(Box.createRigidArea(new Dimension(40, 20)));
		panel.add(load);
		
		panel.setBorder(new CompoundBorder ( new EmptyBorder(2,4,2,4), BorderFactory.createLineBorder(Color.black)));
		
		return panel;
		
	}
	

	
	public void createGUI(){
		
		
		JPanel panelc = new JPanel();
		
		MainListener listener = new MainListener();
		
		newd = new JButton("New Acquisition");
		newd.addActionListener(listener);
		reset = new JButton("Reset");
		reset.addActionListener(listener);
		curent = new JLabel("XX - YY - ZZ");
		curent.setPreferredSize(new Dimension(200,25));
		curent.setBorder(BorderFactory.createLineBorder(Color.black));	
		curent.setHorizontalAlignment(JLabel.CENTER);
		panelc.add(newd);
		panelc.add(Box.createRigidArea(new Dimension(20, 10)));
		panelc.add(curent);
		panelc.add(Box.createRigidArea(new Dimension(20, 10)));
		panelc.add(reset);
		panelc.setBorder(new CompoundBorder ( new EmptyBorder(0,4,2,4), BorderFactory.createLineBorder(Color.black)));
		
		
		this.add(panelc);
		
		mode = new JTabbedPane();
		
		mode.addTab("3D Acquisition",panel3D());
		mode.addTab("Time Laps", panel2D());

		this.add(Box.createRigidArea(new Dimension(2, 10)));
		
		this.add(mode);
		
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.add(control());
		treePanel = new AcquTree(new Dimension(200,300));
		treePanel.setBorder(new EmptyBorder(0,2,0,2));
		this.add(treePanel);
		activeCompoments(false);
		

	}
	
	
}
