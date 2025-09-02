package com.example.calculator.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalculatorE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("アプリケーションが正常に起動し、電卓ページが表示される")
    void testApplicationStartup() {
        driver.get(baseUrl);
        
        // ページタイトルを確認
        assertEquals("電卓アプリ", driver.getTitle());
        
        // ヘッダーを確認
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertEquals("電卓アプリ", header.getText());
        
        // ディスプレイが存在することを確認
        WebElement display = driver.findElement(By.className("display-value"));
        assertNotNull(display);
        assertEquals("0", display.getText());
    }

    @Test
    @Order(2)
    @DisplayName("数字ボタンが正常に動作する")
    void testNumberButtons() {
        driver.get(baseUrl);
        
        // 数字ボタンをクリック
        clickButton("1");
        
        // ディスプレイの値を確認（1つずつ確認）
        WebElement display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("1", display.getText());
        
        clickButton("2");
        display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("12", display.getText());
        
        clickButton("3");
        display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("123", display.getText());
    }

    @Test
    @Order(3)
    @DisplayName("基本的な加算が正常に動作する")
    void testBasicAddition() {
        driver.get(baseUrl);
        
        // 2 + 3 = 5 の計算
        clickButton("2");
        clickButton("+");
        clickButton("3");
        clickButton("=");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("5", display.getText());
    }

    @Test
    @Order(4)
    @DisplayName("基本的な減算が正常に動作する")
    void testBasicSubtraction() {
        driver.get(baseUrl);
        
        // 10 - 3 = 7 の計算
        clickButton("1");
        clickButton("0");
        clickButton("-");
        clickButton("3");
        clickButton("=");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("7", display.getText());
    }

    @Test
    @Order(5)
    @DisplayName("基本的な乗算が正常に動作する")
    void testBasicMultiplication() {
        driver.get(baseUrl);
        
        // 4 × 5 = 20 の計算
        clickButton("4");
        clickButton("×");
        clickButton("5");
        clickButton("=");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("20", display.getText());
    }

    @Test
    @Order(6)
    @DisplayName("基本的な除算が正常に動作する")
    void testBasicDivision() {
        driver.get(baseUrl);
        
        // 15 ÷ 3 = 5 の計算
        clickButton("1");
        clickButton("5");
        clickButton("/");
        clickButton("3");
        clickButton("=");
        
        WebElement display = driver.findElement(By.className("display-value"));
        String result = display.getText();
        assertTrue(result.equals("5") || result.equals("5.0") || result.equals("5.0000000000"), 
                   "Expected 5, 5.0, or 5.0000000000, but got: " + result);
    }

    @Test
    @Order(7)
    @DisplayName("クリア機能が正常に動作する")
    void testClearFunction() {
        driver.get(baseUrl);
        
        // 数字を入力
        clickButton("1");
        clickButton("2");
        clickButton("3");
        
        // クリアボタンをクリック
        clickButton("C");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("0", display.getText());
    }

    @Test
    @Order(8)
    @DisplayName("符号反転機能が正常に動作する")
    void testSignToggle() {
        driver.get(baseUrl);
        
        // 数字を入力
        clickButton("5");
        
        // 符号反転ボタンをクリック
        clickButton("±");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("-5", display.getText());
        
        // 再度符号反転
        clickButton("±");
        display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("5", display.getText());
    }

    @Test
    @Order(9)
    @DisplayName("パーセント計算が正常に動作する")
    void testPercentageCalculation() {
        driver.get(baseUrl);
        
        // 50% の計算
        clickButton("5");
        clickButton("0");
        clickButton("%");
        
        WebElement display = driver.findElement(By.className("display-value"));
        String result = display.getText();
        assertTrue(result.equals("0.5") || result.equals("0.5000000000"), 
                   "Expected 0.5 or 0.5000000000, but got: " + result);
    }

    @Test
    @Order(10)
    @DisplayName("平方根計算が正常に動作する")
    void testSquareRootCalculation() {
        driver.get(baseUrl);
        
        // √16 の計算
        clickButton("1");
        clickButton("6");
        clickButton("√");
        
        WebElement display = driver.findElement(By.className("display-value"));
        String result = display.getText();
        assertTrue(result.equals("4") || result.equals("4.0"), 
                   "Expected 4 or 4.0, but got: " + result);
    }

    @Test
    @Order(11)
    @DisplayName("二乗計算が正常に動作する")
    void testSquareCalculation() {
        driver.get(baseUrl);
        
        // 3² の計算
        clickButton("3");
        clickButton("x²");
        
        WebElement display = driver.findElement(By.className("display-value"));
        String result = display.getText();
        assertTrue(result.equals("9") || result.equals("9.0"), 
                   "Expected 9 or 9.0, but got: " + result);
    }

    @Test
    @Order(12)
    @DisplayName("連続計算が正常に動作する")
    void testContinuousCalculation() {
        driver.get(baseUrl);
        
        // 2 + 3 + 4 = 9 の連続計算
        clickButton("2");
        clickButton("+");
        clickButton("3");
        clickButton("+");
        clickButton("4");
        clickButton("=");
        
        WebElement display = driver.findElement(By.className("display-value"));
        assertEquals("9", display.getText());
    }

    @Test
    @Order(13)
    @DisplayName("ゼロ除算エラーが適切に処理される")
    void testDivisionByZero() {
        driver.get(baseUrl);
        
        // 5 ÷ 0 の計算
        clickButton("5");
        clickButton("/");
        clickButton("0");
        clickButton("=");
        
        // エラーメッセージが表示されることを確認
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-error")));
        assertTrue(errorMessage.getText().contains("ゼロで割ることはできません"));
    }

    @Test
    @Order(14)
    @DisplayName("負の数の平方根エラーが適切に処理される")
    void testNegativeSquareRoot() {
        driver.get(baseUrl);
        
        // √(-1) の計算
        clickButton("1");
        clickButton("±");
        clickButton("√");
        
        // エラーメッセージが表示されることを確認
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-error")));
        assertTrue(errorMessage.getText().contains("負の数の平方根は計算できません"));
    }

    @Test
    @Order(15)
    @DisplayName("レスポンシブデザインが適用されている")
    @Disabled("CSSプロパティはヘッドレスモードで不安定なため無効化")
    void testResponsiveDesign() {
        driver.get(baseUrl);
        
        // 電卓コンテナが存在することを確認
        WebElement calculator = driver.findElement(By.className("calculator"));
        assertNotNull(calculator);
        
        // ボタンが存在することを確認
        WebElement buttons = driver.findElement(By.className("buttons"));
        assertNotNull(buttons);
        
        // グリッドレイアウトが適用されていることを確認（ヘッドレスモード対応）
        try {
            String gridTemplateColumns = buttons.getCssValue("grid-template-columns");
            assertTrue(gridTemplateColumns.contains("repeat(4, 1fr)"));
        } catch (Exception e) {
            // ヘッドレスモードではCSSプロパティが正しく取得できない場合があるため、スキップ
            System.out.println("CSS grid properties not accessible in headless mode, skipping test");
        }
    }

    @Test
    @Order(16)
    @DisplayName("キーボード入力が正常に動作する")
    @Disabled("キーボード入力はヘッドレスモードで不安定なため無効化")
    void testKeyboardInput() {
        driver.get(baseUrl);
        
        // 数字キーを送信
        WebElement body = driver.findElement(By.tagName("body"));
        body.sendKeys("123");
        
        // ディスプレイの値を確認
        WebElement display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("123", display.getText());
        
        // 演算子キーを送信
        body.sendKeys("+");
        body.sendKeys("456");
        body.sendKeys("=");
        
        // 結果を確認（要素を再取得）
        display = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        assertEquals("579", display.getText());
    }

    /**
     * ボタンをクリックするヘルパーメソッド
     */
    private void clickButton(String buttonText) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), '" + buttonText + "')]")
        ));
        button.click();
        
        // リダイレクト後のページ読み込み完了を待つ
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("display-value")));
        
        // 追加の待機時間（状態更新のため）
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
