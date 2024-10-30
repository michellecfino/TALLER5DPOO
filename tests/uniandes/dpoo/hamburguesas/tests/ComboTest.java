package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.util.ArrayList;

public class ComboTest {
    private ProductoMenu producto1;
    private ProductoMenu producto2;
    private Combo combo;

    @BeforeEach
    void setUp() throws Exception {
        producto1 = new ProductoMenu("hamburguesa", 10000);
        producto2 = new ProductoMenu("papas fritas", 5000);
        
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);
        
        combo = new Combo("Combo Clásico", 0.9, items); // 10% de descuento
    }

    @AfterEach
    void tearDown() throws Exception {
        producto1 = null;
        producto2 = null;
        combo = null;
    }

    @Test
    void testGetNombre() {
        assertEquals("Combo Clásico", combo.getNombre(), "El nombre del combo no es el esperado.");
    }

    @Test
    void testGetPrecio() {
        assertEquals(13500, combo.getPrecio(), "El precio del combo con descuento no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
        String expectedOutput = "Combo Combo Clásico\n" +
                                " Descuento: 0.9\n" +
                                "            13500\n";
        assertEquals(expectedOutput, combo.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }

    @Test
    void testPrecioSinDescuento() {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("hamburguesa", 10000));
        items.add(new ProductoMenu("papas fritas", 5000));
        
        Combo comboSinDescuento = new Combo("Combo Sin Descuento", 1.0, items);
        
        assertEquals(15000, comboSinDescuento.getPrecio(), "El precio del combo sin descuento no es el esperado.");
    }
}
