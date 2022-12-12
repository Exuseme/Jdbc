package com.jdbc.koshechkin.example;


import com.jdbc.koshechkin.example.Entity.employeeEntity;
import com.jdbc.koshechkin.example.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;
import static com.jdbc.koshechkin.example.Dao.EmployeeDao.findAllFilter;
import static com.jdbc.koshechkin.example.Dao.EmployeeDao.findAllFilterILIKE;

public class Main {
    public static void main(String[] args) throws SQLException {

        EmployeeDTO employeeDTO = new EmployeeDTO(2, 0, "Vladislav");
        List<employeeEntity> entityList = findAllFilterILIKE(employeeDTO);

        System.out.println(entityList);
    }
}
