package org.huan.myk8s.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DupblicatedBookException extends CommonException {
	
	public DupblicatedBookException(String errCode, String message) {
		super(errCode, message);
	}
}
