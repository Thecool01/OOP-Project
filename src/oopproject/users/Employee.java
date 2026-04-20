package oopproject.users;

import java.util.Date;

public abstract class Employee extends User {
    private double salary;
    private Date hireDate;

    protected Employee() {
    }

    protected Employee(String id, String login, String password, String firstName, String lastName,
                       double salary, Date hireDate) {
        super(id, login, password, firstName, lastName);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}
