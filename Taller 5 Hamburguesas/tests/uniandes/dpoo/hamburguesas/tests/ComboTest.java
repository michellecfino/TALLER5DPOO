package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.util.ArrayList;

public class ComboTest {
    private Combo comboCorral;
    private Combo comboCorralQueso;
    private Combo comboTodoTerreno;
    private Combo comboEspecial;

    @BeforeEach
    void setUp() throws Exception {
        ArrayList<ProductoMenu> itemsCorral = new ArrayList<>();
        itemsCorral.add(new ProductoMenu("corral", 14000));
        itemsCorral.add(new ProductoMenu("papas medianas", 5500));
        itemsCorral.add(new ProductoMenu("gaseosa", 5000));
        comboCorral = new Combo("Combo Corral", 0.10, itemsCorral); // 10% de descuento

        ArrayList<ProductoMenu> itemsCorralQueso = new ArrayList<>();
        itemsCorralQueso.add(new ProductoMenu("corral queso", 16000));
        itemsCorralQueso.add(new ProductoMenu("papas medianas", 5500));
        itemsCorralQueso.add(new ProductoMenu("gaseosa", 5000));
        comboCorralQueso = new Combo("Combo Corral Queso", 0.10, itemsCorralQueso); // 10% de descuento

        ArrayList<ProductoMenu> itemsTodoterreno = new ArrayList<>();
        itemsTodoterreno.add(new ProductoMenu("todoterreno", 25000));
        itemsTodoterreno.add(new ProductoMenu("papas grandes", 4000));
        itemsTodoterreno.add(new ProductoMenu("gaseosa", 5000));
        comboTodoTerreno = new Combo("Combo Todoterreno", 0.07, itemsTodoterreno); // 7% de descuento

        ArrayList<ProductoMenu> itemsEspecial = new ArrayList<>();
        itemsEspecial.add(new ProductoMenu("especial", 24000));
        itemsEspecial.add(new ProductoMenu("papas medianas", 5500));
        itemsEspecial.add(new ProductoMenu("gaseosa", 5000));
        comboEspecial = new Combo("Combo Especial", 0.095, itemsEspecial); // 9.5% de descuento
    }

    @AfterEach
    void tearDown() throws Exception {
        comboCorral = null;
        comboCorralQueso = null;
        comboTodoTerreno = null;
        comboEspecial = null;
    }

    
    @Test
    void testGetNombreCorral() {
    	assertEquals("Combo Corral", comboCorral.getNombre(), "El nombre del combo solicitado no existe.");
    }
    
    @Test
    void testGetNombreCorralQueso() {
    	assertEquals("Combo Corral Queso", comboCorralQueso.getNombre(), "El nombre del combo solicitado no existe.");
    }
    
    @Test
    void testGetNombreTodoTerreno() {
    	assertEquals("Combo Todoterreno", comboTodoTerreno.getNombre(), "El nombre del combo solicitado no existe.");
    }
    
    @Test
    void testGetNombreEspecial() {
    	assertEquals("Combo Especial", comboEspecial.getNombre(), "El nombre del combo solicitado no existe.");
    }
    @Test
    void testComboCorralPrecio() {
    	//System.out.println(comboCorral.getPrecio());
        assertEquals(22050, comboCorral.getPrecio(), "El precio del Combo Corral no es el esperado.");
    }

    @Test
    void testComboCorralQuesoPrecio() {
    	//System.out.println(comboCorralQueso.getPrecio());
        assertEquals(23850, comboCorralQueso.getPrecio(), "El precio del Combo Corral Queso no es el esperado.");
    }

    @Test
    void testComboTodoterrenoPrecio() {

//    	System.out.println(comboTodoTerreno.getPrecio());
        assertEquals(31620, comboTodoTerreno.getPrecio(), "El precio del Combo Todoterreno no es el esperado.");
    }

    @Test
    void testComboEspecialPrecio() {
//    	System.out.println(comboEspecial.getPrecio());
        assertEquals(31222, comboEspecial.getPrecio(), "El precio del Combo Especial no es el esperado.");
    }

    @Test
    void testGenerarTextoFacturaComboCorral() {
        String esperado = "Combo Combo Corral\n" +
                                " Descuento: 10.0%\n" +
                                "            22050\n";
//       System.out.println(comboCorral.generarTextoFactura());
//       System.out.println(esperado);
//        
        assertEquals(esperado, comboCorral.generarTextoFactura(), "El texto de la factura del Combo Corral no es el esperado.");
    }

    @Test
    void testGenerarTextoFacturaComboCorralQueso() {
        String esperado = "Combo Combo Corral Queso\n" +
                                " Descuento: 10.0%\n" +
                                "            23850\n";
//        System.out.println(comboCorralQueso.generarTextoFactura());
//        System.out.println(esperado);
//         
        assertEquals(esperado, comboCorralQueso.generarTextoFactura(), "El texto de la factura del Combo Corral Queso no es el esperado.");
    }

    @Test
    void testGenerarTextoFacturaComboTodoterreno() {
        String esperado = "Combo Combo Todoterreno\n" +
                                " Descuento: 7.000000000000001%\n" +
                                "            31620\n";
//        System.out.println(comboTodoTerreno.generarTextoFactura());
//        System.out.println(esperado);
         
        assertEquals(esperado, comboTodoTerreno.generarTextoFactura(), "El texto de la factura del Combo Todoterreno no es el esperado.");
    }
    @Test
    void testGenerarTextoFacturaEspecial() {
        String esperado = "Combo Combo Especial\n" +
                                " Descuento: 9.5%\n" +
                                "            31222\n";
        
      System.out.println(comboEspecial.generarTextoFactura());
      System.out.println(esperado);
        assertEquals(esperado, comboEspecial.generarTextoFactura(), "El texto de la factura del Combo Todoterreno no es el esperado.");
    }
}