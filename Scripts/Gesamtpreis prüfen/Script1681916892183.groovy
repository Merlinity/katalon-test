import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

'Einkaufswagen anzeigen'
WebUI.click(findTestObject('Startseite/button_show_cart_articles'))

'Warten bis die Seite geladen wurde'
WebUI.waitForElementVisible(findTestObject('Warenkorb/span_price_total'), 5)

'Summe des Einkaufs auslesen'
String priceText = WebUI.getText(findTestObject('Warenkorb/span_price_total'))

priceText = priceText.replace('&nbsp;', '').replace('€', '').replace(',', '.').trim()

Double totalPrice = Double.parseDouble(priceText)

'Prüfen, ob der angezeigte Preis auch der Summe aller Einträge entspricht'
assert (GlobalVariable.totalPrice <= totalPrice + 0.0000001) && (GlobalVariable.totalPrice >= totalPrice - 0.0000001)