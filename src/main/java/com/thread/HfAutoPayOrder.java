package com.thread;
import java.util.Map;
import java.util.concurrent.*;


import com.test.Hf168Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class HfAutoPayOrder implements Runnable{

    private String playerId;
	private String url;
	private String type;
	private List<String> nums;
	private String money;
	private WebDriver driver;
private static ConcurrentHashMap<String,String> accountList=new ConcurrentHashMap<> ();

	public HfAutoPayOrder(String url, String playerId, String type, List<String> nums, String money,WebDriver driver) {
		this.playerId = playerId;
		this.type = type;
		this.nums = nums;
		this.money = money;
		this.driver=driver;
	}
	private static Logger log = Logger.getLogger(HfAutoPayOrder.class);

	@Override
	public void run() {

	}

	public static void putOrder(String url,String playerId,String type,List<String> nums,String money,WebDriver driver) {
		try {
			new Thread(new HfAutoPayOrder(url, playerId,type, nums,money,driver)).start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initAccountList(Map<String,String> map){
		if (map!=null){
			for(Map.Entry<String,String> account:map.entrySet())
			{
				accountList.put(account.getKey(),account.getValue());
			}
		}
	}
}

