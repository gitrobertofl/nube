package servidor; 
import java.rmi.*;

/********************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS (19-20). BARQUITOS
 *
 * <div>Esta es la interface remota del servicio de datos. Implementa los 
 * m�todos necesarios para permitir la comunciacion de la base de datos
 * con el servidor remoto</div>
 *
 *
 * <div>Extiende Remote para exportacion RMI</div>
 
 * <div>Para evitar problemas de seguridad, incluye métodos para añadir y quitar
 * binds al registro RMI</div>
 * 
 * <div>Con licencia GPL v3</div>
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * 
 * @version 20191207
 * 
 ********************************************************************************/

public interface ServicioDatosInterface extends Remote
  {
  //metodos de insercion	  

  /******************************************************************************
   * <div>Metodo remoto que que recibe un idRepositorio nuevo y contrasena 
   * y lo anade a la base de datos</div>
   * 
   * @param repositorioID Cadena con el nombre de usuario
   * @param repositorioPwd Cadena con la contrasena
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean addRepositorio(String repositorioID, String repositorioPwd) 
         throws RemoteException;

  /******************************************************************************
   * <div>Metodo remoto que que recibe un IDCliente nombre nuevo y contrasebna 
   * y lo anade a la base de datos</div>
   * 
   * @param clienteID Cadena con el nombre de usuario
   * @param clientePwd Cadena con la contrasena
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * @throws RemoteException
   * 
   *****************************************************************************/  
   public Boolean addCliente(String clienteID, String clientePwd)
          throws RemoteException;  

  /*****************************************************************************
   * 
   * <div> Metodo remoto  que recibe el id de un cliente para asignarle un
   *  repositorio activo. Si no hay ninguno disponible, devuelve la cadena
   *  vacia</div>
   *
   *  @param iDCliente el nombre de cliente
   *  @return  cadena con el ID de repositorio asignado (vacia si no hay
   *  @throws RemoteException
   ****************************************************************************/
  public String asignarRepoClie(String iDCliente)       
         throws RemoteException;
   
  /******************************************************************************
   * <div> Anade el nombre de fichero del cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   *  @throws RemoteException
   * 
   *****************************************************************************/  
  public void subirFichero(String iDCliente, String iDRepo,String nombreFichero)
            throws RemoteException;   

  //metodos de baja

  /******************************************************************************
   * <div> borra el nombre de fichero del cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * <div> Si el fichero no se encuentra o no se puede borrar, se devuelve false</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nFichero cadena con el nombre del fichero (sin ruta)
   * @return true si el fichero se elimino, false en caso contrario
   *  @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean borrarFichero(String iDCliente, String iDRepo, String nFichero)
    		throws RemoteException;


  //modificaciones
  
  /******************************************************************************
   * <div>metodo remoto que que recibe un IDRepositorio y su contrasena 
   * y comprueba si esta en la base de datos</div>
   * 
   * @param clienteID Cadena con el nombre de usuario
   * @param clientePwd Cadena con la contrasena
   * @return devuelve true si el cliente esta acreditado y false si error
   *  @throws RemoteException
   * 
   *****************************************************************************/   
  public String activarRepositorioAcreditado(String clienteID, String clientePwd)
         throws RemoteException;  

  /******************************************************************************
   * <div>metodo remoto que que recibe un usuario nombre de usuario y contrase�a 
   * y comprueba si esta en la base de datos</div>
   * 
   * @param usr Cadena con el nombre de usuario
   * @param pwd Cadena con la contrase�a
   * @return devuelve true si el cliente esta acreditado y false si error
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public String activarClienteAcreditado(String usr, String pwd) throws RemoteException;
  
  /*****************************************************************************
   * <div> desactivarRepositorio es necesario antes de cerrar sesion 
   * para quitar el ID de sesion.</div>
   * 
   * <div> AHORA NO COMPRUEBA QUE HAYA CLIENTES ACTIVOS USANDOLO</DIV>
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * 
   * @throws RemoteException
   * 
   ****************************************************************************/
  public void desactivarRepositorio(String iDRepositorio)
         throws RemoteException;
   
  /******************************************************************************
   * <div> desactivarCliente es necesario antes de cerrar sesion para quitar el
   * ID de sesion.</div>
   * 
   * @param iDCliente requiere el ID del cliente a desactivar
   *
   * @throws RemoteException
   * 
   *****************************************************************************/
  public void desactivarCliente(String iDCliente)
         throws RemoteException;       
         
 /******************************************************************************
   * <div> marca el fichero pasado por parametros como compartido por su 
   * propietario</div>
   * <div> Si el fichero no se encuentra o no se puede borrar, se devuelve false
   * </div>
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepositorio cadena con el ID de repositorio
   * @param nFichero cadena con el nombre del fichero (sin ruta)
   * @return true si el fichero se marco, false en caso contrario
   *
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean compartirFichero(String iDCliente, String iDRepositorio, String nFichero)
    		throws RemoteException;

  //consultas
 
  /*********************************************************************************
   * <div>existeFichero nos dice si ya existe para el cliente y repositorio 
   *  correspondiente</div>
   * <div> Si el cliente es distitno de propietario, además debe estar marcado
   * como compartido</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDClienteProp cadena con ID del cliente propietario del fichero
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return TRUE si se encontro, FALSE en caso contrario.
   * 
   * @throws RemoteException
   ********************************************************************************/  
  public Boolean existeFichero(String iDCliente, String iDClienteProp, 
		  String iDRepo,String nombreFichero)
        throws RemoteException;

  /*****************************************************************************
   * <div> Comprueba si el repositorio pasado por parametro esta activo</div>
   * 
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * @return true si se desactivo, false en caso contrario
   * @throws RemoteException 
   ****************************************************************************/
  public Boolean esRepositorioActivo(String iDRepositorio)
		  throws RemoteException;

  
  /************************************************************************************
   * <div> Obtenemos el ID del repositorio asignado a un cliente</div>
   * 
   * @param iDCliente requiere una cadena con el ID del cliente
   * @return cadena con el ID del repositorio asignado, vacio si no lo tiene
   *
   * @throws RemoteException
   * 
   ***********************************************************************************/
   public String obtenerRepoClie(String iDCliente)  
		   throws RemoteException ; 
  
  //listados

  /******************************************************************************
   * <div>listadoRepositorios consulta de todos los repositorios registrados</div>
   *
   *  @return cadena con todos los repositorios e IDsesion separados por salto 
   *  			de linea
   *  
   *  @throws RemoteException
   *  
   *****************************************************************************/
  public String getListadoRepositorios() throws RemoteException;

  /******************************************************************************
   * <div> listadoClientes consulta todos los clientes registrados</div>
   * 
   *  @return cadena con todos los repositorios e IDsesion separados por salto 
   *  			de linea
   *  
   *  @throws RemoteException
   *  
   *****************************************************************************/
  public String getListadoClientes() throws RemoteException;
         
  /******************************************************************************
   * <div>listadoRepoclie consulta todos los repositorios con sus clientes 
   *  asociados</div>
   * 
   * @return cadena con los ID de repositorio seguido de ID de cliente
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/
  public String getListadoRepoClie() throws RemoteException;

  /******************************************************************************
   * <div> ListadoClientesRepo consulta los clientes de un repositorio concreto</div>
   * 
   * @param iDRepositorio requiere el ID de un repositorio
   * @return cadena con los nombres de cliente asociados al repositorio
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/   
  public String listadoClientesRepo(String iDRepositorio)throws RemoteException;
    
  /******************************************************************************
   * <div>listadoFicheroCliente consulta los ficheros de un cliente en un
   * repositorio</div>
   * 
   * @param iDCliente requiere el ID de un cliente (no es requerido que esta asociado)
   * @return cadena con los nombres de los ficheros almacenados(sin ruta)
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public String getListadoFicherosCliente(String iDCliente) throws RemoteException;


			
  }//fin interfaz remota de base de datos
    
