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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * 
 * Displays a play/pause button for the presentation,
 * a timer that shows when the presentation starts, when the next slide will be shown
 * and how many slides are left.
 *
 */
public class InfoDisplay extends JFrame implements PresenterStateListener, ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	private JLabel  body;
	private final JButton playpause;
	private final Presenter p;
	
	public InfoDisplay(Presenter p) {
		setSize(800,300);
		playpause = new JButton("play");
		getContentPane().add(playpause,BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		playpause.addActionListener(this);
		
		this.p=p;
		body = new JLabel();
		body.setHorizontalAlignment(SwingConstants.CENTER);
		setMessage("press play to start");
		getContentPane().add(body,BorderLayout.CENTER);
		setTitle("pechacucha");
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}
	
	/**
	 * 
	 * @param msg Message that will be shown in the GUi
	 */
	public void setMessage(String msg){
		
		this.body.setText(String.format("<html><span style='font-size:40pt;font-weight:bold;text-align:center;'>%s</span></html>",msg));
	}
	

	
	@Override
	public void presenterInfoUpdate(String msg) {
		setMessage(msg);
		
	}

	@Override
	public void presenterPlay() {
		this.playpause.setText("pause");		
	}

	@Override
	public void presenterPause() {
		this.playpause.setText("play");
		
		
	}

	/**
	 * Implementation for play/pause button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playpause){
			this.p.switchState();
		}
		
	}

}
