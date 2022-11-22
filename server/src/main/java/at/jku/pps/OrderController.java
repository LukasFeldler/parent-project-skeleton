package at.jku.pps;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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

    @GetMapping("/sortedOrders")
    public List<ProductionOrder> getSortedOrder(){
        List<ProductionOrder> orders = repository.findAll(Sort.by(Sort.Direction.ASC,"priority"));
        return orders;
    }

    @PostMapping("/newOrderFirst")
    public ResponseEntity<ProductionOrder> newOrderFirst(String description,String machineDescription){
        return ResponseEntity.ok(insertOrder(description,1,machineDescription));
    }

    @PostMapping("/newOrderLast")
    public ResponseEntity<ProductionOrder> newOrderLast(String description,String machineDescription){
        int last = repository.findAll().stream().map(ProductionOrder::getPriority).mapToInt(p -> p).max().orElse(0)+1;
        return ResponseEntity.ok(insertOrder(description,last,machineDescription));
    }

    @PostMapping("/newOrderAt")
    public ResponseEntity<ProductionOrder> newOrderAt(String description,int priority, String machineDescription){
        if (priority <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid priority");
        }
        return ResponseEntity.ok(insertOrder(description,priority,machineDescription));
    }

    @DeleteMapping("/deleteOrders")
    public ResponseEntity<ProductionOrder> deleteOrder(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllOrder")
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

    private ProductionOrder insertOrder(String description, int priority, String machineDescription){
        int highestPrio = repository.findAll().stream().map(ProductionOrder::getPriority).mapToInt(p -> p).max().orElse(0)+1;
        if (priority > highestPrio){
            priority = highestPrio;
        }
        final int newPrio = priority;
        repository.findAll().stream().filter(o -> o.getPriority() >= newPrio).forEach(o -> o.setPriority(o.getPriority()+1));
        final ProductionOrder productionOrder = new ProductionOrder(description,priority,machineDescription);
        repository.save(productionOrder);
        return productionOrder;
    }

}
