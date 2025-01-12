package usuario; 

import java.util.Scanner;
/********************************************************************************
 * <div>Esta clase va a implementar el interface del cliente en modo 
 * consola.</div> 
 * <div>Contiene los metodos para la visualizacion de cada menu</div>

 * Con licencia GPL v3
 *
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *********************************************************************************/
public class GUIClienteTexto extends GUIUsuarioTexto implements GUIClienteInterface
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
  public GUIClienteTexto() 
    {
    
    }

  /*********************************************************************************
   * Constructor que recibe el tipo de usuario que ejecuta para llamar al constructor
   * de su clase padre
   *
   * @param tipo cadena con el tipo de usuario
   *
   ********************************************************************************/  
  public GUIClienteTexto(String tipo)
    {
    super(tipo);
    }

  
  /*==========================================================================
   * Metodos publicos
   * ========================================================================*/    
  //menu    
    
  /********************************************************************************
   * <div>Implementacion del metodo abstracto de GuiUsuario</div>
   * <div>Se visualiza el menu especifico del cliente, una vez acreditado.</div>
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
    //límites de opcion
    char opcionInicial='1';
    char opcionFinal='7';
    this.verCabecera();   
    System.out.println("         "+tipoUsuario+" : "+iDUsuario);
    System.out.println();
    System.out.println(" 1 - Subir fichero");
    System.out.println(" 2 - Bajar fichero");
    System.out.println(" 3 - Borrar fichero");
    System.out.println(" 4.- Compartir fichero");
    System.out.println(" 5.- Listar ficheros");
    System.out.println(" 6.- Listar Clientes del sistema");
    System.out.println(" 7.- Salir");
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
    }//fin menuClienteAcreditado

  //captura de datos
  
  /********************************************************************************
   * <div> Este metodo captura si el fichero que se va a bajar es propio o no.</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @return un caracter S, s, N, n 
   *  
   *******************************************************************************/
  public Character esFicheroPropio(String iDCliente)
    {
    Character opcion='s';  
    @SuppressWarnings("resource")//para no cerrar Scanner  	
    Scanner teclado=new Scanner(System.in);
    String cadenaEntrada="";//para capturar la entrada
    limpiarPantalla();   
    System.out.println("        ALMCENAMIENTO EN LA NUBE");
    System.out.println("         Cliente : "+iDCliente);
    System.out.println();
    System.out.print("¿Es fichero propio (S/N)? ");
    cadenaEntrada=teclado.nextLine();
    if (!(cadenaEntrada.length()==0))
      opcion=cadenaEntrada.charAt(0);
   return opcion;  
   }//fin esFicheroPropio
  
  /********************************************************************************
   * <div> Este metodo captura el id del cliente propietario del fichero compartido
   * que se quiere bajaro</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @return cadena introducida (si intro, cadena vacia)
   *  
   *******************************************************************************/
  public String nombrePropietario(String iDCliente)
    {
    @SuppressWarnings("resource")//para no cerrar Scanner  	
    Scanner teclado=new Scanner(System.in);
    String cadenaEntrada="";//para capturar la entrada
    this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("     INTRODUCCION DE ID DE PROPIETARIO");
    System.out.println("");
    System.out.print("Introduce el nombre del propietario: ");
    cadenaEntrada=teclado.nextLine();
    if (cadenaEntrada.length()==0)
  	cadenaEntrada=""; 
    return cadenaEntrada;  
    }//fin nombrePropietario

  /********************************************************************************
   * <div> Este metodo captura el nombre del fichero que se quiere subir/bajar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @return cadena introducida (si intro, cadena vacia)
   *  
   *******************************************************************************/
  public String nombreFichero(String iDCliente)
    {
    @SuppressWarnings("resource")//para no cerrar Scanner  	
    Scanner teclado=new Scanner(System.in);
    String cadenaEntrada="";//para capturar la entrada
    this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("     INTRODUCCION DE NOMBRE DE FICHERO");
    System.out.println();
    System.out.print("Introduce el nombre del fichero : ");
    cadenaEntrada=teclado.nextLine();
    if (cadenaEntrada.length()==0)
      cadenaEntrada=""; 
    return cadenaEntrada;  
    }//fin nombreFichero
  
  /********************************************************************************
   * <div> pantalla de aviso para el repositorio no activo</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void avisoRepoNoActivo(String iDCliente)
    {
	this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("El repositorio asignado no esta activo.");  
    pulsaIntro(); 
    }//fin avisoRepoNoActivo
 
  /********************************************************************************
   * <div> pantalla de aviso de subida de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @param nombreFichero el del fichero bajado
   *  
   *******************************************************************************/
  public void avisoSubidaFichero(String iDCliente,String nombreFichero)
    {
    this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Se ha subido el fichero nombre :  "+nombreFichero);
    pulsaIntro(); 
      }//fin avisoSubidaFichero
  
  /********************************************************************************
   * <div> pantalla de aviso de bajada de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
    public void avisoBajadaFichero(String iDCliente, String iDClienteComp,String nombreFichero)
      {
      this.verCabecera(); 
   	  System.out.println("         Cliente : "+iDCliente);
      System.out.println("---------------------------------------------------------------------------");
      System.out.println();
      System.out.println("Se ha bajado un fichero fichero nombre "+nombreFichero+
    	       "del propietario "+iDClienteComp);
      pulsaIntro(); 
      }//fin avisoBajadaFichero
    
  /********************************************************************************
   * <div> pantalla de aviso de borrado de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void avisoFicheroBorrado(String iDCliente, String nombreFichero)
    {
    this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Se ha borrado el fichero fichero nombre "+nombreFichero);
    pulsaIntro(); 
    }//fin avisoBajadaFichero
    
    
  //errores
  /********************************************************************************
   * <div> pantalla de aviso de error en el nombre del fichero</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorNombreFichero(String iDCliente)
    {
	  this.verCabecera(); 
      System.out.println("         Cliente : "+iDCliente);
      System.out.println("---------------------------------------------------------------------------");
      System.out.println();
      System.out.println("Error en el nombre de fichero");  
      pulsaIntro();  
      }//fin errorNombreFichero
    
  /********************************************************************************
   * <div> pantalla de aviso de error en el nombre del fichero repetido</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorFicheroRepe (String iDCliente)
    {
	this.verCabecera(); 
    System.out.println("         Cliente : "+iDCliente);
    System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Error: el fichero ya existe");  
    pulsaIntro();  
    }//fin errorFicheroRepe

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no existe</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorFicheroNoExiste (String iDCliente)
    {
	this.verCabecera(); 
	System.out.println("         Cliente : "+iDCliente);
	System.out.println("---------------------------------------------------------------------------");
	System.out.println();
    System.out.println("Error: el fichero no existe");  
    pulsaIntro();  
    }//fin errorFicheroNoExiste

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede subir</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorSubidaFichero(String iDCliente)
    {
	this.verCabecera(); 
	System.out.println("         Cliente : "+iDCliente);
	System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Error: no se puede subir fichero");  
    pulsaIntro();  
    }//fin errorSubidaFichero

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede bajar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorBajadaFichero(String iDCliente)
    {
	this.verCabecera(); 
	System.out.println("         Cliente : "+iDCliente);
	System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Error: no se puede bajar fichero");  
    pulsaIntro();  
    }//fin errorBajadaFichero

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede borrar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorBorradoFichero(String iDCliente)
    {
	this.verCabecera(); 
	System.out.println("         Cliente : "+iDCliente);
	System.out.println("---------------------------------------------------------------------------");
    System.out.println();
    System.out.println("Error: no se puede borrar el fichero");  
    pulsaIntro();  
    }//fin errorBajadaFichero
  
   /********************************************************************************
   * <div> Este metodo visualiza el listado de clientes del sistema pasado
   *  por parametro</div>
   * 
   * @param listado cada registro en una linea
   *  
   *******************************************************************************/
  public void verListadoClientes(String listado)
    {
    this.verCabecera();
    System.out.println("       Listado de clientes del sistema");
    System.out.println("---------------------------------------------------------------------------");
    System.out.println("  nombre    ID sesion");
    //datos   
    System.out.println(listado);
    //pulsa tecla
    pulsaIntro();   
    }//fin verListadoClientes   
      
      
  /********************************************************************************
   * <div> Este metodo visualiza el listado pasado por parametro</div>
   * 
   * @param iDRepositorio el correspondiente al repositorio asignado al cliente.
   * @param iDCliente el correspondiente al listado
   * @param listado cada registro en una linea
   *  
   *******************************************************************************/
  public void verListadoFicherosCliente(String iDRepositorio, String iDCliente, String listado)
     {
	 this.verCabecera();
	 System.out.println("           Listado de ficheros del cliente "+iDCliente);
     System.out.println("---------------------------------------------------------------------------");
     System.out.println("  nombre de fichero   (c) compartido, (o) compartido por otros");
     //datos   
     System.out.println(listado);
     System.out.println("fin de listado");
     //pulsa tecla
     pulsaIntro();   
        
     }//fin verListadoFicheroscliente   
     
  }//fin clase GUIClienteTexto