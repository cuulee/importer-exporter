package de.tub.citydb.plugin.api.controller;

import javax.swing.JFrame;

public interface ViewController {
	public void clearConsole();
	public void setStatusText(String statusText);
	public JFrame getTopFrame();
}
