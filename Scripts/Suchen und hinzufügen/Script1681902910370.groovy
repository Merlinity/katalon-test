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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

'Text in Suchfeld eingeben'
WebUI.setText(findTestObject('Startseite/input_search_field'), desiredItem)

WebUI.delay(2)

'Such-Button klicken'
WebUI.click(findTestObject('Suche/button_search'))

WebUI.delay(2)

TestObject testObject

if (WebUI.waitForElementVisible(findTestObject('Suche/link_result_item_amazons_choice'), 1)) {
    testObject = findTestObject('Suche/link_result_item_amazons_choice')
} else if (WebUI.waitForElementVisible(findTestObject('Suche/link_result_item_by_index', [('index') : '1']), 1)) {
    testObject = findTestObject('Suche/link_result_item_by_index', [('index') : '1'])
}

'Ergebnis Item klicken'
WebUI.click(testObject)

WebUI.delay(2)

try {
	String ganzzahl = WebUI.getText(findTestObject('Produktseite/span_price_whole'))
	
	String dezimalzahl = WebUI.getText(findTestObject('Produktseite/span_price_fraction'))
	
	double price = Double.parseDouble(ganzzahl + '.' + dezimalzahl)
	
	GlobalVariable.totalPrice += price
} catch(Exception e) {
	e.printStackTrace()
	KeywordUtil.markError('Gesamtpreis von ' + desiredItem + ' wurde nicht zum Gesamtpreis hinzugefügt.')
}

'In den Einkaufswagen'
WebUI.click(findTestObject('Produktseite/button_add_to_cart'))

'Prüfen, ob die Aufforderung zum Akzeptieren der Cookies auftaucht'
boolean warrantyPresent = WebUI.waitForElementVisible(findTestObject('Produktseite/button_no_coverage'), 5)

'Garantieverlängerung ablehnen, falls die Aufforderung da ist'
if (warrantyPresent) {
    'Garantieverlängerung ablehnen'
    WebUI.click(findTestObject('Produktseite/button_no_coverage'))

    WebUI.delay(2)
}

WebUI.delay(2)

'Browser schließen'

