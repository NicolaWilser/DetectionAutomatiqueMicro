package hardware.shutter;

import com.ftdichip.ftd2xx.*;

public class ShutterDriver {
	
	private boolean extStatus;
    private boolean state = false;
	
	private Device device;
	 public static void wait (int n){
         long t0,t1;
         t0=System.currentTimeMillis();
         do{
              t1=System.currentTimeMillis();
        }
          while (t1-t0<1000);
  }
	public boolean searchDevice(){
		extStatus = false;
		try{
			 Device[] devices = Service.listDevices();
			 if (devices.length > 0){
				 device = devices[0];
				 state = true;
			 } else {
				 state = false;
			 }
		} catch (FTD2xxException e){ 
			e.printStackTrace();
	    }
		return state;
	}
	
	
	public boolean getDeviceStatus(){
		return state;
	}
	
	public void openShutterDriver(){
		if (searchDevice()){
			if (device.isOpen() == false){
				try{
					device.open();
					device.setBitBangMode(7, BitBangMode.SYNCHRONOUS );
					device.setUSBParameters(9600,9600);
					device.write(0);
				} catch (FTD2xxException e){ 
					e.printStackTrace();
				}
			}
		}
	}
	
	public void closeShutterDriver(){
		if (state == false){
		if (device.isOpen() == true){
				try {
					device.close();
				} catch (FTD2xxException e) {
					e.printStackTrace();
				}	
		}
		}
	}
	
	public void openShutter(){
	 if (device != null){	
		if (device.isOpen()){
		try {
			device.write(1);
		} catch (FTD2xxException e) {
			e.printStackTrace();
		}
		}
	 }
	}
	
	public void closeShutter(){
	 if (device != null){	
		if (device.isOpen()){
			try {
				device.write(0);
			} catch (FTD2xxException e) {
				e.printStackTrace();
			}
		}
	 }
	}
	
	public void setExternalControl(boolean cnt){
		if (device.isOpen()){
			try {
				if (cnt == true){
					device.write(4);
				} else {
					device.write(0);
				}
				extStatus = cnt;
			} catch (FTD2xxException e) {
				e.printStackTrace();
				extStatus = false;
			}
		}
	}
	
	public boolean getExternalControlStatus(){
		return (device.isOpen() && extStatus);
	}
	
	public int getBit(int data, int bit){
		int res = (data >> bit) & 0x1;
		return res;
	}

	
	public boolean getHShutterStatus(){
		int b = 1;
		try {
			b = getBit(device.read(),4);
			return (b == 0);
		} catch (FTD2xxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

		 
		/* 
		 
		 device.write(0);
		 
		 
		 for (int i = 1; i < 20; i++){
			 device.write(1);
			 
			 wait(1000);
			
			 device.write(0);
			 
			 wait(500);

		 }
		 
		 
		
		 */


	}

