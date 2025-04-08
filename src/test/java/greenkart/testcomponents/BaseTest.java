package greenkart.testcomponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import greenkartautomation.pageobjects.GreenKartHomePage;

public class BaseTest {
	String browser;
	public WebDriver driver;
	protected GreenKartHomePage greenkarthome;
	
	
	public WebDriver initalizeDriver() throws IOException {
		// Load properties
		Properties prop = new Properties();
		String fileSeparator = System.getProperty("file.separator");

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "java" 
				+ fileSeparator + "greenkartautomation" + fileSeparator + "resources" 
				+ fileSeparator + "GlobalData.properties");

		prop.load(fis);
		
		// Get browser property
		browser = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
		
		// if we want to enter the browser name in console
		
//		try (Scanner sc = new Scanner(System.in)) {
//			System.out.println("Please enter the browser name : ");
//			browser = sc.nextLine();
//		}
		

		// Initialize driver using switch
		switch (browser.toLowerCase()) {
			case "chrome" -> {
				ChromeOptions options = new ChromeOptions();
				driver = new ChromeDriver(options);
			}
			case "chrome-headless", "headless-chrome" -> {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				driver = new ChromeDriver(options);
				driver.manage().window().setSize(new Dimension(1440, 900));
			}
			case "firefox" -> driver = new FirefoxDriver();
			case "edge" -> driver = new EdgeDriver();
			default -> {
				Assert.fail("The Browser Setup is not Configured for " + browser + ". It might be due to spelling mistake or a missing browser");
			}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	@BeforeMethod(alwaysRun = true)
	public GreenKartHomePage launchApplication() throws IOException {
		driver = initalizeDriver();
		greenkarthome = new GreenKartHomePage(driver);
		greenkarthome.goTo();
		return greenkarthome;
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	public List<HashMap<String, Integer>> getJasonDataToMap(String path) throws IOException {
		//Read Json to String
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//greenkart//data//OrderList.json"), StandardCharsets.UTF_8);

		//String to HashMap -> Jackson Databind (read data from a JSON string --> java objects)--> deserialization
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Integer>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, Integer>>>(){});

		return data;
	}

}
