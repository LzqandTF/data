package cn.com.chinaebi.dz.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.com.chinaebi.dz.reload.Backstage;

public class BackstageStartup implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Backstage.getInstance().startup();
	}

}
