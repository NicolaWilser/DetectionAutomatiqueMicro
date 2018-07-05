package gui;

import javax.swing.JPanel;
import hardware.stage.Oasis4i;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dataStruct.FormatterLab;

import resources.RLoader;

public class ZController extends JPanel{
	private static final long serialVersionUID = 1L;
		private JButton zp,zm;
		private JButton setOrigin, gotoPos, initialize;
		private JLabel zpl;
		
		private JFormattedTextField zpos;
		private JCheckBox stepperZ;
		private JSpinner  stepz;
		private JComboBox <String>cruise;
		
		private Oasis4i control;
		private FormatterLab formatter = new FormatterLab(2,2);
		
		private double realPositionZ;
		
		
		private class CheckListener implements ItemListener {

			public void itemStateChanged(ItemEvent e) {

				JCheckBox cb = (JCheckBox) e.getSource();
				
				if (cb.equals(stepperZ)){
					if (stepperZ.isSelected()){
						zp.setIcon( new ImageIcon(RLoader.getResourcePath("up_resize.png")));
						zm.setIcon( new ImageIcon(RLoader.getResourcePath("down_resize.png")));
						stepz.setEnabled(true);
					} else {
						zp.setIcon( new ImageIcon(RLoader.getResourcePath("upFast_resize.png")));
						zm.setIcon( new ImageIcon(RLoader.getResourcePath("downFast_resize.png")));
						stepz.setEnabled(false);
					}
				}
				
			}
			
		}
		
		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JButton src = (JButton) e.getSource();
				
				if (src.equals(zp)){
					
					if (stepperZ.isSelected()){
						double cpos =  ((Double) stepz.getValue()).doubleValue();
						control.OI_StepAxis(Oasis4i.OI_ZAXIS, cpos, 1);
					}
				}
				if (src.equals(zm)){
					if (stepperZ.isSelected()){
						double cpos =  ((Double) stepz.getValue()).doubleValue();
						control.OI_StepAxis(Oasis4i.OI_ZAXIS, -cpos, 1);
					}	
				}
				
