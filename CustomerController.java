package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(
            @PathVariable int id,
            @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/customers/{id}/deposit")
    public Customer deposit(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        Customer customer = customerService.deposit(
                id,
                request.getAmount()
        );

        if (customer == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Customer not found"
            );
        }

        return customer;
    }

    @PostMapping("/customers/{id}/withdraw")
    public Customer withdraw(
            @PathVariable int id,
            @RequestBody WithdrawRequest request) {

        Customer customer = customerService.withdraw(
                id,
                request.getAmount()
        );

        if (customer == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Customer not found"
            );
        }

        return customer;
    }
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {

        customerService.transfer(
                request.getFromCustomerId(),
                request.getToCustomerId(),
                request.getAmount()
        );

        return "Transfer successful";
    }
    @PostMapping("/customers/{id}/loan")
    public Customer applyLoan(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        return customerService.applyLoan(
                id,
                request.getAmount()
        );
    }
    @PostMapping("/customers/{id}/repay-loan")
    public Customer repayLoan(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        return customerService.repayLoan(
                id,
                request.getAmount()
        );
    }
    @PostMapping("/customers/{id}/fix-loan-interest")
    public Customer fixExistingLoanInterest(@PathVariable int id) {
        return customerService.fixExistingLoanInterest(id);
    }
    @PostMapping("/customers/{id}/personal-loan")
    public Customer applyPersonalLoan(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        return customerService.applyPersonalLoan(
                id, request.getAmount());
    }
    @PostMapping("/customers/{id}/gold-loan")
    public Customer applyGoldLoan(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        return customerService.applyGoldLoan(
                id, request.getAmount());
    }
    @PostMapping("/customers/{id}/insurance")
    public Customer applyInsurance(
            @PathVariable int id,
            @RequestBody DepositRequest request) {

        return customerService.applyInsurance(
                id, request.getAmount()
        );
    }
    @PostMapping("/customers/{id}/emi")
    public Customer applyEmi(
            @PathVariable int id,
            @RequestBody EmiRequest request) {

        return customerService.applyEmi(
                id,
                request.getAmount(),
                request.getMonths()
        );
    }

    @PostMapping("/customers/{id}/credit-card")
    public Customer applyCreditCard(
            @PathVariable int id,
            @RequestBody CreditCardRequest request) {

        return customerService.applyCreditCard(
                id,
                request.getLimit()
        );
    }

    @PostMapping("/customers/{id}/nominee")
    public Customer addNominee(
            @PathVariable int id,
            @RequestParam String nomineeName,
            @RequestParam String nomineePhone,
            @RequestParam String nomineeRelationship) {

        return customerService.addNominee(
                id,
                nomineeName,
                nomineePhone,
                nomineeRelationship
        );
    }

    @GetMapping("/customers/{id}/transactions")
    public String getTransactionHistory(@PathVariable int id) {

        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Customer not found with id: " + id
            );
        }

        String history = customer.getTransactionHistory();

        if (history == null || history.isEmpty()) {
            return "No transactions found for customer: " + id;
        }

        return history;
    }

}