package org.example.models;

import org.example.exeptions.DineroInsuficienteRunTime;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;

    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
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

    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);

        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteRunTime("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;

    }

    public void credito (BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }
}
