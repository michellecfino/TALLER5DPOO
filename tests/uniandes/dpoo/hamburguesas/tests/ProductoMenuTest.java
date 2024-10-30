package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu pm;

    @BeforeEach
    void setUp( ) throws Exception
    {
        pm = new ProductoMenu( "papitas", 5000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "papitas", pm.getNombre( ), "El nombre del producto del menú no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 5000, pm.getPrecio(), "El precio base no es el esperado." );
    }
    
    // Coregir este método ya que esa respuesta se lee horrible
    @Test
    void testGenerarTextoFactura( )
    {
    	String respuesta = "papitas\n            5000\n";
        assertEquals( respuesta, pm.generarTextoFactura(), "El texto de la factura no es el esperado." );
    }
    

}
