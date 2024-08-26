package com.seatbooking.system.exception;

public class InvalidPaymentRequestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPaymentRequestException(String message) {
        super(message);
    }
}
