package org.example.models;


import org.example.exeptions.DineroInsuficienteRunTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.parallel.Execution;


import java.util.Properties;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*; //con este import puedo trabajar con las variables por ejemplo que
//se ejecute la prueba si el servidor esta up y desabilitar cuando esta down


class cuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Rafael", new BigDecimal("1000.2"));

    }

    @AfterEach
    void tearDown() {
        System.out.println("finalizando el test de prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Hola Test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Chau test");
    }

    @Test
    @DisplayName("Carga de atributos a la clase usuario")
        // @Disabled cuando quiero desabilitar algun test
    void testNombreCuenta() {

        // cuenta.setPersona("Rafael");
        String esperado = "Rafael";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser Null");
        assertEquals(esperado, real, () -> "El valor de la cuenta no es el esperado es " + esperado
                + "sin embargo fue " + real);
        assertTrue(real.equals("Rafael"), () -> "nombre de cuenta debe ser igual:" + real);

        // expresion lambda normalmente se usa para evitar que jUnit instancie el objeto si la prueba tiene exito.
        // es la invocación futura de un metodo.
    }

    @Test
    @DisplayName("Test saldos de cuenta")
    void testSaldoCuenta() {
        cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue(), () -> "el valor no es igual");
        assertNotNull(cuenta.getSaldo(), () -> "El valor no puede ser null");
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, () -> "El valor no puede ser menor a 0");
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0, () -> "El valor ser mayor a 0");

    }

    @Test
    @DisplayName("Test referencia de cuentas")
    void testReferenciaDeCuenta() {
        cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());

        // assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Test debito de la cuenta")
    void testDebitoCuenta() {
        cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Test credito de la cuenta")
    void testCreditoCuenta() {
        cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    @DisplayName("Test prueba de exepciones")
    void testDineroInsuficienteExceptionCuenta() {
        cuenta = new Cuenta("Rafael", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteRunTime.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Test trasnferencia de dinero")
    void testTranferirDineroCuentas() {

        Cuenta cuenta1 = new Cuenta("Rafael Talavera", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Alejandra Martino", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco Macro");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }


    @Test
    @DisplayName("Test relacion de cuentas bancarias")
    void testRelacionBancoCuentas() {
        //fail();
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertAll(() -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                        () -> "el valor del saldo de la cuenta2 no es el esperado."),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                        () -> "el valor del saldo de la cuenta1 no es el esperado."),
                () -> assertEquals(2, banco.getCuentas().size(), () -> "el banco no tienes las cuentas esperadas"),
                () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                () -> assertEquals("Andres", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Andres"))
                        .findFirst()
                        .get().getPersona()),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Jhon Doe"))));
    }

    @Nested
    class SistemaOperativoTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac() {
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() {
        }
    }

    @Nested
    class SistemPropertiesTest {
        @Test
        void imprimirSystemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "17.0.8")
    void testjavaVersion(){

    }
}

