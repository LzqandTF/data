package com.chinaebi.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.chinaebi.reload.DataManagerInit;


public class DataManagerStartup{

	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	
	public void contextInitialized(){
		dataManagerInit.startup();
	}
	
//	@Override
//	public void contextDestroyed(ServletContextEvent arg0) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void contextInitialized(ServletContextEvent arg0) {
//		dataManagerInit.startup();
//	}

}
