package servidor; 

import java.rmi.*;
import java.rmi.server.*;


/******************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21). 
 *
 * <div>Esta clase es una implementacion del servicio de autenticacion del 
 * servidor. Implementa los metodos necesarios para permitir la comunicacion 
 * del servidor con los clientes remotos para acreditarlos. Para ello implementa
 * el interface ServicioAutenticacionInterface</div>
 *
 *
 * <div>Este servicio será servidor de los usuarios (clientes y repositorios) y
 * cliente del servicio de datos del programa. Por ello, al inicializar este
 * servicio necesita que el de datos ya esté operativo, y así obtener 
 * una referencia al mismo</div>
 * 
 * <div>Extiende Remote para exportacion RMI</div>
 
 * <div>En este clase tendremos una referencia al servicio de datos</div>
 *
 * 
 * <div>Tiene metodos para el alta de un usuarioen la base de datos, asi como
 * para autentificarlo en el inicio de sesion. Al ser este servicio comun a 
 * Clientes y Repositorio, es necesario identificar en todo momento que tipo
 * de usuario se trata (Con una cadena).</div>
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
 * @version 20201106
 * 
 ********************************************************************************/
public class ServicioAutenticacionImpl extends UnicastRemoteObject
                            implements ServicioAutenticacionInterface
  {
  /*==============================================================================
   * Atributos de la clase
   =============================================================================*/ 
  private static final long serialVersionUID = 123456789L;
  private ServicioDatosInterface servicioDatos;
  
  private final String TIPO_CLIENTE="cliente";
  private final String TIPO_REPOSITORIO="repositorio";

  /*=============================================================================
   * Constructores de la clase
   * ===========================================================================*/
   
  /********************************************************************************
   * Constructor por defecto. obtiene referencias a los servicios remotos que precisa:
   * servicio de datos.
   *
   * @throws RemoteException
   * 
   * 
   *******************************************************************************/
  public ServicioAutenticacionImpl()throws RemoteException
    {
    super();
    
    }
  /********************************************************************************
   * Constructor que recibe como parametro una referencia valida del servicio de 
   * base de datos remoto, asignandolo al atributo base de datos de esta clase.
   * 
   * @param datos es el servicio de datos 
   *
   * @throws RemoteException
   * 
   * 
   *******************************************************************************/
  public ServicioAutenticacionImpl(ServicioDatosInterface datos)throws RemoteException
    {
    super();  
    servicioDatos=datos;  
    }
  
  /*================================================================================
        Metodos publicos 
   ================================================================================*/
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
        throws RemoteException
    {
	 Boolean error=false;
	 switch( tipoUsuario)
	   {
	   case TIPO_CLIENTE :error=servicioDatos.addCliente(iDUsuario,pwd);
	 	   				  break;
	   case TIPO_REPOSITORIO :error=servicioDatos.addRepositorio(iDUsuario,pwd);
	 						  break;
	   default: error=true;
	 		
	   }//FIN SWITCH
	return error;  
	}//fin altaUsuario
  
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
               throws RemoteException 
  {
	String iDSesion="";
	 switch( tipoUsuario)
	   {
	   case TIPO_CLIENTE :iDSesion=servicioDatos.activarClienteAcreditado(iDUsuario,pwd);
	 	   				  break;
	   case TIPO_REPOSITORIO :iDSesion=servicioDatos.activarRepositorioAcreditado(iDUsuario,pwd);
	 						  break;
	   default: iDSesion="";
	 		
	   }//FIN SWITCH
	return iDSesion;  
  
  }// autenticarUsuario

 /**********************************************************************************
  * para cerrar sesion hay que quitar el id de sesion 
  *
  * @param iDUsuario cadena con el id del cliente
  * @param tipoUsuario con el tipo de usuario
  *   
  * @throws RemoteException
  * 
  *********************************************************************************/
 public void desactivarUsuario(String iDUsuario, String tipoUsuario)
        throws RemoteException 
   {
   switch( tipoUsuario)
	 {
	 case TIPO_CLIENTE :servicioDatos.desactivarCliente(iDUsuario);
	 	   				  break;
	 case TIPO_REPOSITORIO :servicioDatos.desactivarRepositorio(iDUsuario);  
	 						  break;
	   default: ; 
	 }//fin switch
	 
   }//fin desactivarUsuario
    
  }//fin clase servicioAutenticacionImpl
