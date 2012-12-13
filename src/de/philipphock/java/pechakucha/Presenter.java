/**
 * @author Philipp Hock
 */
/*
Copyright (c) <year> <copyright holders>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.philipphock.java.pechakucha;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;




/**
 * 
 * The main control class for presentation.
 * Uses the Ticker to periodically perform an action (tick())
 * 
 * Implements the next-slide, presentation starts, ends state machine. 
 *
 */
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

	/**
	 * resets the state of the presenter
	 */
	private void resetState(){
		delayFirstSecondsPassed=0;
		secondsForSlidePassed=0;
		slideCount=0;
		firstPlay=true;
	}
	
	/**
	 * performs a systemwide "next-slide" keystroke (pgdown)
	 */
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

	
	/**
	 * callback for the ticker
	 */
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
