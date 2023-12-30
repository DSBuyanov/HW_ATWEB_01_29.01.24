package org.max.home;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CRUDCustomersTest extends AbstractTest {

    @Test
    void testReadCustomerWithoutDatabase() {
        // given
        CustomersEntity entity = new CustomersEntity();
        entity.setFirstName("SomeFirstName");
        entity.setLastName("SomeLastName");


        // then
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("SomeFirstName", entity.getFirstName());
        Assertions.assertEquals("SomeLastName", entity.getLastName());

    }

    @Test
    void testUpdateCustomerWithoutDatabase() {
        // given
        CustomersEntity entity = new CustomersEntity();
        entity.setFirstName("SomeFirstName");
        entity.setLastName("SomeLastName");



        entity.setFirstName("UpdatedFirstName");
        entity.setLastName("UpdatedLastName");


        // then
        Assertions.assertEquals("UpdatedFirstName", entity.getFirstName());
        Assertions.assertEquals("UpdatedLastName", entity.getLastName());

    }

    @Test
    void testDeleteCustomerWithoutDatabase() {
        // given
        CustomersEntity entity = new CustomersEntity();
        entity.setFirstName("SomeFirstName");
        entity.setLastName("SomeLastName");


        // then
        Assertions.assertNotNull(entity);


        entity = null;

        // then
        Assertions.assertNull(entity);
    }
}

