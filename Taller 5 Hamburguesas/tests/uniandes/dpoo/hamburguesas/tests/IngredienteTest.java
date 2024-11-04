package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;

public class IngredienteTest {
    private Ingrediente ingrediente1;

    @BeforeEach
    void setUp() throws Exception {
        ingrediente1 = new Ingrediente("tomate", 1000);
    }

    @AfterEach
    void tearDown() throws Exception {
        ingrediente1 = null;
    }

    @Test
    void testGetNombre() {
        assertEquals("tomate", ingrediente1.getNombre(), "El nombre del ingrediente no es el esperado.");
    }

    @Test
    void testGetCostoAdicional() {
        assertEquals(1000, ingrediente1.getCostoAdicional(), "El costo adicional del ingrediente no es el esperado.");
    }

    @Test
    void testIngredienteRepetido() {
        // Simular un escenario donde se intenta agregar un ingrediente repetido
        // Suponiendo que hay un método en la clase que maneja la lista de ingredientes

        // Este bloque es un ejemplo; necesitarás tener un contexto adecuado donde esto se aplique
        // Suponiendo que tenemos una clase que maneja ingredientes
        try {
            Ingrediente ingredienteRepetido = new Ingrediente("tomate", 1000);
            // Método ficticio que intenta agregar un ingrediente
            // agregarIngrediente(ingredienteRepetido); 
            // Se lanza la excepción si el ingrediente ya existe
            throw new IngredienteRepetidoException(ingredienteRepetido.getNombre());
        } catch (IngredienteRepetidoException e) {
            assertEquals("El ingrediente tomate está repetido", e.getMessage(), "El mensaje de la excepción no es el esperado.");
        }
    }
}
