package udpm.fpt.servicce;

import udpm.fpt.model.Flavor;
import udpm.fpt.model.ProductInfo;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.HistoryProduct;

import udpm.fpt.repository.IFlavor;
import udpm.fpt.repository.IProductInfo;
import udpm.fpt.model.Milk;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.Unit;
import udpm.fpt.model.User;
import udpm.fpt.repository.IHistoryProduct;
import udpm.fpt.repository.IMilk;
import udpm.fpt.repository.IPackagingSpecification;
import udpm.fpt.repository.IUnit;

/**
 *
 * @author NONG HOANG VU
 */
public class ProductService {

    private final IProductInfo r = getBean(IProductInfo.class);
    private final IMilk iMilk = getBean(IMilk.class);
    private final IFlavor iFlavor = getBean(IFlavor.class);
    private final IUnit iUnit = getBean(IUnit.class);
    private final IPackagingSpecification iPackagingSpecification = getBean(IPackagingSpecification.class);
    private final IHistoryProduct iHistoryProduct = getBean(IHistoryProduct.class);

    @Autowired
    public ProductService() {
    }

    public CompletableFuture<List<ProductInfo>> loadAsync() {
        return CompletableFuture.supplyAsync(() -> {
            return r.findAll();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<ProductInfo>> loadSearch(String search) {
        return CompletableFuture.supplyAsync(() -> {
            return r.findProductInfo(search);
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<ProductInfo>> loadFilter(Integer minAmount, Integer maxAmount, Date startDate, Date endDate, String taste, String packaging_type, Integer minPrice, Integer maxPrice) {
        return CompletableFuture.supplyAsync(() -> {
            return r.findProductInfoFilter(minAmount, maxAmount, startDate, endDate, taste, packaging_type, minPrice, maxPrice);
        }, Executors.newCachedThreadPool());
    }

    public Milk getMilkByID(Long id) {
        return iMilk.findAllById(id);
    }

    public CompletableFuture<List<Flavor>> loadFlavor() {
        return CompletableFuture.supplyAsync(() -> {
            return this.iFlavor.findAll();
        }, Executors.newCachedThreadPool());
    }

    public Boolean insertFlavor(Flavor flavor) {
        return this.iFlavor.save(flavor) != null;
    }

    public Boolean removeByTaste(String flavor) {
        return this.iFlavor.deleteFlavor(flavor);
    }

    public CompletableFuture<List<Unit>> loadUnit() {
        return CompletableFuture.supplyAsync(() -> {
            return this.iUnit.findAll();
        }, Executors.newCachedThreadPool());
    }

    public void removeByMeasurement_unit(Unit unit) {
        this.iUnit.deleteById(unit.getId());
    }

    public Boolean insertUnit(Unit unit) {
        return this.iUnit.save(unit) != null;
    }

    public CompletableFuture<List<PackagingSpecification>> loadPackagingSpecification() {
        return CompletableFuture.supplyAsync(() -> {
            return this.iPackagingSpecification.findAll();
        }, Executors.newCachedThreadPool());
    }

    public Boolean insertPackagingSpecification(PackagingSpecification packagingSpecification) {
        return this.iPackagingSpecification.save(packagingSpecification) != null;
    }

    public void removePackagingSpecification(PackagingSpecification packagingSpecification) {
        this.iPackagingSpecification.deleteById(packagingSpecification.getId());
    }

    public Boolean inserProduct(Milk m, ProductInfo pi) {
        if (iMilk.findAllById(m.getId()) == null) {
            if (this.iMilk.save(m) != null && r.save(pi) != null) {
                HistoryProduct historyProduct = new HistoryProduct();
                historyProduct.setDescription("New product has been added with ID " + m.getId());
                historyProduct.setDatetime(new Date());
                historyProduct.setUsername(pi.getUser().getUsername());
                historyProduct.setChangeType("New");
                return this.iHistoryProduct.save(historyProduct) != null;
            }
        }
        return false;
    }

    public Boolean updateProduct(Milk m, ProductInfo pi, User user) {
        if (this.iMilk.save(m) != null && r.save(pi) != null) {
            HistoryProduct historyProduct = new HistoryProduct();
            historyProduct.setDescription("ID " + m.getId() + " has been updated");
            historyProduct.setDatetime(new Date());
            historyProduct.setUsername(user.getUsername());
            historyProduct.setChangeType("Update");
            return this.iHistoryProduct.save(historyProduct) != null;
        }
        return false;
    }

    public Boolean hideRestoreProduct(Milk m, User user) {
        if (this.iMilk.save(m) != null) {
            HistoryProduct historyProduct = new HistoryProduct();
            String type = m.getIsDelete() ? "Added to repository" : "Restored";
            historyProduct.setDescription("ID " + m.getId() + " has been " + type);
            historyProduct.setDatetime(new Date());
            historyProduct.setUsername(user.getUsername());
            historyProduct.setChangeType(type);
            return this.iHistoryProduct.save(historyProduct) != null;
        }
        return false;
    }

    public Boolean deleteProduct(Long idMilk, Integer idProductInfo, Milk milk, User user) {
        Boolean milkDeleted;
        Boolean productInfoDeleted;
        String message = "ID " + milk.getId() + " has been deleted";
        HistoryProduct historyProduct = new HistoryProduct();
        historyProduct.setDescription(message);
        historyProduct.setDatetime(new Date());
        historyProduct.setUsername(user.getUsername());
        historyProduct.setChangeType("Delete");
        this.iHistoryProduct.save(historyProduct);
        try {
            iMilk.deleteById(idMilk);
            milkDeleted = true;
        } catch (Exception e) {
            milkDeleted = false;
        }
        try {
            r.deleteById(idProductInfo);
            productInfoDeleted = true;
        } catch (Exception e) {
            productInfoDeleted = false;
        }
        return milkDeleted && productInfoDeleted;
    }
}
