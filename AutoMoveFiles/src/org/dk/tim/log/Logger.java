package org.dk.tim.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.dk.tim.file.FileTool;

public class Logger {
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static Logger instance;
	
	private String logFilePath;
	private BufferedWriter out;
	private long MAX_LOGFILE_SIZE = 10485760; //10485760 = 10 mb

	public Logger(String logFile){
		this.logFilePath = logFile;
		initialize();
		if(isLogFile()){
			cleanupIfFileIsTooBig(MAX_LOGFILE_SIZE);
		}
	}
	private boolean isLogFile(){
		return logFilePath != null && !logFilePath.equals("");
	}
	private void initialize() {
		try {
			if(isLogFile()){
				FileWriter fstream = new FileWriter(logFilePath, true);
				out = new BufferedWriter(fstream);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void log(String x){
		instance.logStatement(x);
	}
	
	private void logStatement(String statement) {
		try {
			String timestamp = createTimeStamp();
			
			String decoratedStatement = String.format("[%s] : %s", timestamp, statement);
			if(isLogFile()){
				String lineSeperator = System.getProperty("line.separator");;
				out.append(decoratedStatement + lineSeperator);
			}
			System.out.println(decoratedStatement);
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(isLogFile()){
					out.flush();
				}
			} catch (IOException e) {
				System.err.println(e);
				e.printStackTrace();
			}
			
		}
	}
	
	public static void closeLogger(){
		instance.internalCloseLogger();
	}
	
	private void internalCloseLogger(){
		try {
			if(isLogFile()){
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void cleanupIfFileIsTooBig(long MaxSize) {
		try{
			File logFile = new File(logFilePath);
			FileTool fileTool = new FileTool();
			long fileByteSize = fileTool.getFileByteSize(logFile);
			if(fileByteSize > MaxSize){
				clearLogFile(logFile, fileTool);
			}
		} catch (Exception e){
			
		}
	}

	private void clearLogFile(File logFile, FileTool fileTool) {
		try {
			FileWriter fstreamNoAppend = new FileWriter(logFilePath, false);
			BufferedWriter clearLogFileStream = new BufferedWriter(fstreamNoAppend);
			out.flush();
			clearLogFileStream.close();
			
			System.out.println("Log cleared");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String createTimeStamp() {
		Locale locale = new Locale("da", "DK");
		Calendar cal = new GregorianCalendar(locale);
		Date date = cal.getTime();
		return df.format(date);
	}
}
