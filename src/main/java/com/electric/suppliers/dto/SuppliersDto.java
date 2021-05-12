package com.electric.suppliers.dto;

import com.electric.suppliers.api.SuppliersApi;
import com.electric.suppliers.dto.helper.SuppliersHelper;
import com.electric.suppliers.model.form.InsertDataForm;
import com.electric.suppliers.pojo.Suppliers;
import com.electric.suppliers.util.Helper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SuppliersDto {

    private WebDriver driver;

    @Autowired
    private SuppliersApi api;

    public WebDriver setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lenovo\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        driver = new ChromeDriver(options);
        ((JavascriptExecutor) driver).executeScript("window.key = \"blahblah\";");
        driver.manage().window().maximize();
        return driver;
    }

    public void addCsvData(String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<InsertDataForm> suppliersList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                InsertDataForm dataForm = new InsertDataForm(
                        csvRecord.get("Supplier Name"),
                        csvRecord.get("Pricing Structure"),
                        csvRecord.get("Price $/month"),
                        csvRecord.get("Price (cents/kWh)"),
                        csvRecord.get("Introductory Price"),
                        csvRecord.get("Enrollment Fee"),
                        csvRecord.get("Contract Term"),
                        csvRecord.get("Cancellation Fee"),
                        csvRecord.get("Automatic Renewal"),
                        csvRecord.get("Renewable Energy Content %"),
                        csvRecord.get("100% New Regional Resources"),
                        csvRecord.get("Additional Products/Services"),
                        csvRecord.get("Estimated Monthly Cost")
                );

                suppliersList.add(dataForm);
            }
            insertData(suppliersList);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public void electricityRatesWebsite(String zipCode) {
        Document doc;
        try {
            doc = Jsoup.connect("https://electricityrates.com/compare/electricity/" + zipCode + "/?sid=undefined&energyType=ELECTRIC").get();
            Element utilityNameDD = doc.getElementById("utilitySelect");
            String utilityName = "abc";
            for (Element option : utilityNameDD.getElementsByAttribute("selected")) {
                if (option.hasAttr("value")) {
                    utilityName = option.text();
                }
            }
            List<Element> list = doc.getElementsByClass("_3OZ9QmG51QaE _2vcR2zhNGrai _2ghwbTabrCq8 _35OlZnK6MiVd UFcvscMgGSJo");

            for (Element element : list) {
                double rate = Double.parseDouble(element.getElementsByClass("rateCost").get(0).text().substring(0, 4).trim());
                Long duration = Long.parseLong(element.getElementsByClass("termLength").get(0).text().substring(0, 2).trim());
                String provider = element.getElementsByTag("h3").text();
                String plan = element.getElementsByTag("h4").text();
                double content = 49.00;
                double cancellationFee = 0.00;
                List<Element> source = element.getElementsByClass("s_enoWFd4-Oh");
                if (source.size() > 0) {
                    content = 100.00;
                }
                if (provider.equalsIgnoreCase("Constellation")) {
                    cancellationFee = 150.00;
                }

                Double monthlyAmt = Double.parseDouble(new DecimalFormat("#.##").format(rate * 7));
                Suppliers suppliers = SuppliersHelper.convertToSuppliers(provider, rate, duration, plan, content, cancellationFee, monthlyAmt, zipCode, utilityName);
                api.addSuppliers(suppliers);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void energySwitchMAWebsite(String zipCode, WebDriver driver) {
        driver.navigate().to("https://energyswitchma.gov/#/compare/2/1/" + zipCode + "//");
//        ((JavascriptExecutor) driver).executeScript("window.open('https://energyswitchma.gov/#/compare/2/1/"+zipCode+"//')");
//        ArrayList<String> tabs = new ArrayList<String>(this.driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(1));
        Helper.sleep(3000);
        WebElement input = this.driver.findElement(By.xpath("//input[@name='monthlyUsage']"));
        input.click();
        input.clear();
        input.sendKeys("700");
        Helper.sleep(1000);
        driver.findElement(By.xpath("//div[@class='panel-body']//button")).click();
        Helper.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
        if (Helper.isElementPresent(element)) {
            driver.findElement(By.xpath("//button[contains(text(),'Download to CSV (Excel)')]")).click();
        }
        Helper.sleep(3000);
//        driver.manage().window().minimize();
//        driver.switchTo().window(tabs.get(0));
        Helper.sleep(1000);
    }


//    public static void website3() {
//
//        File input = new File("C:\\Users\\Lenovo\\Downloads\\Compare Rates_ElectricityRates.html");
//        Document doc;
//        try {
//            doc = Jsoup.parse(input, "UTF-8", "https://electricityrates.com/compare/electricity/02493/?sid=undefined&energyType=ELECTRIC");
//            Element list = doc.getElementById("pageAContainer");
//            List<Element> rateList = list.getElementsByClass("rateCost");
//            List<Element> termLengthList = list.getElementsByClass("termLength");
//            List<Element> providerList = list.getElementsByTag("h3");
//            List<Element> planNameList = list.getElementsByTag("h4");
//            for (Element e : rateList) {
//                System.out.println("Rate is " + e.text());
//            }
//            for (Element e : termLengthList) {
//                System.out.println("TermLength is " + e.text());
//            }
//            for (Element e : providerList) {
//                System.out.println("Provider is " + e.text());
//            }
//            for (Element e : planNameList) {
//                System.out.println("Plan Name is " + e.text());
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

    private void insertData(List<InsertDataForm> insertDataFormList) {
        List<Suppliers> suppliersList = SuppliersHelper.convertToSuppliersList(insertDataFormList);
        api.addSuppliersInBulk(suppliersList);
    }

    public Suppliers getCheapestPlan(String zipCode, Long duration) {
        List<Suppliers> suppliersList = api.getSuppliersWithZipCodeAndDuration(zipCode, duration);
        if(suppliersList.isEmpty()) {
            return new Suppliers();
        }
        Long requiredId = calculateValue1(suppliersList);
        return api.getSupplierById(requiredId);
    }

    public List<Suppliers> getTopThreeCheaperPlans(String zipCode, Long duration) {
        List<Suppliers> suppliersList = api.getSuppliersWithZipCodeAndDuration(zipCode, duration);
        if(suppliersList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> list = calculateValue2(suppliersList);
        return api.getSuppliersById(list);
    }

    public Suppliers getGreenestPlan(String zipCode, Long duration) {
        Double renewableContent = 100.00;
        List<Suppliers> suppliersList = api.getSuppliersWithZipCodeAndDurationAndContent(zipCode, duration, renewableContent);
        if(suppliersList.isEmpty()) {
            return new Suppliers();
        }
        Long requiredId = calculateValue1(suppliersList);
        return api.getSupplierById(requiredId);
    }

    public List<Suppliers> getTopThreeGreenerPlan(String zipCode, Long duration) {
        Double renewableContent = 100.00;
        List<Suppliers> suppliersList = api.getSuppliersWithZipCodeAndDurationAndContent(zipCode, duration, renewableContent);
        if(suppliersList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> list = calculateValue2(suppliersList);
        return api.getSuppliersById(list);
    }

    private Long calculateValue1(List<Suppliers> suppliersList) {
        Map<Long, Double> supplierMap = new HashMap<>();
        for(Suppliers sup : suppliersList) {
            Double monthlyPrice = sup.getMonthlyPrice();
            Double cancellationFee = sup.getCancellationFee();
            Long duration = sup.getDuration();
            Double meanPrice = (monthlyPrice + (cancellationFee/duration));
            supplierMap.put(sup.getId(), meanPrice);
        }
        Long requiredId = 0L;
        Double maxValueInMap=(Collections.min(supplierMap.values()));
        for (Map.Entry<Long, Double> entry : supplierMap.entrySet()) {
            if (entry.getValue().equals(maxValueInMap)) {
                requiredId = entry.getKey();
            }
        }
        return requiredId;
    }

    private List<Long> calculateValue2(List<Suppliers> suppliersList) {
        Map<Long, Double> supplierMap = new HashMap<>();
        for(Suppliers sup : suppliersList) {
            Double monthlyPrice = sup.getMonthlyPrice();
            Double cancellationFee = sup.getCancellationFee();
            Long duration = sup.getDuration();
            Double meanPrice = (monthlyPrice + (cancellationFee/duration));
            supplierMap.put(sup.getId(), meanPrice);
        }
        return supplierMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<Suppliers> getSuppliers(String zipCode) {
        return api.getAllSuppliersWithZipCode(zipCode);
    }
}
