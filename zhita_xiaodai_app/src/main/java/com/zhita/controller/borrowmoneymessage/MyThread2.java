package com.zhita.controller.borrowmoneymessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhita.service.test.TestService;

public class MyThread2 extends Thread {


	private String name;
    
    public MyThread2(String name) {
    	this.name = name;
	}

	@Override
    public void run() {
        try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
borrowMoneyMessageController bController = new borrowMoneyMessageController();
bController.get(name);

    }
    

    
}
