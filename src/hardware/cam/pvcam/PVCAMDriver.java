package hardware.cam.pvcam;

import java.nio.ByteBuffer;

import resources.RLoader;



public class PVCAMDriver {
	



	/*****************************************************************************/
	/*****************************************************************************/
	/*                                                                           */
	/*             Class 0: Camera Communications Function Prototypes            */
	/*                                                                           */
	/*****************************************************************************/
	/*****************************************************************************/


	/*****************************************************************************/
	/* pvcam_version     Version number of the PVCAM library                     */
	/*                     16 bits = MMMMMMMMrrrrTTTT where MMMMMMMM = Major #,  */
	/*                     rrrr = Minor #, and TTTT = Trivial #                  */
	/*****************************************************************************/

	  public native int pl_pvcam_get_ver ();
	  public native boolean pl_pvcam_init ();
	  public native boolean pl_pvcam_uninit ();
	  

	/*****************************************************************************/
	/* hcam              Camera handle returned from pl_cam_open()               */
	/* cam_num           Camera number Range: 0 through (totl_cams-1)            */
	/* camera_name       Text name assigned to a camera (with RSConfig)          */
	/* totl_cams         Total number of cameras in the system                   */
	/* o_mode            Mode to open the camera in (must be OPEN_EXCLUSIVE)     */
	/*****************************************************************************/

	  public native boolean pl_cam_check (int hcam);
	  public native boolean pl_cam_close (int hcam);
	  public native String pl_cam_get_name (int cam_num);
	  public native int pl_cam_get_total ();
	  public native int pl_cam_open (String cam_name);

	  public native int pl_error_code ();
	  public native String pl_get_message(int code);
	  public native String get_latest_error_message ();


	/*****************************************************************************/
	/*****************************************************************************/
	/*                                                                           */
	/*              Class 2: Configuration/Setup Function Prototypes             */
	/*                                                                           */
	/*****************************************************************************/
	/*****************************************************************************/

	/*****************************************************************************/
	/* param_id          ID of the parameter to get or set (PARAM_...)           */
	/* param_attribute   Attribute of the parameter to get (ATTR_...)            */
	/* param_value       Value to get or set                                     */
	/* index             Index of enumeration Range: 0 through N-1 ... where N   */
	/*                     is retrieved with get_param(...,ATTR_COUNT,...)       */
	/* value             Numerical value of enumeration                          */
	/* desc              Text description of enumeration                         */
	/* length            Length of text description of enumeration               */
	/*****************************************************************************/
	 
	  
	  public native boolean setExposure(int hcam, int exp);
	  public native boolean set_ccd_clear_mode(int hcam, int mode);
	  public native boolean set_adc(int hcam, long port, int adc_index,int gain);
	  
	  public boolean set_std_adc(int hcam){
		  return set_adc(hcam,0,1,1);
	  }
	  
	  
	  
	  public native double pl_get_param (int hcam, long param_id,int param_attribute, int val_type);
	  public native boolean pl_set_param (int hcam, long param_id,double param_value , int val_type);
	  
	  public native int get_param_type(int hcam, long param_id);
	  public native boolean get_param_support(int hcam, long param_id);
	  public native int get_param_access(int hcam, long param_id);
	  
	  public native int pl_get_enum_param_value(int hcam, long param_id, long index);
	  public native String pl_get_enum_param_String (int hcam, long param_id,long index);
	  public native int get_enum_count(int hcam, long param_id);
	  public native int pl_enum_str_length (int hcam, long param_id, int index);

	/*****************************************************************************/
	/*****************************************************************************/
	/*                                                                           */
	/*               Class 3: Data Acquisition Function Prototypes               */
	/*                                                                           */
	/*****************************************************************************/
	/*****************************************************************************/

	/*****************************************************************************/
	/* pixel_stream      Buffer to hold image(s)                                 */
	/* byte_cnt          Size of bufer to hold images (in bytes)                 */
	/* exp_total         Total number of exposures to take                       */
	/* rgn_total         Total number of regions defined for each image          */
	/* rgn_array         Array of regions (must be rgn_total in size)            */
	/*                     s1    starting pixel in the serial register           */
	/*                     s2    ending pixel in the serial register             */
	/*                     sbin  serial binning for this region                  */
	/*                     p1    starting pixel in the parallel register         */
	/*                     p2    ending pixel in the parallel register           */
	/*                     pbin  parallel binning for this region                */
	/* exp_mode          Mode for capture (TIMED_MODE, STROBED_MODE, ...)        */
	/* exposure_time     Time to expose in selected exposure resolution          */
	/*                     Default is milliseconds (see PARAM_EXP_RES)           */
	/* exp_bytes         Value returned from PVCAM specifying the required       */
	/*                     number of bytes to allocate for the capture           */
	/* buffer_mode       Circular buffer mode (CIRC_OVERWRITE,...)               */
	/* size              Size of continuous capture pixel_stream                 */
	/*                     (must be a multiple of byte_cnt)                      */
	/* status            Status of the current capture (EXPOSURE_IN_PROGRESS,...)*/
	/* bytes_arrived     Number of bytes that have arrived.  For continuous      */
	/*                     mode this is the number of bytes that have arrived    */
	/*                     this time through the buffer.                         */
	/* buffer_cnt        Number of times through the buffer (continuous mode)    */
	/* frame             Pointer to the requested image                          */
	/* cam_state         State to set the camera in (CCS_NO_CHANGE,...)          */
	/* hbuf              Standard image buffer                                   */
	/* exposure          Exposure # to unravel, 65535 for All, else exposure #   */
	/* array_list        Array of Pointers that will get the unraveled images    */
	/*                     in the same order as the regions.                     */
	/* tlimit            Time in milliseconds to wait for a transfer             */
	/*****************************************************************************/

