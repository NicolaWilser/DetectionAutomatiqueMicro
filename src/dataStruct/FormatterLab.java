package dataStruct;

public class FormatterLab {
	
	private int avantv, apresv;
	
	public FormatterLab(int d, int p){
		avantv = d;
		apresv = p;
	}
	
	
	public String format(double v){
		
		String val = String.valueOf(Math.abs(v));
		String fp = val.substring(0, val.indexOf("."));
		String sp = val.substring(val.indexOf("."));
		
		if (fp.length() < avantv){
			int nblz = avantv - fp.length();
			for (int i = 0; i < nblz; i++){
				fp = "0".concat(fp);
			}
		}
		
		if ((sp.length() - 1) < apresv){
			int nblz = apresv - sp.length() + 1;
			for (int i = 0; i < nblz; i++){
				sp = sp.concat("0");
			}
		} else {
			sp = sp.substring(0,apresv + 1);
		}
		if (v < 0){
			fp = "-" + fp;
		}
		return (fp.concat(sp));
		
	}
	public String format(float v){
		return format((double) v);
	}
	public String format(int v){
		
		return format((long) v);
	
	}
	public String format(long v){
		String decimal = String.valueOf(Math.abs(v));
		if (decimal.length() < avantv){
			int nblz = avantv - decimal.length();
			for (int i = 0; i < nblz; i++){
				decimal = "0".concat(decimal);
			}
		}
		
		if (apresv > 0){
			decimal = decimal.concat(".");
			for (int i = 0; i < apresv; i++){
				decimal = decimal.concat("0");
			}
		}
		
		if (v < 0){
			decimal = "-" + decimal;
		}
		return decimal;
	}
	public String format(short v){
	  return format((long) v);
	}

}