				if (src.equals(gotoPos)){

					String sp = (String) cruise.getSelectedItem();
					int speed = 1000;
					if (sp.equalsIgnoreCase("slowly")){
						speed = Oasis4i.OI_CRUISE_MIN;
					}
					if (sp.equalsIgnoreCase("normal")){
						speed = (Oasis4i.OI_CRUISE_MIN + Oasis4i.OI_CRUISE_MAX) / 2 ;
					}
					if (sp.equalsIgnoreCase("fast")){
						speed = Oasis4i.OI_CRUISE_MAX;
					}
					
					control.OI_SetCruiseZ(speed);
					double pz = ((Number)zpos.getValue()).doubleValue();
					control.OI_MoveToZ(pz, 1);
				}
				
				
				if (src.equals(initialize)){
					
					String s = (String)JOptionPane.showInputDialog("please enter 'min-max' values, the current position will be the zero and min/max are the allowed margins\nFor better initialisation please use Oasis configuration wizard",
					               "20:20");

					if ((s != null) && (s.length() > 0)) {
					    double minv = 0;
					    double maxv = 0;
						String[] val = s.split(":");
						try{
							minv = Double.valueOf(val[0].trim()).doubleValue();
							maxv = Double.valueOf(val[1].trim()).doubleValue();
						} catch (NumberFormatException exc){
							return;
						}
						
					   control.OI_InitializeZ(maxv, minv);
					}
					
				}
				
				
				if (src.equals(setOrigin)){
					control.OI_SetOriginZ();
				}
				
			}
			
		}
		
		private class joyListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
				JButton but = (JButton) arg0.getComponent();
			
				
				if (!stepperZ.isSelected()){
					String sp = (String) cruise.getSelectedItem();
					int speed = 1000;
					if (sp.equalsIgnoreCase("slowly")){
						speed =1000;
					}
					if (sp.equalsIgnoreCase("normal")){
						speed = 2000;
					}
					if (sp.equalsIgnoreCase("fast")){
						speed = 4000;
					}
					
					if (but.equals(zm)){
						speed = -speed ;
					}
					
					control.OI_DriveAxisContinuous(Oasis4i.OI_ZAXIS, speed);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (!stepperZ.isSelected()){
					control.OI_HaltAxis(Oasis4i.OI_ZAXIS);
				}
			}
			
		}
		
		private class timerActionUpdate implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				realPositionZ = control.OI_ReadZ();
				zpl.setText("x: " + formatter.format(realPositionZ));
			}
		}
		
		private class SpinnerChnage implements ChangeListener {

			public void stateChanged(ChangeEvent e) {
				JSpinner src = (JSpinner) e.getSource();
				SpinnerModel model = src.getModel();
		        if (model instanceof SpinnerNumberModel) {
		          control.passStep =  ((Double) ((SpinnerNumberModel)model).getValue()).doubleValue();
		        }
				
			}
			
		}
		

		
		private ButtonListener bl = new ButtonListener();
		private joyListener jxy = new joyListener();
		private CheckListener cl = new CheckListener();
		
		
		
		private JPanel zControle(){
			
			JPanel panz = new JPanel();
		//	panxy.setSize(200,150);
			panz.setLayout(new BoxLayout(panz,BoxLayout.Y_AXIS));
			
			SpinnerModel modelstepZ = new SpinnerNumberModel(0.25,0.1,30,0.1);
			JLabel stp = new JLabel("Z step (µm)");
			stepz = new JSpinner(modelstepZ);
			stepz.addChangeListener(new SpinnerChnage());
			stepperZ = new JCheckBox("Stepper");
			stepperZ.setSelected(true);
			stepperZ.addItemListener(cl);

			JPanel line1 = new JPanel(new FlowLayout());
			line1.add(stepperZ);
			line1.add(stp);
			line1.add(stepz);
		
	        panz.add(line1);
	        panz.add(Box.createRigidArea(new Dimension(2, 10)));
	        
			JPanel jstk = new JPanel(new SpringLayout());
			zp = new JButton();
			zp.setIcon( new ImageIcon(RLoader.getResourcePath("up_resize.png")));
			zm = new JButton();
			zm.setIcon( new ImageIcon(RLoader.getResourcePath("down_resize.png")));
			
			zp.addActionListener(bl);
			zp.addMouseListener(jxy);
			
			zm.addActionListener(bl);
			zm.addMouseListener(jxy);

			int brx = 50;
			int bry = 50;
			
			jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
			jstk.add(zp);
			jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
			
			jstk.add(Box.createRigidArea(new Dimension(brx, 30)));
			zpl = new JLabel("z: 00000.00");
			zpl.setBorder(BorderFactory.createLineBorder(Color.black));
			jstk.add(zpl);
			jstk.add(Box.createRigidArea(new Dimension(brx, 30)));
			
			jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
			jstk.add(zm);
			jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
			
			SpringUtilities.makeCompactGrid(jstk, 3, 3, 2, 2, 2, 15);
			
			panz.add(jstk,BorderLayout.PAGE_START);
			//panz.add(Box.createRigidArea(new Dimension(2,2)));
			
			String[] speedStrings = { "Slowly", "Normal", "Fast"};
			cruise = new JComboBox<String>(speedStrings);
			cruise.setSelectedIndex(1);
			
			JPanel line3 = new JPanel(new FlowLayout());
			
			gotoPos = new JButton("Go To");
			gotoPos.addActionListener(bl);
			
			line3.add(gotoPos);
			
			zpos = new JFormattedTextField(NumberFormat.getNumberInstance());
			zpos.setValue(new Double(0.0));
			zpos.setColumns(5);

			line3.add(zpos);
			
			line3.add(cruise);
			
			panz.add(line3);
			
			JPanel line4 = new JPanel(new FlowLayout());
			
			setOrigin = new JButton("Set as Origin");
			setOrigin.addActionListener(bl);
			
			line4.add(setOrigin);
			
			initialize = new JButton("Hard Initialize");
			initialize.addActionListener(bl);
			
			line4.add(initialize);

			panz.add(line4);
			return panz;
			
		}

		public ZController(Oasis4i cont){

			super();
			
			control = cont;
			this.add(zControle());
			control.OI_SetAxisBacklash(Oasis4i.OI_ZAXIS, true);
			Timer tim = new Timer(500,new timerActionUpdate());
			tim.start();
			
		}

}
