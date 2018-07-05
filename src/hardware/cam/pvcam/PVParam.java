package hardware.cam.pvcam;

public class PVParam {
	
	/******************************************************************************
	   Copyright (C) Roper Scientific, Inc. 1990-2000. All rights reserved.
	******************************************************************************/



	/************************ Class 2: Data types ********************************/
	/* Data type used by pl_get_param with attribute type (ATTR_TYPE).           */



	public static final int CLASS0 = 0;
	public static final int CLASS1 = 1;
	public static final int CLASS2 = 2;
	public static final int CLASS3 = 3;
	public static final int CLASS4 = 4;
	public static final int CLASS5 = 5;
	public static final int CLASS6 = 6;
	public static final int  CLASS7 =		7;		   /* Bridge Commands							 */
	public static final int CLASS29 =    29;         /* Buffer Functions                           */
	public static final int CLASS30 =    30;         /* Utility functions                          */
	public static final int CLASS31 =    31;         /* Memory Functions                           */
	public static final int CLASS32 =    32;         /* CCL Engine                                 */
	public static final int CLASS91 =    91;         /* RS170                                      */
	public static final int CLASS92 =    92;         /* Defect Mapping                             */
	public static final int CLASS93 =    93;         /* Fast frame operations (PIV/ACCUM/Kinetics) */
	public static final int CLASS94 =    94;         /* PTG                                        */
	public static final int CLASS95 =    95;         /* Virtual Chip                               */
	public static final int CLASS96 =    96;         /* Acton diagnostics.                         */
	public static final int CLASS97 =    97;         /* Custom Chip                                */
	public static final int CLASS98 =    98;         /* Custom timing                              */
	public static final int CLASS99 =    99;         /* Trenton diagnostics.                       */
	
	
	/************************ Parameter IDs **************************************/
	
	/* Parameters PVCAM PARAM     */

	public static final int PARAM_CCS_STATUS = 16908798;
	public static final int PARAM_CLEAR_MODE = 151126539;
	public static final int PARAM_PAR_SIZE = 100794425;
	public static final int PARAM_SER_SIZE = 100794426;
	
	/*CCD Temperature control */

	public static final int PARAM_TEMP = 16908813;
	public static final int PARAM_TEMP_SETPOINT = 16908814;

	/*readout */

	public static final int PARAM_READOUT_TIME  = 67240115 ;
	public static final int PARAM_CLEAR_CYCLES = 100794465 ;
	public static final int PARAM_EXPOSURE_MODE = 151126551;

	        /* SPEED TABLE PARAMETERS (CLASS 2) */

	public static final int PARAM_BIT_DEPTH = 16908799;
	public static final int PARAM_GAIN_INDEX = 16908800;
	public static final int PARAM_SPDTAB_INDEX = 16908801;
	public static final int PARAM_READOUT_PORT = 151126263;
	public static final int PARAM_PIX_TIME = 100794884;

	        /* SHUTTER PARAMETERS (CLASS 2) */

	public static final int PARAM_SHTR_CLOSE_DELAY = 100794887;
	public static final int PARAM_SHTR_OPEN_DELAY = 100794888;
	public static final int PARAM_SHTR_OPEN_MODE = 151126537;
	public static final int PARAM_SHTR_STATUS = 151126538;
	public static final int PARAM_SHTR_CLOSE_DELAY_UNIT = 151126559;  /* use enum TIME_UNITS to specify the unit */


	        /* I/O PARAMETERS (CLASS 2) */

	public static final int PARAM_IO_ADDR  = 100794895;
	public static final int PARAM_IO_TYPE = 151126544;
	public static final int PARAM_IO_DIRECTION = 151126545;
	public static final int PARAM_IO_STATE = 67240466;
	public static final int PARAM_IO_BITDEPTH = 100794899;


	        /* ACQUISITION PARAMETERS (CLASS 3) */

	public static final int PARAM_EXP_TIME = 100859905;
	public static final int PARAM_EXP_RES = 151191554;
	public static final int PARAM_EXP_MIN_TIME = 67305475;
	public static final int PARAM_EXP_RES_INDEX = 100859908;

	        /* PARAMETERS FOR  BEGIN and END of FRAME Interrupts */
	public static final int PARAM_BOF_EOF_ENABLE = 151191557;
	public static final int PARAM_BOF_EOF_COUNT = 117637126;
	public static final int PARAM_BOF_EOF_CLR = 184745991;


