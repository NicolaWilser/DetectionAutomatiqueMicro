package controllers;


import java.io.File;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;

import javax.swing.JProgressBar;

import gui.SwingWorker;

import dataStruct.AcquisitionData;

import gui.DataManager;

public class ServiceProvider {

	private SystemController cntrl;
	private DataManager dtm;
	
	public ServiceProvider(SystemController cnt, DataManager dt){
		cntrl = cnt;
		dtm = dt;
	}
	
	
	public void aqcuiereAllData(final JProgressBar local,final JProgressBar global, final boolean save, final String path){
		
		final SwingWorker worker = new SwingWorker() {
	        public Object construct() {
	        	AcquisitionData[] line = dtm.getTreeWorkLine();
	    		int num = line.length;
	    		if (num > 0){
	    			if (global != null){
	    				global.setMaximum(num);
	    				global.setMinimum(0);
	    				global.setValue(0);
	    			}
	    			
	    			for (int i = 0; i < num; i++){
	    				ImageStack stk = cntrl.get3DImage(line[i], local);
	    				ImagePlus ims = new ImagePlus(line[i].getName(),stk);
	    				if (save == true){
	    					IJ.save(ims, path + File.separator + line[i].getName() + ".tif" );
	    				}	
	    				if (global != null){
	    					global.setValue(i+1);
	    				}
	    				ims.show();
	    			}
	    			
	    		}
	        	
	           return null;
	        }
	    };

	    worker.start();  //required for SwingWorker 3	
	
	}
	
	
	public void stopEmergency(){
		cntrl.halt();
	}	
	
	
}
