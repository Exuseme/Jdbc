package com.jdbc.koshechkin.example.Entity;

public class employeeEntity {
    private int id ;
    private String firstName;
    private String lastName;
    private int companyId;
    private Long salary;
    private String department;

    public employeeEntity(String firstName, String lastName, int companyId, Long salary, String department) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.companyId = companyId;
        this.salary = salary;
        this.department = department;
    }

    public employeeEntity(int id,String firstName, String lastName, int companyId, Long salary, String department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyId = companyId;
        this.salary = salary;
        this.department = department;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLast_name(String lastName) {
        this.lastName = lastName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompany_id(int company_id) {
        this.companyId = company_id;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "employeeEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyId=" + companyId +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
