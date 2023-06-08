package com.hospital.exceptions;

import java.sql.SQLException;

public class DAOException extends RuntimeException{
    public DAOException (String message, Throwable cause) {
        super(message, cause);
    }
}
