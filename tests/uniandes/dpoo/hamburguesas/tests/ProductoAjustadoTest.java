package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
public class ProductoAjustadoTest
{
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
    void testGenerarTextoFacturaSinModificaciones() {
        String esperado = "hamburguesa\n            10000\n";
        assertEquals(esperado, pa.generarTextoFactura(), "El texto de la factura sin modificaciones no es el esperado.");
    }

    @Test
    void testAgregarIngrediente() {
        Ingrediente ingredienteExtra = new Ingrediente("queso", 2000);
        pa.agregados.add(ingredienteExtra);
        
        assertEquals(12000, pa.getPrecio(), "El precio debería incluir el costo del ingrediente agregado.");
    }

    @Test
    void testGenerarTextoFacturaConIngredienteAgregado() {
        Ingrediente ingredienteExtra = new Ingrediente("queso", 2000);
        pa.agregados.add(ingredienteExtra);

        String esperado = "hamburguesa\n" +
                                "    +queso\n" +
                                "                2000\n" +
                                "            12000\n";
        assertEquals(esperado, pa.generarTextoFactura(), "El texto de la factura con ingredientes agregados no es el esperado.");
    }

    void testEliminarIngrediente() {
    	Ingrediente ingredienteEliminado = new Ingrediente("queso",2000);
    	pa.eliminados.add(ingredienteEliminado);
    	
    	assertEquals("queso",pa.getNombre(),"Debería estar eliminado");
    }

}
