package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Alice", new Money(500, SEK));
		testAccount.addTimedPayment("payment1", 1, 1, new Money(100, SEK), SweBank, "Alice");
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
		testAccount.tick();  // Trigger timed payment
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(500, SEK));
		assertEquals(new Money(9999500, SEK), testAccount.getBalance());
	}

	@Test
	public void testGetBalance() {
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
	}
}

