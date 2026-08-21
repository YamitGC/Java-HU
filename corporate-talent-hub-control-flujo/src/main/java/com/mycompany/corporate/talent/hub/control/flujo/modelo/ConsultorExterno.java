package main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * Segunda (y última) rama permitida por Persona. Se declara "final" porque
 * no necesitamos que nadie extienda a su vez a ConsultorExterno: cierra
 * por completo esta rama de la jerarquía sellada.
 */
public final class ConsultorExterno extends Persona {

    private final String empresaContratista;
    private final double tarifaPorHora;

    public ConsultorExterno(String nombre, byte edad, String empresaContratista, double tarifaPorHora) {
        super(nombre, edad);
        this.empresaContratista = empresaContratista;
        this.tarifaPorHora = tarifaPorHora;
    }

    public String getEmpresaContratista() {
        return empresaContratista;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }
}