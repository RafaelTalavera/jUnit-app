package org.example.models;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class cuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.2"));
        cuenta.setPersona("Rafael");
        String esperado = "Rafael";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals("Rafel"));

    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);

    }

    @Test
    void testReferenciaDeCuenta() {
        Cuenta cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Rafael", new BigDecimal("1000.12345"));

       // assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2,cuenta);
    }
}