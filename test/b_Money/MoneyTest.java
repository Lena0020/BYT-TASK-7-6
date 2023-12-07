package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, SEK100.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
	}


	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money result = SEK100.add(EUR10);
		assertEquals(10100, result.getAmount().intValue());
		assertEquals(SEK, result.getCurrency());
	}


	@Test
	public void testSub() {
		Money result = SEK200.sub(EUR20);
		assertEquals(19800, result.getAmount().intValue());
		assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
	}

	@Test
	public void testNegate() {
		Money result = SEK100.negate();
		assertEquals(-10000, result.getAmount().intValue());
		assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testCompareTo() {
		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(SEK100.compareTo(SEKn100) > 0);
		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
	}
}

