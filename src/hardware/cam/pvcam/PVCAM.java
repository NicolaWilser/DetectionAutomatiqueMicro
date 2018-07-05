package hardware.cam.pvcam;



public class PVCAM {
	
	private int hcam;
	private PVCAMDriver cam;
	private String cam_name;
	
	private boolean cam_opened = false;
	private boolean cam_initiated = false;

	public int getHcam() {
		return hcam;
	}

	public PVCAMDriver getCam() {
		return cam;
	}

	public boolean isCam_initiated() {
		return cam_initiated;
	}

	
	public void initDriver(){
		
		if (cam_initiated == false){
			cam.pl_pvcam_init();
		    if (cam.pl_cam_get_total() > 0){
		    	cam_name = cam.pl_cam_get_name(0);
		    	cam_initiated = true;
			
		    } else {
		    	System.err.println("no camera connected!");
		    	cam.pl_pvcam_uninit();
		    }
		}
	}
	
	public void UninitDriver(){
		if (cam_initiated == true){
			cam.pl_pvcam_uninit();
			cam_initiated = false;
		}
	}
	
	public PVCAM () {
		
		cam = new PVCAMDriver();
		initDriver();
	}
	
	public void openCam(){
		if (cam_initiated){
			hcam = cam.pl_cam_open(cam_name);
			cam.set_std_adc(hcam);
			if (cam.pl_error_code() == 0){
				cam_opened = true;
			} else {
				cam_opened = false;
			}
		} else {
			System.err.println("not Initialized");
		}
	}
	
	
	public void closeCam(){
		cam.pl_cam_close(hcam);
	}
	
	
	
	
	public String getCam_name() {
		return cam_name;
	}

	public boolean isCam_opened() {
		return cam_opened;
	}
	

 public int getExposure(){
	 return (int) cam.pl_get_param(hcam, PVParam.PARAM_EXP_TIME, PVParam.ATTR_CURRENT, PVParam.TYPE_UNS16);
 }
	

	
	
	
}
