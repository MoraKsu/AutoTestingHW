package org.HW4;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "main", catalog = "")
public class ProductsEntity {
    private Long productId;
    private String menuName;
    private String price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    
    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && Objects.equals(menuName, that.menuName) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, menuName, price);
    }
}
