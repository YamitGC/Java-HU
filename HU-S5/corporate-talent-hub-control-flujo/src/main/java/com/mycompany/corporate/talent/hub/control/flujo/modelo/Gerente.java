package com.mycompany.corporate.talent.hub.control.flujo.modelo;

public final class Gerente extends Empleado {

    private final double presupuestoMensual;

    public Gerente(int id, String nombre, byte edad, double salario, double presupuestoMensual) {
        super(id, nombre, edad, salario);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    @Override
    public double calcularBonoAscenso() {
        return getSalario() * 0.12 + presupuestoMensual * 0.01;
    }
}