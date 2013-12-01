package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IntervalFeatureTest {
	private MockedFeature mockedFeature;

	@Before
	public void setUpBefore() throws Exception {
		mockedFeature = this.new MockedFeature();
	}

	@Test
	public void testValidateStrandness() throws IntervalFeatureException {
		mockedFeature.validateStrandness("+");
		mockedFeature.validateStrandness("-");
	}

	@Test
	public void testValidateStrandnessFail() {
		try {
			mockedFeature.validateStrandness("anythink but + or -");
		} catch (IntervalFeatureException e) {
			// OK
		}
	}

	@Test
	public void testIsInternal() throws IntervalFeatureException {
		int start = 1;
		int end = 12;
		mockedFeature.setX(start);
		mockedFeature.setY(end);
		assertTrue(mockedFeature.isInternal(start + 1));
		assertTrue(mockedFeature.isInternal(start));
		assertTrue(mockedFeature.isInternal(end));
		assertFalse(mockedFeature.isInternal(end + 1));
	}

	@Test
	public void testIsInternalError() {
		mockedFeature.setX(0);
		mockedFeature.setY(0);
		try {
			assertTrue(mockedFeature.isInternal(1));
		} catch (IntervalFeatureException e) {
			// OK
		}
	}

	@Test
	public void testLength() {
		// his.getY() - this.getX() + 1;
		int start = 0;
		int end = 4;
		int length = end - start + 1;
		mockedFeature.setX(start);
		mockedFeature.setY(end);
		assertEquals(length, mockedFeature.length());
	}

	private class MockedFeature extends IntervalFeature {
		private String strandness;
		private int x;
		private int y;

		@Override
		public String getStrandness() {
			return strandness;
		}

		@Override
		public void setStrandness(String strandness) {
			this.strandness = strandness;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public void setX(int x) {
			this.x = x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public void setY(int y) {
			this.y = y;
		}

	}
}
