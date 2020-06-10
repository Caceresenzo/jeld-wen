package jeldwen.common.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jeldwen.common.type.SimpleDuration;

class SimpleDurationTests {

	@Test
	void testHour() {
		assertEquals(12, new SimpleDuration(12, 0, 0).getHour());
		assertEquals(12, new SimpleDuration(12, 30, 30).getHour());
		assertEquals(12, new SimpleDuration(12, 59, 59).getHour());
	}

	@Test
	void testMinutes() {
		assertEquals(0, new SimpleDuration(12, 0, 0).getMinutes());
		assertEquals(30, new SimpleDuration(12, 30, 30).getMinutes());
		assertEquals(59, new SimpleDuration(12, 59, 59).getMinutes());
	}

	@Test
	void testSeconds() {
		assertEquals(0, new SimpleDuration(12, 0, 0).getSeconds());
		assertEquals(30, new SimpleDuration(12, 30, 30).getSeconds());
		assertEquals(59, new SimpleDuration(12, 59, 59).getSeconds());
	}

}