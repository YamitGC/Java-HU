/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * @author Coder
 *
 * Empleado ahora extiende la sealed class Persona (TASK 1) y además es
 * sealed a su vez: solo Desarrollador y Gerente pueden extenderla
 * (TASK 3). Así queda una jerarquía sellada de dos niveles, totalmente
 * controlada por el equipo dueño del dominio.
 *
 * También implementa Promocionable (TASK 4), heredando de forma gratuita
 * el método default registrarLog() sin tener que escribir nada nuevo.
 *
 * Encapsulamiento (TASK 4): id, salario y promedioDesempeno son
 * "private" (solo accesibles vía getters/setters); nombre y edad son
 * "protected" y viven en Persona, visibles únicamente para las subclases
 * de la jerarquía (Empleado, Desarrollador, Gerente, ConsultorExterno).
 */
public sealed class Empleado extends Persona implements Promocionable
        permits Desarrollador, Gerente {

    private final int id;
    private final double salario;
    private double promedioDesempeno;

    public Empleado(int id, String nombre, byte edad, double salario) {
        super(nombre, edad);
        this.id = id;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public double getSalario() {
        return salario;
    }

    public double getPromedioDesempeno() {
        return promedioDesempeno;
    }

    public void setPromedioDesempeno(double promedioDesempeno) {
        this.promedioDesempeno = promedioDesempeno;
    }

    /**
     * Regla de bono por defecto para un empleado "genérico" (sin rol
     * especializado). Desarrollador y Gerente la sobreescriben con su
     * propia regla de negocio.
     */
    @Override
    public double calcularBonoAscenso() {
        return salario * 0.05; // 5% de bono estándar
    }
}