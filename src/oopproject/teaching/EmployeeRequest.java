package oopproject.teaching;

import oopproject.enums.RequestStatus;
import oopproject.users.Employee;
import oopproject.users.Manager;

import java.io.Serializable;

public class EmployeeRequest implements Serializable {
    private String requestId;
    private Employee sender;
    private String text;
    private RequestStatus status = RequestStatus.PENDING;
    private Manager signedBy;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String requestId, Employee sender, String text) {
        this.requestId = requestId;
        this.sender = sender;
        this.text = text;
    }

    public String getRequestId() {
        return requestId;
    }

    public Employee getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Manager getSignedBy() {
        return signedBy;
    }

    public void approve(Manager manager) {
        status = RequestStatus.APPROVED;
        signedBy = manager;
    }

    public void reject(Manager manager) {
        status = RequestStatus.REJECTED;
        signedBy = manager;
    }
}
