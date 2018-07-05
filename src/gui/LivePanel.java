package gui;

import hardware.cam.pvcam.*;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import dataStruct.CRoi;

public class LivePanel implements Runnable {
	
	ShortProcessor sp;
	ImagePlus imp;
 
	private PVCAMDriver driver;
	private int hCam;
	private boolean go = false;
	
	private Thread runner = null;
	
	private double thres = 4090;
	
	private int fullFrameX;
	private int fullFrameY;
	
	private ByteBuffer buffer, frameBuffer;
	private int exposure;
	private boolean activeTh = false;
    private long framesize;
	
	
	
public void hidePanel(){
  if (imp != null){
	if (imp.isVisible())
		imp.hide();
  }
}

public void showPanel(){
	
	Roi r = new Roi(0,0,0,0);
	boolean setr = false;
	if (panelIsShowing() == false){
		if (imp != null){
			r = imp.getRoi();
			setr = true;;
		}
		imp = new ImagePlus("Live View",sp);
		imp.show();
		
		if (setr == true){
			imp.setRoi(r);
		}
	}		
	
}
	
public boolean panelIsShowing(){
	if (imp == null){
		return false;
	} else {
		return imp.isVisible();
	}
}


public LivePanel(PVCAM gener){
	driver = gener.getCam();
	hCam = gener.getHcam();
	fullFrameX = driver.pl_get_full_width(hCam);
	fullFrameY = driver.pl_get_full_hight(hCam);
	sp = new ShortProcessor(fullFrameX,fullFrameY);
	exposure = 20;
	sp.resetRoi();
	sp.setValue(0);
	sp.fill();
}



public void start(){
	
	showPanel();
	driver.pl_exp_init_seq();
	//long framesize = driver.pl_exp_setup_cont(hCam,0, 0, fullFrameX, fullFrameY, PVParam.TIMED_MODE, exposure, PVParam.CIRC_OVERWRITE);
	framesize = driver.pl_exp_setup_cont(hCam,0, 0, fullFrameX, fullFrameY, PVParam.TIMED_MODE, exposure, PVParam.CIRC_OVERWRITE);
	
	long totalSize = framesize * 3 ;
	

	buffer = ByteBuffer.allocateDirect((int) totalSize);
	frameBuffer = ByteBuffer.allocateDirect((int)framesize);
	driver.pl_exp_start_cont(hCam, buffer, buffer.capacity());
	if (runner == null){
	     runner = new Thread(this);
	 	 go = true;
	     runner.start();
	}
}



public boolean isRunnig(){
	return (go && (runner != null));
}

public void stop(){
	 if (runner != null)
	  {
	      go = false;
	      runner = null;
	  }
	  boolean stop = false;
	  while( stop == false){
	    int stat = driver.exp_check_cont_status(hCam);
	    if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)|| (stat == PVParam.READOUT_NOT_ACTIVE)){
	    	stop = true;
	    }
	  }
	driver.pl_exp_stop_cont(hCam, 0);
	driver.pl_exp_uninit_seq();
}



	@Override
	public void run() {
		
 try{
	 

		while (go == true){
			 int status = 0;
			 boolean stop = false;
			 while( stop == false){
			    status = driver.exp_check_cont_status(hCam);
			    if ((status == PVParam.READOUT_COMPLETE) || (status == PVParam.READOUT_FAILED) || (status == PVParam.READOUT_NOT_ACTIVE)){
			    	stop = true;
			    }
			 }
			 if( status == PVParam.READOUT_COMPLETE ) {
				 
				 driver.pl_exp_get_latest_frame(hCam, frameBuffer);
				 frameBuffer.order(ByteOrder.LITTLE_ENDIAN);
				 ShortBuffer image =  frameBuffer.asShortBuffer();
				 image.rewind();
				 image.get((short[])sp.getPixels());
				 sp.setMinAndMax(0, 4095);
				 if (activeTh){
					sp.setThreshold(thres, 65535,ImageProcessor.RED_LUT);
				 }
				 imp.updateAndDraw();
			 } else {
				 System.err.println("Data collection error: " + driver.get_latest_error_message());
			 }
		}
 } catch (Exception e){
	 System.out.println( "error: " +  e.getMessage());
 }
}

	
	
	public void setThreshold(double thr){
		thres = thr;
	}

	public double getCurrentThreshold(){
		return thres;
	}
	
	public void activateThreshold(boolean status){
		activeTh = status;
	}
	
	public boolean getThresholdStatus(){
		return activeTh;
	}

	
	public CRoi getDrawedRoi(){
		if (imp != null){
			Roi r = imp.getRoi();
			if (r != null){
				return new CRoi(r.getBounds().x,r.getBounds().y,r.getBounds().width,r.getBounds().height);
			} else {
				return new CRoi(0,0,fullFrameX,fullFrameY);
			}
		} else {
			return new CRoi(0,0,fullFrameX,fullFrameY);
		}
		
	}
	
	public void setRoi(CRoi roi){
	
		imp.setRoi(roi.getImageJRoi());
	}
	public void setRoi(int x, int y, int w, int h){
		imp.setRoi(new Roi(x,y,w,h));
	}
	
	public int getExposure(){
		return exposure;
	}
	
	public void setExposure(int expo){
		exposure = expo;
		if (isRunnig()){
			stop();
			start();
		}
	}
	
	
	
}
