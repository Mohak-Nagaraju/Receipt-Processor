package com.fetch.receiptprocessor;

import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class ReceiptServiceTest {

	private ReceiptService receiptService;

	@BeforeEach
	void setUp() {
		receiptService = new ReceiptService();
	}

	@Test
	void testCalculatePoints_OnlyRetailerNamePoints() {
		// Given
		Receipt receipt = new Receipt("Walmart", "2022-01-01", "13:01", "10.00", Arrays.asList());

		// When
		int points = receiptService.calculatePoints(receipt);

		// Then
		assertEquals(88, points); // Points breakdown should give 88 for "Walmart"
	}

	@Test
	void testCalculatePoints_WithMultipleRulesApplied() {
		// Given
		Receipt receipt = new Receipt("M&M Corner Market", "2022-03-20", "14:33", "9.00", Arrays.asList(
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25")
		));

		// When
		int points = receiptService.calculatePoints(receipt);

		// Then
		assertEquals(109, points); // Points breakdown should give 109 for the given receipt
	}

	@Test
	void testProcessReceipt_ShouldReturnValidId() {
		// Given
		Receipt receipt = new Receipt("Walmart", "2022-01-01", "13:01", "10.00", Arrays.asList());

		// When
		String receiptId = receiptService.processReceipt(receipt);

		// Then
		assertNotNull(receiptId); // Ensure that the receipt ID is not null
	}

	@Test
	void testGetPoints_ShouldReturnCorrectPoints() {
		// Given
		Receipt receipt = new Receipt("Walmart", "2022-01-01", "13:01", "10.00", Arrays.asList());
		String receiptId = receiptService.processReceipt(receipt);

		// When
		Integer points = receiptService.getPoints(receiptId);

		// Then
		assertNotNull(points); // Ensure points are not null
		assertEquals(88, points); // Points for this receipt should be 88
	}
}
