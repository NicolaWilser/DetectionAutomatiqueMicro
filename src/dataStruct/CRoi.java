package dataStruct;

import ij.gui.Roi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



public class CRoi {
	private Rectangle r;
	private Rectangle hul,hu,hur,hmr,hlr,hl,hll,hml;
	
	private int hndw = 7, hndh = 7;
	
	public static final int UPER_LEFT_H = 1;
	public static final int UPER_H = 2;
	public static final int UPER_RIGHT_H = 3;
	public static final int MID_RIGHT_H = 4;
	public static final int LOWER_RIGHT_H = 5;
	public static final int LOWER_H = 6;
	public static final int LOWER_LEFT_H = 7;
	public static final int MID_LEFT_H = 8;
	
	
	
	public CRoi(int x, int y, int w , int h){
		r = new Rectangle(x,y,w,h);
		createHandles(r);
	}
	public CRoi(double x, double y, double w , double h){
		r= new Rectangle((int) x,(int)y,(int)w,(int)h);
		createHandles(r);
	}
	
	public Roi getImageJRoi(){
		return new Roi(r.x,r.y,r.width,r.height);
	}
	
	private void createHandles(Rectangle mr){
		int x = (int) mr.getX();
		int y = (int) mr.getY();
		int w = (int) mr.getWidth();
		int h = (int) mr.getHeight();
		
		hul = makeHandle(x,y);
		hu = makeHandle(x+(w/2),y);
		hur = makeHandle(x+w,y);
		hmr = makeHandle(x+w,y+(h/2)); 
		hlr = makeHandle(x+w,y+h);
		hl = makeHandle(x+(w/2),y+h);
		hll = makeHandle(x,y+h);
		hml = makeHandle(x,y+(h/2));
	
	}
	
	public Rectangle makeHandle(int x, int y){
		return new Rectangle(x - (hndw / 2), y - (hndh/2),hndw, hndh);
	}
	
	private void drawHandle(Rectangle rec, Graphics g){
		g.setColor(Color.white);
		g.fillRect(((int) rec.getX()), ((int)rec.getY()) , hndw, hndh);
	}
	
	
	public boolean isInside(int xp, int yp){
		if (r.contains(xp,yp)){
			return true;
		} else {
			return false;
		}
	}
	
	
	public int isOnHandle(int xp , int yp){
		if (hul.contains(xp,yp)){
			return UPER_LEFT_H;
		} 
		if (hu.contains(xp,yp)){
			return UPER_H;
		}
		if (hur.contains(xp,yp)){
			return UPER_RIGHT_H;
		}
		if (hmr.contains(xp,yp)){
			return MID_RIGHT_H;
		}
		if (hlr.contains(xp,yp)){
			return LOWER_RIGHT_H;
		}
		if (hl.contains(xp,yp)){
			return LOWER_H;
		}
		if (hll.contains(xp,yp)){
			return LOWER_LEFT_H;
		}
		if (hml.contains(xp,yp)){
			return MID_LEFT_H;
		}
		
		return 0;
	}
	
	public Rectangle getBounds(){
		return r;
	}
	public int getX(){
		return ((int) r.getX());
	}
	public int getY(){
		return ((int) r.getY());
	}
	
	public int getWidth(){
		return ((int)r.getWidth());
	}
	public int getHeight(){
		return ((int)r.getHeight());
	}
	public void setHandleWH(int wh){
		hndw = wh;
		hndh = wh;
	}
	
	
	public void moveRoi(int dx, int dy){
		r.translate(dx, dy);
		createHandles(r);
	}
	
	private void moveUp(int dx){
		//if ((r.y + dx) < (r.y + r.height)){
		if ((r.height - dx)> 0){
		 r.y = r.y + dx;
		 r.height = r.height - dx;
		}
	}
	
	private void moveDown(int dx){
		if ((r.height + dx)> 0){
		 r.height = r.height + dx;
		}
	}
	
	private void moveLeft( int dx){
		if ((r.width - dx) > 0){
			r.x = r.x + dx;
			r.width = r.width - dx;
		}
	}
	private void moveRight(int dx){
		if ((r.width + dx) > 0){
		 r.width = r.width + dx;
		}
	}
	
	public void resizeRoi(int handle, int dx, int dy){
		
		switch (handle) {
		
		case 1:{
			moveUp(dy);
			moveLeft(dx);
			break;
		}
		case 2:{
			moveUp(dy);
			break;
		}
		case 3:{
			moveUp(dy);
			moveRight(dx);
			break;
		}
		case 4:{
			moveRight(dx);
			break;
		}
		case 5:{
			moveRight(dx);
			moveDown(dy);
			break;
		}
		case 6:{
			moveDown(dy);
			break;
		}
		case 7:{
			moveDown(dy);
			moveLeft(dx);
			break;
		}
		case 8:{
			moveLeft(dx);
			break;
		}
		default:
		}
		
	    createHandles(r);
	}
	public String toString(){
		return "x-y: " + r.x + "-" + r.y + "/ w-h: " + r.width + "-" + r.height;
	}
	
	public int getMiddleX(){
		return ((int) r.getCenterX());
	}
	public int getMiddleY(){
		return ((int) r.getCenterY());
	}
	
	public void drawRoi(Graphics g){
		int x,y,w,h;
		x = (int) r.getX();
		y = (int) r.getY();
		w = (int) r.getWidth();
		h = (int) r.getHeight();
		g.setColor(Color.yellow);
		g.drawRect(x, y, w, h);

		drawHandle(hul,g);
		drawHandle(hu,g);
		drawHandle(hur,g);
		drawHandle(hmr,g);
		
		drawHandle(hlr,g);
		drawHandle(hl,g);
		
		drawHandle(hll,g);
		drawHandle(hml,g);
	}
	
	public CRoi convertToPVCAM(){
		return new CRoi(r.getX(),r.getY(),r.getWidth() + r.getX(),r.getHeight() + r.getY());
	}

}
