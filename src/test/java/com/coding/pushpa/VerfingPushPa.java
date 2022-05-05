package com.coding.pushpa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VerfingPushPa {
	
	public WebDriver driver;
	public String imdbReleaseDate, imdbcountry;
	public String wikiReleaseDate, wikiCountry;

	@BeforeTest
	public void openBrower() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void imdb() throws InterruptedException {
		driver.get("https://www.imdb.com/title/tt9389998/?ref_=nv_sr_srsg_0");
		Thread.sleep(1000);
	}

	@Test(priority = 2)
	public void getReleaseDateinIMDB() throws InterruptedException {
		Actions action = new Actions(driver);

		WebElement element = driver.findElement(By.xpath("(//*[@id='iconContext-chevron-right'])[19]"));
		action.moveToElement(element).perform();

		driver.findElement(By.xpath(
				"(//a[@class='ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link'])[24]"))
				.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//table//tbody/tr")).size();

		for (int i = 1; i <= count; i++) {
			String CountryName = driver.findElement(By.xpath("//table//tbody/tr[" + i + "]/td[1]")).getText();
			if (CountryName.equals("India")) {
				imdbcountry = driver.findElement(By.xpath("//table//tbody/tr[" + i + "]/td[1]")).getText();
				imdbReleaseDate = driver.findElement(By.xpath("//table//tbody/tr[" + i + "]/td[2]")).getText();
				System.out.println(imdbcountry + "  " + imdbReleaseDate);
				break;
			}

		}
	}
	
	@Test(priority = 3)
	public void navigateToWIKIPage() throws InterruptedException {
		Thread.sleep(1000);
		driver.navigate().to("https://en.wikipedia.org/wiki/Pushpa:_The_Rise");
		Thread.sleep(1000);
	}
	
	@Test(priority = 4)
	public void getReleaseDateAndCout() throws InterruptedException {
		int count = driver.findElements(By.xpath("//table[@class='infobox vevent']/tbody/tr")).size();

		for (int i = 3; i <= count; i++) {
			String releaseDate = driver.findElement(By.xpath("//table[@class='infobox vevent']//tbody/tr[" + i + "]/th")).getText();
			if (releaseDate.equals("Release date")) {
				wikiReleaseDate = driver.findElement(By.xpath("//table[@class='infobox vevent']//tbody/tr[" + i + "]/td[1]")).getText();
				System.out.println(wikiReleaseDate);				
			}else if (releaseDate.equals("Country")) {
				wikiCountry = driver.findElement(By.xpath("//table[@class='infobox vevent']//tbody/tr[" + i + "]/td[1]")).getText();
				System.out.println(wikiCountry);
			}
		}
	}
	
	public void verifyMIDBandWIKI() {
		Assert.assertEquals(imdbcountry, wikiCountry);
		Assert.assertEquals(imdbReleaseDate, wikiReleaseDate);
	}

	
	  @AfterTest 
	  public void closeBrower() { 
		  driver.quit(); 
	  }

}
