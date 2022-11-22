package at.jku.pps;

import org.aspectj.weaver.ast.Or;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductionOrder {

    private String description;
    private int priority;
    private String machineDescription;
    @Id
    @GeneratedValue
    private Long id;


    public ProductionOrder(){
    }

    public ProductionOrder(String description, int priority, String machineDescription) {

        this.description = description;
        this.priority = priority;
        this.machineDescription = machineDescription;

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


    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }


}
