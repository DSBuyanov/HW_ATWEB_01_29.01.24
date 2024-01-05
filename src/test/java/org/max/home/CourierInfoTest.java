package org.max.home;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import javax.persistence.PersistenceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourierInfoTest extends AbstractTest {

    @Test
    @Order(1)
    void getCouriers_whenValid_shouldReturn() throws SQLException {
        // given
        String sql = "SELECT * FROM courier_info WHERE delivery_type='car'";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        // when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery("SELECT * FROM courier_info WHERE delivery_type='car'")
                .addEntity(CourierInfoEntity.class);
        // then
        Assertions.assertEquals(2, countTableSize);
        Assertions.assertEquals(2, query.list().size());
    }

    
    // Пример теста для Create
    @Test
    @Order(2)
    void addCourierInfo_whenValid_shouldSave() {
        // given
        CourierInfoEntity entity = new CourierInfoEntity();
        entity.setCourierId((short) 3);
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setPhoneNumber("+1 234 567 890");
        entity.setDeliveryType("Standard");
        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 3).addEntity(CourierInfoEntity.class);
        CourierInfoEntity courierEntity = (CourierInfoEntity) query.uniqueResult();
        // then
        Assertions.assertNotNull(courierEntity);
        Assertions.assertEquals("John", courierEntity.getFirstName());
    }



    // Пример теста для Delete
    @Test
    @Order(3)
    void deleteCourierInfo_whenValid_shouldDelete() {
        // given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 3).addEntity(CourierInfoEntity.class);
        CourierInfoEntity courierEntity = (CourierInfoEntity) query.uniqueResult();
        Assumptions.assumeTrue(courierEntity != null);
        // when
        Session session = getSession();
        session.beginTransaction();
        session.delete(courierEntity);
        session.getTransaction().commit();
        // then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 3).addEntity(CourierInfoEntity.class);
        CourierInfoEntity courierEntityAfterDelete = (CourierInfoEntity) queryAfterDelete.uniqueResult();
        Assertions.assertNull(courierEntityAfterDelete);
    }

    @Test
    @Order(5)
    void addCourierInfo_whenNotValid_shouldThrow() {
        //given
        CourierInfoEntity entity = new CourierInfoEntity();
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        //then
        Assertions.assertThrows(PersistenceException.class, () -> session.getTransaction().commit());
    }

}
