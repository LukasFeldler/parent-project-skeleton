package at.jku.pps;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private final OrderRepository repository;

    public OrderController(final OrderRepository repository){
        this.repository = repository;
    }

    @GetMapping("/orders")
    public List<Order> getOrders(){
        return repository.findAll();
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> newOrder(int identificationNumber, String description, int priority, String machineDescription){
        if (description == null ||
                description.isBlank() ||
                machineDescription == null ||
                machineDescription.isBlank() ||
                priority == 0 || identificationNumber == 0){
            return ResponseEntity.badRequest().build();
        }
        final Order order = new Order(identificationNumber, description, priority, machineDescription);
        repository.save(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orders")
    public ResponseEntity<Order> deleteOrder(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //@DeleteMapping("/order")
    //public ResponseEntity<Order> deleteAllOrders(){
        //repository.deleteAll();
        //return ResponseEntity.ok().build();
    //}

    @PutMapping("/orders")
    public ResponseEntity<Order> changeMachineDescription(Long id, String newMachineDescription){
        repository.findById(id).ifPresent(o -> o.setMachineDescription(newMachineDescription));
        final Optional<Order> order = repository.findById(id);
        repository.save(order.get());
        return ResponseEntity.ok(order.get());
    }

    @PutMapping("/orders")
    public ResponseEntity<Order> changePriority(Long id, int newPriority){
        repository.findById(id).ifPresent(o -> o.setPriority(newPriority));
        final Optional<Order> order = repository.findById(id);
        repository.save(order.get());
        return ResponseEntity.ok(order.get());
    }

}
