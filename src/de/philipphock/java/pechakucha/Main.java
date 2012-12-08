package de.philipphock.java.pechakucha;

public class Main {
	
	public static void main(String[] args) {
		
		int delayFirst = 10;
		int slideCount = 15;
		int slideSeconds = 20;
		

		
		if (args.length == 3){
			delayFirst = Integer.parseInt(args[0]);
			slideCount = Integer.parseInt(args[1]);
			slideSeconds = Integer.parseInt(args[2]);
		}
		
		
		Presenter p = new Presenter(delayFirst,slideCount,slideSeconds);
		
		InfoDisplay d = new InfoDisplay(p);
		d.setVisible(true);
		
		
		Tray t = new Tray(p,d);
		
		p.addPresenterStateListener(t);
		p.addPresenterStateListener(d);
		
		
		
		
	}
}
