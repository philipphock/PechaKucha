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
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * 
 * Spawns a trayicon to control the presentation (play/pause)
 *
 */
public class Tray implements PresenterStateListener, ActionListener{
	private final Image playImage = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resources/play.png"));
	private final Image pauseImage = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resources/pause.png"));
	private final String TOOLTIP_PLAYING = "pechakucha - playing";
	private final String TOOLTIP_STOPPED = "pechakucha - stopped";
	private TrayIcon trayIcon;
	
	private final PopupMenu menu  = new PopupMenu();
    private final MenuItem exit_menu_item = new MenuItem("exit");
    private final MenuItem toggle_gui = new MenuItem("toggle gui");
	
    private final Presenter p;

    private final JFrame gui;
	
    private boolean disableTrayNotifications=true;
	
	
	public boolean isDisableTrayNotifications() {
		return disableTrayNotifications;
	}




	public void setDisableTrayNotifications(boolean disableTrayNotifications) {
		this.disableTrayNotifications = disableTrayNotifications;
	}




	public Tray(Presenter p, JFrame f){
		gui=f;
		trayIcon = new TrayIcon(playImage,TOOLTIP_STOPPED);
		this.p=p;
		
		trayIcon.setImageAutoSize(true);
		
		trayIcon.setPopupMenu(menu);
		
		menu.add(exit_menu_item);
		
		exit_menu_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		toggle_gui.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gui.isVisible()){
					gui.setVisible(false);
				}else{
					gui.setVisible(true);
				}
				
				
			}
		});
		menu.add(toggle_gui);
		
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
		

		
		trayIcon.addActionListener(this);
	}

	
	

	public void setMessage(String msg){
		if(disableTrayNotifications)return;
		trayIcon.displayMessage("pechacucha",msg.replaceAll("<br>", "\n"), MessageType.INFO);
	}

	@Override
	public void presenterPlay() {
		trayIcon.setImage(pauseImage);
		trayIcon.setToolTip(TOOLTIP_PLAYING);
		
	}

	@Override
	public void presenterPause() {
		trayIcon.setImage(playImage);
		trayIcon.setToolTip(TOOLTIP_STOPPED);
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		this.p.switchState();
	}




	@Override
	public void presenterInfoUpdate(String msg) {
		setMessage(msg);
		
	}
	
}
