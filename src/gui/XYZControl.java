package gui;
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

import dataStruct.FormatterLab;

import resources.RLoader;


public class XYZControl extends JPanel {

	private static final long serialVersionUID = -8786717041837544358L;
	private JButton xp,xm,yp,ym;
	private JButton setOriginXY, gotoPosXY, initializeXY;
	private JLabel xpl, ypl;
	
	private JFormattedTextField xpos, ypos;
	private JCheckBox stepperXY;
	private JSpinner stepy, stepx;
	private JComboBox <String>cruise;
	private ZController zc;
	private Oasis4i control;
	private FormatterLab formatter = new FormatterLab(5,2);
	
	
	
	@SuppressWarnings("unused")
	private double realPosX, realPosY, realPosZ;
	
	
	
	private class CheckListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			JCheckBox cb = (JCheckBox) e.getSource();
			
			if (cb.equals(stepperXY)){
				if (stepperXY.isSelected()){
					xp.setIcon( new ImageIcon(RLoader.getResourcePath("right_resize.png")));
					xm.setIcon( new ImageIcon(RLoader.getResourcePath("left_resize.png")));
					yp.setIcon( new ImageIcon(RLoader.getResourcePath("up_resize.png")));
					ym.setIcon( new ImageIcon(RLoader.getResourcePath("down_resize.png")));
					
					stepx.setEnabled(true);
					stepy.setEnabled(true);
					
				} else {
					xp.setIcon( new ImageIcon(RLoader.getResourcePath("rightFast_resize.png")));
					xm.setIcon( new ImageIcon(RLoader.getResourcePath("leftFast_resize.png")));
					yp.setIcon( new ImageIcon(RLoader.getResourcePath("upFast_resize.png")));
					ym.setIcon( new ImageIcon(RLoader.getResourcePath("downFast_resize.png")));
					stepx.setEnabled(false);
					stepy.setEnabled(false);
				}
			}
			
		}
		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			
			if (src.equals(xp)){
				
				if (stepperXY.isSelected()){
					double cpos =  ((Double) stepx.getValue()).doubleValue();
					control.OI_StepAxis(Oasis4i.OI_XAXIS, cpos, 1);
				}
			}
			if (src.equals(xm)){
				if (stepperXY.isSelected()){
					double cpos =  ((Double) stepx.getValue()).doubleValue();
					control.OI_StepAxis(Oasis4i.OI_XAXIS, -cpos, 1);
				}	
			}
			if (src.equals(yp)){
				if (stepperXY.isSelected()){
					double cpos =  ((Double) stepy.getValue()).doubleValue();
					control.OI_StepAxis(Oasis4i.OI_YAXIS, cpos, 1);
				}	
			}
			if (src.equals(ym)){
				if (stepperXY.isSelected()){
					double cpos =  ((Double) stepy.getValue()).doubleValue();
					control.OI_StepAxis(Oasis4i.OI_YAXIS, -cpos, 1);
				}	
			}
			if (src.equals(gotoPosXY)){
				//xpos.commitEdit();
				double px = ((Number)xpos.getValue()).doubleValue();
				double py = ((Number) ypos.getValue()).doubleValue();
				control.OI_MoveToXY(px, py, 1);
			}
			
			if (src.equals(initializeXY)){
				
				control.OI_InitializeXY();
				
			}
			if (src.equals(setOriginXY)){
				
				control.OI_SetOriginXY();
				
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
		
			
			if (!stepperXY.isSelected()){
				int axis = Oasis4i.OI_XAXIS;
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
				if (but.equals(xp)){
					axis = Oasis4i.OI_XAXIS;
				}
				if (but.equals(xm)){
					axis = Oasis4i.OI_XAXIS;
					speed = -speed ;
				}
				if (but.equals(yp)){
					axis = Oasis4i.OI_YAXIS;
				}
				if (but.equals(ym)){
					axis = Oasis4i.OI_YAXIS;
					speed = -speed;
				}
				control.OI_DriveAxisContinuous(axis, speed);
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			JButton but = (JButton) arg0.getComponent();
			int axis = Oasis4i.OI_XAXIS;
			if (!stepperXY.isSelected()){
				if (but.equals(xp) || but.equals(xm)){
					axis = Oasis4i.OI_XAXIS;
				}
				
				if (but.equals(yp) || but.equals(ym)){
					axis = Oasis4i.OI_YAXIS;
				}
				control.OI_HaltAxis(axis);
			}
		}
		
	}
	
	private class timerActionUpdate implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			realPosX = control.OI_ReadAxis(Oasis4i.OI_XAXIS);
			realPosY = control.OI_ReadAxis(Oasis4i.OI_YAXIS);
			realPosZ = control.OI_ReadAxis(Oasis4i.OI_ZAXIS);
			
			xpl.setText("x: " + formatter.format(realPosX));
			ypl.setText("y: " +  formatter.format(realPosY));
			
		}
	}
	
	
	
	private ButtonListener bl = new ButtonListener();
	private joyListener jxy = new joyListener();
	private CheckListener cl = new CheckListener();
	
	private JPanel xyControle(){
		
		JPanel panxy = new JPanel();
		panxy.setSize(200,150);
		panxy.setLayout(new BoxLayout(panxy,BoxLayout.Y_AXIS));
		
		SpinnerModel modelstepX = new SpinnerNumberModel(1,0.1,1000,0.1); 
		SpinnerModel modelstepY = new SpinnerNumberModel(1,0.1,1000,0.1); 
	
		JLabel xstp = new JLabel("X step (�m)");
		JLabel ystp = new JLabel("Y step (�m)");
		stepx = new JSpinner(modelstepX);
		stepy = new JSpinner(modelstepY);
		
		
		stepperXY = new JCheckBox("Stepper");
		stepperXY.setSelected(true);
		stepperXY.addItemListener(cl);
		
		
		
		JPanel line1 = new JPanel(new FlowLayout());
		line1.add(stepperXY);
		line1.add(xstp);
		line1.add(stepx);
		line1.add(ystp);
		line1.add(stepy);
	
        panxy.add(line1);
        panxy.add(Box.createRigidArea(new Dimension(2, 10)));
		JPanel jstk = new JPanel(new SpringLayout());
		xp = new JButton();
		xp.setIcon( new ImageIcon(RLoader.getResourcePath("right_resize.png")));
		xm = new JButton();
		xm.setIcon( new ImageIcon(RLoader.getResourcePath("left_resize.png")));
		yp = new JButton();
		yp.setIcon( new ImageIcon(RLoader.getResourcePath("up_resize.png")));
		ym = new JButton();
		ym.setIcon( new ImageIcon(RLoader.getResourcePath("down_resize.png")));
		
		xp.addActionListener(bl);
		xp.addMouseListener(jxy);
		
		xm.addActionListener(bl);
		xm.addMouseListener(jxy);
		
		yp.addActionListener(bl);
		yp.addMouseListener(jxy);
		
		ym.addActionListener(bl);
		ym.addMouseListener(jxy);
		
		
		int brx = 50;
		int bry = 50;
		
		jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
		jstk.add(yp);
		jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
		jstk.add(xm);
		//jstk.add(Box.createRigidArea(new Dimension(50, 50)));
		
		JPanel lbp = new JPanel();
		lbp.setLayout(new BoxLayout(lbp, BoxLayout.PAGE_AXIS));

		xpl = new JLabel("x: 00000.00");
		ypl = new JLabel("y: 00000.00");
		lbp.add(Box.createRigidArea(new Dimension(10, 10)));
		lbp.add(xpl);
		lbp.add(Box.createRigidArea(new Dimension(10, 10)));
		lbp.add(ypl);
		lbp.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		jstk.add(lbp);
		
		
		jstk.add(xp);
		jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
		jstk.add(ym);
		jstk.add(Box.createRigidArea(new Dimension(brx, bry)));
		
		SpringUtilities.makeCompactGrid(jstk, 3, 3, 2, 2, 2, 2);
		
		panxy.add(jstk,BorderLayout.PAGE_START);
		panxy.add(Box.createRigidArea(new Dimension(2, 10)));
		
		String[] speedStrings = { "Slowly", "Normal", "Fast"};
		cruise = new JComboBox <String>(speedStrings);
		cruise.setSelectedIndex(1);
		
		JPanel line3 = new JPanel(new FlowLayout());
		
		gotoPosXY = new JButton("Go To");
		gotoPosXY.addActionListener(bl);
		
		line3.add(gotoPosXY);
		
		xpos = new JFormattedTextField(NumberFormat.getNumberInstance());
		ypos = new JFormattedTextField(NumberFormat.getNumberInstance());
		xpos.setValue(new Double(0.0));
		xpos.setColumns(5);
		ypos.setValue(new Double(0.0));
		ypos.setColumns(5);

		
		line3.add(xpos);
		line3.add(ypos);
		
		line3.add(cruise);
		
		panxy.add(line3);
		
		JPanel line4 = new JPanel(new FlowLayout());
		setOriginXY = new JButton("Set as Origin");
		setOriginXY.addActionListener(bl);
		line4.add(setOriginXY);
		initializeXY = new JButton("Hard Initialize");
		initializeXY.addActionListener(bl);
		line4.add(initializeXY);
		panxy.add(line4);
		return panxy;
		
	}
	public XYZControl(Oasis4i cont){

		super();
		control = cont;
		JPanel col1 = new JPanel();
		JPanel cl1 = new JPanel();
		zc = new ZController(control);
		cl1.setLayout(new FlowLayout());
		cl1.add(xyControle());
		cl1.add(zc);
		col1.add(cl1);
		this.add(col1);
		
		if (control.status == false){
			SpringUtilities.disableIt(this,false);
		}
		
		Timer tim = new Timer(500,new timerActionUpdate());
		tim.start();
		
	}


}
