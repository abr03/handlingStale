package com.handleStaleElement;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		
		driver.get("https://www.espncricinfo.com/");
		driver.manage().window().maximize();
		
		
		driver.findElement(By.xpath("//a[contains(@href,'pujara-retires')]//child::h3")).click();

	}

}
