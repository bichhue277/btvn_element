package org.example;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Unit test for simple App.
 */
public class AppTest
    /*
    Thực hiện viết test script cho các case (login ko có username/pass, pass sai, username ko tồn tại,
    username/pass đúng) cho trang https://fado.vn/dang-nhap?redirect=https%3A%2F%2Ffado.vn%2F
     */
{
    WebDriver driver;
    @Before
    public void start()
    {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.get("https://fado.vn/dang-nhap?redirect=https%3A%2F%2Ffado.vn%2F");
    }
    @Test
    public void login_without_userName() throws InterruptedException {
        WebElement txtUserName = this.driver.findElement(By.cssSelector("input[type=\"email\"]"));
        WebElement txtPassWord = this.driver.findElement(By.cssSelector("input[type=\"password\"]"));
        WebElement btnLogin = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        //txtUserName.sendKeys("");
        txtPassWord.sendKeys("12345678");
        btnLogin.click();
        Thread.sleep(1000);
        WebElement lbMessage = this.driver.findElement(By.cssSelector("label[id=\"auth-block__form-group__email-error\"]"));
        Assert.assertEquals("Vui lòng nhập dữ liệu",lbMessage.getText());
    }
    @Test
    public void login_without_passWord() throws InterruptedException {
        WebElement txtUserName = this.driver.findElement(By.cssSelector("input[type=\"email\"]"));
        WebElement txtPassWord = this.driver.findElement(By.cssSelector("input[type=\"password\"]"));
        WebElement btnLogin = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        txtUserName.sendKeys("huedtb@gmail.com");
        //txtPassWord.sendKeys("");
        btnLogin.click();
        Thread.sleep(1000);
        WebElement lbMessage = this.driver.findElement(By.cssSelector("label[id=\"password-error\"]"));
        Assert.assertEquals("Vui lòng nhập dữ liệu",lbMessage.getText());
    }
    @Test
    public void login_with_wrong_passWord() throws InterruptedException {
        WebElement txtUserName = this.driver.findElement(By.cssSelector("input[type=\"email\"]"));
        WebElement txtPassWord = this.driver.findElement(By.cssSelector("input[type=\"password\"]"));
        WebElement btnLogin = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        txtUserName.sendKeys("bichhue11995@gmail.com");
        txtPassWord.sendKeys("123");
        btnLogin.click();
        Thread.sleep(1000);
        WebElement lbMessage = this.driver.findElement(By.cssSelector("div.my-alert.-alert-danger"));
        Assert.assertEquals("Có lỗi xảy ra:\n" + "- Mật khẩu không đúng, vui lòng kiểm tra lại",lbMessage.getText().replace("\n\r", "").toString().trim());
    }
    @Test
    public void login_with_nonExisting_userName() throws InterruptedException {
        WebElement txtUserName = this.driver.findElement(By.cssSelector("input[type=\"email\"]"));
        WebElement txtPassWord = this.driver.findElement(By.cssSelector("input[type=\"password\"]"));
        WebElement btnLogin = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        txtUserName.sendKeys("huedtb@gmail.com");
        txtPassWord.sendKeys("12345678");
        btnLogin.click();
        Thread.sleep(1000);
        WebElement lbMessage = this.driver.findElement(By.cssSelector("div.my-alert.-alert-danger"));
        Assert.assertEquals("Có lỗi xảy ra:\n" + "- Tài khoản không tồn tại, vui lòng kiểm tra lại",lbMessage.getText().replace("\n\r", "").toString().trim());
    }
    @Test
    public void login_success() throws InterruptedException {
        WebElement txtUserName = this.driver.findElement(By.cssSelector("input[type=\"email\"]"));
        WebElement txtPassWord = this.driver.findElement(By.cssSelector("input[type=\"password\"]"));
        WebElement btnLogin = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        txtUserName.sendKeys("bichhue11995@gmail.com");
        txtPassWord.sendKeys("A12345678@");
        btnLogin.click();

        Thread.sleep(2000);
        String  actual = driver.getCurrentUrl();
        String expected = "https://fado.vn/";
        Assert.assertEquals(actual,expected);

    }
    @After
    public void finish()
    {
        this.driver.quit();
    }
}
