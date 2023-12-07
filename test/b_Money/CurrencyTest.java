package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.001);
	}

	@Test
	public void testSetRate() {
		SEK.setRate(0.2);
		assertEquals(0.2, SEK.getRate(), 0.001);
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK.universalValue(10000).intValue());
	}

	@Test
	public void testValueInThisCurrency() {
		assertEquals(10000, SEK.valueInThisCurrency(10000, SEK).intValue());
	}
}

