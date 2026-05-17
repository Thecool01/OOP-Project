package oopproject.teaching;

import oopproject.enums.RequestStatus;
import oopproject.users.Employee;
import oopproject.users.Manager;

import java.io.Serial;
import java.util.Objects;
import java.io.Serializable;

public class EmployeeRequest implements Serializable {
    @Serial //serialization version
    private static final long serialVersionUID = 1L; //версия класса

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

    public boolean isPending() {
        return status == RequestStatus.PENDING;
    }

    // allows editing request text
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeRequest that)) return false;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "requestId='" + requestId + '\'' +
                ", sender=" + sender +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", signedBy=" + signedBy +
                '}';
    }
}
