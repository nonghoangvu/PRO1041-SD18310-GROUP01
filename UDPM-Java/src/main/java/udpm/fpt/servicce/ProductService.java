/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.servicce;

import udpm.fpt.model.ProductInfo;

import java.util.ArrayList;
import java.util.List;
import udpm.fpt.repository.IProductInfo;
import static udpm.fpt.Applocation.getBean;

/**
 *
 * @author NONG HOANG VU
 */
public class ProductService {
    private List<ProductInfo> list;
    private IProductInfo r = getBean(IProductInfo.class);

    public ProductService() {
        this.list = list = new ArrayList<>();
    }

    public List<ProductInfo> getList() {
        this.list.clear();
        this.list = r.findAll();
        return list;
    }

}
