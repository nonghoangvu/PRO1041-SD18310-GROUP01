/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.servicce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.Bill;
import udpm.fpt.model.Customer;
import udpm.fpt.model.Milk;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;
import udpm.fpt.repository.ICustomer_Resposutory;
import udpm.fpt.repository.IMilk;
import udpm.fpt.repository.IMilk_Respository;

/**
 *
 * @author PHONG PC
 */
public class BillService {

    IBill_Respository bill = getBean(IBill_Respository.class);
    IBillDetails_Respository billDetail = getBean(IBillDetails_Respository.class);
    IMilk_Respository milk = getBean(IMilk_Respository.class);
    ICustomer_Resposutory customer = getBean(ICustomer_Resposutory.class);
    IMilk milkss = getBean(IMilk.class);
    public CompletableFuture<List<Milk>> loadMilk() {
        return CompletableFuture.supplyAsync(() -> {
            return milk.findAll();
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<Bill>> loadBill() {
        return CompletableFuture.supplyAsync(() -> {
            return bill.findAll();
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<Customer>> loadCustomer() {
        return CompletableFuture.supplyAsync(() -> {
            return customer.findAll();
        }, Executors.newCachedThreadPool());
    }
    public String generateRandomNumber() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            digits.add(i);
        }

        Collections.shuffle(digits);

        StringBuilder randomNumber = new StringBuilder();
        Random random = new Random();

        int codeLength = 6;
        for (int i = 0; i < codeLength; i++) {
            int randomDigit = digits.get(random.nextInt(digits.size()));

            // Đảm bảo số không trùng
            while (randomNumber.toString().contains(String.valueOf(randomDigit))) {
                randomDigit = digits.get(random.nextInt(digits.size()));
            }

            randomNumber.append(randomDigit);
        }

        return randomNumber.toString();
    }
    public CompletableFuture<List<Customer>> searchCustomer(String name) {
        return CompletableFuture.supplyAsync(() -> {
            return customer.findByName(name);
        }, Executors.newCachedThreadPool());
    }
    public int updateProduct(int amout, Long id){
        return milkss.updateQuantity(amout, id);
    }
}
