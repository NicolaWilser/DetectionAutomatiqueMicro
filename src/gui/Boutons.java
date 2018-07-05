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

import XYZControl.ButtonListener;
import dataStruct.FormatterLab;

import resources.RLoader;

public class Boutons extends JPanel {
	private static final long serialVersionUID = -8786717041837544358L;

	private JButton allerPositionXYZ, definirOrigine; 
	private JLabel affichageX, affichageY, affichageZ;  
	private JFormattedTextField zoneTexteX, zoneTexteY, zoneTexteZ;
	private JSpinner rouletteX, rouletteY, rouletteZ;
	
	private Oasis4i control;
	private FormatterLab formatter = new FormatterLab(5,2);
	private double positionX, positionY, positionZ;
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) 
		{
			JButton src = (JButton) e.getSource();
			if (src.equals(allerPositionXYZ))
			{
				double px = ((Number)positionX.getValue()).doubleValue();
				double py = ((Number)positionY.getValue()).doubleValue();
				double pz = ((Number))positionZ.getValue()).doubleValue();
				control.OI_MoveToXYZ(px, py, pz, 1);
			}
			if (src.equals(definirOrigine))
			{
				control.OI_SetOriginXY();
				control.OI_SetOriginZ();
			}
		}
	}
	
	private class timerActionUpdate implements ActionListener{

		public void actionPerformed(ActionEvent arg0) 
		{
			positionX = control.OI_ReadAxis(Oasis4i.OI_XAXIS);
			positionY = control.OI_ReadAxis(Oasis4i.OI_YAXIS);
			positionZ = control.OI_ReadAxis(Oasis4i.OI_ZAXIS);
			
			zoneTexteX.setText("x: " + formatter.format(positionX));
			zoneTexteY.setText("y: " +  formatter.format(positionY));
			zoneTexteZ.setText("z: " +  formatter.format(positionZ));
		}
	}
	
	private ButtonListener bl = new ButtonListener();
	
	private JPanel xyzControl(){
		
		GridLayout total = new GridLayout(6,1);
		
		JPanel ligne1 = new JPanel(); // modifier X + go to
		JPanel ligne2 = new JPanel(); // modifier Y + définir origine
		JPanel ligne3 = new JPanel(); // modifier Z
		JPanel ligne4 = new JPanel(); // pas de X
		JPanel ligne5 = new JPanel(); // pas de Y
		JPanel ligne6 = new JPanel(); // pas de Z
		
		affichageX = new JLabel("x: 00000.00");
		ligne1.add(etiquettePositionX);
		affichageY = new JLabel("y: 00000.00");
		ligne2.add(etiquettePositionX);
		affichageZ = new JLabel("y: 00000.00");
		ligne3.add(etiquettePositionX);
		
		zoneTexteX = new JFormattedTextField(NumberFormat.getNumberInstance());
		zoneTexteX.setColumns(5);
		ligne1.add(etiquettePositionX);
		zoneTexteY = new JFormattedTextField(NumberFormat.getNumberInstance());
		zoneTexteY.setColumns(5);
		ligne2.add(etiquettePositionX);
		zoneTexteZ = new JFormattedTextField(NumberFormat.getNumberInstance());
		zoneTexteZ.setColumns(5);
		ligne3.add(etiquettePositionX);
		
		allerPositionXYZ = new JButton("Aller a la position");
		allerPositionXYZ.addActionListener(bl);
		ligne1.add(allerPositionXYZ);
		
		definirOrigine = new JButton("Definir origine");
		definirOrigine.addActionListener(bl);
		ligne2.add(definirOrigine);
		
		JLabel etiquettePasX = new JLabel("X step (�m)");
		ligne4.add(etiquettePasX);
		JLabel etiquettePasY = new JLabel("Y step (�m)");
		ligne5.add(etiquettePasY);
		JLabel etiquettePasZ = new JLabel("Z step (�m)");
		ligne6.add(etiquettePasZ);
		
		SpinnerModel modelstepX = new SpinnerNumberModel(1,0.1,1000,0.1); 
		rouletteX = new JSpinner(modelstepX);
		ligne4.add(rouletteX);
		SpinnerModel modelstepY = new SpinnerNumberModel(1,0.1,1000,0.1); 
		rouletteY = new JSpinner(modelstepY);
		ligne5.add(rouletteY);
		SpinnerModel modelstepZ = new SpinnerNumberModel(0.1,0.1,1,0.01); 
		rouletteZ = new JSpinner(modelstepZ);
		ligne6.add(rouletteZ);
		
		total.addLayoutComponent("Ligne1", ligne1);
		total.addLayoutComponent("Ligne2", ligne2);
		total.addLayoutComponent("Ligne3", ligne3);
		total.addLayoutComponent("Ligne4", ligne4);
		total.addLayoutComponent("Ligne5", ligne5);
		total.addLayoutComponent("Ligne6", ligne6);
		
	}
	
}