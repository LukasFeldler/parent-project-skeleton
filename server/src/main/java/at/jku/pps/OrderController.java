package at.jku.pps;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private OrderRepository repository;

    public OrderController(final OrderRepository repository){
        this.repository = repository;
    }

    @GetMapping("/orders")
    public List<ProductionOrder> getOrders(){
        return repository.findAll();
    }

    @PostMapping("/newOrder")
    public ResponseEntity<ProductionOrder> newOrder(String description, int priority, String machineDescription){
        if (description == null ||
                description.isBlank() ||
                machineDescription == null ||
                machineDescription.isBlank() ||
                priority == 0){
            return ResponseEntity.badRequest().build();
        }
        final ProductionOrder order = new ProductionOrder(description, priority, machineDescription);
        repository.save(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/deleteAllOrders")
    public ResponseEntity<ProductionOrder> deleteOrder(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<ProductionOrder> deleteAllOrders(){
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeOrderDescription")
    public ResponseEntity<ProductionOrder> changeMachineDescription(Long id, String newMachineDescription){
        repository.findById(id).ifPresent(o -> o.setMachineDescription(newMachineDescription));
        final Optional<ProductionOrder> order = repository.findById(id);
        repository.save(order.get());
        return ResponseEntity.ok(order.get());
    }

    @PutMapping("/changeOrderPriority")
    public ResponseEntity<ProductionOrder> changePriority(Long id, int newPriority){
        repository.findById(id).ifPresent(o -> o.setPriority(newPriority));
        final Optional<ProductionOrder> order = repository.findById(id);
        repository.save(order.get());
        return ResponseEntity.ok(order.get());
    }

}
