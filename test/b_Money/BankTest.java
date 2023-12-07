package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}
/*the account havent actually been created so this test case failed at first along with some others, but I fixed this issue thanks to the NullPointerException in here so its good*/
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
		assertTrue(SweBank.getBalance("Alice") == 0);
	}

	@Test(expected = AccountExistsException.class)
	public void testOpenExistingAccount() throws AccountExistsException {
		SweBank.openAccount("Bob");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(100, SEK));
		assertEquals(100, SweBank.getBalance("Ulrika").intValue());
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testDepositToNonexistentAccount() throws AccountDoesNotExistException {
		SweBank.deposit("NonexistentAccount", new Money(100, SEK));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(200, SEK));
		SweBank.withdraw("Ulrika", new Money(50, SEK));
		assertEquals(150, SweBank.getBalance("Ulrika").intValue());
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testWithdrawFromNonexistentAccount() throws AccountDoesNotExistException {
		SweBank.withdraw("NonexistentAccount", new Money(50, SEK));
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(500, SEK));
		assertEquals(500, SweBank.getBalance("Bob").intValue());
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testGetBalanceOfNonexistentAccount() throws AccountDoesNotExistException {
		SweBank.getBalance("NonexistentAccount");
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(300, SEK));
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(100, SEK));
		assertEquals(200, SweBank.getBalance("Ulrika").intValue());
		assertEquals(100, Nordea.getBalance("Bob").intValue());
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testTransferFromNonexistentAccount() throws AccountDoesNotExistException {
		SweBank.transfer("NonexistentAccount", Nordea, "Bob", new Money(100, SEK));
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testTransferToNonexistentAccount() throws AccountDoesNotExistException {
		SweBank.transfer("Ulrika", Nordea, "NonexistentAccount", new Money(100, SEK));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(200, SEK));
		SweBank.addTimedPayment("Ulrika", "payment1", 1, 0, new Money(50, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals(150, SweBank.getBalance("Ulrika").intValue());
		assertEquals(50, Nordea.getBalance("Bob").intValue());
	}
}