	/* Test to see if hardware/software can perform circular buffer */
	public static final int PARAM_CIRC_BUFFER = 184746283;

	
	

	
	


	
	
	
	public static final int TYPE_BOOLEAN = 11;
	public static final int TYPE_ENUM = 9;
	public static final int TYPE_FLT64 = 4;
	public static final int TYPE_INT16 = 1;
	public static final int TYPE_UNS16 = 6;
	public static final int TYPE_CHAR_PTR  =  13;
	
	
	public static final int  TYPE_INT8      =   12;
	public static final int  TYPE_UNS8     =     5;
	public static final int  TYPE_INT32    =     2;
	public static final int  TYPE_UNS32    =     7;
	public static final int  TYPE_UNS64    =     8;
	public static final int  TYPE_VOID_PTR =    14;
	public static final int  TYPE_VOID_PTR_PTR = 15;

	        /* DEVICE DRIVER PARAMETERS (CLASS 0) */

	/*  Class 0 (next available index for class zero = 6) */







	/********************** Class 0: Open Camera Modes ***************************/
	/*
	  Function: pl_cam_open()
	  PI Conversion: CreateController()
	*/
	public static final int OPEN_EXCLUSIVE = 0;
	public static final int ERROR_MSG_LEN  = 255 ;     /* No error message will be longer than this */

	public static final int NORMAL_COOL = 0;
	public static final int CRYO_COOL = 1;


	/************************** Class 2: Name/ID sizes ***************************/
	public static int CCD_NAME_LEN =  17    ;       /* Includes space for the null terminator */
	public static int MAX_ALPHA_SER_NUM_LEN  = 32;   /* Includes space for the null terminator */
	public static int MAX_PP_NAME_LEN = 32 ;

	/************************** Class 2: Shutter flags ***************************/
	/* used with the PARAM_SHTR_STATUS parameter id.                            
	  PI Conversion: n/a   (returns SHTR_OPEN)
	*/
	public static final int SHTR_FAULT = 0;
	public static final int SHTR_OPENING = 1;
	public static final int SHTR_OPEN = 2;
	public static final int SHTR_CLOSING = 3;
	public static final int SHTR_CLOSED = 4;

	public static final int EXP_RES_ONE_MILLISEC = 0;
	public static final int EXP_RES_ONE_MICROSEC = 1;
	public static final int EXP_RES_ONE_SEC  = 2;
	/************************ Class 2: Attribute IDs *****************************/
	/*
	  Function: pl_get_param()
	*/
	public static final int ATTR_ACCESS = 7 ;
	public static final int ATTR_AVAIL = 8;
	public static final int ATTR_COUNT = 1;
	public static final int ATTR_CURRENT = 0;
	public static final int ATTR_DEFAULT = 5;
	public static final int ATTR_INCREMENT = 6;
	public static final int ATTR_MAX = 4;
	public static final int ATTR_MIN = 3;
	public static final int ATTR_TYPE = 2;


	/************************ Class 2: Access types ******************************/
	/*
	  Function: pl_get_param( ATTR_ACCESS )
	*/
	public static final int ACC_ERROR = 0;
	public static final int ACC_READ_ONLY = 1;
	public static final int ACC_READ_WRITE = 2;
	public static final int ACC_EXIST_CHECK_ONLY = 3;
	public static final int ACC_WRITE_ONLY = 4;

	/* This enum is used by the access Attribute */

	/************************ Class 2: I/O types *********************************/
	/* used with the PARAM_IO_TYPE parameter id.                                 */
	public static final int IO_TYPE_TTL = 0;
	public static final int IO_TYPE_DAC = 1;
	
	/************************ Class 2: I/O direction flags ***********************/
	/* used with the PARAM_IO_DIRECTION parameter id.                            */
	public static final int O_DIR_INPUT = 0;
	public static final int IO_DIR_OUTPUT = 1;
	public static final int IO_DIR_INPUT_OUTPUT = 2;
	
	/************************ Class 2: I/O port attributes ***********************/
	public static final int IO_ATTR_DIR_FIXED = 0;
	public static final int IO_ATTR_DIR_VARIABLE_ALWAYS_READ = 1;

	/************************ Class 2: Trigger polarity **************************/
	/* used with the PARAM_EDGE_TRIGGER parameter id.                            */
	public static final int EDGE_TRIG_POS = 2;
	public static final int EDGE_TRIG_NEG = 3;

	/************************ Class 2: Logic Output ******************************/
	/* used with the PARAM_LOGIC_OUTPUT parameter id.                            */
	public static final int OUTPUT_NOT_SCAN = 0;
	public static final int OUTPUT_SHUTTER = 1;
	public static final int OUTPUT_NOT_RDY = 2;
	public static final int OUTPUT_LOGIC0 = 3;
	public static final int OUTPUT_CLEARING = 4;
	public static final int OUTPUT_NOT_FT_IMAGE_SHIFT = 5;
	public static final int OUTPUT_RESERVED = 6;
	public static final int OUTPUT_LOGIC1 = 7;
	
