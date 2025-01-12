package usuario; 

import java.util.Scanner;
/********************************************************************************
 * <div>Esta clase va a implementar el interface del entorno de usuario en modo 
 * consola.</div> 
 * <div>Contiene los metodos para la visualizacion de cada menu</div>

 * Con licencia GPL v3
 *
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *********************************************************************************/
public class GUIRepositorioTexto extends GUIUsuarioTexto implements GUIRepositorioInterface
  {
  /*==========================================================================
    Atributos de la clase
    ========================================================================*/  

  /*===========================================================================
    Constructores de la clase
    ==========================================================================*/  
      
  /*********************************************************************************
   * Constructor por defecto. no hace nada
   ********************************************************************************/
  public GUIRepositorioTexto()
    {
    
    }

  /*********************************************************************************
   * Constructor que recibe el tipo de usuario que ejecuta para llamar al constructor
   * de su clase padre
   *
   * @param tipo cadena con el tipo de usuario
   *
   ********************************************************************************/  
  public GUIRepositorioTexto(String tipo)
    {
    super(tipo);
    }
    
  /*==========================================================================
   * Metodos publicos
   * ========================================================================*/    
  //menu    
   
  /********************************************************************************
   * <div>Implementacion del metodo abstracto de GuiUsuario</div>
   * <div>Se visualiza el menu especifico del repositorio, una vez acreditado.</div>
   * 
   * @param iDUsuario obtiene el ID del repostitorio
   * @return devuelve el primer caracter pulsado con numero que se pulsa (u otra cosa)
   * o 'n' si intro
   *******************************************************************************/ 
  public Character menuUsuarioAcreditado(String iDUsuario)
    {
	@SuppressWarnings("resource")//para no cerrar Scanner 
    Scanner teclado=new Scanner(System.in);
    Character tecla='0'; //captura la primera tecla pulsada
    String cadenaEntrada="";//para capturar la entrada
    //limites de opcion
    char opcionInicial='1';
    char opcionFinal='3';
    this.verCabecera(); 
    System.out.println("         "+tipoUsuario+" : "+iDUsuario);
    System.out.println();
    System.out.println(" 1 - Listar clientes");
    System.out.println(" 2 - Listar ficheros del cliente");
    System.out.println(" 3 - Salir");
    System.out.println("");
    System.out.println("Pulsar una opcion y despues intro. Si es erronea volvera a este menu");
    System.out.print("opcion elegida : ");
    cadenaEntrada=teclado.nextLine();
    //si solo se pulsa intro, la cadena esta vacia, por lo que hay que volver a imprimir
    if ((cadenaEntrada.length()==0) ||
           ((cadenaEntrada.charAt(0)<opcionInicial)&&(cadenaEntrada.charAt(0)>opcionFinal)))
      {
      tecla='n';   
      }//fin si entrada vacia
     else
      {
      tecla=cadenaEntrada.charAt(0);   
      }
    return tecla;
    }//fin menuRepositorioAcreditado
  
  /********************************************************************************
   * <div> Este metodo captura el id del cliente con ficheros en este repositorio</div>
   * 
   * @param iDRepositorio el correspondiente al repositorio en ejecucion.
   * @return cadena introducida (si intro, cadena vacia)
   *  
   *******************************************************************************/
   public String obtenerIDCliente(String iDRepositorio)
     {
	 @SuppressWarnings("resource")//para no cerrar Scanner  
     Scanner teclado=new Scanner(System.in);
     String cadenaEntrada="";//para capturar la entrada
     this.verCabecera(); 
     System.out.println("           Repositorio : "+iDRepositorio);
     System.out.println("           Listado de ficheros del cliente ");
     System.out.println("---------------------------------------------------------------------------");
     System.out.println("");
     System.out.print("Introduce el nombre de cliente con ficheros de este repositorio : ");
     cadenaEntrada=teclado.nextLine();    
     //si es solo intro cadena vacia 
     if (cadenaEntrada.length()==0)
       cadenaEntrada="";  
    return cadenaEntrada;    
    }//fin obtenerIDCliente
 
   //listados
   /********************************************************************************
    * <div> Este metodo visualiza el listado pasado por parametro</div>
    * 
    * @param iDRepositorio el correspondiente al repositorio en ejecucion.
    * @param listado cada registro en una linea
    *  
    *******************************************************************************/
   public void verListadoClientes(String iDRepositorio, String listado) 
     {
	 this.verCabecera();
	 System.out.println("        Listado de clientes asociados a repositorio "+iDRepositorio);
     System.out.println("---------------------------------------------------------------------------");
     System.out.println("  nombre de cliente   ");
     //datos   
     System.out.println(listado);
     //pulsa tecla
     pulsaIntro();     
     }//fin verListadoClientes 
    
   /********************************************************************************
    * <div> Este metodo visualiza el listado pasado por parametro</div>
    * 
    * @param iDRepositorio el correspondiente al repositorio en ejecucion.
    * @param iDCliente el correspondiente al listado
    * @param listado cada registro en una linea
    *  
    *******************************************************************************/
   public void verListadoFicherosCliente(String iDRepositorio, String iDCliente, String listado)
     {
	 this.verCabecera(); 
     System.out.println("                  Repositorio : "+iDRepositorio);
     System.out.println("           Listado de ficheros del cliente "+iDCliente);
     System.out.println("---------------------------------------------------------------------------");
     System.out.println("  nombre de fichero   ");
     //datos   
     System.out.println(listado);
     //pulsa tecla
     pulsaIntro();   
    }//fin verListadoFicheroscliente
  }//fin clase GUIRepositorioTexto