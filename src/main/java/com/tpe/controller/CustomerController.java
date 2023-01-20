package com.tpe.controller;


import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired //field injection
    private CustomerService customerService;

    //1-Spring Bootu selamlama:)->http://localhost:8080/customers/greet
    @GetMapping("/greet")
    public String greetSpringBoot(){
        return "Hello Spring Boot :)";
    }

    @PostMapping("/save") //2-customer oluşturma/kaydetme->http://localhost:8080/customers/save + Json Body
    public ResponseEntity<String> saveCustomer(@Valid @RequestBody Customer customer) {

        customerService.saveCustomer(customer);

        String message = "Customer is saved successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //3-Tüm customerları getirme->http://localhost:8080/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customerList = customerService.getAllCustomer();

        return ResponseEntity.ok(customerList);
    }


    //4-Id ile tek bir customer getirme->http://localhost:8080/customers/1
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customer") //http://localhost:8080/customers/customer?id=1
    public ResponseEntity<Customer> getCustomerById2(@RequestParam("id") Long id){
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomerById(@RequestParam("id") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PutMapping("/update/{id}") //http://localhost:8080/customers/update/1
    public ResponseEntity<Map<String,String>> updateCustomer(@PathVariable("id") Long id,
                                                    @Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomerById(id,customerDTO);
        Map<String, String> map = new HashMap<>();
        map.put("message","The Customer updated successfully");
        map.put("status","true");
        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    //Pageleme islemi
    //6-tüm customerları page page gösterme
    //http://localhost:8080/customers/page?page=1&size=2&sort=id&direction=ASC

    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getAllWithPage(@RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("sort") String prop,
                                                         @RequestParam("direction")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Customer> customerPage = customerService.getWithAllPage(pageable);

        return ResponseEntity.ok(customerPage);

    }
}
