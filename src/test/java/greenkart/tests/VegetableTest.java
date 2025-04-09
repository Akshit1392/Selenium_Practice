package greenkart.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import greenkart.testcomponents.BaseTest;
import greenkartautomation.pageobjects.GreenKartCheckoutPage;

public class VegetableTest extends BaseTest{
	
	@Test(dataProvider = "getData", groups = { "smoke", "regression" })
	public void flowone(HashMap<String, Integer> input) throws InterruptedException {
	    
	    // Handle product selection and adding to cart
	    greenkarthome.handleProductList(input);
	    // Click on the icon 
	    greenkarthome.clickonicon();
	    // Verify the number of product is equal to the product we added in cart
	    greenkarthome.verifyCartItemCount(3);
	    //Click on the proceed button
	    greenkarthome.clickonproceed();
	    
	    GreenKartCheckoutPage checkoutpage = new GreenKartCheckoutPage(driver);
	    // Apply promo code and verify discount
	    checkoutpage.applypromocode();
	    checkoutpage.clickingonapply();
	    // Check that promocode is applied and text after that is visbile
	    checkoutpage.codevisible();
	    // Verify that the after promocode is applied the price is updated
	    checkoutpage.verifycodeapplied();
	    
	    // Proceed to checkout and place order
	    checkoutpage.placinganorder();
	    // choose the country 
	    checkoutpage.choosecountry("India");
	    // Click on the button to check the terms and condition
	    checkoutpage.termandcondition();
	    // Clicking on the proceed button
	    checkoutpage.clickingonprocedd();
	}
	@Test(groups = {"regression"})
	public void flowtwo() {
		System.out.println("Testing for regression test cases");
	}
	@Test(groups = {"smoke"})
	public void flowthree() {
		System.out.println("Testing for smoke test cases only");
	}
	@Test(groups = {"endtoend"})
	public void flowfour() {
		System.out.println("Testing for end to end test cases");
	}
	@Test(groups = {"end"})
	public void flowfive() {
		System.out.println("Testing for end to end test cases");
	}
	@Test(groups = {"end"})
	public void flowsix() {
		System.out.println("Testing for end to end test cases");
	}
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, Integer>> data = getJasonDataToMap(System.getProperty("user.dir")+"//src//test//java//greenkart//data//OrderList.json");

		return new Object[][] {{data.get(0)}};
	}
}
