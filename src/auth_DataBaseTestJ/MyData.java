package auth_DataBaseTestJ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyData {

	// global data
	WebDriver driver;
	String theWebSite = "https://automationteststore.com/";
	String theSignUpPage = "https://automationteststore.com/index.php?rt=account/create";
	String TheLoginPage = "https://automationteststore.com/index.php?rt=account/login";
	String welcomeBackMssg;
	 String userPassword = "123@Abc";
	
	
	Random rand =new Random();
	int randomEmailNumber=rand.nextInt(999);
	int randomLoginNameNumber=rand.nextInt(999);
	//Data Base
	Connection con;
	Statement stmt;
	ResultSet rs;// only used once with a query READ type only

	// data in the database
	int customerNumberInDataBase;
	String customerFirstNameInDataBase;
	String customerLastNameInDataBase;
	String theEmail;
	String thephone;
	
	String customerCountryInDataBase;

	String addressLine1;
	String zipcode;
	String loginName;
	

	@BeforeTest
	public void mySetUp() throws SQLException {

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "Juman@mysql01");

		driver = new ChromeDriver();
		driver.get(theWebSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@Test(priority = 1)
	public void addData() throws SQLException {

		String query = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit)"
				+ "VALUES (999,'Random Company','Ali','Ahmad','0791234567','45 Rue Royale', NULL, 'Nantes', NULL,'44000', 'Spain',1370, 21000)";

		stmt = con.createStatement();


		int rowInserted = stmt.executeUpdate(query); 


		Assert.assertEquals(rowInserted, 1, "Record was NOT inserted into the database");

	}

	@Test(priority = 3)
	public void readData() throws SQLException {

		String query = "SELECT * FROM customers WHERE customerNumber = 999";

		stmt = con.createStatement();

		rs = stmt.executeQuery(query);

		while (rs.next()) {

			customerNumberInDataBase = rs.getInt("customerNumber");

			customerFirstNameInDataBase = rs.getString("contactFirstName").toString().trim();
																								
																								

			customerLastNameInDataBase = rs.getString("contactLastName").toString().trim();

			customerCountryInDataBase = rs.getString("country").toString().trim();

			theEmail = customerFirstNameInDataBase + customerLastNameInDataBase+randomEmailNumber+ "@gmail.com";
			
			thephone =rs.getString("phone").toString().trim();
			
		

			addressLine1 = rs.getString("addressLine1").toString().trim();

			zipcode=rs.getString("postalCode").toString().trim();
			
			loginName=customerFirstNameInDataBase+randomLoginNameNumber;
			
			welcomeBackMssg="Welcome back "+customerFirstNameInDataBase;
			
		}

	}

	@Test(priority = 2)
	public void updateData() throws SQLException {

		String query = "UPDATE customers SET contactLastName = 'Assad' WHERE customerNumber = 999";

		stmt = con.createStatement();
		int rowInserted = stmt.executeUpdate(query);
	}

	@Test(priority = 4)
	public void deleteData() throws SQLException {
		String query = "DELETE FROM customers WHERE customerNumber = 999";
		stmt = con.createStatement();
		int rowInserted = stmt.executeUpdate(query);
	}

	@AfterTest
	public void muAfterTest() {
	}

}