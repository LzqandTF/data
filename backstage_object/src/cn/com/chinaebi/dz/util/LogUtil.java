package cn.com.chinaebi.dz.util;

import java.util.concurrent.Callable;

public class LogUtil implements Callable<String>{

	
	private Object[] obj;
	public LogUtil(Object ... obj){
		this.obj = obj;
	}
	
	@Override
	public String call() throws Exception {
		StringBuffer buffer = new StringBuffer();
		if(obj != null && obj.length > 0){
			for (Object object : obj) {
				buffer.append(object.toString());
			}
		}
		return buffer.toString();
	}
}
