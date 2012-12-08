package de.philipphock.java.pechakucha;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;





public class Presenter  extends PresenterController {

	private final int delayFirstSeconds;
	private int delayFirstSecondsPassed;
	
	private final int numSlides;
	private int slideCount=0;
	
	
	private boolean firstPlay=true;
	
	private final int secondsForSlide;
	private int secondsForSlidePassed;

	private Robot robot;
	
	private final Ticker ticker;
	
	public Presenter(int delayFirstSeconds, int numSlides, int secondsForSlide) {
		super();
		resetState();
		this.delayFirstSeconds=delayFirstSeconds;
		this.numSlides=numSlides;
		this.secondsForSlide=secondsForSlide;
		ticker = new Ticker(this);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
				
	}


	private void resetState(){
		delayFirstSecondsPassed=0;
		secondsForSlidePassed=0;
		slideCount=0;
		firstPlay=true;
	}
	
	public void nextSlide(){
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

	@Override
	protected void stateChangedStopped() {
		ticker.pause();
		
	}

	@Override
	protected void stateChangedStarted() {
		ticker.play();
		if (firstPlay){
			firstPlay=false;
		}
		
	}

	public void tick(){

		delayFirstSecondsPassed++;
		
		
		if (delayFirstSecondsPassed >= delayFirstSeconds){
			secondsForSlidePassed++;
			//delay over, start
			if (firstPlay){
				firstPlay=false;
				
			}
			
			if (secondsForSlidePassed>=secondsForSlide){
				//next slide
				slideCount++;
				secondsForSlidePassed=0;
				
				
				if (numSlides-slideCount<=0){
					//presentation over
					switchState();
					msgPresenterStateListeners("presentation finished");
					resetState();
					return;
				}
				nextSlide();
			}
			msgPresenterStateListeners("Seconds until next slide: "+(secondsForSlide-secondsForSlidePassed)+" <br>Slides left: "+(numSlides-slideCount-1));
			
			
			
		}else{
			//delay phase
			msgPresenterStateListeners("Seconds until start: "+(delayFirstSeconds-delayFirstSecondsPassed));
		}
		
		
	}
	
	

}
