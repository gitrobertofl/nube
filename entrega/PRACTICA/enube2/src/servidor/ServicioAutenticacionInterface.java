package servidor; 

import java.rmi.*;

/********************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21). 
 *
 * <div>Esta es la interface remota del servicio de autenticacion. Implementa los 
 * metodos necesarios para permitir la comunicacion del servidor con los 
 * Clientes remotos para acreditarlos.</div>
 *
 * <div>Este servicio será servidor de los usuarios (clientes y repositorios) y
 * cliente del servicio de datos del programa. Por ello, al inicializar este
 * servicio necesita que el de datos ya esté operativo, y así obtener 
 * una referencia al mismo.</div>
 *
 * <div>Extiende Remote para exportacion RMI</div>
 * 
 * <div>Tiene metodos para el alta de un Cliente en la base de datos, asi como
 * para autentificarlo en el inicio de sesion</div>
 *
 * @see MiRegistroRMI
 * @see NubeRMI
 *
 * 
 * <div>Con licencia GPL v3</div>
 * 
 * @author Roberto J. de la Fuente Lopez 
 * 
 * rfuente4@alumno.uned.es
 * robertofl@aconute.es
 * 
 * @version 20201103
 * 
 * 
 ********************************************************************************/
public interface ServicioAutenticacionInterface extends Remote
  {
  /**********************************************************************************
   * Metodo remoto que recibe como parametros el ID y la contrasena de cliente y pwd
   * junto con el tipo de usuario (cliente o repositorio)
   * y pide que se anada a la base de datos si se puede.
   * 
   * @param iDUsuario cadena con el id del usuario
   * @param pwd cadena con la contrasena del cliente
   * @param tipoUsuario una cadena que indica el tipo de usuario
   * 
   * @return Si ya existe o error, devuelve false,true en caso contrario. Por ejemplo
   * 		si el tipo de usuario no existe y ya existe el usuario
   *   
   * @throws RemoteException
   * 
   *********************************************************************************/
  public Boolean altaUsuario(String iDUsuario, String pwd, String tipoUsuario)
        throws RemoteException;
 
  /**********************************************************************************
   * Metodo remoto que recibe como parametros el ID y la contrasena de usuario
   * asi como su tipo y pide a la base de datos si existe con estos datos.
   * 
   * @param iDUsuario cadena con el id del cliente
   * @param pwd cadena con la contrasena del cliente
   * @param tipoUsuario cadena con el tipo de usuario
   * 
   * @return Si no existe ID o error en contrasena o tipo , devuelve ""
   *            ID de sesion (cadena aleatoria) en caso contrario
   *
   * @throws RemoteException
   * 
   *********************************************************************************/
 public String autenticarUsuario(String iDUsuario, String pwd,String tipoUsuario) 
                throws RemoteException ;  
  
  /**********************************************************************************
   * para cerrar sesion hay que quitar el id de sesion 
   *
   * @param iDUsuario cadena con el id del cliente
   * @param tipoUsuario cadena con el tipo de usuario
   * 
   * @throws RemoteException
   * 
   *********************************************************************************/
  public void desactivarUsuario(String iDUsuario, String tipoUsuario)
         throws RemoteException ;
      
  }//fin interface ServicioAutenticacionInterface
