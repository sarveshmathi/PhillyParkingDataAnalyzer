package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	
	public static String filename;
	
	private static Logger instance;
	
	private PrintWriter out;
	
	private Logger(String filename) {	
		try {
			out = new PrintWriter(new FileWriter(filename, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void instantiateLogger() {
		if (instance == null) {
			instance = new Logger(filename);
		}	
	}
	
	public static Logger getInstance() {
		return instance;
	}
	 
	public void log(String string) {
		out.println(string);
		out.flush();
	}
	
}
