package main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * Una de las dos únicas ramas permitidas por Empleado (sealed permits
 * Desarrollador, Gerente). Final: no necesita subclases propias.
 */
public final class Desarrollador extends Empleado {

    private final String lenguajePrincipal;

    public Desarrollador(int id, String nombre, byte edad, double salario, String lenguajePrincipal) {
        super(id, nombre, edad, salario);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }

    @Override
    public double calcularBonoAscenso() {
        return getSalario() * 0.08; // bono especializado para desarrolladores
    }
}