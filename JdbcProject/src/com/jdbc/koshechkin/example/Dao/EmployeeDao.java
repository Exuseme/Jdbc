package com.jdbc.koshechkin.example.Dao;


import com.jdbc.koshechkin.example.Entity.employeeEntity;
import com.jdbc.koshechkin.example.dto.EmployeeDTO;
import com.jdbc.koshechkin.example.getConnection.ConnectionToDateBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDao {

    private static final EmployeeDao employeeDao = new EmployeeDao();

    private EmployeeDao() {

    }

    private static final String selectById = """   
            SELECT 
                       e.id,
                       e.first_name,
                       e.last_name,
                       e.company_id,
                       e.salary,
                       e.department
                       FROM company_storage.employee e WHERE  e.id = ?;
                       """;

    private static final String deleteById = """
            DELETE FROM company_storage.employee WHERE id = ?
            """;

    private static final String select = """
            SELECT 
            e.id,
            e.first_name,
            e.last_name,
            e.company_id,
            e.salary,
            e.department
            FROM company_storage.employee e
            """;

    private static final String insertToSqlTable = """
            INSERT INTO  company_storage.employee (first_name, last_name, company_id, salary, department) 
            VALUES (?,?,?,?,?)
                        
            """;

    private static String update = """
            UPDATE company_storage.employee 
            SET first_name = ?, last_name = ?, company_id = ?, salary = ?, department = ? 
            WHERE id = ?;
            """;

    public static List<employeeEntity> selectFromSql() {
        List<employeeEntity> list = new ArrayList<>();

        try (Connection connection = ConnectionToDateBase.open();
             PreparedStatement preparedStatement = connection.prepareStatement(select)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                list.add(new employeeEntity(

                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("company_id"),
                        resultSet.getLong("salary"),
                        resultSet.getString("department")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void delete(int id) {

        try (Connection connection = ConnectionToDateBase.open();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteById)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<employeeEntity> findById(int id) {

        try (Connection connection = ConnectionToDateBase.open();
             PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            employeeEntity employee = null;
            if (resultSet.next()) {
                employee = new employeeEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("company_id"),
                        resultSet.getLong("salary"),
                        resultSet.getString("department"));

            }
            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(employeeEntity employeeEntity) {
        try (Connection connection = ConnectionToDateBase.open(); PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, employeeEntity.getFirstName());
            preparedStatement.setString(2, employeeEntity.getLastName());
            preparedStatement.setInt(3, employeeEntity.getCompanyId());
            preparedStatement.setLong(4, employeeEntity.getSalary());
            preparedStatement.setString(5, employeeEntity.getDepartment());
            preparedStatement.setInt(6, employeeEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static employeeEntity insert(employeeEntity employeeEntity) {

        try (Connection connection = ConnectionToDateBase.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertToSqlTable, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, employeeEntity.getFirstName());
            preparedStatement.setString(2, employeeEntity.getLastName());
            preparedStatement.setInt(3, employeeEntity.getCompanyId());
            preparedStatement.setLong(4, employeeEntity.getSalary());
            preparedStatement.setString(5, employeeEntity.getDepartment());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employeeEntity.setId(generatedKeys.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeEntity;


    }


    public static List<employeeEntity> findAllFilter(EmployeeDTO employeeDTO) {
        List<Object> filetList = new ArrayList<>();
        filetList.add(employeeDTO.limit());
        filetList.add(employeeDTO.offset());

        List<employeeEntity> entityList = new ArrayList<>();

        String sqlFilet = select + "LIMIT ? OFFSET ?";
        try (Connection con = ConnectionToDateBase.open(); PreparedStatement preparedStatement = con.prepareStatement(sqlFilet)) {
            for (int i = 0; i < filetList.size(); i++) {
                preparedStatement.setObject(i + 1, filetList.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entityList.add(new employeeEntity(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("company_id"),
                        resultSet.getLong("salary"),
                        resultSet.getString("department")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entityList;
    }

    public static List<employeeEntity> findAllFilterILIKE(EmployeeDTO employeeDTO) {

        List<Object> parameters = new ArrayList<>();
        List<String> where = new ArrayList<>();
        List<employeeEntity> list = new ArrayList<>();


        if (employeeDTO.first_name() != null) {
            where.add(" first_name = ? ");
            parameters.add(employeeDTO.first_name());
        }

        parameters.add(employeeDTO.limit());
        parameters.add(employeeDTO.offset());

        String whereString = where.stream().collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        String resultFindAll = select + "ORDER BY e.id" + whereString;

        try (Connection con = ConnectionToDateBase.open(); PreparedStatement statement = con.prepareStatement(resultFindAll)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new employeeEntity(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("company_id"),
                        resultSet.getLong("salary"),
                        resultSet.getString("department")));

            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}







