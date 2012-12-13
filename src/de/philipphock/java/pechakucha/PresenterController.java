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

import java.util.Vector;


/**
 * 
 * A state machine for switching between play/pause states,
 * also implements the Observable for PresenterStateListener
 *
 */
public abstract class PresenterController {
	
	private final Vector<PresenterStateListener> listeners; 
	
	public PresenterController() {
		listeners = new Vector<PresenterStateListener>(); 
	}
	
	protected enum State{
		PLAYING, STOPPED;
	}
	
	protected State state; 

	
	/**
	 * callback; is called after the state changes to stop 
	 */
	protected abstract void stateChangedStopped();
	
	/**
	 * callback; is called after the state changes to start 
	 */
	protected abstract void stateChangedStarted();
	
	
	/**
	 * switches the state to play/pause, depends on the previous state
	 */
	public void switchState(){
		if (state == State.PLAYING){
			state = State.STOPPED;

			for (PresenterStateListener l: listeners){
				l.presenterPause();
			}
			stateChangedStopped();
		}else{
			state = State.PLAYING;

			for (PresenterStateListener l: listeners){
				
				l.presenterPlay();
			}
			stateChangedStarted();
			
		}

	}
	
	protected void msgPresenterStateListeners(String msg){
		for (PresenterStateListener l: listeners){
			l.presenterInfoUpdate(msg);
		}
	}
	
	public void addPresenterStateListener(PresenterStateListener p){
		listeners.add(p);
	}
	public void removePresenterStateListener(PresenterStateListener p){
		listeners.remove(p);
	}
}
