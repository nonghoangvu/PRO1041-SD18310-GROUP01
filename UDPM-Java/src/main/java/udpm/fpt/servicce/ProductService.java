package udpm.fpt.servicce;

import udpm.fpt.model.Flavor;
import udpm.fpt.model.ProductInfo;

import java.util.ArrayList;
import java.util.List;

import udpm.fpt.repository.IFlavor;
import udpm.fpt.repository.IProductInfo;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.Milk;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.Unit;
import udpm.fpt.repository.IMilk;
import udpm.fpt.repository.IPackagingSpecification;
import udpm.fpt.repository.IUnit;

/**
 *
 * @author NONG HOANG VU
 */
public class ProductService {

    private List<ProductInfo> list;
    private final IProductInfo r = getBean(IProductInfo.class);
    private final IMilk iMilk = getBean(IMilk.class);
    private final IFlavor iFlavor = getBean(IFlavor.class);
    private final IUnit iUnit = getBean(IUnit.class);
    private final IPackagingSpecification iPackagingSpecification = getBean(IPackagingSpecification.class);

    public ProductService() {
        this.list = list = new ArrayList<>();
    }

    public List<ProductInfo> getList() {
        this.list.clear();
        this.list = r.findAll();
        return list;
    }

    public List<ProductInfo> getSearch(String search) {
        this.list.clear();
        this.list = r.findProductInfo(search);
        return list;
    }

    public List<ProductInfo> getPriceRange(String search, Integer minPrice, Integer maxPrice) {
        this.list.clear();
        this.list = r.findProductInfoInPriceRange(search, minPrice, maxPrice);
        return list;
    }

    public Milk getMilkByID(Long id) {
        return iMilk.findAllById(id);
    }

    public List<Flavor> getFlavor() {
        List<Flavor> listFlavor = this.iFlavor.findAll();
        return listFlavor;
    }

    public Boolean insertFlavor(Flavor flavor) {
        return this.iFlavor.save(flavor) != null;
    }

    public Boolean removeByTaste(String flavor) {
        return this.iFlavor.deleteFlavor(flavor);
    }

    public List<Unit> getUnit() {
        List<Unit> listUnit = this.iUnit.findAll();
        return listUnit;
    }

    public void removeByMeasurement_unit(Unit unit) {
        this.iUnit.deleteById(unit.getId());
    }

    public Boolean insertUnit(Unit unit) {
        return this.iUnit.save(unit) != null;
    }

    public List<PackagingSpecification> getPackagingSpecification() {
        List<PackagingSpecification> listPackagingSpecificationt = this.iPackagingSpecification.findAll();
        return listPackagingSpecificationt;
    }

    public Boolean insertPackagingSpecification(PackagingSpecification packagingSpecification) {
        return this.iPackagingSpecification.save(packagingSpecification) != null;
    }

    public void removePackagingSpecification(PackagingSpecification packagingSpecification) {
        this.iPackagingSpecification.deleteById(packagingSpecification.getId());
    }

    public Boolean inserProduct(Milk m, ProductInfo pi) {
        if (iMilk.findAllById(m.getId()) == null) {
            return this.iMilk.save(m) != null && r.save(pi) != null;
        }
        return false;
    }

    public Boolean updateProduct(Milk m, ProductInfo pi) {
        return this.iMilk.save(m) != null && r.save(pi) != null;
    }

    public Boolean hideRestoreProduct(Milk m) {
        return this.iMilk.save(m) != null;
    }

    public Boolean deleteProduct(Long idMilk, Integer idProductInfo) {
        Boolean milkDeleted;
        Boolean productInfoDeleted;
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
