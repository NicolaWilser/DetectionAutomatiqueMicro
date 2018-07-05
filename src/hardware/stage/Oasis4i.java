package hardware.stage;
import resources.RLoader;

public class Oasis4i {

	///////////////// OI DLL finalANTS /////////////////
	
	public boolean status = true;

	///////////////////////////////////////////
	// Return Codes
	///////////////////////////////////////////
	public static final short OI_OK = 0;
	public static final short OI_FAILED = 1;
	public static final short OI_ABORT = 2;
	public static final short OI_NOHARDWARE  = 4;
	public static final short OI_TIMEOUT = 8;
	public static final short OI_INVALIDARG  = 16;
	public static final short OI_HARDWAREBUSY = 32;
	public static final short OI_ACCESSDENIED = 64;
	public static final short OI_NOTSUPPORTED = 128;
	public static final short OI_ILLEGALAXIS = 256;
	public static final short OI_INVALIDCONFIG = 512;
	public static final short OI_MAXMOVEFAIL = 1024;
	
	///////////////////////////////////////////
	// Axes IDs
	///////////////////////////////////////////
	public static final short OI_XAXIS = 1;
	public static final short OI_YAXIS = 2;
	public static final short OI_ZAXIS = 3;
	public static final short OI_FAXIS = 4;
	public static final short OI_TAXIS = 5;
	
	///////////////////////////////////////////
	// Axis Types (reserved)
	///////////////////////////////////////////
	public static final short OI_TYPE_NOT_FITTED = 0;
	public static final short OI_TYPE_STEPPER = 1;
	public static final short OI_TYPE_DC = 2;
	public static final short OI_TYPE_SIMULATED = 3;
	
	///////////////////////////////////////////
	// Init Methods (reserved)
	///////////////////////////////////////////
	public static final short OI_INITBY_SWITCH = 0;
	public static final short OI_INITBY_MANUAL = 1;
	
	///////////////////////////////////////////
	// Hardware Modes
	///////////////////////////////////////////
	public static final short OI_SIM = 0;
	public static final short OI_OASIS = 1;
	public static final short OI_LEICA_DM = 10;
	public static final short OI_LEICA_DM2 = 11;
	public static final short OI_LEICA_MZ = 12;
	public static final short OI_OLYMPUS_BX = 20;
	public static final short OI_OLYMPUS_IX = 21;
	public static final short OI_NAVITAR = 50;
	public static final short OI_OPTEM = 51;
	
	///////////////////////////////////////////
	// Status Codes
	///////////////////////////////////////////
	public static final short S_LIMIT_PHY_NEG = 0x1;
	public static final short S_LIMIT_USR_NEG = 0x2;
	public static final short S_LIMIT_PHY_POS = 0x8;
	public static final short S_LIMIT_USR_POS = 0x4;
	public static final short S_LIMIT_USR_NEG_SET = 0x10;
	public static final short S_LIMIT_USR_POS_SET = 0x20;
	public static final short S_INITIALIZED = 0x40;
	public static final short S_DIRECTION = 0x80;
	public static final short S_MOVING = 0x100;
	public static final int S_MOTOR_DETECTED = 0x8000;
	
