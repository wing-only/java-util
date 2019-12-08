package com.wing.java.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

public class SpringUtil extends ApplicationObjectSupport {

    private static ApplicationContext applicationContext = null;

    @Override
    protected void initApplicationContext(ApplicationContext context)
            throws BeansException {
        // TODO Auto-generated method stub
        super.initApplicationContext(context);
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = context;

        }
    }

    public static ApplicationContext getAppContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getAppContext().getBean(name);
    }

}
