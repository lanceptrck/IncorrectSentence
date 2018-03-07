package com.esrawa.domain;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ParallelDataTest {

	@Test
	public void identifyErrorDifferenceTest_SpellingError() throws IOException {
		ParallelData p = new ParallelData().of("umakyat", "umakyat-", "HUE", "HUE");
		assertEquals(0, p.identifyErrorDifference());
	}
	
	@Test
	public void identifyErrorDifferenceTest_MergingError() throws IOException {
		ParallelData p = new ParallelData().of("umakyat ako ng bakod", "umakyat akong bakod", "HUE", "HUE");
		assertEquals(-1, p.identifyErrorDifference());
	}
	
	
	@Test
	public void identifyErrorTypeTest_SpellingError() throws IOException {
		ParallelData p = new ParallelData().of("umakyat", "umakyat-", "HUE", "HUE");
		assertEquals(ErrorType.SPELLING, p.identifyErrorType());
	}
	
	@Test
	public void identifyErrorTypeTest_UnmergingError() throws IOException {
		ParallelData p = new ParallelData().of("Tilapia", "Tila pia", "HUE", "HUE");
		assertEquals(ErrorType.UNMERGING, p.identifyErrorType());
	}
	
	@Test
	public void identifyErrorTypeTest_DeletionError() throws IOException {
		ParallelData p = new ParallelData().of("Ako ay isang Tilapia", "Ako isang Tilapia", "HUE", "HUE");
		assertEquals(ErrorType.DELETION, p.identifyErrorType());
	}
	
	@Test
	public void identifyErrorTypeTest_NoError() throws IOException {
		ParallelData p = new ParallelData().of("Ako ay isang Tilapia", "Ako ay isang Tilapia", "HUE", "HUE");
		assertEquals(ErrorType.NOERROR, p.identifyErrorType());
	}
	
	@Test
	public void identifyErrorTypeTest_InsertionError() throws IOException {
		ParallelData p = new ParallelData().of("Ako dapat ay may lobo", "Ako dapat ang ay may lobo", "HUE", "HUE");
		assertEquals(ErrorType.INSERTION, p.identifyErrorType());
	}
	
	@Test
	public void identifyErrorTypeTest_SubstitutionError() throws IOException {
		ParallelData p = new ParallelData().of("Siya dapat ay may aso", "Siya dapat ang may aso", "HUE", "HUE");
		assertEquals(ErrorType.SUBSTITUTION, p.identifyErrorType());
	}
	

}
