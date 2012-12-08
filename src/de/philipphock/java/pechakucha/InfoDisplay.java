package de.philipphock.java.pechakucha;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InfoDisplay extends JFrame implements PresenterStateListener, ActionListener{
	/**
	 * 
	 */
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playpause){
			this.p.switchState();
		}
		
	}

}
