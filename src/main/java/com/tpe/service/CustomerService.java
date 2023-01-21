package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {

        boolean isExistCustomer = customerRepository.existsByEmail(customer.getEmail());

        if(isExistCustomer) {
            throw new ConflictException("Customer already exists by email"+customer.getEmail());
        }
        customerRepository.save(customer);

    }

    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Customer getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException("Customer not found by id : "+id));
        return customer;
    }

    public void deleteCustomerById(Long id){
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    public void updateCustomerById(Long id, CustomerDTO customerDTO) {

        boolean existEmail = customerRepository.existsByEmail(customerDTO.getEmail());
        Customer customer = getCustomerById(id);

        if (existEmail && !customerDTO.getEmail().equals(customer.getEmail())){
            throw new ConflictException("Email is already exist");
        }

        customer.setName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());

        customerRepository.save(customer);

    }

    public Page<Customer> getWithAllPage(Pageable pageable) {

        return customerRepository.findAll(pageable);

    }

    public List<Customer> findCustomerByName(String name){

        return customerRepository.findByName(name);
    }

    public List<Customer> findCustomerByFullName(String name, String lastName) {

        return customerRepository.findByNameAndLastName(name,lastName);
    }

    public List<Customer> findCustomerByContainLetter(String letter) {
        String lowerLetter = letter.toLowerCase();
        return customerRepository.findAllByNameLike(lowerLetter);
    }
}
