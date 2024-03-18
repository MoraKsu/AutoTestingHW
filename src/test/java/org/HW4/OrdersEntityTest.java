package org.HW4;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdersEntityTest extends AbstractTest {

    @Test
    @Order(1)
    void testOrdersCount() throws SQLException {
        // Given
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;

        // When
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM orders");
        if (rs.next()) {
            countTableSize = rs.getInt(1);
        }

        // Then
        assertEquals(15, countTableSize);
    }

    @Test
    @Order(2)
    void testOrderDates() throws SQLException {
        // Given
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

        // When
        while (rs.next()) {
            assertNotNull(rs.getString("date_get"));
        }
    }

    @Test
    @Order(3)
    void testAddOrder() {
        // Given
        OrdersEntity order = new OrdersEntity();
        order.setOrderId((short) 16);
        order.setCustomerId((short) 1);
        order.setDateGet(LocalDateTime.now().toString());

        // When
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();

        session.close();

        // Then
        Session newSession = getSession();
        OrdersEntity retrievedOrder = newSession.get(OrdersEntity.class, (short) 16);
        assertNotNull(retrievedOrder);
        assertEquals(order.getDateGet(), retrievedOrder.getDateGet());

        newSession.close();
    }

    @Test
    @Order(4)
    void testDeleteOrder() {
        // Given
        Session session = getSession();
        OrdersEntity order = session.get(OrdersEntity.class, (short) 16);
        assertNotNull(order);

        // When
        Transaction transaction = session.beginTransaction();
        session.delete(order);
        transaction.commit();

        // Then
        OrdersEntity deletedOrder = session.get(OrdersEntity.class, (short) 16);
        assertNull(deletedOrder);
    }

    @Test
    @Order(5)
    void testGetOrdersByCustomerId() {
        // Given
        Session session = getSession();

        // When
        List<OrdersEntity> orders = session.createQuery("FROM OrdersEntity WHERE customer_id = :customerId", OrdersEntity.class)
                .setParameter("customerId", 1)
                .list();

        // Then
        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).getOrderId());
    }
}
