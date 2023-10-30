package org.example.models;


import org.example.exeptions.DineroInsuficienteRunTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class cuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.2"));
        cuenta.setPersona("Rafael");
        String esperado = "Rafael";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado, real);
        assertTrue(real.equals("Rafael"));

    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertNotNull(cuenta.getSaldo());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);

    }

    @Test
    void testReferenciaDeCuenta() {
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());

       // assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2,cuenta);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100,cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteRunTime.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTranferirDineroCuentas() {

      Cuenta cuenta1 =  new Cuenta("Rafael Talavera", new BigDecimal("2500"));
      Cuenta cuenta2 =  new Cuenta("Alejandra Martino", new BigDecimal("1500.8989"));

      Banco banco = new Banco();
      banco.setNombre("Banco Macro");
      banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
      assertEquals("1000.8989",cuenta2.getSaldo().toPlainString());
      assertEquals("3000",cuenta1.getSaldo().toPlainString());
    }


    @Test
    void testRelacionBancoCuentas() {

        Cuenta cuenta1 =  new Cuenta("Rafael Talavera", new BigDecimal("2500"));
        Cuenta cuenta2 =  new Cuenta("Alejandra Martino", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco Macro");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989",cuenta2.getSaldo().toPlainString());
        assertEquals("3000",cuenta1.getSaldo().toPlainString());

        assertEquals(2,banco.getCuentas().size());
        assertEquals("Banco Macro", cuenta1.getBanco().getNombre());
        assertEquals("Rafael Talavera", banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Rafael Talavera"))
                .findFirst()
                .get().getPersona());
    }
}