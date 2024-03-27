package package13;

import java.util.ArrayList;
import java.util.Random;

public class CreateLine {
	
	private ArrayList<Double> xlist;
	private ArrayList<Double> ylist;
	private Share shared;
	
	public CreateLine(Share shared, double a, double b, double c, double d, double e, double f, double g, double h, double y) {
		this.shared = shared;
	 	xlist = shared.getXlist();
		ylist = shared.getYlist();
		double[] numbers={a,b,c,d,e,f,g,h,y};
		Random rand=new Random();
		for(double num:numbers){
			int sign=rand.nextBoolean()?1:-1;
			double signednumber=sign*num;
		}
		a=numbers[0]*100;
		b=(int)(numbers[1]*100);
		c=(numbers[2]*100*(180/16));
		d=(int)(numbers[3]*100);
		e=numbers[4]*100;
		f=(int)(numbers[5]*100);
		g=(int)(numbers[6]*100*(180/10));
		h=(int)(numbers[7]*100);
		y=numbers[8]*100;
		for (float i = 0 ; i < (2*Math.PI); i+=((2*Math.PI)/360)) {
			double r = ((a * Math.pow(Math.sin(b*i+c),d) + (e * Math.pow(Math.cos(f*i+g),h)+y)));
			xlist.add(r * Math.cos(i));;
			ylist.add(r * Math.sin(i));
		}
	 	shared.setXlist(xlist);
		shared.setYlist(ylist);
	}
	
	public String Describe(double a, double b, double c, double d, double e, double f, double g, double h, double i){
		double multiplier, thetaC, angle, exponent, constant1, constant2, cardioidConstant, gradient, yIntercept;
		String description="This is a shape";
		String circle=description.replace("shape","centred circle with radius");
		String cardioid=description.replace("shape","Cardioid with a cusp at angleNum degrees from the centre");
		String flower=description.replace("shape","flower with petalNum petals of length petalLength");
		String flower2=flower.replace("with", "where the distance between petal intersection and the centre is ");
		String hyperbola=description.replace("shape", "hyperbolaNum hyperbola of distance hyperbolaDist from the centre rotated by ");
		if (f==0){
			multiplier=a;
			thetaC=b;
			angle=c;
			exponent=d;
			constant1=e*Math.pow(Math.cos(g),h)+i; 
			constant2=a*Math.sin(c)+constant1;
			cardioidConstant=270;
			gradient=Math.tan(c);
			yIntercept=a/Math.cos(c);
		}
		else if (g==0){
			multiplier=e;
			thetaC=f;
			angle=g;
			exponent=h;
			constant1=a*Math.pow(Math.sin(c),d)+i;;
			constant2=e*Math.cos(g)+constant1;
			cardioidConstant=180;
			gradient=1/Math.tan(g);
			yIntercept=-e/Math.sin(g);
		}
		else{
			return "Sorry this equation is too complex";
		}
		if (exponent==1 && (thetaC==0||thetaC==1)){
			if(thetaC==0){
				description=circle+String.valueOf(constant2); //centred circle				
			}
			else{
				if(constant1==0){
					description=circle.replace("centred circle","circle centred at ("+((a/2)*Math.sin(c))+ ", "
							       +(a/2)*Math.cos(c)+")")+String.valueOf(Math.pow(a,2)/4); //offcentre circle
				}
				else if (constant1>=a){
					description=cardioid.replace("angleNum",String.valueOf(angle)+cardioidConstant); //cardioid
				}
				else{
					description=cardioid.replace("angleNum",String.valueOf(angle)+cardioidConstant)
						+" and a loop at the same point"; //cardioid with loop
				}
			}
			return description;
		}
		else if (exponent>0 ){
			String petalLength=String.valueOf(multiplier);
			String petalsNum=String.valueOf(2*Math.abs(thetaC));
			if (exponent%2==0){
				if (constant1==0){
					description=flower+" and rotated by angle"+angle+"degrees"; //standard flower
				}
				else if (constant1>multiplier){
					description=flower2.replace("flower", "spiky flower")+String.valueOf(constant1); //spiky flower
				}
				else if (constant1<-multiplier){
					description=flower2.replace("flower", "circular flower with wide petals")+String.valueOf(constant1); //wide petal flower
					petalLength=String.valueOf(i);
				}
				else{
					description=flower.replace("of", "that alternates from wide petals to spiky petals of");
					petalLength=i+" and "+multiplier+" respectively"; //mix of wide and spiky petals flower
				}
			}
			else{
				if(thetaC%2==1){petalsNum=String.valueOf(Math.abs(thetaC));}
				if (constant1==0){
					description=flower+" and rotated by angle"+angle+"degrees"; //standard flower
				}
				else{
					description=flower2+String.valueOf(Math.abs(constant1)-multiplier);
					petalLength=String.valueOf(multiplier+i);
					if (Math.abs(constant1)-multiplier>=0){
						description=description.replace("flower", "where every other spiky flower goes inwards"); //spiky inward flower
					}
					else{
						description=description.replace("flower", "where spiked petals overlap as inwards petals become outwards");
						petalLength=String.valueOf(petalLength)+" and "+String.valueOf(multiplier-Math.abs(i))+"respectively"; //spiky overlap flower
					}
				}
			}
			description=flower.replace("petalNum", petalsNum);
			description=description.replace("petalLength",petalLength);
			return description;
		}
		else {
			if (thetaC==1 && exponent==-1){
				description=description.replace("shape", "line with gradient "+gradient
						       +"y intercept "+ yIntercept); //line
				return description;
			}
			else{
				String hyperbolaNum=String.valueOf(2*Math.abs(thetaC));
				String hyperbolaDist=String.valueOf(constant1);
				description=hyperbola;
				if (exponent%2==1){
					hyperbolaDist="distance between every other hyperbola opposite increases by "+String.valueOf(constant1)
							+"the others decrease by"+String.valueOf(constant1);
					if (thetaC%2==1){
						description=description.replace("of "," but looks like "+thetaC+" as they overlap of");
						hyperbolaDist=hyperbolaDist.replace("other", "overlapping");
					}	
				}
				if (Math.abs(constant1)>multiplier){
					description=description+"and a flower of "+thetaC+" petals forms in the centre";
				}
				description=description.replace("hyperbolaNum", hyperbolaNum);
				description=description.replace("hyperbolaDist", hyperbolaDist)+String.valueOf(angle);
				return description;
			}
		}
	}
	
	
	public ArrayList<Double> getXlist(){
		return xlist;
	}
	
	public ArrayList<Double> getYlist(){
		return ylist;
	}
	
}