	  public native boolean pl_exp_init_seq ();
	  public native boolean pl_exp_uninit_seq ();
	  public native int pl_get_full_width(int hcam);
	  public native int pl_get_full_hight(int hcam);
	  public native long pl_exp_setup_seq (int hcam, int exp_total, int xr, int yr , int width, int height, int exp_mode, int exp_time);  
	  
	  
	  
	  public native boolean pl_exp_start_seq (int hcam, ByteBuffer pixel_stream);
	  public native long pl_exp_setup_cont (int hcam, int xr, int yr , int width, int height, int exp_mode, int exp_time,int buffer_mode);
	  
	  
	  public native boolean pl_exp_start_cont (int hcam, ByteBuffer pixel_stream,long size);
	  public native int pl_exp_check_status (int hcam);
	  public native int exp_check_cont_status (int hcam);
	  
	  public native boolean pl_exp_get_latest_frame (int hcam, ByteBuffer frame);
	 
	  public native boolean pl_exp_get_oldest_frame (int hcam, ByteBuffer frame);
	  public native boolean pl_exp_unlock_oldest_frame (int hcam);
	  public native boolean pl_exp_stop_cont (int hcam, int cam_state);
	  public native boolean pl_exp_abort (int hcam, int cam_state);
	  public native boolean pl_exp_finish_seq (int hcam, ByteBuffer pixel_stream, int hbuf);

	/*****************************************************************************/
	/* addr              Specifies which I/O address to control                  */
	/* state             Specifies the value to write to the register            */
	/* location          Specifies when to control the I/O (SCR_PRE_FLASH,...)   */
	/*****************************************************************************/
	  
	  public native boolean pl_io_setting (int hcam, int addr, float state, long location);

	 public native boolean pl_io_clear (int hcam);

	/*****************************************************************************/
	/*****************************************************************************/
	/*                                                                           */
	/*             Class 4: Buffer Manipulation Function Prototypes              */
	/*                                                                           */
	/*****************************************************************************/
	/*****************************************************************************/

	/*****************************************************************************/
	/* bit_depth         Bit depth of buffer to allocate (PRECISION_UNS16,...)   */
	/* exp_num           Exposure number to get information about                */
	/* year              Year exposure was taken                                 */
	/* month             Month of the year the exposure was taken                */
	/* day               Day of the month the exposure was taken                 */
	/* hour              Hour of the Day the exposure was taken                  */
	/* min               Minute of the Hour the exposure was taken               */
	/* sec               Second of the Minute the exposure was taken             */
	/* msec              Millisecond of the Second the exposure was taken        */
	/* exp_msec          Exposure duration (in milliseconds)                     */
	/* total_exps        Number of exposures in buffer                           */
	/* himg              Single image in buffer                                  */
	/* ibin              Serial binning factor of image                          */
	/* jbin              Parallel binning factor of image                        */
	/* img_num           Number of region in exposure to retrieve                */
	/* s_ofs             Serial offset of image                                  */
	/* p_ofs             Parallel offset of image                                */
	/* x_size            Width of image                                          */
	/* y_size            Height of image                                         */
	/* totl_imgs         Number of images in each exposure                       */
	/* buf_size          Size of buffer in bytes                                 */
	/*****************************************************************************/
/*
	  public native boolean pl_buf_init ();
	  rs_bool PV_DECL pl_buf_uninit (void);

	  rs_bool PV_DECL pl_buf_alloc (int16_ptr hbuf, int16 exp_total,
	                                int16 bit_depth, int16 rgn_total,
	                                rgn_const_ptr rgn_array);
	  rs_bool PV_DECL pl_buf_get_bits (int16 hbuf, int16_ptr bit_depth);

	  rs_bool PV_DECL pl_buf_get_img_bin (int16 himg, int16_ptr ibin,
	                                      int16_ptr jbin);
	  rs_bool PV_DECL pl_buf_get_img_handle (int16 hbuf, int16 exp_num,
	                                         int16 img_num, int16_ptr himg);
	  rs_bool PV_DECL pl_buf_get_img_ofs (int16 himg, int16_ptr s_ofs,
	                                      int16_ptr p_ofs);
	  rs_bool PV_DECL pl_buf_get_img_ptr (int16 himg, void_ptr_ptr img_addr);
	  rs_bool PV_DECL pl_buf_get_img_size (int16 himg, int16_ptr x_size,
	                                       int16_ptr y_size);
	  rs_bool PV_DECL pl_buf_get_img_total (int16 hbuf, int16_ptr totl_imgs);
	  rs_bool PV_DECL pl_buf_get_size (int16 hbuf, int32_ptr buf_size);
	  rs_bool PV_DECL pl_buf_free (int16 hbuf);

*/ 
/* not supported by the cam for the moment */

	static {
		
		RLoader.loadResourceLibrary("PVCAM");

	}

}
