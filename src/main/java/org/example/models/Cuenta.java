package org.example.models;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Cuenta)) {
            return false;
        }
        Cuenta c = (Cuenta) obj;

        // Verifica si either this.persona or c.getPersona() is null
        if (this.persona == null || c.getPersona() == null) {
            if (this.persona != c.getPersona()) {
                return false;
            }
        } else {
            if (!this.persona.equals(c.getPersona())) {
                return false;
            }
        }

        // Verifica si either this.saldo or c.saldo is null
        if (this.saldo == null || c.saldo == null) {
            if (this.saldo != c.saldo) {
                return false;
            }
        } else {
            if (!this.saldo.equals(c.saldo)) {
                return false;
            }
        }

        return true;
    }


    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
