/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.serviceDelivery;

import static udpm.fpt.Application.getBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import udpm.fpt.model.Bill;
import udpm.fpt.model.BillDetails;
import udpm.fpt.model.Customer;
import udpm.fpt.model.DeliveryNote;
import udpm.fpt.model.Milk;
import udpm.fpt.model.Status;
import udpm.fpt.model.TransportUnit;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;
import udpm.fpt.repository.ICustomer_Resposutory;
import udpm.fpt.repository.IDelivery_noteRespository;
import udpm.fpt.repository.IMilk_Respository;
import udpm.fpt.repository.IStatus_Respository;
import udpm.fpt.repository.ITranportUnit_Respository;

/**
 *
 * @author PHONG PC
 */
public class Service {

    private final IDelivery_noteRespository delivery = getBean(IDelivery_noteRespository.class);
    private final ICustomer_Resposutory customer = getBean(ICustomer_Resposutory.class);
    private final ITranportUnit_Respository tranportUnit = getBean(ITranportUnit_Respository.class);
    private final IBill_Respository bill = getBean(IBill_Respository.class);
    private final IBillDetails_Respository billDetail = getBean(IBillDetails_Respository.class);
    private final IMilk_Respository milk = getBean(IMilk_Respository.class);
    private final IStatus_Respository status = getBean(IStatus_Respository.class);

    public boolean insertDeli(DeliveryNote deli) {
        boolean check = false;
        if (delivery.save(deli) != null) {
            check = true;
        }
        return check;
    }

    public List<Customer> findAllCustomer() {
        List<Customer> list = customer.findAll();
        return list;
    }

    public List<Milk> findAllMilk() {
        List<Milk> list = milk.findAll();
        return list;
    }

    public List<TransportUnit> findAllTranportsUnit() {
        List<TransportUnit> list = tranportUnit.findAll();
        return list;
    }

    public boolean updateDeliveryNote(Long deliveryId, String maVanDon, DeliveryNote delinotupdate) {
        DeliveryNote deliveryNoteexist = delinotupdate;
        if (deliveryNoteexist != null) {
            deliveryNoteexist.setId(deliveryId);
            deliveryNoteexist.setWaybill_number(deliveryNoteexist.getWaybill_number());
            deliveryNoteexist.setAddress(delinotupdate.getAddress());
            deliveryNoteexist.setBill_id2(delinotupdate.getBill_id2());
            deliveryNoteexist.setCreationdate(delinotupdate.getCreationdate());
            deliveryNoteexist.setCustomer_name(delinotupdate.getCustomer_name());
            deliveryNoteexist.setNote(delinotupdate.getCustomer_name());
            deliveryNoteexist.setMilk_name(delinotupdate.getMilk_name());
            deliveryNoteexist.setQuantity(delinotupdate.getQuantity());
            deliveryNoteexist.setTransport_unit_id2(delinotupdate.getTransport_unit_id2());
            deliveryNoteexist.setStatus_id(delinotupdate.getStatus_id());
            deliveryNoteexist.setSdt(delinotupdate.getSdt());
            deliveryNoteexist.setTransport_unit_id(deliveryNoteexist.getTransport_unit_id());
            delivery.save(deliveryNoteexist);
            return true;
        } else {
            return false;
        }
    }

    public Optional<DeliveryNote> filById(Long id) {
        Optional<DeliveryNote> deli = delivery.findById(id);
        return deli;
    }

    public boolean updateQuanlityProduct(Long id, int amout) {
        Milk m = new Milk();
        m.setId(id);
        if (id != null) {
            milk.updateQuantity(amout, id);
            return true;
        } else {
            return false;
        }
    }

    public String ranDom() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> charList = new ArrayList<>();
        for (char c : alphabet.toCharArray()) {
            charList.add(c);
        }
        Collections.shuffle(charList);
        int numberOfChars = 6;
        StringBuilder invoiceCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < numberOfChars; i++) {
            char randomChar = charList.get(random.nextInt(charList.size()));
            invoiceCode.append(randomChar);
        }
        return invoiceCode.toString();
    }

    public List<Status> findAllStatus() {
        return status.findAll();
    }

    public String deleteLineBreak(String ten) {
        String modifiedString = ten.replaceAll("\\n", ",");
        return modifiedString;
    }

    public String addLineBreak(String ten) {
        String modifiedString = ten.replaceAll(",", "\n");
        return modifiedString;
    }

    public String addLineBreakInMessgase(String ten) {
        String modifiedString = ten.replaceAll(",", "<br>");
        return modifiedString;
    }

    public CompletableFuture<List<DeliveryNote>> loadAsync() {
        return CompletableFuture.supplyAsync(() -> {
            return delivery.findAll();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<DeliveryNote>> loadSearch(String search, int row) {
        if (row == 1) {
            return CompletableFuture.supplyAsync(() -> {
                return delivery.findByName(search);
            }, Executors.newCachedThreadPool());
        } else {
            return CompletableFuture.supplyAsync(() -> {
                return delivery.findByID(search);
            }, Executors.newCachedThreadPool());
        }
    }

    public CompletableFuture<List<BillDetails>> loadAsyncBillDetail() {
        return CompletableFuture.supplyAsync(() -> {
            return billDetail.findAll();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<Bill>> findAllBill() {
        return CompletableFuture.supplyAsync(() -> {
            return bill.findBillByshoppingmethod();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<BillDetails>> findAllBillDetails() {
        return CompletableFuture.supplyAsync(() -> {
            return billDetail.findAll();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<DeliveryNote>> findBetweenDate(Date minSalary, Date maxSalary) {
        return CompletableFuture.supplyAsync(() -> {
            return delivery.findBySalaryBetween(minSalary, maxSalary);
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<DeliveryNote>> findByStatus(int idStatus) {
        return CompletableFuture.supplyAsync(() -> {
            return delivery.findByStatus(idStatus);
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<DeliveryNote>> SearchAll (String idDelivery, String Customer_name, String idStatus) {
        return CompletableFuture.supplyAsync(() -> {
            return delivery.searchAll(idDelivery, Customer_name, idStatus);
        }, Executors.newCachedThreadPool());
    }
    
}
