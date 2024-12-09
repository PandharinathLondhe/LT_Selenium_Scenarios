package seleniumPKG;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.lambdatest.tunnel.Tunnel;

public class Scenarios_LT {
    WebDriver driver;
    Tunnel t;
	String username = "pandhari.0305";
	String access_key = "PMN1Y6OkGTTz36JQWoHQYQo5EGVUuVU6Z7WVE2ad0wXCcibFmi";
	String URL = "https://www.lambdatest.com/selenium-playground/";

    @Parameters({"browser", "browserVersion", "platform"})
    @BeforeMethod
    public void setUp(String browser,String browserVersion,String platform ) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "Selenium "+platform +" : "+browser);
        capabilities.setCapability("name", "Selenium Run " +browser);
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("platform", platform);
		capabilities.setCapability("network",true);
		capabilities.setCapability("console",true);
		capabilities.setCapability("visual", true);
		if(platform == "macOS Ventura" ) {
			capabilities.setCapability("marionette", true);
		}
		
		//create tunnel instance
		t = new Tunnel();
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user", username);
		options.put("key", access_key);

		//start tunnel
		t.start(options);
	
		driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
		Thread.sleep(3000);
		driver.get(URL);
		Thread.sleep(3000);
		System.out.println("Started session for " + browser);
		
    }

    @Test(priority = 1, timeOut = 20000)
    public void testSimpleFormDemo() {
		System.out.println("Started senario 1");		
        // Step 1: Open Simple Form Demo
        driver.findElement(By.linkText("Simple Form Demo")).click();
        
        // Step 2: Validate URL contains "simple-form-demo"
        Assert.assertTrue(driver.getCurrentUrl().contains("simple-form-demo"));
        
        // Step 3: Enter message in text box and validate output
        String message = "Welcome to LambdaTest";
        WebElement inputBox = driver.findElement(By.id("user-message"));
        inputBox.sendKeys(message);
        driver.findElement(By.id("showInput")).click();
        WebElement displayMessage = driver.findElement(By.id("message"));
        Assert.assertEquals(displayMessage.getText(), message);
    }

    @Test(priority = 2 , timeOut = 20000)
    public void testDragAndDropSlider() {  
		System.out.println("Started senario 2");
		// Step 1: Open Drag & Drop Sliders
        driver.findElement(By.linkText("Drag & Drop Sliders")).click();
        
        // Step 2: Drag slider to 95 and validate
        WebElement slider = driver.findElement(By.xpath("//input[@type='range'][@value='15']"));
        Actions move = new Actions(driver);
        move.dragAndDropBy(slider, 215, 0).perform();
        WebElement rangeValue = driver.findElement(By.id("rangeSuccess"));
        Assert.assertEquals(rangeValue.getText(), "95");
    }

    @Test(priority = 3 , timeOut = 20000)
    public void testInputFormSubmit(){
		System.out.println("Started senario 3");
		// Step 1: Open Input Form Submit
        driver.findElement(By.linkText("Input Form Submit")).click();
        
        // Step 2: Click Submit without filling form and validate error
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
//        String AlrtTxt = driver.switchTo().alert().getText();
//        Assert.assertEquals(AlrtTxt,"Please fill out this field.");
        
        // Step 3: Fill form details and submit
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("John Doe");
        driver.findElement(By.xpath("//input[@id='inputEmail4']")).sendKeys("john.doe@example.com");
        driver.findElement(By.xpath("//input[@id='inputPassword4']")).sendKeys("Pass@1234");
        driver.findElement(By.xpath("//input[@id='company']")).sendKeys("PvtIndiaLtd");
        driver.findElement(By.xpath("//input[@id='websitename']")).sendKeys("www.sample.com");
        driver.findElement(By.xpath("//input[@id='inputCity']")).sendKeys("Pune");
        driver.findElement(By.xpath("//input[@id='inputAddress1']")).sendKeys("Street 123");
        driver.findElement(By.xpath("//input[@id='inputAddress2']")).sendKeys("Street 345");
        driver.findElement(By.xpath("//input[@id='inputState']")).sendKeys("MH");
        driver.findElement(By.xpath("//input[@id='inputZip']")).sendKeys("123 345");
        new Select(driver.findElement(By.xpath("//select[@name='country']"))).selectByVisibleText("India");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        
        // Step 4: Validate success message
        String AlrtTxt1 = driver.findElement(By.xpath("//p[@class='success-msg hidden']")).getText();
        Assert.assertEquals(AlrtTxt1,"Thanks for contacting us, we will get back to you shortly.");
       
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
          }
    }
}



