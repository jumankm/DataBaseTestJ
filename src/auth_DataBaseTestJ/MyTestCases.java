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

	Connection con;
	Statement stmt;
	ResultSet rs;
	
	@BeforeTest
	public void mySetUp() throws SQLException {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","Juman@mysql01");
		
		driver=new ChromeDriver();
		driver.get(TheWebSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to(TheLoginPage);
		//driver.findElement(By.linkText("Login or register")).click();
		//driver.findElement(By.partialLinkText("Login")).click();
		
	}
	
	@Test(priority=1,enabled=false)
	public void register() throws InterruptedException {
		//Your Personal Details
		
		WebElement signUpFirstContinueButton=driver.findElement(By.xpath("//button[@title='Continue']"));
		signUpFirstContinueButton.click();
		
		WebElement fisrtNameInputField=driver.findElement(By.id("AccountFrm_firstname"));
		fisrtNameInputField.sendKeys(userFirstName);
		
		WebElement lastNameInputField=driver.findElement(By.id("AccountFrm_lastname"));
		lastNameInputField.sendKeys(userLastName);
		
		WebElement email= driver.findElement(By.id("AccountFrm_email"));
		email.sendKeys(randomEmail);
		
        //Your Address
		
		WebElement address1=driver.findElement(By.id("AccountFrm_address_1"));
		address1.sendKeys("Amman");
		
		
		
	
		int randomCountry=rand.nextInt(1,4);
		int randomRegion=rand.nextInt(1,4);
		
		WebElement country=driver.findElement(By.id("AccountFrm_country_id"));
		Select myCountrySelector=new Select (country);
		myCountrySelector.selectByIndex(randomCountry);
		
		Thread.sleep(2000);
		
		WebElement region_state=driver.findElement(By.id("AccountFrm_zone_id"));
		Select myRegion_StateSelector=new Select (region_state);
		myRegion_StateSelector.selectByIndex(randomRegion);
		//myRegion_StateSelector.selectByValue("3516");
		//myRegion_StateSelector.selectByVisibleText("Angus");
		
		String selectedState=myRegion_StateSelector.getFirstSelectedOption().getText();
		Thread.sleep(2000);
		
		WebElement city=driver.findElement(By.id("AccountFrm_city"));
		city.sendKeys(selectedState);
		//solve it with making a list from state the get the text from it 
		//findtheelements using the tagname "option
		
		WebElement zipCode=driver.findElement(By.id("AccountFrm_postcode"));
		zipCode.sendKeys("77777");
		
		
		
		//Login Details
		
		WebElement loginNameInputField=driver.findElement(By.id("AccountFrm_loginname"));
		loginNameInputField.sendKeys(randomLoginName);
		
		WebElement passwordInputField=driver.findElement(By.id("AccountFrm_password"));
		passwordInputField.sendKeys(userPassword);
		
		WebElement passwordConfirmInputField=driver.findElement(By.id("AccountFrm_confirm"));
		passwordConfirmInputField.sendKeys(userPassword);
		
		WebElement radioButtons=driver.findElement(By.xpath("//*[@id=\"AccountFrm\"]/div[4]/fieldset/div/div"));
		List<WebElement> allRadioButtons=radioButtons.findElements(By.tagName("input"));
		
		for(int i=0;i<allRadioButtons.size();i++) {
			allRadioButtons.get(i).click();
			
		}
		
		WebElement checkInputField=driver.findElement(By.id("AccountFrm_agree"));
		checkInputField.click();
		
		
		
		WebElement signUpSecondContinueButton=driver.findElement(By.xpath("//button[@title='Continue']"));
		signUpSecondContinueButton.click();
//		
		//Assert method 1
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
	
	@Test(priority=2,enabled=false)
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
	@Test(priority=3,enabled=false)
	public void login() {
		driver.navigate().to(TheLoginPage);
		WebElement loginNameLoginPage=driver.findElement(By.id("loginFrm_loginname"));
		loginNameLoginPage.sendKeys(randomLoginName); 
		WebElement passwordLoginPage=driver.findElement(By.id("loginFrm_password"));
		passwordLoginPage.sendKeys(userPassword);
	
		WebElement loginButton=driver.findElement(By.xpath("//button[@title='Login']"));
		loginButton.click();

		boolean actualValue=driver.getPageSource().contains(welcomeBackMssg);
		boolean expectedValue=true;
		
		System.out.println(actualValue);
		System.out.println(welcomeBackMssg);
		Assert.assertEquals(actualValue, expectedValue,"The Login Function Has Failed");
		
	}
	
	@Test(priority=4)
	public void addItemToTheCart() {
		
		driver.navigate().to(TheWebSite);
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
//		driver.navigate().to(TheWebSite);
//		
//		List<WebElement> allProductItems=driver.findElements(By.className("prdocutname"));
//		
//		int randomIndexForItems=rand.nextInt(allProductItems.size());
//		
//		allProductItems.get(randomIndexForItems).click();
//		
//		
//		//First Logic 
//		if(driver.getCurrentUrl().contains("product_id=116"))
//		{
//			WebElement availableOption=driver.findElement(By.id("option344747"));
//			availableOption.click();
//			
//		}
//		
//		//Second Logic
//		if(driver.getPageSource().contains("Out of Stock")) {
//			driver.navigate().back();
//			
//			//because of the back >> page to page >> data will be lost
//			List<WebElement> alternativeAllProductItems=driver.findElements(By.className("prdocutname"));
//			int secondRandomIndexForItems=rand.nextInt(alternativeAllProductItems.size());
//			
//			alternativeAllProductItems.get(secondRandomIndexForItems).click();
//		}
//		WebElement addToCartButton=driver.findElement(By.cssSelector(".cart"));
//		addToCartButton.click();
	}
	
	@AfterTest
	public void myAfterTest() {}
}
