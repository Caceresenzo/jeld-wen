package jeldwen.common.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jeldwen.common.type.CustomDate;

class CustomDateTests {
	
	@Test
	void testDay_fromString() {
		assertEquals("12/00/0000", CustomDate.fromString("12/00/0000").toString());
	}
	
}