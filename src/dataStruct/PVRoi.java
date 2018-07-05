package dataStruct;


public class PVRoi {
	
	private int xStart, xEnd, yStart, yEnd;

	public PVRoi(int x, int y , int xd, int yd){
		xStart = x;
		xEnd = xd;
		yStart = y;
		yEnd = yd;
	}
	
	public PVRoi (CRoi roi){
		xStart = roi.getX();
		xEnd = roi.getX() + roi.getWidth();
		yStart = roi.getY();
		yEnd = roi.getY() + roi.getHeight();
	}
	
	public int getWidth(){
		return (xEnd - xStart);
	}
	
	public int getHeight(){
		return (yEnd - yStart);
	}
	
	public CRoi convertToCRoi(){
		return new CRoi(xStart,yStart,xEnd - xStart,yEnd - yStart);
	}
	
	
	
	
	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}
	
	

}
