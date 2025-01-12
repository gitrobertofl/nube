 package servidor; 
import java.rmi.*;
import java.rmi.server.*;

/********************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21)
 *
 * <div>Esta clase implementa la interface remota ServicioDatosInterface.
 * Implementa los metodos necesarios para permitir la comunicacion de la base 
 * de datos con el servidor remoto</div>
 *
 * <div>En este clase tendremos una referencia a la base de datos</div>
 *
 * <div>Extiende Remote para exportacion RMI</div>
 
 * <div>Para evitar problemas de seguridad, incluye metodos para anadir y quitar
 * binds al registro RMI</div>
 * 
 * <div>Con licencia GPL v3</div>
 * 
* @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * 
 * @version 20201116
 * 
 ********************************************************************************/

public class ServicioDatosImpl extends UnicastRemoteObject
                               implements ServicioDatosInterface
            
  {
  /*==============================================================================
   * Atributos de la clase
   ===============================================================================*/ 
	private static final long serialVersionUID = 123456789L;  
   //eliminar al final
    Datos BD;
   
    
  /*===============================================================================
   * Constructores de la clase
   * ==============================================================================*/  
  /**********************************************************************************
   * 
   * <div>Constructor del servicio de datos. 
   * Por defecto construye una estructura de datos vacia
   * </div>
   * 
   * @throws RemoteException
   *  
   *********************************************************************************/  
  public ServicioDatosImpl() throws RemoteException
    {
    super(); //constructor UnicastRemoteObject
    
    //crea una referencia a la base de datos vacia
    this.BD=new Datos();
    //lo hacen las asignaciones por defecto    
    }//fin constructor implementacion
  
  /**********************************************************************************
   * 
   *  <div>Constructor del servicio de datos. Simplemente crea una referencia a la base
   * de datos en uso y una referencia al registro RMI</div>
   * 
   * @param datos una referencia a un objeto de clase Datos
   * 
   * @throws RemoteException
   *  
   *********************************************************************************/  
  public ServicioDatosImpl(Datos datos) throws RemoteException
    {
    super();//constructor UnicastRemoteObject
    this.BD= datos; //obtenemos la referencia a una base de datos
    
    }//fin constructor implementacion
  /*================================================================================
     Metodos publicos 
   ================================================================================*/  
  //metodos get no hay
  //métodos set no hay
  
  //métodos insercion registros
    
  /******************************************************************************
   * <div>Metodo remoto que que recibe un idRepositorio nuevo y contrasena 
   * y lo anade a la base de datos</div>
   * 
   * @param repositorioID Cadena con el nombre de usuario
   * @param repositorioPwd Cadena con la contrasena
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * 
   * @throws RemoteException
   *  
   *****************************************************************************/  
  public Boolean addRepositorio(String repositorioID, String repositorioPwd) 
         throws RemoteException
    {
    return BD.insertaRepositorio(repositorioID,repositorioPwd); 
    }//fin addRepostiorio
	
  /******************************************************************************
   * <div>Metodo remoto que que recibe un idCliente nuevo y contrasena 
   * y lo anade a la base de datos</div>
   * 
   * @param clienteID Cadena con el nombre de usuario
   * @param clientePwd Cadena con la contraseña
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * 
   * @throws RemoteException 
   * 
   *****************************************************************************/  
  public Boolean addCliente(String clienteID, String clientePwd) 
         throws RemoteException
    {
    return BD.insertaCliente(clienteID,clientePwd); 
    }//fin addCliente
 
  /*****************************************************************************
   * 
   * <div> Metodo remoto  que recibe el id de un cliente para asignarle un
   *  repositorio activo. Si no hay ninguno disponible, devuelve la cadena
   *  vacia</div>
   *
   *  @param iDCliente el nombre de cliente
   *  @return  cadena con el ID de repositorio asignado (vacia si no hay
   *  
   *  @throws RemoteException
   *  
   ****************************************************************************/
  public String asignarRepoClie(String iDCliente)       
         throws RemoteException
    {
    //devuelve el repositorio asignado
    return BD.asignarRepoClie(iDCliente);
    }//fin asigna repoClie

  /******************************************************************************
   * <div> Anade el nombre de fichero del cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public void subirFichero(String iDCliente, String iDRepo,String nombreFichero)
            throws RemoteException
    {
    BD.altaFichero(iDCliente,iDRepo,nombreFichero);
    }//fin subirFichero

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
   * 
   * @throws RemoteException   
   * 
   *****************************************************************************/  
  public Boolean borrarFichero(String iDCliente, String iDRepo, String nFichero)
		  throws RemoteException
    {
	return BD.borrarFichero(iDCliente,iDRepo,nFichero);  
	}//fin borrar fichero
  
  //modificaciones

  /******************************************************************************
   * <div>metodo remoto que que recibe un IDRepositorio y su contrasena 
   * y comprueba si esta en la base de datos</div>
   * 
   * @param repositorioID Cadena con el ID
   * @param repositorioPwd Cadena con la contrasena
   * @return String con IDSesion o cadena vacia en caso de error.
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/   
  public String activarRepositorioAcreditado(String repositorioID, String repositorioPwd)
         throws RemoteException
    {
    return BD.activarRepositorioAcreditado(repositorioID,repositorioPwd);   
    }//fin activarRepositorioAcreditado  
  
  /******************************************************************************
   * <div>metodo remoto que que recibe un ID de Cliente y su contrasena 
   * y comprueba si esta en la base de datos</div>
   * 
   * @param clienteID Cadena con el nombre de usuario
   * @param clientePwd Cadena con la contrasena
   * @return String con IDSesion o cadena vacia en caso de error.
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/   
  public String activarClienteAcreditado(String clienteID, String clientePwd)
         throws RemoteException
    {
    return BD.activarClienteAcreditado(clienteID,clientePwd);    
    }//fin activarClienteAcreditado

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
         throws RemoteException 
    {
    BD.desactivarRepositorio(iDRepositorio);        
    }//fin desactivarRepositorio
  
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
         throws RemoteException 
    {
    BD.desactivarCliente(iDCliente);         
    }//fin desactivarRepositorio

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
		  throws RemoteException
    {
	return BD.compartirFichero(iDCliente,iDRepositorio,nFichero);  
	}//fin compartir fichero
   
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
        throws RemoteException
    {
    return BD.existeFichero(iDCliente,iDClienteProp, iDRepo,nombreFichero);
    }// fin public Boolean existeFichero

  /*****************************************************************************
   * <div> Comprueba si el repositorio pasado por parametro esta activo</div>
   * 
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * @return true si se desactivo, false en caso contrario
   * @throws RemoteException 
   ****************************************************************************/
  public  Boolean esRepositorioActivo(String iDRepositorio)
		  throws RemoteException
    {
	return BD.esRepositorioActivo(iDRepositorio);  
    }//fin esRepositorioActivo   
 
  
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
		   throws RemoteException
     {
     return BD.obtenerRepoClie(iDCliente);
     }// fin public Boolean existeFichero
  //listados

  /******************************************************************************
   * <div>listadoRepositorios consulta de todos los repositorios registrados</div>
   *
   *  @return cadena con todos los repositorios e IDsesion separados por salto 
   *    de linea
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/
  public String getListadoRepositorios() throws RemoteException
    {
    return BD.listadoRepositorios();
    
    }//fin get ListadoRepositorios
  
  /******************************************************************************
   * <div> listadoClientes consulta todos los clientes registrados</div>
   * 
   * @return cadena con todos los clientes e IDsesion separados por salto de linea
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/
  public String getListadoClientes() throws RemoteException
    {
    return BD.listadoClientes();
    }//fin get ListadoClientes
  
  /******************************************************************************
   * <div>listadoRepoclie consulta todos los repositorios con sus clientes 
   *  asociados</div>
   * 
   * @return cadena con los ID de repositorio seguido de ID de cliente
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/
  public String getListadoRepoClie() throws RemoteException
    {
    return BD.listadoRepoClie();
    }//fin get ListadoClientes   

  /******************************************************************************
   * <div> ListadoClientesRepo consulta los clientes de un repositorio concreto</div>
   * 
   * @param iDRepositorio requiere el ID de un repositorio
   * @return cadena con los ID de los clientes asociados al repositorio del par�metro
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/   
  public String listadoClientesRepo(String iDRepositorio)throws RemoteException  
    {
    return BD.listadoClientesRepo(iDRepositorio);
    }  

  /******************************************************************************
   * <div>listadoFicheroCliente consulta los ficheros de un cliente en un repositorio</div>
   * 
   * @param iDCliente requiere el ID de un cliente (no es requerido que esta asociado)
   * @return cadena con los nombres de los ficheros almacenados(sin ruta)
   * 
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public String getListadoFicherosCliente(String iDCliente)
        throws RemoteException
    {
    return BD.listadoFicherosCliente(iDCliente); 
    }  
   

  }//fin servicioDatosImpl