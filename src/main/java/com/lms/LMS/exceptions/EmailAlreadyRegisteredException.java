package com.lms.LMS.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException
{
    public EmailAlreadyRegisteredException(String message)
    {
        super(message);
    }
}