	/************************ Class 2: Readout Port ******************************/
	/* used with the PARAM_READOUT_PORT parameter id.                            */
	public static final int READOUT_PORT_MULT_GAIN = 0;
	public static final int READOUT_PORT_NORMAL = 1;
	public static final int READOUT_PORT_LOW_NOISE = 2;
	public static final int READOUT_PORT_HIGH_CAP = 3;



	/************************ Class 2: Clearing mode flags ***********************/
	/* used with the PARAM_CLEAR_MODE parameter id.                              */
	public static final int CLEAR_NEVER = 0;
	public static final int CLEAR_PRE_EXPOSURE = 1;
	public static final int CLEAR_PRE_SEQUENCE = 2;
	public static final int CLEAR_POST_SEQUENCE =3;
	public static final int CLEAR_PRE_POST_SEQUENCE = 4;
	public static final int CLEAR_PRE_EXPOSURE_POST_SEQ = 5;
	public static final int MAX_CLEAR_MODE = 6;

	/************************ Class 2: Shutter mode flags ************************/

	public static final int OPEN_NEVER = 0;
	public static final int OPEN_NO_CHANGE = 4;
	public static final int OPEN_PRE_EXPOSURE = 1;
	public static final int OPEN_PRE_SEQUENCE = 2;
	public static final int OPEN_PRE_TRIGGER = 3;
	/************************ Class 2: Exposure mode flags ***********************/

	public static final int TIMED_MODE =0;
	public static final int STROBED_MODE = 1;
	public static final int BULB_MODE = 2;
	public static final int TRIGGER_FIRST_MODE = 3;
	public static final int FLASH_MODE = 4;
	public static final int VARIABLE_TIMED_MODE = 5;
	public static final int INT_STROBE_MODE = 6;
	public static final int MAX_EXPOSE_MODE = 7;


	/********************** Class 3: Readout status flags ************************/
	public static final int READOUT_NOT_ACTIVE = 0;
	public static final int EXPOSURE_IN_PROGRESS = 1;
	public static final int READOUT_IN_PROGRESS = 2;
	public static final int READOUT_COMPLETE =    3;               /* Means frame available for a circular buffer acq */
	public static final int FRAME_AVAILABLE = 3;
	public static final int READOUT_FAILED = 4;
	public static final int ACQUISITION_IN_PROGRESS = 5;
	public static final int MAX_CAMERA_STATUS = 6;

	/********************** Class 3: Abort Exposure flags ************************/
	public static final int CCS_CLEAR = 3;
	public static final int CCS_CLEAR_CLOSE_SHTR = 4;
	public static final int CCS_CLEAR_OPEN_SHTR = 6;
	public static final int CCS_HALT = 1;
	public static final int CCS_HALT_CLOSE_SHTR = 2;
	public static final int CCS_NO_CHANGE = 0;
	public static final int CCS_OPEN_SHTR = 5;

	/************************ Class 3: Event constants ***************************/
	public static final int EVENT_START_READOUT = 0;
	public static final int VENT_END_READOUT =1;

	/************************ Class 3: EOF/BOF constants *************************/
	/* used with the PARAM_BOF_EOF_ENABLE parameter id.                          */
	public static final int NO_FRAME_IRQS = 0;
	public static final int BEGIN_FRAME_IRQS = 1;
	public static final int END_FRAME_IRQS = 2 ;
	public static final int BEGIN_END_FRAME_IRQS = 3;

	/************************ Class 3: Continuous Mode constants *****************/
	/*
	  Function: pl_exp_setup_cont()
	*/
	public static final int CIRC_NONE = 0;
	public static final int CIRC_OVERWRITE = 1;
	public static final int CIRC_NO_OVERWRITE =2;


	/************************ Class 3: Callback constants *************************/

	public static enum _PL_CALLBACK_EVENT {
		PL_CALLBACK_BOF ,
		PL_CALLBACK_EOF,
		PL_CALLBACK_CHECK_CAMS,
		PL_CALLBACK_CAM_REMOVED,
		PL_CALLBACK_CAM_RESUMED,
		PL_CALLBACK_MAX
	}


	/********************** Class 4: Buffer bit depth flags **********************/
	public static final int PRECISION_INT8 = 0;
	public static final int PRECISION_UNS8 = 1;
	public static final int PRECISION_INT16 = 2;
	public static final int PRECISION_UNS16= 3;
	public static final int PRECISION_INT32= 4;
	public static final int PRECISION_UNS32 = 5;

}


