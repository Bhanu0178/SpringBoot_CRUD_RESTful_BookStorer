package org.bhanu.exception;

@SuppressWarnings("serial")
public class BookNotFoundException extends Exception {
	public BookNotFoundException(String errMsg) {
		super(errMsg);
	}
}
