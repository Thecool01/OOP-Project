package oopproject.users;

import oopproject.teaching.EmployeeRequest;
import oopproject.teaching.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Employee extends User {
    private String employeeId;
    private double salary;
    private Date hireDate;
    private final List<Message> messages = new ArrayList<>();
    private final List<EmployeeRequest> requests = new ArrayList<>();

    protected Employee() {
    }

    protected Employee(String id, String login, String password, String firstName, String lastName,
                       double salary, Date hireDate) {
        super(id, login, password, firstName, lastName);
        this.employeeId = id;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        setId(employeeId);
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

    public List<Message> getMessages() {
        return messages;
    }

    public List<EmployeeRequest> getRequests() {
        return requests;
    }

    public Message sendMessage(Employee receiver, String text) {
        Message message = new Message("MSG-" + (messages.size() + 1), this, receiver, text);
        messages.add(message);
        if (receiver != null) {
            receiver.getMessages().add(message);
        }
        return message;
    }

    public EmployeeRequest sendRequest(String text) {
        EmployeeRequest request = new EmployeeRequest("REQ-" + (requests.size() + 1), this, text);
        requests.add(request);
        return request;
    }
}
