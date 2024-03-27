package package13;

import java.util.ArrayList;

public class Share {
	
	private ArrayList<Double> xlist;
	private ArrayList<Double> ylist;
	private int select;
	
	public Share() {
		this.xlist = new ArrayList<Double>();
		this.ylist =  new ArrayList<Double>();

	}
	
	public ArrayList<Double> getXlist(){
		return xlist;
	}
	
	public ArrayList<Double> getYlist(){
		return ylist;
	}
	
	public void setXlist(ArrayList<Double> xlist) {
		this.xlist = xlist;
	}
	
	public void setYlist(ArrayList<Double> ylist) {
		this.ylist = ylist;
	}
	
	public int getSelect() {
		return select;
	}
	
	public void setSelect(int select) {
		this.select = select;
	}
	
	
}
