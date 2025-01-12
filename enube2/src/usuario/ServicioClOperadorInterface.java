package usuario;
import java.rmi.*;

/********************************************************************************
 * <div>Servicio Cliente-Operador:</div>
 * <div>Este servicio se encarga de las operaciones de subida de ficheros al
 * repositorio y borrado de los mismos. El servicio Gestor del servidor responde
 *  a la peticion del cliente enviandole la URL de este servicio para que pueda
 *  completar su operacion. </div>
 * 
 * Con licencia GPL v3
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *******************************************************************************/
public interface ServicioClOperadorInterface extends Remote 
  {
  /*****************************************************************************
   * obtener el nombre del repositorio activo.
   * @return nombre del repositorio
   * @throws RemoteException
   *   
   *************************************************************************** */
  public String getNickRepositorio() throws RemoteException ;
  //set
 /*****************************************************************************
  * cambiar el nombre del repositorio activo.
  * @param nombre nombre nuevo para el repositorio
  * @throws RemoteException
  *   
  *************************************************************************** */
  public void setNicRepositorio(String nombre)throws RemoteException; 
  
  //servicios ficheros
  /*****************************************************************************
   * realiza la operacion de guardar el fichero pasado por paramentro en la 
   * carpeta para el cliente correspondiente de este repositorio
   * @param fichero objeto con el fichero a guardar
   * @return true si se subio con exito
   * @throws RemoteException
   *   
   *************************************************************************** */
  public Boolean subirFichero (Fichero fichero) throws RemoteException;
  
  /*****************************************************************************
   * realiza la operacion de borrar el fichero pasado por paramentro en la 
   * carpeta para el cliente correspondiente de este repositorio
   * @param iDCliente nombre del cliente propietario
   * @param fichero objeto con el fichero a guardar
   * @return true si se subio con exito
   * @throws RemoteException
   *   
   *************************************************************************** */
  public Boolean borrarFichero (String iDCliente, String fichero) throws RemoteException;
  
  }//fin interface ServicioClOperador
