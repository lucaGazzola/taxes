package it.unimib.taxes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import it.unimib.taxes.invoice.InvoiceTest;
import it.unimib.taxes.item.ItemTest;
import it.unimib.taxes.utils.InputParserTest;
import it.unimib.taxes.utils.MyTaxesCalculatorTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   InvoiceTest.class,
   ItemTest.class,
   InputParserTest.class,
   MyTaxesCalculatorTest.class
})

public class UnitTestSuite {   
} 