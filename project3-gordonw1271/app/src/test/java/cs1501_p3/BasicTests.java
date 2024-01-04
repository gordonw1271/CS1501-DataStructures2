/**
 * Basic tests for CS1501 Project 3
 * @author    Dr. Farnan
 */
package cs1501_p3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.NoSuchElementException;
import java.util.HashMap;

import static java.time.Duration.ofSeconds;

class BasicTests {
    final int DEFAULT_TIMEOUT = 10;
    CarsPQ cpq;

    @BeforeEach
    void init_cpq() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            cpq = new CarsPQ("build/resources/test/cars.txt");
        });
    }

    @Test
    @DisplayName("Add/Get")
    void add_get() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            assertEquals("Red", cpq.get("PUAF85WU5R6L6H1P9").getColor());
            assertEquals("Green", cpq.get("X1U2PEJSC361L10MZ").getColor());
            assertEquals("Yellow", cpq.get("16Z2DPEHSUK5KCMEH").getColor());
        });
    }

    @Test
    @DisplayName("Update")
    void update() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            String vin = "1Y5NWYGLY5F4PX4HH";
            String newColor = "White";
            cpq.updateColor(vin, newColor);
            assertEquals(newColor, cpq.get(vin).getColor());
        });
    }

    @Test
    @DisplayName("Remove")
    void gremove() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            String vin = "X1U2PEJSC361L10MZ";
            cpq.get(vin);
            cpq.remove(vin);
            assertThrows(NoSuchElementException.class, () -> cpq.get(vin));
        });
    }

    @Test
    @DisplayName("Get from all")
    void get_all() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            Car c = cpq.getLowPrice();
            boolean r = c.getVIN().equals("UTJYU67091B71NGZ3")
                    || c.getVIN().equals("RAMM7ZJBSFZ0HRTTN")
                    || c.getVIN().equals("SY719WJ4MMYVN0XNG");
            assertTrue(r);

            c = cpq.getLowMileage();
            r = c.getVIN().equals("PUAF85WU5R6L6H1P9")
                    || c.getVIN().equals("GNX5TS04SM5V5EXP8");
            assertTrue(r);
        });
    }

    @Test
    @DisplayName("Get for specific make/model")
    void get_make_model() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            Car c = cpq.getLowPrice("Ford", "Escort");
            assertEquals("8BSM1K0A6GXY2CHD7", c.getVIN());

            c = cpq.getLowMileage("Hyundai", "Elantra");
            assertEquals("GNX5TS04SM5V5EXP8", c.getVIN());
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void remove() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars.txt");
            cpq1.add(new Car("AAAAAAAAAAAAAAAAA", "Honda", "FastCar", 10, 10, "Darkness"));
            cpq1.add(new Car("BBBBBBBBBBBBBBBBB", "Honda", "Civic", 999999999, 999999999, "Rainbow"));
            assertEquals("AAAAAAAAAAAAAAAAA",cpq1.getLowPrice().getVIN());
            assertEquals("AAAAAAAAAAAAAAAAA",cpq1.getLowMileage().getVIN());
            
            //remove first element
            cpq1.remove("AAAAAAAAAAAAAAAAA");
            assertTrue(false == cpq1.getLowPrice().getVIN().equals("AAAAAAAAAAAAAAAAA"));
            assertTrue(false == cpq1.getLowMileage().getVIN().equals("AAAAAAAAAAAAAAAAA"));

            // add already added car
            assertThrows(IllegalStateException.class, () -> cpq1.add(new Car("BBBBBBBBBBBBBBBBB", "Chevy", "Malibu", 2, 3, "dark")));
            
            // remove last
            cpq1.remove("BBBBBBBBBBBBBBBBB");
            assertThrows(NoSuchElementException.class, () -> cpq1.get("BBBBBBBBBBBBBBBBB"));
            
            // check error updating car not in data structure
            assertThrows(NoSuchElementException.class, () -> cpq1.updatePrice("BBBBBBBBBBBBBBBBB", 69420));
            assertThrows(NoSuchElementException.class, () -> cpq1.updateMileage("BBBBBBBBBBBBBBBBB", 69420));
            assertThrows(NoSuchElementException.class, () -> cpq1.updateColor("BBBBBBBBBBBBBBBBB", "beige"));
            assertThrows(NoSuchElementException.class, () -> cpq1.remove("BBBBBBBBBBBBBBBBB"));
            
            // remove everything
            String[] data = {"PUAF85WU5R6L6H1P9", "UTJYU67091B71NGZ3", "5DZ623ZRW0C4N80YZ", "Y9BXE6H7957YNKD2C", "678PL45NTNWRED0RJ", "X1U2PEJSC361L10MZ", "8BSM1K0A6GXY2CHD7", "1Y5NWYGLY5F4PX4HH", "SM0G8H2WXK466CRCA", "M750UYC6G01AN7590", "RAMM7ZJBSFZ0HRTTN", "SY719WJ4MMYVN0XNG", "GNX5TS04SM5V5EXP8", "16Z2DPEHSUK5KCMEH"};
            for(String vin : data){
                cpq1.remove(vin);
                assertThrows(NoSuchElementException.class, () -> cpq1.get(vin));
            }

            
            //remove from empty PQ
            for(String vin : data){
                assertThrows(NoSuchElementException.class, () -> cpq1.remove(vin));
            }

            // returns null getting lowest price from empty data structures
            assertTrue(null==cpq1.getLowPrice());
            assertTrue(null==cpq1.getLowPrice("Ford", "Fiesta"));
            assertTrue(null==cpq1.getLowPrice("Hyundai", "Elantra"));
            assertTrue(null==cpq1.getLowMileage());
            assertTrue(null==cpq1.getLowMileage("Ford", "Fiesta"));
            assertTrue(null==cpq1.getLowMileage("Hyundai", "Elantra"));

            // add a LARGE amount of cars
            // assumes no VIN error checking
            for(int i =0; i<10001; i++){
                String temp = String.valueOf(i);
                cpq1.add(new Car(temp, "Ford", "Fiesta", 10000,10000,"Black"));
                assertTrue(null != cpq1.getLowPrice());
                assertTrue(null != cpq1.getLowPrice("Ford", "Fiesta"));
                assertTrue(null != cpq1.getLowMileage());
                assertTrue(null != cpq1.getLowMileage("Ford", "Fiesta"));
                assertEquals(String.valueOf(i), cpq1.get(String.valueOf(i)).getVIN());
            }

        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void proper_price_order() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars2.txt");
            String[] data = {"PUAF85WU5R6L6H1P9", "UTJYU67091B71NGZ3", "5DZ623ZRW0C4N80YZ", "Y9BXE6H7957YNKD2C", "678PL45NTNWRED0RJ", "X1U2PEJSC361L10MZ", "8BSM1K0A6GXY2CHD7", "1Y5NWYGLY5F4PX4HH", "SM0G8H2WXK466CRCA", "M750UYC6G01AN7590", "RAMM7ZJBSFZ0HRTTN", "SY719WJ4MMYVN0XNG", "GNX5TS04SM5V5EXP8", "16Z2DPEHSUK5KCMEH"};

            for(String vin : data){
                assertEquals(vin, cpq1.getLowPrice().getVIN());
                cpq1.remove(vin);
            }
            
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void updatingPrice() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars2.txt");
            String[] data = {"PUAF85WU5R6L6H1P9", "UTJYU67091B71NGZ3", "5DZ623ZRW0C4N80YZ", "Y9BXE6H7957YNKD2C", "678PL45NTNWRED0RJ", "X1U2PEJSC361L10MZ", "8BSM1K0A6GXY2CHD7", "1Y5NWYGLY5F4PX4HH", "SM0G8H2WXK466CRCA", "M750UYC6G01AN7590", "RAMM7ZJBSFZ0HRTTN", "SY719WJ4MMYVN0XNG", "GNX5TS04SM5V5EXP8", "16Z2DPEHSUK5KCMEH"};

            for(int i = data.length-1;i>=0;i--){
                cpq1.updatePrice(data[i],-1);
                assertEquals(data[i], cpq1.getLowPrice().getVIN());
                cpq1.remove(data[i]);
            }
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void updatingMilage() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars2.txt");
            String[] data = {"PUAF85WU5R6L6H1P9", "UTJYU67091B71NGZ3", "5DZ623ZRW0C4N80YZ", "Y9BXE6H7957YNKD2C", "678PL45NTNWRED0RJ", "X1U2PEJSC361L10MZ", "8BSM1K0A6GXY2CHD7", "1Y5NWYGLY5F4PX4HH", "SM0G8H2WXK466CRCA", "M750UYC6G01AN7590", "RAMM7ZJBSFZ0HRTTN", "SY719WJ4MMYVN0XNG", "GNX5TS04SM5V5EXP8", "16Z2DPEHSUK5KCMEH"};

            for(String vin : data){
                cpq1.updateMileage(vin,0);
                assertEquals(vin, cpq1.getLowMileage().getVIN());
                cpq1.remove(vin);
            }
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void MakeAndModel() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars2.txt");

            assertEquals("PUAF85WU5R6L6H1P9", cpq1.getLowPrice("Ford","Fiesta").getVIN());
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void getTest() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ();
            Car c1 = new Car("11111111111111111","Ford","Model",1000,10,"Blue");
            Car c2 = new Car("11111111111111112","Ford","Model",1001,9,"Blue");
            Car c3 = new Car("11111111111111113","Ford","Model",1002,8,"Blue");
            Car c4 = new Car("11111111111111114","Ford","Model",1003,7,"Blue");
            Car c5 = new Car("11111111111111115","Ford","Model",1004,6,"Blue");
            Car c6 = new Car("11111111111111116","Ford","Model",1005,5,"Blue");
            cpq1.add(c1);
            cpq1.add(c2);
            cpq1.add(c3);
            cpq1.add(c4);
            cpq1.add(c5);
            cpq1.add(c6);
            assertEquals(c1, cpq1.get("11111111111111111"));
            assertEquals(c2, cpq1.get("11111111111111112"));
            assertEquals(c3, cpq1.get("11111111111111113"));
            assertEquals(c4, cpq1.get("11111111111111114"));
            assertEquals(c5, cpq1.get("11111111111111115"));
            assertEquals(c6, cpq1.get("11111111111111116"));
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void updating() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars2.txt");
            cpq1.updateMileage("16Z2DPEHSUK5KCMEH",15);
            cpq1.updateColor("GNX5TS04SM5V5EXP8","Yellow");
            cpq1.updatePrice("GNX5TS04SM5V5EXP8",1000);
            Car c = cpq1.getLowMileage();
            assertEquals("Yellow21000", c.getColor()+c.getMileage()+c.getPrice());
        });
    }

    @Test
    @DisplayName("Gordon's Tests")
    void emptyTest() {
        CarsPQ cpq1 = new CarsPQ();
        Car c1 = new Car("11111111111111111","Ford","Model",1000,10,"Blue");
        cpq1.add(c1);
        cpq1.remove("11111111111111111");
        assertEquals(null, cpq1.getLowPrice("Ford","Model"));
        assertEquals(null, cpq1.getLowPrice("Fordd","Model"));
    }

    @Test
    @DisplayName("Gordon's Tests")
    void everything() {
        assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
            CarsPQ cpq1 = new CarsPQ("build/resources/test/cars.txt");
            cpq1.updateColor("SM0G8H2WXK466CRCA","White");
            cpq1.updatePrice("X1U2PEJSC361L10MZ",100);
            cpq1.updatePrice("GNX5TS04SM5V5EXP8",50);
            cpq1.updateMileage("Y9BXE6H7957YNKD2C",4000);
            cpq1.remove("GNX5TS04SM5V5EXP8");
            cpq1.add(new Car("11111111111111111","Ferarri","F56",10,151,"Black"));
            cpq1.updateColor("11111111111111111","Pink");
            assertEquals("11111111111111111",cpq1.getLowMileage().getVIN());
            assertEquals("White",cpq1.get("SM0G8H2WXK466CRCA").getColor());
            assertEquals(100,cpq1.get("X1U2PEJSC361L10MZ").getPrice());
            try {
                cpq1.get("GNX5TS04SM5V5EXP8");
                //cpq1.add(new Car("X1U2PEJSC361L10MZ","dsa","dsf",10,10,"daf"));

            } catch (NoSuchElementException e) {
                // Use assertEquals to confirm that the caught exception is of type NoSuchElementException
                assertEquals(NoSuchElementException.class, e.getClass());
            }
        });
    }
}
