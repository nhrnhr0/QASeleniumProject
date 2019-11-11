package sdk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static String line = "";
	private static PrintWriter printWriter = null;
	
	
	public static void print(String msg) {
		if(printWriter == null)
			init();
		printWriter.print(msg);
		System.out.print(msg);
	}
	public static void println(String msg) {
		if(printWriter == null)
			init();
		printWriter.println(msg);
		printWriter.flush();
		System.out.println(msg);
	}
	
	private static void init() {
		try {
			FileWriter fileWriter = new FileWriter("Log_" + getCurrentTimeStamp() + ".txt");
			printWriter = new PrintWriter(fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("dd_MM_yyy_HH_mm");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	public static void close() {
		Log.println("flushing log file and closing the FileWriter");
		printWriter.flush();
		printWriter.close();
	}
	
	

}
