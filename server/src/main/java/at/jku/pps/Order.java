package at.jku.pps;

import org.aspectj.weaver.ast.Or;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {
    private int identificationNumber;
    private String description;
    private int priority;
    private String machineDescription;
    private Long id;

    public Order(){
    }

    public Order(int identificationNumber, String description, int priority, String machineDescription) {
        this.identificationNumber = identificationNumber;
        this.description = description;
        this.priority = priority;
        this.machineDescription = machineDescription;

    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMachineDescription() {
        return machineDescription;
    }

    public void setMachineDescription(String machineDescription) {
        this.machineDescription = machineDescription;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
