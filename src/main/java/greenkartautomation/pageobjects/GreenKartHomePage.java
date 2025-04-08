package greenkartautomation.pageobjects;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import greenkartautomation.abstractcomponents.HelperClass;

public class GreenKartHomePage extends HelperClass{
	WebDriver driver;
	String homepageurl = "https://rahulshettyacademy.com/seleniumPractise/#/";
	
	public GreenKartHomePage(WebDriver driver) {
		//initialization
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	//Locators by page factory
	@FindBy(xpath = "//input[@class='search-keyword']")
    WebElement placeholdertoentervegies;
	
	@FindBy(xpath = "//button[@class='search-button']")
    WebElement clickforsearch;
    
    @FindBy(xpath = "//div[@class='no-results']")
    WebElement errormessage;
    
    @FindBy(xpath = "//h4[@class='product-name' and text()='Brocolli - 1 Kg'] ")
    WebElement nameofproduct;
    
    @FindBy(xpath = "//h4[@class='product-name'] ")
    WebElement searchproduct;
    
    @FindBy(xpath ="//a[@class='increment']")
    WebElement addproduct;
    
    @FindBy(xpath ="//a[@class='decrement']")
    WebElement removeproduct;
    
    @FindBy(xpath ="//div[@class='product-action']/button")
    WebElement cartadding;
    
    @FindBy(xpath = "//a[@class='cart-icon']/img")
    WebElement carticonclick;
    
    @FindBy(xpath = "//button[text() = 'PROCEED TO CHECKOUT']")
    WebElement proceedtocheck;
    
    @FindBy(xpath = "//div[@class='products']/div[@class='product']")
    WebElement productdivtoappears;
    
    @FindBy(xpath="//div[@class='products']")
    WebElement listofproduct;
   
    @FindBy(xpath="//div[@class='cart']/div[@class='cart-preview active']/div/div/ul")
    WebElement cartitemlist;
    
    @FindBy(xpath="//div[@class='cart']/div[@class='cart-preview active']/div/div/ul/li")
    List<WebElement> listofcartitem;
    
 // Method to get url
 	public void goTo() {
 		driver.get(homepageurl);
 	}
    
 // Searching the product 
 	public GreenKartHomePage searching(String productName) {
 		placeholdertoentervegies.clear();
 		placeholdertoentervegies.sendKeys(productName);
        System.out.println("Searching for the product: " + productName);
        return this;
     }
    
    // Method to check if a product is available
    public boolean isProductAvailable(String productName) {
        try {
            return driver.findElement(By.xpath("//h4[@class='product-name' and text()='" + productName + "']")).isDisplayed();
        } catch (Exception e) {
            System.out.println("Product not found: " + productName);
            return false;
        }
    }
    
    // Method to increment product quantity
    public void incrementproduct() {
        addproduct.click();
        System.out.println("Product Added");
    }
    
    // Method to handle adding multiple products with specified quantities
    public void handleProductList(HashMap<String, Integer> products) throws InterruptedException {
        for (String product : products.keySet()) {
            searching(product);
            try {
                waitForElementToAppear(listofproduct);
            } catch (Exception e) {
                System.out.println("Product list did not load properly.");
                continue;
            }
            if (isProductAvailable(product)) {    
                WebElement addToCartButton = driver.findElement(By.xpath("//h4[contains(text(),'" + product + "')]/ancestor::div[contains(@class,'product')]/div[@class='product-action']/button"));
                WebElement incrementToCartButton = driver.findElement(By.xpath("//h4[contains(text(),'" + product + "')]/ancestor::div[contains(@class,'product')]/div[@class='stepper-input']/a[@class='increment']"));
                
                String quantityStr = driver.findElement(By.xpath("//h4[contains(text(),'" + product + "')]/parent::div/div/input")).getDomProperty("value");
                int productCount = Integer.parseInt(quantityStr);
                int requiredQuantity = products.get(product);

                if (requiredQuantity > 1) {
                    for (int i = productCount; i < requiredQuantity; i++) {              		
                        incrementToCartButton.click();
                        System.out.println("Quantity increased to " + (i + 1));
                    }
                }
                addToCartButton.click();
                System.out.println("Product " + product + " added to cart.");
            } else {
                System.out.println("Product " + product + " is not available. Skipping...");
            }
        }
    }

    
    // Method to add a product to the cart
    public void addingtocart() {
        cartadding.click();
        System.out.println("Adding to cart");
    }
    
    // Method to click on the cart icon
    public void clickonicon() {
    	waitForClick(carticonclick);
        carticonclick.click();
        System.out.println("Cart button will click");
    }
    
    // Method to verify the number of items in the cart
    public void verifyCartItemCount(int expectedCartCount) {
        waitForElementToAppear(cartitemlist);
        int actualCartCount = listofcartitem.size();
        if (actualCartCount == expectedCartCount) {
            System.out.println("Cart validation successful: Expected " + expectedCartCount + " and found " + actualCartCount);
        } else {
            System.out.println("Cart validation failed: Expected " + expectedCartCount + " but found " + actualCartCount);
        }
    }
    
    // Method to proceed to the checkout page
    public void clickonproceed() {
    	waitForClick(proceedtocheck);
        proceedtocheck.click();
        System.out.println("Proceeding to the Checkout page");
    }
}
