package com.example.demo.service;
import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer saveCustomer(Customer customer) {

        if (customerRepository.existsById(customer.getId())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "Customer already exists with id: " + customer.getId()
            );
        }

        if (customer.getAge() < 18 || customer.getAge() > 100) {
            throw new IllegalArgumentException(
                    "Age must be between 18 and 100"
            );
        }

        if (customer.getCibil() < 300 || customer.getCibil() > 900) {
            throw new IllegalArgumentException(
                    "CIBIL score must be between 300 and 900"
            );
        }

        return customerRepository.save(customer);
    }
    public Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + id
                ));
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer updateCustomer(int id, Customer updatedCustomer) {

        if (updatedCustomer.getAge() < 18 ||
                updatedCustomer.getAge() > 100) {
            throw new IllegalArgumentException(
                    "Age must be between 18 and 100"
            );
        }

        if (updatedCustomer.getCibil() < 300 ||
                updatedCustomer.getCibil() > 900) {
            throw new IllegalArgumentException(
                    "CIBIL score must be between 300 and 900"
            );
        }

        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + id
                ));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setAge(updatedCustomer.getAge());
        existingCustomer.setBalance(updatedCustomer.getBalance());
        existingCustomer.setSalary(updatedCustomer.getSalary());
        existingCustomer.setCibil(updatedCustomer.getCibil());
        existingCustomer.setActive(updatedCustomer.isActive());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(int id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + id
                ));

        if (customer.isLoanActive()
                || customer.isPersonalLoanActive()
                || customer.isGoldLoanActive()
                || customer.isInsuranceActive()
                || customer.isEmiActive()
                || customer.isCreditCardActive()) {

            throw new IllegalArgumentException(
                    "Cannot delete customer with active financial products"
            );
        }

        customerRepository.delete(customer);
    }


    public Customer deposit(int id, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero"
            );
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        customer.setBalance(customer.getBalance() + amount);

        String history = customer.getTransactionHistory();

        String transaction = "Deposit: +" + amount;

        if (history == null || history.isEmpty()) {
            customer.setTransactionHistory(transaction);
        } else {
            customer.setTransactionHistory(
                    history + " | " + transaction
            );
        }

        return customerRepository.save(customer);
    }


    public Customer withdraw(int id, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException(
                    "Insufficient balance"
            );
        }

        customer.setBalance(customer.getBalance() - amount);

        String history = customer.getTransactionHistory();

        String transaction = "Withdrawal: -" + amount;

        if (history == null || history.isEmpty()) {
            customer.setTransactionHistory(transaction);
        } else {
            customer.setTransactionHistory(
                    history + " | " + transaction
            );
        }

        return customerRepository.save(customer);
    }

    @Transactional
    public void transfer(int fromCustomerId, int toCustomerId, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero"
            );
        }

        if (fromCustomerId == toCustomerId) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same customer"
            );
        }

        Customer sender = customerRepository.findById(fromCustomerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Sender not found with id: " + fromCustomerId
                ));

        Customer receiver = customerRepository.findById(toCustomerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Receiver not found with id: " + toCustomerId
                ));

        if (sender.getBalance() < amount) {
            throw new IllegalArgumentException(
                    "Insufficient balance"
            );
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        // Sender transaction history
        String senderHistory = sender.getTransactionHistory();
        String senderTransaction =
                "Transfer to Customer " + toCustomerId + ": -" + amount;

        if (senderHistory == null || senderHistory.isEmpty()) {
            sender.setTransactionHistory(senderTransaction);
        } else {
            sender.setTransactionHistory(
                    senderHistory + " | " + senderTransaction
            );
        }

        // Receiver transaction history
        String receiverHistory = receiver.getTransactionHistory();
        String receiverTransaction =
                "Transfer from Customer " + fromCustomerId + ": +" + amount;

        if (receiverHistory == null || receiverHistory.isEmpty()) {
            receiver.setTransactionHistory(receiverTransaction);
        } else {
            receiver.setTransactionHistory(
                    receiverHistory + " | " + receiverTransaction
            );
        }

        customerRepository.save(sender);
        customerRepository.save(receiver);
    }

    public Customer applyLoan(int id, double amount) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (customer.getAge() < 21) {
            throw new IllegalArgumentException(
                    "Customer must be at least 21 years old"
            );
        }

        if (customer.getSalary() < 15000) {
            throw new IllegalArgumentException(
                    "Minimum salary must be 15000"
            );
        }

        if (customer.getCibil() < 700) {
            throw new IllegalArgumentException(
                    "CIBIL score must be at least 700"
            );
        }

        if (amount <= 0 || amount > 1000000) {
            throw new IllegalArgumentException(
                    "Loan amount must be between 1 and 1000000"
            );
        }

        if (customer.isLoanActive()) {
            throw new IllegalArgumentException(
                    "Customer already has an active loan"
            );
        }

        // Calculate annual interest rate
        double interestRate;

        if (customer.getCibil() >= 750) {
            interestRate = 8.0;
        } else {
            interestRate = 10.0;
        }

        // Calculate one year's simple interest
        double interestAmount = amount * interestRate / 100;

        // Update loan details
        customer.setLoanActive(true);
        customer.setLoanAmount(amount);
        customer.setLoanInterestRate(interestRate);
        customer.setLoanInterestAmount(interestAmount);
        customer.setBalance(customer.getBalance() + amount);
        return customerRepository.save(customer);
    }
    @Transactional
    public Customer repayLoan(int id, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Repayment amount must be greater than zero"
            );
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (!customer.isLoanActive()) {
            throw new IllegalArgumentException(
                    "Customer has no active loan"
            );
        }

        double totalAmount = customer.getLoanAmount()
                + customer.getLoanInterestAmount();

        if (amount != totalAmount) {
            throw new IllegalArgumentException(
                    "Full repayment amount must be: " + totalAmount
            );
        }

        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException(
                    "Insufficient balance for loan repayment"
            );
        }

        // Deduct repayment from bank balance
        customer.setBalance(customer.getBalance() - amount);

        // Close the loan
        customer.setLoanActive(false);
        customer.setLoanAmount(0);
        customer.setLoanInterestRate(0);
        customer.setLoanInterestAmount(0);

        return customerRepository.save(customer);
    }
    public Customer fixExistingLoanInterest(int id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (!customer.isLoanActive()) {
            throw new IllegalArgumentException(
                    "Customer has no active loan"
            );
        }

        double interestRate = customer.getCibil() >= 750 ? 8.0 : 10.0;

        double interestAmount =
                customer.getLoanAmount() * interestRate / 100;

        customer.setLoanInterestRate(interestRate);
        customer.setLoanInterestAmount(interestAmount);

        return customerRepository.save(customer);
    }
    public Customer applyPersonalLoan(int id, double amount) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new  CustomerNotFoundException("Customer not found"));

        if (customer.getAge() < 21) {
            throw  new IllegalArgumentException(
                    "Customer age must be at least 21");
        }

        if (customer.getSalary() < 15000) {
            throw new IllegalArgumentException(
                    "Minimum salary must be 15000");
        }

        if (customer.getCibil() < 700) {
            throw new IllegalArgumentException(
                    "CIBIL score must be at least 700");
        }

        if (amount <= 0 || amount > 500000) {
            throw new IllegalArgumentException(
                    "Personal loan amount must be between 1 and 500000");
        }

        if (customer.isPersonalLoanActive()) {
            throw new IllegalArgumentException(
                    "Customer already has an active personal loan");
        }

        customer.setPersonalLoanActive(true);
        customer.setPersonalLoanAmount(amount);

        // Credit the approved loan amount to the account balance
        customer.setBalance(customer.getBalance() + amount);

        return customerRepository.save(customer);
    }
    public Customer applyGoldLoan(int id, double amount) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (amount <= 0 || amount > 500000) {
            throw new IllegalArgumentException(
                    "Gold loan amount must be between 1 and 500000"
            );
        }

        if (customer.isGoldLoanActive()) {
            throw new IllegalArgumentException(
                    "Customer already has an active gold loan"
            );
        }

        customer.setGoldLoanActive(true);
        customer.setGoldLoanAmount(amount);
        customer.setBalance(customer.getBalance() + amount);

        return customerRepository.save(customer);
    }
    public Customer applyInsurance(int id, double amount) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (amount <= 0 || amount > 500000) {
            throw new IllegalArgumentException(
                    "Insurance amount must be between 1 and 500000"
            );
        }

        if (customer.isInsuranceActive()) {
            throw new IllegalArgumentException(
                    "Customer already has active insurance"
            );
        }

        customer.setInsuranceActive(true);
        customer.setInsuranceAmount(amount);

        return customerRepository.save(customer);
    }
    public Customer applyEmi(int id, double amount, int months) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "EMI amount must be greater than zero"
            );
        }

        if (months <= 0) {
            throw new IllegalArgumentException(
                    "EMI months must be greater than zero"
            );
        }

        if (customer.isEmiActive()) {
            throw new IllegalArgumentException(
                    "Customer already has an active EMI"
            );
        }

        customer.setEmiActive(true);
        customer.setEmiAmount(amount);
        customer.setRemainingEmi(months);

        return customerRepository.save(customer);
    }

    public Customer applyCreditCard(int id, double limit) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (limit <= 0 || limit > 500000) {
            throw new IllegalArgumentException(
                    "Credit card limit must be between 1 and 500000"
            );
        }

        if (customer.isCreditCardActive()) {
            throw new IllegalArgumentException(
                    "Customer already has an active credit card"
            );
        }

        customer.setCreditCardActive(true);
        customer.setCreditCardLimit(limit);

        return customerRepository.save(customer);
    }

    public Customer addNominee(
            int id,
            String nomineeName,
            String nomineePhone,
            String nomineeRelationship) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id
                ));

        if (nomineeName == null || nomineeName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nominee name is required"
            );
        }

        if (nomineePhone == null ||
                !nomineePhone.matches("[0-9]{10}")) {
            throw new IllegalArgumentException(
                    "Nominee phone must contain 10 digits"
            );
        }

        if (nomineeRelationship == null ||
                nomineeRelationship.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nominee relationship is required"
            );
        }

        customer.setNomineeName(nomineeName);
        customer.setNomineePhone(nomineePhone);
        customer.setNomineeRelationship(nomineeRelationship);

        return customerRepository.save(customer);
    }

}
