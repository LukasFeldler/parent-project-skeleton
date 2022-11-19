package at.jku.pps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class OrderController {

    @GetMapping("/customers")
    public List<Order> getOrders(){
        return Arrays.asList(new Order(1,"test",1,"Stampfmaschine"),
                            new Order(2,"test2",2,"Stampfmaschine"));
    }
}
