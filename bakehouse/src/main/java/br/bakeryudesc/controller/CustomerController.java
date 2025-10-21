package br.bakeryudesc.controller;

import br.bakeryudesc.dao.CustomerDAO;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.utils.PersistenceInstance;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerController {

    private final EntityManager entityManager;
    private final CustomerDAO customerDAO;

    public CustomerController() {
        this.entityManager = PersistenceInstance.getEntityManager();
        this.customerDAO = new CustomerDAO(entityManager);
    }

    public void createCustomer(String name, String cpf, String phone) {
        if (customerDAO.findByCpf(cpf) != null) {
            System.err.println("Error: CPF " + cpf + " already exists.");
            return;
        }

        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setCpf(cpf);
        newCustomer.setPhone(phone);
        newCustomer.setTotalPoints(0);

        customerDAO.save(newCustomer);
    }

    public void updateCustomer(Long customerId, String name, String phone) {
        Customer customer = customerDAO.findById(customerId);
        if (customer != null) {
            customer.setName(name);
            customer.setPhone(phone);
            customerDAO.update(customer);
        } else {
            System.err.println("Error: Customer id " + customerId + " not found to update.");
        }
    }

    public void deleteCustomer(Long customerId) {
        customerDAO.deleteById(customerId);
    }

    public Customer findCustomerById(Long customerId) {
        return customerDAO.findById(customerId);
    }

    public Customer findCustomerByCpf(String cpf) {
        return customerDAO.findByCpf(cpf);
    }

    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    public void close() {
        if (this.entityManager != null && this.entityManager.isOpen()) {
            this.entityManager.close();
        }
    }
}
