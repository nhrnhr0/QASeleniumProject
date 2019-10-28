package sdk;

import java.util.HashMap;
import java.util.Map;

public class Log {
	private static Map<String, StringBuilder> unflushedLogs;
	private static boolean isInit = false;
	
	private static void Init() {
		if(isInit)
			return;
		isInit = true;
		unflushedLogs = new HashMap<>();
	}
	
	public static void Info(String id, String msg) {
		Init();
		StringBuilder currMsg = unflushedLogs.get(id);
		if(currMsg == null) {
			currMsg = new StringBuilder();
			unflushedLogs.put(id, currMsg);
		}
		currMsg.append(msg);
	}
	
	public static void FlushInfo(String id, String msg) {
		Init();
		Info(id, msg);
		flush(id);
	}
	
	public static void FlushInfo(String msg) {
		Init();
		FlushInfo("", msg);
	}
	
	public static void flush(String id) {
		Init();
		StringBuilder msg = unflushedLogs.remove(id);
		output(msg.toString());
	}
	
	private static void output(String msg) {
		Init();
		System.out.println(msg);
	}

}
