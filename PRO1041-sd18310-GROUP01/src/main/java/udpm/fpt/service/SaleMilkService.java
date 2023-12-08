/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.Milk;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.SaleMilk;
import udpm.fpt.repository.IMilk;
import udpm.fpt.repository.ISaleMilk;

/**
 *
 * @author Thanh Dat
 */
public class SaleMilkService {
    private ISaleMilk ism = getBean(ISaleMilk.class);
    private IMilk im = getBean(IMilk.class);
    public SaleMilkService(){
    }
    public CompletableFuture<List<Milk>> loadAsync() {
        return CompletableFuture.supplyAsync(() -> {
            return im.findAll();
        }, Executors.newCachedThreadPool());
    }
    public List<SaleMilk> getAll(){
        return this.ism.findAll();
    }
    
    public boolean addNew(SaleMilk sm){
        return this.ism.save(sm) != null;
    }
    
    public boolean deleteMilk(int ID){
        try {
            this.ism.deleteById(ID);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean updateMilk(SaleMilk sm){
        return this.ism.save(sm) != null;
    }
    
    public List<SaleMilk> Search(Date ed){
        return this.ism.findByEndDate(ed);
    }
}
