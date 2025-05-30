package co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos;

/**
 * Enum representing teacher types in the system
 */
public enum TipoDocente {
    CATEDRA("CATEDRA"),
    TIEMPO_COMPLETO("TIEMPO_COMPLETO"),
    PLANTA("PLANTA");
    
    private final String value;
    
    TipoDocente(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
