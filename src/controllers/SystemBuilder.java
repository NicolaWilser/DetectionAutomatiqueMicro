package controllers;

import gui.DataManager;
import gui.LivePanel;
import gui.MainBoard;
import gui.XYZControl;
import hardware.cam.pvcam.PVCAM;
import hardware.shutter.ShutterDriver;
import hardware.stage.Oasis4i;
import ij.IJ;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import testUnit.TempPanel;

public class SystemBuilder {
	
	private Oasis4i stage;
	private PVCAM cam;
	private ShutterDriver shut;
	private LivePanel pnl;
	
	
	SystemController cntrl;
	
	private boolean shutterStatus;
	
private void buildDrivers(){
	stage = new Oasis4i();
	stage.initializeDriver();
	int res = stage.getLastCallResult();
	if (res != 0){
		IJ.error("XYZ Oasis4i stage",stage.OI_GetLastErrorMsg() + "\n no stage support will be included");
		stage.status = false;
	} else {
		stage.status = true;
	}
	cam = new PVCAM();
	cam.initDriver();
	cam.openCam();
	if (cam.isCam_initiated()){
		pnl = new LivePanel(cam);
	} else {
		IJ.error("CollSnap HQ2 Camera","Camera Fatal Error, make sure the camera is connected and turned on!");
		destroyDrivers();
		System.exit(0);
	}

	
	  shut = new ShutterDriver();
	  shut.openShutterDriver();
	 if (shut.getDeviceStatus()){
	  shutterStatus = true;
	} else {
		IJ.error("MS Shutter System", "no Shutter System connected - no PC control possible");
		shutterStatus = false;
	}
}


public boolean getShutterStatus(){
	return shutterStatus;
}

private void destroyDrivers(){
	
	if (stage.status == true){
		if (stage.OI_GetDriverOpen() == 1){
			stage.OI_Close();
		}
	}
	
	if (pnl.isRunnig()){
		pnl.stop();
	}
	
	pnl.hidePanel();

	if(cam.isCam_opened()){
		
		cam.closeCam();
		cam.UninitDriver();
	}
	
	if (shutterStatus == true){
		shut.closeShutter();
		shut.closeShutterDriver();
	}

}
	
public JPanel buildSystem(){
	
	buildDrivers();

	cntrl = new SystemController(cam,stage,shut);
	DataManager dt = new DataManager(stage,pnl);
	dt.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	
	ServiceProvider srvp = new ServiceProvider(cntrl,dt);
	JPanel col1 = new JPanel();
	XYZControl xyc = new XYZControl(stage);
	col1.add(xyc);
	MainBoard mb = new MainBoard(shut,pnl,srvp);
	col1.add(mb);
	col1.setLayout(new BoxLayout(col1,BoxLayout.PAGE_AXIS));
	col1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	JPanel total = new JPanel(new FlowLayout());
	total.add(col1);
	total.add(Box.createRigidArea(new Dimension(2,5)));
	total.add(dt);
	
	/**********/
	
	TempPanel pnl = new TempPanel(cam);
	Thread th = new Thread(pnl);
	th.start();
	
	
	/*********/
	
	
	return total;
}

public void destroySystem(){
	destroyDrivers();
}

}
