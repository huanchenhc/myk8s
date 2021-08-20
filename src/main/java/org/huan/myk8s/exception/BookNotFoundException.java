package org.huan.myk8s.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends CommonException {
	public BookNotFoundException(String errCode, String message) {
		super(errCode, message);
	}
}
