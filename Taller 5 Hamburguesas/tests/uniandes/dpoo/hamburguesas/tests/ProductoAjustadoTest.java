package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;

public class ProductoAjustadoTest {
    private ProductoAjustado pa;
    private ProductoMenu pm;

    @BeforeEach
    void setUp() throws Exception {
        pm = new ProductoMenu("hamburguesa", 10000);
        pa = new ProductoAjustado(pm);
    }

    @AfterEach
    void tearDown() throws Exception {
        pm = null;
        pa = null;
    }

    @Test
    void testGetNombre() {
        assertEquals("hamburguesa", pa.getNombre(), "El nombre del producto ajustado no es el esperado.");
    }

    @Test
    void testGetPrecioSinModificaciones() {
        assertEquals(10000, pa.getPrecio(), "El precio del producto ajustado sin modificaciones no es el esperado.");
    }

    @Test
    void testAgregarIngredienteRepetido() throws IngredienteRepetidoException {
        
        Ingrediente ingredienteExtra = new Ingrediente("queso", 2000);
        pa.agregarIngrediente(ingredienteExtra); 

        IngredienteRepetidoException exception = assertThrows(IngredienteRepetidoException.class, () -> {
            pa.agregarIngrediente(ingredienteExtra); 
        });

        assertEquals("El ingrediente queso está repetido", exception.getMessage());
    }


    @Test
    void testGenerarTextoFacturaConIngredienteAgregado() throws IngredienteRepetidoException {
        Ingrediente ingredienteExtra = new Ingrediente("queso", 2000);
        pa.agregarIngrediente(ingredienteExtra);

        String esperado = "hamburguesa" +
                          "    +queso" +
                          "                2000" +
                          "            12000\n";

        assertEquals(esperado, pa.generarTextoFactura(), "El texto de la factura con ingredientes agregados no es el esperado.");
    }

    @Test
    void testGenerarTextoFacturaConIngredienteEliminado() throws IngredienteRepetidoException {
        Ingrediente ingredienteAgregado = new Ingrediente("aguacate", 3000);
        pa.agregarIngrediente(ingredienteAgregado);
        pa.removerIngrediente(ingredienteAgregado);

        String esperado = "hamburguesa" +
                          "    -aguacate" +
                          "            10000\n";

        assertEquals(esperado, pa.generarTextoFactura(), "El texto de la factura con ingredientes eliminados no es el esperado.");
    }

    @Test
    void testRemoverIngredienteCuandoEstáAgregado() throws IngredienteRepetidoException {
        Ingrediente ingredienteAgregado = new Ingrediente("queso", 2000);
        pa.agregarIngrediente(ingredienteAgregado); // Agregamos el ingrediente

        pa.removerIngrediente(ingredienteAgregado); // Removemos el ingrediente

        assertFalse(pa.agregados.contains(ingredienteAgregado), "El ingrediente debería haber sido eliminado de agregados.");
    }

    @Test
    void testRemoverIngredienteCuandoNoEstáAgregado() {
        Ingrediente ingredienteNoAgregado = new Ingrediente("lechuga", 1000); // Creamos un ingrediente que no está agregado

        // Intentar remover un ingrediente que no está agregado debe manejarse sin excepción
        pa.removerIngrediente(ingredienteNoAgregado);

        // Verificamos que la lista de agregados y la de eliminados no cambian
        assertFalse(pa.agregados.contains(ingredienteNoAgregado), "La lista de agregados no debería contener el ingrediente.");
        assertFalse(pa.eliminados.contains(ingredienteNoAgregado), "La lista de eliminados no debería contener el ingrediente.");
    }
}