	///////////////////////////////////////////
	// PCB STATUS BITS
	/*
	'Bit meaning
	'----------------
	'31  Reserved
	'30  Reserved
	'29  Reserved
	'28  Reserved
	'27  Reserved
	'26  Reserved
	'25  Reserved
	'24  Reserved
	'23  Reserved
	'22  Reserved
	'21  Reserved
	'20  Reserved
	'19  Flash_user_OK       ( 1 = Flash user area checksum OK )
	'18  Flash_OI_OK         ( 1 = Flash OI area checksum OK )
	'17  Reserved
	'16  Joystick_detect     ( 1 = Joystick unit fitted )
	'15  Reserved
	'14  Trackball_detect        ( 1 = Serial device is Kensington Trackball 5)
	'13  Mouse_detect            ( 1 = Serial device is standard 2-button mouse)
	'12  Serial_Device_detect        ( 1 = Serial device detected on RS232_0)
	'11  Camera_type         ( 1 = Colour, 0 = Mono )
	'10  Camera_frequency        ( 1 = 50 Hz, 0 = 60 Hz )
	'9   Camera_channel      ( 1 = Channel 3, 0 = channel 0 (default))
	'8   Camera_detect       ( 1 = Camera input detected )
	'7   Video_Encoder_OK        ( 1 = Encoder configured OK )
	'6   Autofocus_Module_type_1 ( 2 bit code indicating module type )
	'5   Autofocus_Module_type_0 ( 2 bit code indicating module type )
	'4   Autofocus_Module_fitted ( 1 = Module Detected )
	'3   ADC_Analogue_input_X    ( 1 = ADC zero (correct) at switch on )
	'2   ADC_Analogue_input_Y    ( 1 = ADC zero (correct) at switch on )
	'1   PCB_Temperature     ( 1 = Temperature too high )
	'0   Motor_volts_OK      ( 1 = Motor supply >= 10V i.e. OK )
	'*/
	///////////////////////////////////////////
	public static final short S_PCB_MOTOR_VOLTS_OK = 0x1;
	public static final short S_PCB_TEMP_OK = 0x2;
	public static final short S_PCB_ADC_YIN_OK = 0x4;
	public static final short S_PCB_ADC_XIN_OK = 0x8;
	public static final short S_PCB_AF_FITTED = 0x10;
	public static final short S_PCB_AF_TYPE0 = 0x20;
	public static final short S_PCB_AF_TYPE1 = 0x40;
	public static final short S_PCB_VIDEO_ENCODER_OK = 0x80;
	public static final short S_PCB_CAMERA_DETECTED = 0x100;
	public static final short S_PCB_CAMERA_CHANNEL = 0x200;
	public static final short S_PCB_CAMERA_FREQ = 0x400;
	public static final short S_PCB_CAMERA_TYPE = 0x800;
	public static final short S_PCB_SERIAL_DEV_DETECTED = 0x1000;
	public static final short S_PCB_MOUSE_DETECTED = 0x2000;
	public static final short S_PCB_TRACKBALL_FITTED = 0x4000;

	public static final int S_PCB_JOYSTICK_FITTED = 0x10000;

	public static final int S_PCB_FLASH_OI_OK = 0x40000;
	public static final int S_PCB_FLASH_USER_OK  = 0x80000;
	
	
	///////////////////////////////////////////
	// Range finalants
	///////////////////////////////////////////
	public static final short OI_CRUISE_MIN = 0;
	public static final short OI_CRUISE_MAX = 511;
	public static final short OI_RAMP_MIN = 0;
	public static final short OI_RAMP_MAX = 3;
	public static final short OI_DRIVE_SPEED_MIN = -4096;
	public static final short OI_DRIVE_SPEED_MAX = 4096;
	
	
	////////////////////////////////////////////////////////
	// Filter initialization methods
	////////////////////////////////////////////////////////
	public static final short OI_FILTER_INIT_HOME = 0;
	public static final short OI_FILTER_INIT_LIMITS = 1;
	public static final short OI_FILTER_INIT_USER = 2;
	
	///////////////////////////////////////////////////////
	// OI-SC4 Shutter Controller masks
	////////////////////////////////////////////////////////
	public static final short SHUTTER1 = 1;
	public static final short SHUTTER2 = 2;
	public static final short SHUTTER3 = 4;
	public static final short SHUTTER4 = 8;
	
	///////////////////////////////////////////////////////////////////////
	// Hardware defs
	public double passStep = 0.25 ; // bad idea but well, bite me
	// All Getters return the wanted values, to check the call result use getLastResult methode (also can be used for all calls)
	
	public native int getLastCallResult();
	public native int OI_Open();
	public native int OI_Close();
	
	
	public native int OI_GetDriverOpen(); // returns the result (byref in original)
	public native int OI_SetHardwareMode(int nMode);
	public native int OI_GetHardwareMode();
	
	
	public native int OI_ReadPCBStatus();
	public native double OI_ReadPCBTemperature ();
	
