package dataStruct;

import java.io.Serializable;



public class AcquisitionData implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 193956167287549799L;
	private String name;
	private Point3D pos;
	private double zMax;
	private double zMin;
	private double step;
	private int num2d;
	private String emission;
	private String excitation;
	private int exposure;
	private int tempo;
	private CRoi roi;
	private boolean is2d = false;
	
	public boolean is2DType(){
		return is2d;
	}

	public AcquisitionData(String n, Point3D p, int exp, int num, int wt){
		setName(n);
		pos = p;
		exposure = exp;
		setNum2d(num);
		tempo = wt;
		is2d = true;

	}
	public AcquisitionData(String n, Point3D p,double zm, double zmin,double sp){
		setName(n);
		pos = p;
		zMax = zm;
		zMin = zmin;
		step = sp;

	}
	
	public AcquisitionData(String n,double x, double y, double z, double zm, double zmin, double sp){
		setName(n);
		pos = new Point3D(x,y,z);
		zMax = zm;
		zMin = zmin;
		step = sp;
	}
	public AcquisitionData(String n, double[] p, double zm, double zmin, double sp){
		setName(n);
		pos = new Point3D(p);
		zMax = zm;
		zMin = zmin;
		step = sp;
	}
	
	
	public AcquisitionData(String n,Point3D p){
		setName(n);
		pos = p;
		zMax = 0;
		zMin = 0;
		step = 0;
	}
	public AcquisitionData(String n,double[] p){
		setName(n);
		pos = new Point3D(p);
		zMax = 0;
		zMin = 0;
		step = 0;
	}
	
	public AcquisitionData(String n){
		setName(n);
		pos = null;
		zMax = 0;
		zMin = 0;
		step = 0;
	}
	
	public Point3D getPos() {
		return pos;
	}

	public double getzMax() {
		return zMax;
	}

	public double getzMin() {
		return zMin;
	}
	public double getStep() {
		return step;
	}
	
	
	public void setzMax(double m) {
		zMax = m;
	}

	public void setzMin(double m) {
		zMin = m;
	}

	public void setStep(double m) {
		step = m;
	}
	public void setEmission(String emission) {
		this.emission = emission;
	}
	public String getEmission() {
		return emission;
	}
	public void setExcitation(String excitation) {
		this.excitation = excitation;
	}
	public String getExcitation() {
		return excitation;
	}
	public void setExposure(int exposure) {
		this.exposure = exposure;
	}
	public int getExposure() {
		return exposure;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public CRoi getRoi() {
		return roi;
	}

	public void setRoi(CRoi roi) {
		this.roi = new CRoi(roi.getX(), roi.getY(),roi.getWidth(), roi.getHeight());
	}
	public void setRoi(int x, int y, int w, int h) {
		roi = new CRoi(x,y,w,h);
	}
	
	public int getZNum(){
		int res = (int) Math.floor((zMax - zMin) / step);
		if ((zMin + (res * step)) < zMax){
			res = res + 2;
		} else {
			res = res + 1;
		}
		return res;
	}

	public int getNum2d() {
		return num2d;
	}

	public void setNum2d(int num2d) {
		this.num2d = num2d;
	}

	public void setIs2d(boolean is2d) {
		this.is2d = is2d;
	}
	
}
