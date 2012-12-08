package de.philipphock.java.pechakucha;

import java.util.Vector;



public abstract class PresenterController {
	
	private final Vector<PresenterStateListener> listeners; 
	
	public PresenterController() {
		listeners = new Vector<PresenterStateListener>(); 
	}
	
	protected enum State{
		PLAYING, STOPPED;
	}
	
	protected State state; 

	protected abstract void stateChangedStopped();
	protected abstract void stateChangedStarted();
	
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
