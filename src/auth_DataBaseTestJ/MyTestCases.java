package auth_DataBaseTestJ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases extends MyData {

	@Test(priority=5)
	public void signUp() throws InterruptedException {
		//Your Personal Details
		driver.navigate().to(theSignUpPage);
//		WebElement signUpFirstContinueButton=driver.findElement(By.xpath("//button[@title='Continue']"));
//		signUpFirstContinueButton.click();
		
		WebElement fisrtNameInputField=driver.findElement(By.id("AccountFrm_firstname"));
		fisrtNameInputField.sendKeys(customerFirstNameInDataBase);
		
		WebElement lastNameInputField=driver.findElement(By.id("AccountFrm_lastname"));
		lastNameInputField.sendKeys(customerLastNameInDataBase);
		
		WebElement email= driver.findElement(By.id("AccountFrm_email"));
		email.sendKeys(theEmail);
	
		WebElement phone =driver.findElement(By.id("AccountFrm_telephone"));
		phone.sendKeys(thephone);
		
		
		
		
//        //Your Address 
//		
		WebElement address1=driver.findElement(By.id("AccountFrm_address_1"));
		address1.sendKeys(addressLine1);
		
		
		WebElement country=driver.findElement(By.id("AccountFrm_country_id"));
		Select myCountrySelector=new Select (country);
		//myCountrySelector.selectByIndex(randomCountry);
		myCountrySelector.selectByVisibleText(customerCountryInDataBase);// watch out for selectByVisibleText In linking the DB 
		Thread.sleep(2000);
		
		WebElement region_state=driver.findElement(By.id("AccountFrm_zone_id"));
		Select myRegion_StateSelector=new Select (region_state);
		myRegion_StateSelector.selectByIndex(1);// not 0 cuz 0 is [Select By option]
		
		//myRegion_StateSelector.selectByValue("3516");
		//myRegion_StateSelector.selectByVisibleText("Angus");
		Thread.sleep(2000);
		
		List<WebElement> allStates=region_state.findElements(By.tagName("option"));
		
		String theCity=allStates.get(1).getText();
		
		WebElement city=driver.findElement(By.id("AccountFrm_city"));
		city.sendKeys(theCity);
		
		WebElement zipCode=driver.findElement(By.id("AccountFrm_postcode"));
		zipCode.sendKeys(zipcode);
		
		
//		//Login Details
		
		WebElement loginNameInputField=driver.findElement(By.id("AccountFrm_loginname"));
		loginNameInputField.sendKeys(loginName);
		
		WebElement passwordInputField=driver.findElement(By.id("AccountFrm_password"));
		passwordInputField.sendKeys(userPassword);
		
		WebElement passwordConfirmInputField=driver.findElement(By.id("AccountFrm_confirm"));
		passwordConfirmInputField.sendKeys(userPassword);
		
		WebElement radioButtons=driver.findElement(By.cssSelector("div[class='form-group'] div[class='input-group col-sm-4']"));
		List<WebElement> allRadioButtons=radioButtons.findElements(By.tagName("input"));
		
		for(int i=0;i<allRadioButtons.size();i++) {
			allRadioButtons.get(i).click();
			
		}
		
		WebElement checkInputField=driver.findElement(By.id("AccountFrm_agree"));
		checkInputField.click();
		
		
		
		WebElement signUpSecondContinueButton=driver.findElement(By.xpath("//button[@title='Continue']"));
		signUpSecondContinueButton.click();
		
//		//Assert method 1
//		boolean actualValue=driver.getPageSource().contains("Your Account Has Been Created!");
//		boolean  expectedValue=true;
		
		//assert method 2
		String actualValue=driver.findElement(By.className("maintext")).getText();
		String expectedValue="YOUR ACCOUNT HAS BEEN CREATED!";
		
		//System.out.println(actualValue);
		Assert.assertEquals(actualValue,expectedValue,"Account creation message was NOT found on the page");
		
		WebElement afterSignUpContinueButton =driver.findElement(By.xpath("//a[@title='Continue']"));
		afterSignUpContinueButton.click();
		
	}
	
	@Test(priority=6)
	public void logout() {
		
		
		WebElement logoffButton=driver.findElement(By.linkText("Logoff"));
		logoffButton.click();


		boolean actualValue=driver.getPageSource().contains("Account Logout");
		boolean expectedValue=true;
		
//		String actualValue=driver.findElement(By.className("maintext")).getText();
//		String expectedValue="ACCOUNT LOGOUT";
//		
		System.out.println(actualValue);
		Assert.assertEquals(actualValue,expectedValue,"The Logout Function Has Failed");
		
		WebElement afterLogOutContinueButton =driver.findElement(By.xpath("//a[@title='Continue']"));
		afterLogOutContinueButton.click();
		
		
	}
	@Test(priority=7)
	public void login() {
		driver.navigate().to(TheLoginPage);
		WebElement loginNameLoginPage=driver.findElement(By.id("loginFrm_loginname"));
		loginNameLoginPage.sendKeys(loginName); 
		WebElement passwordLoginPage=driver.findElement(By.id("loginFrm_password"));
		passwordLoginPage.sendKeys(userPassword);
	
		WebElement loginButton=driver.findElement(By.xpath("//button[@title='Login']"));
		loginButton.click();

		boolean actualValue=driver.getPageSource().contains(welcomeBackMssg);
		boolean expectedValue=true;
		
		//System.out.println(actualValue);
	//	System.out.println(welcomeBackMssg);
		Assert.assertEquals(actualValue, expectedValue,"The Login Function Has Failed");
		
	}
	
	@Test(priority=8,enabled=false)
	public void addItemToTheCart() {
		
		driver.navigate().to(theWebSite);
		Random rand =new Random();
		
		for(int i=0;i<10.;i++) {
			
			List<WebElement> allProductItems=driver.findElements(By.className("prdocutname"));
			int randomProductItemIndex=rand.nextInt(allProductItems.size());
			allProductItems.get(randomProductItemIndex).click();
			
			boolean shoesItem =driver.getCurrentUrl().contains("product_id=116");
			boolean outOfStock=driver.getPageSource().contains("Out of Stock");
			
			if(!shoesItem &&!outOfStock) {
				WebElement addToCartButton =driver.findElement(By.cssSelector(".cart"));
				addToCartButton.click();
				return; //success
			}
			
			driver.navigate().back();
			
		}
		
		throw new RuntimeException("No In-stock item found after 10 attempts!!");
	}
	
	@AfterTest
	public void myAfterTest() {}
}
