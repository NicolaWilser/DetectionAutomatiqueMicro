package controllers;

import ij.ImageStack;
import ij.process.ShortProcessor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.swing.JProgressBar;

import dataStruct.AcquisitionData;
import dataStruct.PVRoi;

import dataStruct.Point3D;

import hardware.cam.pvcam.PVCAM;
import hardware.cam.pvcam.PVCAMDriver;
import hardware.cam.pvcam.PVParam;
import hardware.shutter.ShutterDriver;
import hardware.stage.Oasis4i;

public class SystemController {
	
	private PVCAMDriver cam;
	private int hCam;
	private Oasis4i stage;
	private ShutterDriver shutter;
	private final int shutWaitTime = 10;
	private boolean halt = false;
	
	public SystemController(PVCAM pvc, Oasis4i oasis, ShutterDriver drv){
		cam = pvc.getCam();
		hCam = pvc.getHcam();
		stage = oasis;
		shutter = drv;
		halt = false;
	}

	
	private void wait(int tmp){
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		while ((t2 - t1) < tmp ){
			t2 = System.currentTimeMillis();
		}
	}
	
	 public short[] getStaticSingleShot(PVRoi roi,int exp){
		 
		 	
		    shutter.openShutter();
			cam.pl_exp_init_seq();
			long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TIMED_MODE, exp);
			ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
		    cam.pl_exp_start_seq(hCam, buffer);
		    boolean stop = false;
		    while( stop == false){
		    	int stat = cam.pl_exp_check_status( hCam);
		    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
		    		stop = true;
		    	}
		    }

		    buffer.order(ByteOrder.LITTLE_ENDIAN);
		    cam.pl_exp_finish_seq(hCam, buffer, 0);
			cam.pl_exp_uninit_seq();
			    
		    ShortBuffer image =  buffer.asShortBuffer();
		    shutter.closeShutter();
		    short[] res = new short[image.capacity()];
		    image.get(res);
		    System.gc();
			return res;
	 }
	 
	 public short[] getSingleShot(Point3D position, PVRoi roi, int exp){
		 if (stage.status == true)
			 stage.OI_MoveToXYZ(position.x, position.y, position.z,1);
		 return getStaticSingleShot(roi,exp);
		 
	 }
	 
	 
	 
		
	 public short[] getStaticSingleSynchronizedShot(PVRoi roi,int exp){
		 
		
			cam.pl_exp_init_seq();
			long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, exp);
					
			ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
		    cam.pl_exp_start_seq(hCam, buffer);
		 
		    ShutterDriver.wait(shutWaitTime);
		    
		    shutter.openShutter();
		    boolean stop = false;
		    
		    while( stop == false){
		    	int stat = cam.pl_exp_check_status( hCam);
		    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
		    		stop = true;
		    	}
		    }

		    shutter.closeShutter();
			cam.pl_exp_finish_seq(hCam, buffer, 0);
			cam.pl_exp_uninit_seq();
		    buffer.order(ByteOrder.LITTLE_ENDIAN);
		    ShortBuffer image =  buffer.asShortBuffer();
		    short[] res = new short[image.capacity()];
		    image.get(res);
		    System.gc();
			return res;	
	 }
	 
	 public short[] getSingleSynchronizedShot(Point3D position, PVRoi roi, int exp){
		 if (stage.status == true)
		   stage.OI_MoveToXYZ(position.x, position.y, position.z,1);
		 return getStaticSingleSynchronizedShot(roi,exp);
		 
	 }
	 
	 
	 public ShortProcessor getStaticSingleImage(PVRoi roi,int exp){
		 
		 	
		    shutter.openShutter();
			cam.pl_exp_init_seq();
			long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TIMED_MODE, exp);
			
			ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
			
			
		    cam.pl_exp_start_seq(hCam, buffer);
		    
		    
		    
		    boolean stop = false;
		    while( stop == false){
		    	int stat = cam.pl_exp_check_status( hCam);
		    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
		    		stop = true;
		    	}
		    }

		    buffer.order(ByteOrder.LITTLE_ENDIAN);
		    cam.pl_exp_finish_seq(hCam, buffer, 0);
			cam.pl_exp_uninit_seq();
			    
		    ShortBuffer image =  buffer.asShortBuffer();
		    shutter.closeShutter();
		    ShortProcessor sp = new ShortProcessor(roi.getWidth(), roi.getHeight());
		    image.get((short[])sp.getPixels());
		    System.gc();
			return sp;
	 }
	 
	 public ShortProcessor getSingleImage(Point3D position, PVRoi roi, int exp){
		 
		 stage.OI_MoveToXYZ(position.x, position.y, position.z,1);
		 return getStaticSingleImage(roi,exp);
		 
	 }
	 
	 
	 
		
	 public ShortProcessor getStaticSingleSynchronizedImage(PVRoi roi,int exp){

		
			cam.pl_exp_init_seq();
			long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, exp);	
			ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
		    cam.pl_exp_start_seq(hCam, buffer);
		    
		    boolean stop = false;
		    
		    ShutterDriver.wait(shutWaitTime);
		    
		    
		    shutter.openShutter();
		    while( stop == false){
		    	int stat = cam.pl_exp_check_status( hCam);
		    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
		    		stop = true;
		    	}
		    }

		    shutter.closeShutter();
			cam.pl_exp_finish_seq(hCam, buffer, 0);
			cam.pl_exp_uninit_seq();
		    buffer.order(ByteOrder.LITTLE_ENDIAN);
		    ShortBuffer image =  buffer.asShortBuffer();
		    ShortProcessor sp = new ShortProcessor(roi.getWidth(), roi.getHeight());
		    image.get((short[])sp.getPixels());
		    System.gc();
			return sp;	
	 }
	 
	 public ShortProcessor getSingleSynchronizedImage(Point3D position, PVRoi roi, int exp){
		 if (stage.status == true)
			 stage.OI_MoveToXYZ(position.x, position.y, position.z,1);
		 return getStaticSingleSynchronizedImage(roi,exp);
		 
	 }
	 
	 public void halt(){
		 halt = true;
	 }
	 
	 
	public ImageStack getTimeLaps(Point3D pos, PVRoi roi, int expo, int tempo, int num ,JProgressBar local){ 
		
		
		ImageStack ims = new ImageStack(roi.getWidth(),roi.getHeight());
		if (stage.status == true)
			stage.OI_MoveToXYZ(pos.x, pos.y, pos.z, 1);
		
		if (local != null){
			local.setMaximum(num);
			local.setMinimum(0);
			local.setValue(0);
		}
		int i = 1;
		while((i <=num) && (halt == false)){
			ims.addSlice(String.valueOf(i), getStaticSingleSynchronizedImage(roi,expo));
			if (local != null){
				local.setValue(i);
			}
			if (i != num){
				wait(tempo);
			}
			i++;
		}
		return ims;
	 }

	 public ImageStack get3D(Point3D mPos, PVRoi roi, int expo, double step, int num,JProgressBar local){
		 stage.OI_MoveToXYZ(mPos.x, mPos.y, mPos.z,1);
		 ImageStack ims = new ImageStack(roi.getWidth(),roi.getHeight());
		   if (local != null){
				local.setMaximum(num);
				local.setMinimum(0);
				local.setValue(0);
			}
		   int i = 1;
		   while((i <= num) && (halt == false)){
				ims.addSlice(String.valueOf(i), getStaticSingleSynchronizedImage(roi,expo));
				if ((i != num) && (halt != true)){
					stage.OI_StepZ(step, 1);
				}
				
				if (local != null){
					local.setValue(i);
				}
				i++;
			}
			return ims;
	 }
	 
	 
	/* public ImageStack getTimeLaps2(Point3D pos, PVRoi roi, int expo, int tempo, int num ,JProgressBar local){ 
			
			
			ImageStack ims = new ImageStack(roi.getWidth(),roi.getHeight());
			if (stage.status == true)
				stage.OI_MoveToXYZ(pos.x, pos.y, pos.z, 1);
			
			if (local != null){
				local.setMaximum(num);
				local.setMinimum(0);
				local.setValue(0);
			}
			
			cam.pl_exp_init_seq();
			long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, expo);	
			ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
			
			for (int i = 1; i <= num; i++){
				
				if (i > 1){
					cam.pl_exp_init_seq();
					framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, expo);	
				}

			    cam.pl_exp_start_seq(hCam, buffer);
			    boolean stop = false;
			    ShutterDriver.wait(shutWaitTime);
			    shutter.openShutter();
			    while( stop == false){
			    	int stat = cam.pl_exp_check_status( hCam);
			    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
			    		stop = true;
			    	}
			    }

			    shutter.closeShutter();
				cam.pl_exp_finish_seq(hCam, buffer, 0);
				cam.pl_exp_uninit_seq();
			    buffer.order(ByteOrder.LITTLE_ENDIAN);
			    ShortBuffer image =  buffer.asShortBuffer();
			    ShortProcessor sp = new ShortProcessor(roi.getWidth(), roi.getHeight());
			    image.get((short[])sp.getPixels());
				ims.addSlice(String.valueOf(i),sp);
				if (local != null){
					local.setValue(i);
				}
				if (i != num){
					wait(tempo);
				}
			}
			System.gc();
			return ims;
		 }

		 public ImageStack get3D2(Point3D mPos, PVRoi roi, int expo, double step, int num,JProgressBar local){
			 stage.OI_MoveToXYZ(mPos.x, mPos.y, mPos.z,1);
			 ImageStack ims = new ImageStack(roi.getWidth(),roi.getHeight());
			   if (local != null){
					local.setMaximum(num);
					local.setMinimum(0);
					local.setValue(0);
				}
			   
			   	cam.pl_exp_init_seq();
				long framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, expo);	
				ByteBuffer buffer = ByteBuffer.allocateDirect((int)framesize);
				
				for (int i = 1; i <= num; i++){
					
					if (i > 1){
						cam.pl_exp_init_seq();
						framesize = cam.pl_exp_setup_seq(hCam, 1, roi.getxStart(), roi.getyStart(), roi.getxEnd(), roi.getyEnd(), PVParam.TRIGGER_FIRST_MODE, expo);	
					}

				    cam.pl_exp_start_seq(hCam, buffer);
				    boolean stop = false;
				    ShutterDriver.wait(shutWaitTime);
				    shutter.openShutter();
				    while( stop == false){
				    	int stat = cam.pl_exp_check_status( hCam);
				    	if ((stat == PVParam.READOUT_COMPLETE) || (stat == PVParam.READOUT_FAILED)){
				    		stop = true;
				    	}
				    }

				    shutter.closeShutter();
					cam.pl_exp_finish_seq(hCam, buffer, 0);
					cam.pl_exp_uninit_seq();
				    buffer.order(ByteOrder.LITTLE_ENDIAN);
				    ShortBuffer image =  buffer.asShortBuffer();
				    ShortProcessor sp = new ShortProcessor(roi.getWidth(), roi.getHeight());
				    image.get((short[])sp.getPixels());
					ims.addSlice(String.valueOf(i),sp);
					if (local != null){
						local.setValue(i);
					}
					
					if (i != num){
						stage.OI_StepZ(step, 1);
					}
				}
				System.gc();
				return ims;
		 }
		 
	*/ 
	 // trying optimized data acqu
		 
	 public ImageStack get3DImage(AcquisitionData data, JProgressBar local){
		 PVRoi roi = new PVRoi(data.getRoi());
		 ImageStack ims;
		 if (data.is2DType()){
			  ims = getTimeLaps(data.getPos(),roi,data.getExposure(),data.getTempo(),(int) data.getNum2d(),local);
		 } else {
			 Point3D minp = new Point3D(data.getPos().x,data.getPos().y,data.getzMin());
			 ims = get3D(minp,roi,data.getExposure(),data.getStep(), data.getZNum(),local);
		 }

		 halt = false;
		 return ims;
		 
	 }


}
