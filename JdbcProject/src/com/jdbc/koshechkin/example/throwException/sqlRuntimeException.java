package com.jdbc.koshechkin.example.throwException;

import java.sql.SQLException;

public class sqlRuntimeException extends RuntimeException{


    public sqlRuntimeException(SQLException e) {
    }
}