	public native int OI_CountCards();
	public native int OI_EnableMotorPower(boolean bXYEnabled, boolean bZFEnabled);
	///////////////////////////////////////////////////////////////////////
	// Version functions
	
	public native String OI_GetDriverVersion ();
	public native String OI_ReadPCBID();
	public native String OI_ReadPCBVersion();
	
	///////////////////////////////////////////////////////////////////////
	// General, Single Axis
	public native int OI_GetTotalAxisCount();
	public native short OI_GetAxisEnabled( int AxisID);
	public native int OI_MoveAxis(int AxisID, double dValue , int nWait);
	public native double OI_ReadAxis (int AxisID);
	public native double[] OI_GetAxisRange (int axisID);
	public native int OI_SetAxisStepSize (int axisID, double dStepSize);
	public native double OI_GetAxisStepSize (int axisID);
	public native int OI_SetAxisBacklash (int axisID, boolean bEnabled);
	public native boolean OI_GetAxisBacklash (int axisID);
	public native int OI_GetAxisCruise (int axisID);
	public native int OI_SetAxisCruise (int axisID,int nCruise);
	public native int OI_GetAxisSense (int axisID);
	public native int OI_SetAxisSense (int axisID, int nSense);
	public native int OI_GetAxisRamp (int axisID);
	public native int OI_SetAxisRamp (int axisID, int nRamp);
	public native int OI_DriveAxisContinuous (int axisID, int nSpeed);
	public native int OI_StepAxis (int axisID, double dValue, int nWait);
	public native int OI_SetAxisInitMethod (int axisID, int nMethod);
	public native int OI_GetAxisInitMethod (int axisID);
	public native short[] OI_ReadAxisAtLimit (int axisID);// ByRef pbAtNegLimit As Short, ByRef pbAtPosLimit As Short
	public native boolean OI_ReadAxisMoving (int axisID);
	public native short OI_ReadAxisStatus (int axisID);
	public native int OI_SetAxisToDefaults (int axisID);
	public native short[] OI_ReadAxisRampValue (int axisID, short wIndex);// ByRef lpwInterval As Short, ByRef lpwStepSize As Short
	public native int OI_HaltAxis (int axisID);
	public native int OI_GetAxisMaxMove (int axisID);
	public native int OI_GetAxisStepsPerRev (int axisID);
	public native int OI_MoveAxisAbs (int axisID, int lPos ,int nWait );
	public native int OI_StepAxisAbs (int axisID, int lPos ,int nWait );
	public native int OI_ReadAxisAbs (int axisID);
	public native int[] OI_GetAxisRangeAbs (int axisID);// ByRef plMin As Integer, ByRef plMax As Integer);
	public native int OI_WaitForAxisStopped (int axisID);
	
	///////////////////////////////////////////////////////////////////////
	// Full axes
	
	public native int OI_SetPositionXYZ (double xpos, double ypos, double zpos);
	public native int OI_MoveToXYZ (double xpos, double ypos, double zpos, int nWait);
	public native int OI_MoveToXYZ_Auto (double xpos, double ypos, double zpos, int nWait);
	public native double[] OI_ReadXYZ();
	public native int OI_WaitForStoppedXYZ (int xstop, int ystop, int zstop);
	public native int OI_DriveContinuousXYZ (int nXSpeed, int nYSpeed, int nZSpeed);
	///////////////////////////////////////////////////////////////////////
	// XY Stage conveniences: X and Y Axes combined
	
