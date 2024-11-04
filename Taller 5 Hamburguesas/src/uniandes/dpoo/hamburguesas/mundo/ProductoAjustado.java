package uniandes.dpoo.hamburguesas.mundo;

import java.util.ArrayList;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;

/**
 * Un producto ajustado es un producto para el cual el cliente solicitó alguna modificación.
 */
public class ProductoAjustado implements Producto
{
    /**
     * El producto base que el cliente sobre el cual el cliente quiere hacer ajustes
     */
    private ProductoMenu productoBase;

    /**
     * La lista de ingrediente que el usuario quiere agregar. El mismo ingrediente puede aparecer varias veces.
     */
    public ArrayList<Ingrediente> agregados;

    /**
     * La lista de ingrediente que el usuario quiere eliminar.
     */
    public ArrayList<Ingrediente> eliminados;

    /**
     * Construye un nuevo producto ajustado a partir del producto base y sin modificaciones
     * @param productoBase El producto base que se va a ajustar
     */
    public ProductoAjustado( ProductoMenu productoBase )
    {
        this.productoBase = productoBase;
        agregados = new ArrayList<Ingrediente>( );
        eliminados = new ArrayList<Ingrediente>( );
    }

    @Override
    public String getNombre( )
    {
        return productoBase.getNombre( );
    }

    /**
     * Retorna el precio del producto ajustado, que debe ser igual al del producto base, sumándole el precio de los ingredientes adicionales.
     */
    @Override
    public int getPrecio() {
        int precioNuevo = productoBase.getPrecio(); 

        for (Ingrediente ingrediente : agregados) {
            precioNuevo += ingrediente.getCostoAdicional(); 
        }

        return precioNuevo; 
    }


    /**
     * Genera el texto que debe aparecer en la factura.
     * 
     * El texto incluye el producto base, los ingredientes adicionales con su costo, los ingredientes eliminados, y el precio total
     */
    @Override
    public String generarTextoFactura( )
    {
        StringBuffer sb = new StringBuffer( );
        //cambio en el nombre del producto porque estaba imprimiendo el objeto :c
        sb.append( productoBase.getNombre() );
        for( Ingrediente ing : agregados )
        {
            sb.append( "    +" + ing.getNombre( ) );
            sb.append( "                " + ing.getCostoAdicional( ) );
        }
        for( Ingrediente ing : eliminados )
        {
            sb.append( "    -" + ing.getNombre( ) );
        }

        sb.append( "            " + getPrecio( ) + "\n" );

        return sb.toString( );
    }
    
    //método para meter cositas a las lista de agregados
    public void agregarIngrediente(Ingrediente ingrediente) throws IngredienteRepetidoException {
        if (agregados.contains(ingrediente)) {
            throw new IngredienteRepetidoException(ingrediente.getNombre());
        }
        agregados.add(ingrediente);
    }

    
    
    //método que elimina cositas de la lista de agregados, o sea que agrega 
    //cosas a la lista de eliminados
    public void removerIngrediente(Ingrediente ingrediente) {
        if (agregados.remove(ingrediente)) {
            eliminados.add(ingrediente);
        }
    }


    
    
}
