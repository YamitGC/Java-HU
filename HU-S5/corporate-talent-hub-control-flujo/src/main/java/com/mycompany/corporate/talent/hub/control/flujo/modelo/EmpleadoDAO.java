package com.mycompany.corporate.talent.hub.control.flujo.modelo;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempenoReport;

import java.util.List;

public interface EmpleadoDAO {
    void insertar(Empleado empleado);
    List<Empleado> listar();
    void actualizar(Empleado empleado);
    void eliminar(int id);
    Empleado buscarPorId(int id);
    List<DesempenoReport> obtenerReportesComplex();
}