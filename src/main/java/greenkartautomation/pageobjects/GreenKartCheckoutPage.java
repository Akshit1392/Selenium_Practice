package greenkartautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import greenkartautomation.abstractcomponents.HelperClass;


public class GreenKartCheckoutPage extends HelperClass {
	  WebDriver driver;
  
	  public GreenKartCheckoutPage(WebDriver driver) {
		  super(driver);
	      this.driver = driver;
	      PageFactory.initElements(driver, this);
	  }
	  
	//Locator 
	  @FindBy(xpath = "//input[@class='promoCode']")
	  WebElement promocodewrite;
	  
	  @FindBy(xpath = "//button[@class='promoBtn']")
	  WebElement applingcode;
	  
	  @FindBy(xpath = "//span[@class='promoInfo']")
	  WebElement codetext;
	  
	  @FindBy(xpath = "//button[text() ='Place Order']")
	  WebElement placeorder;
	  
	  @FindBy(xpath = "//input[@class='chkAgree']")
	  WebElement checkingthetermx;
	  
	  @FindBy(xpath = "//button")
	  WebElement proccedbutton;
	  
	  @FindBy(xpath="//span[@class='totAmt']")
	  WebElement totalamount;
	  
	  @FindBy(xpath="//span[@class='discountAmt']")
	  WebElement discountedamaount;
	  
	  @FindBy(xpath="//span[@class='errorAlert']/b")
	  WebElement erronwhennotclickonterm;
	  
	  // Method to enter the promo code
	  public void applypromocode() {
	  	  waitForClick(promocodewrite);
	      promocodewrite.sendKeys("rahulshettyacademy");
	      System.out.println("Promo code applied");
	  }
	  
	  // Method to click on the apply button for promo code
	  public void clickingonapply() {
	  	  waitForClick(applingcode);
	      applingcode.click();        
	  }
	  
	  // Method to get the promo code confirmation text
	  public void codevisible() {
	  	  waitForClick(codetext);
	      codetext.getText();
	  }
	  
	  // Method to verify if the promo code has been applied successfully
	  public void verifycodeapplied() {
		  waitForElementToAppear(totalamount);
		  waitForElementToAppear(discountedamaount);
	      
	      double originalTotal = Double.parseDouble(totalamount.getText());
	      double discountedTotal = Double.parseDouble(discountedamaount.getText());
	      
	      if(originalTotal > discountedTotal) {
	          System.out.println("Promo code applied and money is deducted");
	      } else {
	          System.out.println("Code applied but the money is not deducted");
	      }
	  }
	  
	  // Method to place an order
	  public void placinganorder() {
	      placeorder.click();
	      System.out.println("Placing an order");
	  }
	  
	  // Method to choose a country from the dropdown
	  public void choosecountry(String countryName) {
	  	WebElement countryOption = driver.findElement(By.xpath("//select/option[text()='" + countryName + "']"));
	      countryOption.click();
	      System.out.println("Country is selected");
	  }
	  
	  // Method to accept terms and conditions
	  public void termandcondition() {
	      checkingthetermx.click();
	      System.out.println("Term and condition checkbox applied");
	  }
	  public void nottermandcon() {
	  	erronwhennotclickonterm.getText();
	  	System.out.println("Term and condition checkbox is not applied");
	  }
	  
	  // Method to proceed with checkout
	  public void clickingonprocedd() {
	      proccedbutton.click();
	      System.out.println("Order is placed");
	  }	  
}
