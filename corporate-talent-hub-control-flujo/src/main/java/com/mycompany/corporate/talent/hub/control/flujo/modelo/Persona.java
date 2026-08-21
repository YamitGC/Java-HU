package main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * ESTILO LEGACY (Java 8/11) — herencia abierta:
 * Antes de Java 17, una clase base como "Persona" solo podía declararse
 * como una clase abstracta común:
 *
 *   public abstract class Persona {
 *       protected String nombre;
 *       ...
 *   }
 *
 * Con esa declaración, CUALQUIER clase, en cualquier paquete —incluso una
 * librería externa fuera de nuestro control— podía escribir:
 *
 *   public class ClienteExterno extends Persona { ... }
 *
 * y el compilador lo permitía sin restricción alguna. Esto es "herencia
 * abierta": nada impide que aparezcan subtipos inesperados del dominio.
 *
 * ESTILO MODERNO (Java 17/21) — Sealed Classes:
 * Al declarar Persona como "sealed" y usar "permits", definimos de forma
 * cerrada y explícita cuáles son las ÚNICAS clases autorizadas a extenderla:
 * Empleado y ConsultorExterno.
 *
 * ¿POR QUÉ ES MÁS SEGURO PARA EL DISEÑO DE APIs?
 * 1. CONTROL TOTAL DEL DOMINIO: el equipo dueño de Persona conoce de
 *    antemano TODAS las subclases posibles; nadie puede "colarse" en la
 *    jerarquía desde otro módulo o librería externa.
 * 2. EXHAUSTIVIDAD EN PATTERN MATCHING: como el compilador conoce el
 *    conjunto cerrado y finito de subtipos, un switch sobre una Persona
 *    puede verificarse EXHAUSTIVAMENTE en tiempo de compilación, sin
 *    necesitar una rama "default" que oculte casos no contemplados.
 * 3. MODELADO FIEL AL NEGOCIO: en este dominio una "Persona" del sistema
 *    solo puede ser Empleado o ConsultorExterno. La sealed class convierte
 *    esa regla de negocio en una garantía del lenguaje, no solo en una
 *    convención documentada que alguien podría ignorar.
 * 4. EVOLUCIÓN CONTROLADA: si en el futuro se necesita un nuevo tipo de
 *    Persona, el compilador OBLIGA a actualizar "permits" y a revisar
 *    cualquier switch exhaustivo existente, evitando romper invariantes
 *    de forma silenciosa.
 *
 * En resumen: la herencia abierta prioriza flexibilidad sin restricción;
 * las sealed classes priorizan seguridad y previsibilidad, algo valioso
 * en un dominio corporativo sensible como el de talento humano.
 */
public abstract sealed class Persona permits Empleado, ConsultorExterno {

    protected final String nombre;
    protected final byte edad;

    protected Persona(String nombre, byte edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public byte getEdad() {
        return edad;
    }
}