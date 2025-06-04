package com.brokenlink.scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinkScanner {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://example.com"); // Change to your target website

        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("🔗 Total Links Found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println("⚠️ Empty or null href");
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
                connection.setRequestMethod("HEAD");
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    System.out.println("❌ Broken Link: " + url + " => " + responseCode);
                } else {
                    System.out.println("✅ Valid Link: " + url + " => " + responseCode);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Exception for URL: " + url + " => " + e.getMessage());
            }
        }

        driver.quit();
    }
}
