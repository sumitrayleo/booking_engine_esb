package com.cognizant.orchestration.booking.poi.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cognizant.orchestration.booking.poi.dto.ErrorResponse;
import com.cognizant.orchestration.booking.poi.dto.OrchestrationError;
import com.cognizant.orchestration.booking.poi.enumeration.ReturnCodeEnum;
import com.cognizant.orchestration.booking.poi.exception.PoiApplException;

public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private MessageSource messageSource;

    @Value("${booking.request.id.header.name}")
    private String bookingRequestId;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("MethodArgumentNotValidException - " + appendClientInformation(httpServletRequest));

        headers.setContentType(MediaType.APPLICATION_JSON);

        StringBuffer sb = new StringBuffer();
        for (Object object : exception.getBindingResult().getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                sb.append(messageSource.getMessage(fieldError, null)).append("; ");
            }
        }

        ErrorResponse response = new ErrorResponse();
        response.getErrors().add(new OrchestrationError(ReturnCodeEnum.INVALID_PARAMETERS.toString(), sb.toString()));

        return new ResponseEntity<ErrorResponse>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(MissingServletRequestParameterException exception,
        HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("MissingServletRequestParameterException - " + appendClientInformation(httpServletRequest));

        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse response = new ErrorResponse();
        response.getErrors().add(new OrchestrationError(ReturnCodeEnum.INVALID_PARAMETERS.toString(), exception.getMessage()));

        return new ResponseEntity<ErrorResponse>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException exception, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("IllegalArgumentException - " + appendClientInformation(httpServletRequest));

        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse response = new ErrorResponse();
        response.getErrors().add(new OrchestrationError(ReturnCodeEnum.INVALID_PARAMETERS.toString(), exception.getMessage()));

        return new ResponseEntity<ErrorResponse>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final PoiApplException exception, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("IllegalArgumentException - " + appendClientInformation(httpServletRequest));

        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse response = new ErrorResponse();
        response.getErrors().add(new OrchestrationError(ReturnCodeEnum.INVALID_PARAMETERS.toString(), exception.getMessage()));

        return new ResponseEntity<ErrorResponse>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDefault(Exception exception, HttpServletRequest httpServletRequest) {
        LOGGER.error("Exception - " + appendClientInformation(httpServletRequest));
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getErrors().add(
            new OrchestrationError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getLocalizedMessage()));
        return new ResponseEntity<ErrorResponse>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String appendClientInformation(HttpServletRequest httpServletRequest) {
        final String appPlatform = httpServletRequest.getHeader("appPlatform");
        final String appVersion = httpServletRequest.getHeader("appVersion");
        final StringBuffer buffer = new StringBuffer();
        buffer.append("AppPlatform: ").append(appPlatform).append(", Version: ").append(appVersion).append(", RequestID: ")
            .append(MDC.get(bookingRequestId));
        return buffer.toString();
    }
}
