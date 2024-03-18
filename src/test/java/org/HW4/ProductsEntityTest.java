package org.HW4;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsEntityTest extends AbstractTest {

    @Test
    @Order(1)
    void testCreateProduct() {
        try (Session session = getSession()) {
            ProductsEntity product = new ProductsEntity();
            product.setMenuName("Test");
            product.setPrice("50.0");

            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();

            assertNotNull(product.getProductId());
        }
    }

    @Test
    @Order(2)
    void testReadProduct() {
        try (Session session = getSession()) {
            final Query<ProductsEntity> query = session.createQuery("from ProductsEntity where menuName=:menuName", ProductsEntity.class)
                    .setParameter("menuName", "Test");

            List<ProductsEntity> products = query.getResultList();
            assertFalse(products.isEmpty(), "No product found with menuName 'Test'");

            ProductsEntity product = products.get(0);
            assertEquals("Test", product.getMenuName());
            assertEquals("50.0", product.getPrice());
        }
    }

    @Test
    @Order(3)
    void testUpdateProduct() {
        try (Session session = getSession()) {
            final Query<ProductsEntity> query = session.createQuery("from ProductsEntity where menuName=:menuName", ProductsEntity.class)
                    .setParameter("menuName", "Test");
            List<ProductsEntity> products = query.getResultList();
            assertEquals(1, products.size(), "More than one product found with menuName 'Test'");

            ProductsEntity product = products.get(0);
            product.setPrice("60.0");

            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();

            ProductsEntity updatedProduct = query.getSingleResult();
            assertNotNull(updatedProduct);
            assertEquals("60.0", updatedProduct.getPrice());
        }
    }

    @Test
    @Order(4)
    void testDeleteProduct() {
        try (Session session = getSession()) {
            final Query<ProductsEntity> query = session.createQuery("from ProductsEntity where menuName=:menuName", ProductsEntity.class)
                    .setParameter("menuName", "Test");
            List<ProductsEntity> products = query.getResultList();
            assertEquals(1, products.size(), "More than one product found with menuName 'Test'");

            ProductsEntity product = products.get(0);

            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();

            List<ProductsEntity> deletedProduct = query.getResultList();
            assertTrue(deletedProduct.isEmpty(), "Product with menuName 'Test' still exists after deletion");
        }
    }

    @Test
    @Order(5)
    void testAddProductWithoutMenuName_shouldThrowException() {
        try (Session session = getSession()) {
            ProductsEntity product = new ProductsEntity();
            product.setPrice("50.0");

            assertThrows(PersistenceException.class, () -> {
                session.beginTransaction();
                session.persist(product);
                session.getTransaction().commit();
            });
        }
    }
}
