package com.mycompany.corporatetalenthub.modelo;


public class Empleado {
    
    // 8 tipos de datos primitivos:
    private byte nivelAcceso;
    private short anioIngreso;
    private int idEmpleado;
    private long numeroDocumento;
    private float puntajeTest;
    private double salarioBase;
    private char tipoContrato;
    private boolean esActivo;
    
    // String no es primitivo, es una clase:
    private String nombre;
    
    // datos adicionales para reglas del negocio:
    private int edad;
    private int idSede;
    private double bonoMensual;
    
    // define constructor
    public Empleado(
            byte nivelAcceso,
            short anioIngreso,
            int idEmpleado,
            long numeroDocumento,
            float puntajeTest,
            double salarioBase,
            char tipoContrato,
            boolean esActivo,
            String nombre,
            int edad,
            int idSede,
            double bonoMensual) {
        this.nivelAcceso = nivelAcceso;
        this.anioIngreso = anioIngreso;
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.puntajeTest = puntajeTest;
        this.salarioBase = salarioBase;
        this.tipoContrato = tipoContrato;
        this.esActivo = esActivo;
        this.nombre = nombre;
        this.edad = edad;
        this.idSede = idSede;
        this.bonoMensual = bonoMensual;
    }
    
    public double calcularSalarioFinal() {
        return (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
    }
    
    public boolean tieneBonoExtra() {
        return (idEmpleado % 2 == 0);
    }
    
    public boolean validarElegibilidad() {
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }
    
    public void actualizarBonoMensual(double incremento) {
        bonoMensual += incremento;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre){   
        this.nombre = nombre;
    }
    
    public int getIdEmpleado() {
        return idEmpleado;
    }
    
    public double getBonoMensual() {
        return bonoMensual;
    }
    
    @Override
    public String toString() {
        return "Empleado:\n" +
        "  nivelAcceso=" + nivelAcceso + ",\n" +
        "  anioIngreso=" + anioIngreso + ",\n" +
        "  idEmpleado=" + idEmpleado + ",\n" +
        "  numeroDocumento=" + numeroDocumento + ",\n" +
        "  puntajeTest=" + puntajeTest + ",\n" +
        "  salarioBase=" + salarioBase + ",\n" +
        "  tipoContrato=" + tipoContrato + ",\n" +
        "  esActivo=" + esActivo + ",\n" +
        "  nombre='" + nombre + "',\n" +
        "  edad=" + edad + ",\n" +
        "  idSede=" + idSede + ",\n" +
        "  bonoMensual=" + bonoMensual + "\n" +
        "";

    }
}
