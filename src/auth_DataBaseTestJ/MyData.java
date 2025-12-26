package auth_DataBaseTestJ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyData {

	WebDriver driver = new ChromeDriver();
	String theWebSite = "https://automationteststore.com/";
	String theSignUpPage = "https://automationteststore.com/index.php?rt=account/create";

	Connection con;
	Statement stmt;
	ResultSet rs;// only used once with a query READ type only 

	  int customerNumberInDataBase;
    String customerFirstNameInDataBase;
			String customerLastNameInDataBase;
			  String email;
			    String password;

	@BeforeTest
	public void mySetUp() throws SQLException {
		driver.get(theSignUpPage);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "Juman@mysql01");

	}

	@Test(priority=1)
	public void addData() throws SQLException {

		String query = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit)"  
		+ "VALUES (999,'Random Company','Ali','Ahmad','0791234567','45 Rue Royale', NULL, 'Nantes', NULL,'44000', 'France',1370, 21000)";

		stmt = con.createStatement();

//		    con → database connection (Connection)
//
//		    createStatement() → creates a Statement object
//
//		    stmt is used to send SQL commands to the database

		int rowInserted = stmt.executeUpdate(query); // how much rows got affected

//		    executeUpdate(query):
//
//		    	Executes INSERT / UPDATE / DELETE
//
//		    	Returns an int
//		    	1 → one row inserted successfully ✅
//
//		    	0 → nothing inserted ❌

		Assert.assertEquals(rowInserted, 1, "Record was NOT inserted into the database");

	}
	
	@Test(priority = 3)
	public void readData() throws SQLException {

		  String query = "SELECT * FROM customers WHERE customerNumber = 999";

		    stmt = con.createStatement();
		    
		    rs = stmt.executeQuery(query);//read does not excuteupdate [changes] <<diff

		
			
			while (rs.next()) {//as long as there is data to read [cuz the rs returns a boolean value]

		        customerNumberInDataBase = rs.getInt("customerNumber");

		        customerFirstNameInDataBase =
		                rs.getString("contactFirstName").toString().trim();//trim() is for trimming the uneeded  space in the last 

		        customerLastNameInDataBase =
		                rs.getString("contactLastName").toString().trim();
		    
		        email = customerFirstNameInDataBase + customerLastNameInDataBase + "@gmail.com";
		        password = "123@Abc";
		        
		    System.out.println(customerNumberInDataBase);
		    System.out.println(customerFirstNameInDataBase);
		    System.out.println(customerLastNameInDataBase);

		    System.out.println(email);
		    System.out.println(password);
			}
			
			//to send the data
			driver.findElement(By.id("AccountFrm_firstname"))
	        .sendKeys(customerFirstNameInDataBase);

	driver.findElement(By.id("AccountFrm_lastname"))
	        .sendKeys(customerLastNameInDataBase);

	driver.findElement(By.id("AccountFrm_email"))
	        .sendKeys(email);

//	driver.findElement(By.id("customer[password]"))
//	        .sendKeys(password);

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