	public native int OI_InitializeXY ();
	public native int OI_SetPositionXY (double xpos, double ypos);
	public native int OI_SetOriginXY ();
	public native double[] OI_ReadXY ();
	public native int OI_MoveToXY (double xpos, double ypos,int nWait);
	public native int OI_MoveToXY_Auto (double xpos, double ypos, int nWait);
	public native int OI_StepXY (double dXDistance, double dYDistance , int nWait);
	public native int OI_StepX (double dXDistance, int nWait);
	public native int OI_StepY (double dYDistance, int nWait);
	public native short[] OI_ReadStatusXY ();//ByRef lpwXStatus As Short, ByRef lpwYStatus As Short
	public native double[] OI_GetPitchXY (); //ByRef pdXPitch As Double, ByRef pdYPitch As Double
	public native int OI_SetPitchXY (double dXPitch, double dYPitch );
	public native int OI_SetRampXY (int nXRamp, int nYRamp);
	public native int[] OI_GetRampXY (); //ByRef pnXRamp As Integer, ByRef pnYRamp As Integer
	public native int OI_SetCruiseXY (int nXCruise, int nYCruise);
	public native int[] OI_GetCruiseXY (); //ByRef pnXCruise As Integer, ByRef pnYCruise As Integer);
	public native int OI_SetDriveSenseXY (int nXDir, int nYDir);
	public native int[] OI_GetDriveSenseXY (); //ByRef pnXDir As Integer, ByRef pnYDir As Integer);
	public native int OI_DriveContinuousXY (int nXSpeed, int nYSpeed);
	public native int OI_HaltXY ();
	public native int OI_SetUserLimitsXY (double dXMin , double dXMax, double dYMin, double dYMax);
	public native double[] OI_GetUserLimitsXY (); //ByRef pdXMin As Double, ByRef pdXMax As Double, ByRef pdYMin As Double, ByRef pdYMax As Double);
	public native int[] OI_ReadLimitAlarmsXY (); //ByRef XNeg As Integer, ByRef xpos As Integer, ByRef YNeg As Integer, ByRef ypos As Integer);
	
	///////////////////////////////////////////////////////////////////////
	// Z Axis
	
	public native int OI_InitializeZ(double ZRangeAbove, double ZRangeBelow);
	public native int OI_SetPositionZ(double zpos);
	public native double OI_ReadZ();
	public native int OI_MoveToZ(double zpos, int nWait);
	public native int OI_StepZ(double ZDistance, int nWait);
	public native short OI_ReadStatusZ ();
	public native int OI_SetRampZ (int nZRamp);
	public native int OI_GetRampZ ();
	public native int OI_SetCruiseZ (int nZCruise);
	public native int OI_GetCruiseZ ();
	public native int OI_SetDriveSenseZ (int nZDir);
	public native int OI_GetDriveSenseZ ();
	public native int OI_DriveContinuousZ (int nSpeed);
	public native int OI_HaltZ ();
	public native double[] OI_ReadRangeZ (); //ByRef pZMin As Double, ByRef pZMax As Double);
	public native int OI_SetUserLimitsZ (double dZMin, double dZMax);
	public native double[] OI_GetUserLimitsZ (); //ByRef pdZMin As Double, ByRef pdZMax As Double);
	public native int OI_SetFocusJoystick (int nLeft, int nRight);
	public native int OI_SetOriginZ ();

	// General stops
	public native int OI_HaltAllAxes ();
	public native int OI_EmergencyStopAll();
	
	///////////////////////////////////////////////////////////////////////
	// Timeouts

	public native int OI_SetMoveTimeout (int dwMSecs);
	public native int OI_GetMoveTimeout ();
	
	///////////////////////////////////////////////////////////////////////
	// File I/O
	public native int OI_SaveSettings (String sFile);
	public native int OI_LoadSettings (String sFile);
	public native int OI_SavePositions (String sFile);
	public native int OI_LoadPositions (String sFile);
	
	///////////////////////////////////////////////////////////////////////
	// Various selection
	
	public native int OI_SetJoystickEnabled(int xen,int yen,int zen);

	///////////////////////////////////////////////////////////////////////
	// Error
	public native int OI_GetLastErrorCmd ();
	public native String OI_GetLastErrorMsg();
	public native int OI_EnableMsgReportDlg (int bEnabled);

	
	public void initializeDriver(){
		OI_SetHardwareMode(Oasis4i.OI_OASIS);
		OI_Open();
	}
	
	
	static {
		RLoader.loadResourceLibrary("joasis4i");
	}

}

