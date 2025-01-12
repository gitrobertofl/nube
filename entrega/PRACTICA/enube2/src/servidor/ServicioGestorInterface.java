package servidor; 
import java.rmi.*;
//import java.util.Vector;

/*INTERFAZ REMOT RECUERDA:CUANDO ACABES PASAR UNA COPIA DEL OTRO PAQUETE*/

/********************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS 
 *
 * <div>Esta es la interface remota del servicio gestor . Implementa los 
 * metodos necesarios para permitir la comunicacion del servidor con los 
 * Clientes remotos</div>
 *
 *
 * <div>Extiende Remote para exportacion RMI</div>
 * 
 * <div>En este clase tendremos una referencia al servicio de datos</div>
 
 * 
 * <div>Con licencia GPL v3</div>
 *
 *  @see ServicioDatosInterface
 *  @see ServicioAutenticacionInterface
 * 
 * @author Roberto J. de la Fuente Lopez 
 * 
 * rfuente4@alumno.uned.es
 * robertofl@aconute.es
 * 
 * @version 20201205
 * 
 ********************************************************************************/
public interface ServicioGestorInterface extends Remote
  {
   //metodos de registro/desregistro de servicios

  /********************************************************************************
   * <div>Este metodo registra los servicios del repositorio (servidor y cliente).
   * Para ello recibe la referencia a los objetos remotos de los dos servicios
   * junto con el nombre de repositorio. Como el servicio esta en el mismo host
   * que el registro, llama al método correspondiente de MiRegistroRMI</div>
   * 
   * @param iDRepositorio cadena con el nombre de repositorio
   * @param servicioSrOperador referencia al objeto remoto Sr
   * @param servicioClOperador referencia al objeto remot Cl   * 
   * 
   * @throws RemoteException
   * @throws AlreadyBoundException
   * 
   *******************************************************************************/   
  public void registraServiciosRepositorio(String iDRepositorio,
                                Remote servicioSrOperador,
                                Remote servicioClOperador)
             throws RemoteException,AlreadyBoundException;

  /********************************************************************************
   * <div>Este metodo registra el servicio de Cliente.
   * Para ello recibe la referencia al objeto remoto del servicio
   * junto con el nombre de cliente. Como el servicio esta en el mismo host
   * que el registro, llama al método correspondiente de MiRegistroRMI</div>
   * 
   * @param iDCliente nombre del cliente que registra
   * @param servicioDiscoCliente el servicio a registrar
   * @throws RemoteException
   * @throws AlreadyBoundException
   * 
   *******************************************************************************/   
  public void registraServicioCliente(String iDCliente,
                                Remote servicioDiscoCliente)
             throws RemoteException,AlreadyBoundException;    

  /********************************************************************************
   * <div>Una vez que el repositorio deja de usarse (se cierra su sesion, hay que 
   *  desregistrar los servicios que este prestaba. Para ello solo necesita el 
   *  nombre del repositorio </div>
   * 
   * @param iDRepositorio nombre del repositorio
   * @throws RemoteException
   * @throws NotBoundException
   * 
   *******************************************************************************/   
  public void desRegistrarServiciosRepositorio(String iDRepositorio)           
             throws RemoteException,NotBoundException;
             
  /********************************************************************************
   * <div>Una vez que el cliente deja de usarse (se cierra su sesion, hay que 
   *  desregistrar el servicio que prestaba. Para ello solo necesita el 
   *  nombre del cliente </div>
   * 
   * @param iDCliente nombre del cliente
   * @throws RemoteException
   * @throws NotBoundException
   * 
   *******************************************************************************/   
  public void desRegistrarServicioCliente(String iDCliente)
             throws RemoteException,NotBoundException;
      
  //metodos que hablan con repositorio para procesar ficheros	 
	 
  /****************************************************************************
   * Obtiene el ID del repositorio del cliente del parametro, si lo tienen
   * 
   * @param IDCliente ID del ciente
   * @return cadena con el ID del repositorio asignado. Si no lo tiene recibe
   *  la cadena vacia "".
   * @throws RemoteException
   ***************************************************************************/  
  public String repoAsignadoCliente(String IDCliente)
         throws RemoteException;    
         
  /****************************************************************************
   * <div>Le pide al repositorio IDRepoAsignado, a traves
   * del servicio SR del mismo, que le cree una carpeta al cliente si no la 
   * tenia ya creada</div>
   * 
   * @param iDCliente nombre del cliente
   * @param iDRepoAsignado nombre de repositorio
   * @return true si se creo la carpeta y false en caso contrario.
   * @throws RemoteException
   * @throws NotBoundException
   * 
   ***************************************************************************/  
  public Boolean  carpetaClienteEnRepo(String iDCliente, String iDRepoAsignado)
         throws RemoteException,NotBoundException    ;

  /*****************************************************************************
   * <div> Comprueba si el repositorio pasado por parametro esta activo</div>
   * 
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * @return true si se desactivo, false en caso contrario
   * @throws RemoteException 
   ****************************************************************************/
  public  Boolean esRepositorioActivo(String iDRepositorio)
		  throws RemoteException;

  
  
  /*********************************************************************************
   * <div>existeFichero le pide al servicio de datos si ya existe el fichero 
   * para el cliente y repositorio correspondiente</div>
   * <div> Si el cliente es distitno de propietario, además debe estar marcado
   * como compartido</div>
   * 
   * @param iDCliente requiere cadena con el ID del cliente que ejecuta la consulta
   * @param iDClienteProp cadena con el ID del cliente propietario
   * @param iDRepo requiere cadena con el ID del repositorio
   * @param nombreFichero requiere cadena con el nombre del fichero (sin ruta)
   * @return TRUE si se encontro, FALSE en caso contrario.
   ********************************************************************************/  
  public Boolean existeFichero(String iDCliente, String iDClienteProp,
		  						String iDRepo,String nombreFichero)
         throws RemoteException;       

  /******************************************************************************
   * <div> Le pide al servicio de datos que anada el nombre de fichero del 
   * cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se sube correctamente, false en caso contraro
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean subirFichero(String iDCliente, String iDRepo,String nombreFichero) 
         throws RemoteException;
  
         
  /******************************************************************************
   * <div> Le dice al repositorio iDRepo, a través de su servicio SR, que el
   * cliente iDCliente  quiere bajar el fichero nombreFichero.</div>
   * <div> comprueba previamente a traves del servicio de datos si el fichero
   * existe.</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDClienteProp cadena con el ID de repositorio
   * @param iDRepo cadena con el nombre del fichero (sin ruta)
   * @param nombreFichero el nombre del que se baja
   * @return true si se sube correctamente, false en caso contraro
   * @throws RemoteException
   * @throws NotBoundException
   * 
   * 
   *****************************************************************************/  
  public Boolean bajarFichero(String iDCliente, String iDClienteProp, String iDRepo,String nombreFichero) 
         throws RemoteException,NotBoundException; 
         
  /******************************************************************************
   * <div> Le pide al servicio de datos que borre el nombre de fichero del 
   * cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se sube correctamente, false en caso contraro
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean borrarFichero(String iDCliente, String iDRepo,String nombreFichero) 
	         throws RemoteException;
  
  /******************************************************************************
   * <div> Le pide al servicio de datos que marque el nombre de fichero del 
   * cliente y repositorio indicado como compartido con el resto de clientes</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepositorio cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se sube correctamente, false en caso contraro
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean compartirFichero(String iDCliente,String iDRepositorio,String nombreFichero)
            throws RemoteException;
  
  
  //metodos para obtener listados para usuarios

  /******************************************************************************
   * <div> Le pide al servicio de datos un listadoClientes, que es una consulta
   * de todos los clientes registrados</div>
   * 
   * @return cadena con todos los clientes e IDsesion separados por salto de linea
   * @throws RemoteException
   * 
   *****************************************************************************/
  public String getListadoClientesSistema() throws RemoteException;       
         
  /******************************************************************************
   * <div> Le pide al servicio de datos un ListadoClientesRepo que es una consulta
   *  de los clientes de un repositorio concreto</div>
   * 
   * @param nickRepositorio requiere el ID de un repositorio
   * @return cadena con los ID de los clientes asociados al repositorio del parametro
   * @throws RemoteException

   *****************************************************************************/   
   public String getListadoClientesRepo(String nickRepositorio)
         throws RemoteException;
  
  /******************************************************************************
   * <div>Le pide al servicio de datos un listadoFicheroCliente que una consulta
   *  de los ficheros a los que tiene acceso el cliente iDCLiente</div>
   * 
   * @param iDCliente requiere el ID de un cliente (no es requerido que esta asociado)
   * @return cadena con los nombres de los ficheros almacenados(sin ruta)
   * @throws RemoteException

   *****************************************************************************/  
  public String getListadoFicherosCliente(String iDCliente)       
         throws RemoteException;
      
      
  }//fin interface ServicioGestorInterface