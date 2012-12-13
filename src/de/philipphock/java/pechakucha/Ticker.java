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

/**
 * 
 * Performs an action (callbacks the Presenter's tick method)
 * every second.
 * 
 *  The ticker can be in three states: PLAY, PAUSE, STOP
 *  On Play, every seconds the callback will be called,
 *  on Pause, the ticker-thread will be on wait.
 *  On Stop, the Ticker can never be in play or paused state again. 
 *
 */
public class Ticker implements Runnable{

	private volatile boolean stopped=false;
	
	private Thread ownThread;
	
	private long lastSecond=0;
	
	private int every;
	
	private enum TickerState{
		PLAY,PAUSE,STOP;
	}
	
	private volatile TickerState state; 
	
	private Presenter p;
	
	
	public Ticker(Presenter p) {
		this(p,1);
	}
	
	public Ticker(Presenter p, int every) {
		this.p=p;
		this.every=every;
		state=TickerState.PAUSE;
		ownThread = new Thread(this);
		ownThread.setDaemon(true);
		ownThread.start();
		
		
	}
	
	/**
	 * starts or continues the ticker (this method is idempotent)
	 */
	public void play(){
		
		state = TickerState.PLAY;
		synchronized (ownThread) {
			
		
			ownThread.notifyAll();
		}
		
		
	}
	
	/**
	 * pauses the ticker (this method is idempotent)
	 */
	public void pause(){
		
			state = TickerState.PAUSE;	
	}		
	
	/**
	 * stops the ticker (this method is idempotent)
	 */
	public synchronized void stop(){
		stopped=true;
	}
	
	@Override
	public void run() {
		
		synchronized (ownThread) {
			
		
			while (!stopped){
				
				while (state==TickerState.PAUSE){
					
					try {
						ownThread.wait();
						
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}				
				}
				
				
				
				if (System.currentTimeMillis() - lastSecond > 1000*every) {
					//tick - a second
					p.tick();
					lastSecond=System.currentTimeMillis();
				}
			}
		}
	
	}
}
