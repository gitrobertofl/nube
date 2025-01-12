package servidor; 
 

import java.rmi.*;
import java.rmi.server.*;

/*INTERFAZ REMOT RECUERDA:CUANDO ACABES PASAR UNA COPIA DEL OTRO PAQUETE*/
import usuario.ServicioSrOperadorInterface;


/* FIN INTERFACE REMOTA*/


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
public class ServicioGestorImpl extends UnicastRemoteObject
                            implements ServicioGestorInterface 
  {
  /*==============================================================================
   * Atributos de la clase
   ===============================================================================*/ 
  private static final long serialVersionUID = 123456789L;
  private ServicioDatosInterface BDRemota;
  
  /*===============================================================================
   * Constructores de la clase
   * ==============================================================================*/  
      
  /********************************************************************************
   * Constructor por defecto. NO hace nada
   * @throws RemoteException
   * 
   *******************************************************************************/
  public ServicioGestorImpl()throws RemoteException
    {
    super();
    }//fin constructor ()

  
  /********************************************************************************
   * Constructor que recibe como parametro una referencia valida del servicio de 
   * base de datos remoto, asignandola al atributo base de datos de esta clase.
   * 
   * @param BD servicio de datos
   * @throws RemoteException
   * 
   *******************************************************************************/  
  public ServicioGestorImpl(ServicioDatosInterface BD)throws RemoteException
    {
    super();
    this.BDRemota=BD;
    }//fin constructor(bd)

    
  /*================================================================================
     Metodos publicos 
   ================================================================================*/  
  
   //metodos de registro/desregistro de servicios

  /********************************************************************************
   * <div>Este metodo registra los servicios del repositorio (servidor y cliente).
   * Para ello recibe la referencia a los objetos remotos de los dos servicios
   * junto con el nombre de repositorio. Como el servicio esta en el mismo host
   * que el registro, llama al método correspondiente de MiRegistroRMI</div>
   * 
   * @param servicioSrOperador,servicioClOperador,iDRepositorio
   * @throws RemoteException
   * @throws AlreadyBoundException
   * 
   *******************************************************************************/   
  public void registraServiciosRepositorio(String iDRepositorio,
                                Remote servicioSrOperador,
                                Remote servicioClOperador)
             throws RemoteException,AlreadyBoundException
     {
	 MiRegistroRMI.registrarObjetoRemoto
					(NubeRMI.getBindRepoSr()+iDRepositorio,servicioSrOperador);
	 MiRegistroRMI.registrarObjetoRemoto
					(NubeRMI.getBindRepoCl()+iDRepositorio,servicioClOperador);
	 }//fin registra serviciosRepositorio
     
  /********************************************************************************
   * <div>Este metodo registra el servicio de Cliente.
   * Para ello recibe la referencia al objeto remoto del servicio
   * junto con el nombre de cliente. Como el servicio esta en el mismo host
   * que el registro, llama al método correspondiente de MiRegistroRMI</div>
   * 
   * @param iDCliente el nombre del cliente que registra
   * @param servicioDiscoCliente el servicio que se registra
   * 
   * @throws RemoteException
   * @throws AlreadyBoundException
   * 
   *******************************************************************************/   
  public void registraServicioCliente(String iDCliente,
                                        Remote servicioDiscoCliente)
              throws RemoteException,AlreadyBoundException
     {
	 MiRegistroRMI.registrarObjetoRemoto
					(NubeRMI.getBindDiscoCliente()+iDCliente,servicioDiscoCliente); 
     }//fin registraServicioCliente         

  /********************************************************************************
   * <div>Una vez que el repositorio deja de usarse (se cierra su sesion, hay que 
   *  desregistrar los servicios que este prestaba. Para ello solo necesita el 
   *  nombre del repositorio </div>
   * 
   * @param iDRepositorio el nombre del repo que se quita
   * @throws RemoteException
   * @throws NotBoundException
   * 
   *******************************************************************************/   
  public void desRegistrarServiciosRepositorio(String iDRepositorio)           
             throws RemoteException,NotBoundException
    {
    MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindRepoSr()+iDRepositorio);
    MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindRepoCl()+iDRepositorio);
    }//fin desregistrar servicios repositorio          
             
  /********************************************************************************
   * <div>Una vez que el cliente deja de usarse (se cierra su sesion, hay que 
   *  desregistrar el servicio que prestaba. Para ello solo necesita el 
   *  nombre del cliente </div>
   * 
   * @param iDCliente nombre del cliente que se quita
   * 
   * @throws RemoteException
   * @throws NotBoundException
   * 
   *******************************************************************************/   
  public void desRegistrarServicioCliente(String iDCliente)
             throws RemoteException,NotBoundException
    {
    MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindDiscoCliente()+iDCliente);
    }//fin desregistro servicio cliente        
     
  //metodos que hablan con repositorio para procesar ficheros	 
	 
  /****************************************************************************
   * Obtiene el ID del repositorio del cliente del parametro, si lo tienen
   * 
   * @param iDCliente ID del ciente
   * @return cadena con el ID del repositorio asignado. Si no lo tiene recibe
   *  la cadena vacia "".
   * @throws RemoteException
   * 
   ***************************************************************************/  
  public String repoAsignadoCliente(String iDCliente)
         throws RemoteException
    {
    return BDRemota.obtenerRepoClie(iDCliente);
    }//fin repoAsignadoCliente

  /****************************************************************************
   * <div>Le pide al repositorio IDRepoAsignado, a traves
   * del servicio SR del mismo, que le cree una carpeta al cliente si no la 
   * tenia ya creada</div>
   * 
   * @param iDCliente nombre del cliente
   * @param iDRepoAsignado nombre del repo asignado
   * @return true si se creo la carpeta y false en caso contrario.
   * @throws RemoteException
   * @throws NotBoundException
   * 
   ***************************************************************************/  
  public Boolean  carpetaClienteEnRepo(String iDCliente, String iDRepoAsignado)
		  throws RemoteException, NotBoundException
    {
    Boolean resultado=false;    
    resultado=((ServicioSrOperadorInterface)
                  MiRegistroRMI.getRefRegistroRMI().
                    lookup(NubeRMI.getBindRepoSr()+iDRepoAsignado))
					.carpetaParaCliente(iDCliente);
    return resultado;
    }//fin carpetaClienteEnRepo  

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
	return BDRemota.esRepositorioActivo(iDRepositorio);  
    }//fin esRepositorioActivo   
   
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
   * @throws RemoteException
  
   ********************************************************************************/  
  public Boolean existeFichero(String iDCliente, String iDClienteProp,
		  						String iDRepo,String nombreFichero)
        throws RemoteException
    {
    return BDRemota.existeFichero(iDCliente,iDClienteProp,iDRepo,nombreFichero);
    }// fin public Boolean existeFichero  

  /******************************************************************************
   * <div> Le pide al servicio de datos que anada el nombre de fichero del 
   * cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se subio con exito, false en caso contrario
   * @throws RemoteException
   * 
   * 
   *****************************************************************************/  
  public Boolean subirFichero(String iDCliente, String iDRepo,String nombreFichero)
         throws RemoteException
    {
    Boolean resultado=false;
    BDRemota.subirFichero(iDCliente,iDRepo,nombreFichero);    
    return resultado;
    }//fin subirfichero

  /******************************************************************************
   * <div> Le dice al repositorio iDRepo, a través de su servicio SR, que el
   * cliente iDCliente  quiere bajar el fichero nombreFichero.</div>
   * <div> comprueba previamente a traves del servicio de datos si el fichero
   * existe.</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDClienteProp cadena con el ID de cliente propietario
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se bajo con exito, false en caso contrario
   * @throws RemoteException
   * @throws NotBoundException
   * 
   * 
   *****************************************************************************/  
  public Boolean bajarFichero(String iDCliente, String iDClienteProp,String iDRepo,String nombreFichero) 
         throws RemoteException,NotBoundException
    {
    Boolean resultado=false;
    resultado=BDRemota.existeFichero(iDCliente,iDClienteProp,iDRepo,nombreFichero);    
    //si existe, notifica a repositorio que el cliente quiere su fichero    
    if (resultado)
      //le pasa a SrRepo los datos del cliente para bajar el fichero	
        resultado=((ServicioSrOperadorInterface)
                MiRegistroRMI.getRefRegistroRMI().lookup(NubeRMI.getBindRepoSr()+iDRepo))
				.bajarFichero(iDCliente,iDClienteProp,nombreFichero);
    return resultado;
    }//fin bajarFichero
   
  /******************************************************************************
   * <div> Le pide al servicio de datos que borre el nombre de fichero del 
   * cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se borro con exito, false en caso contrario
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean borrarFichero(String iDCliente, String iDRepo,String nombreFichero) 
	         throws RemoteException
	{
	return BDRemota.borrarFichero(iDCliente,iDRepo,nombreFichero) ;
	}//fin borrar fichero

  /******************************************************************************
   * <div> Le pide al servicio de datos que marque el nombre de fichero del 
   * cliente y repositorio indicado como compartido con el resto de clientes</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepoAsignado cadena con el ID de repositorio
   * @param nombreFichero cadena con el nombre del fichero (sin ruta)
   * @return true si se compartio con exito, false en caso contrario
   * @throws RemoteException
   * 
   *****************************************************************************/  
  public Boolean compartirFichero(String iDCliente, String iDRepoAsignado, String nombreFichero) 
	         throws RemoteException
	{
	return BDRemota.compartirFichero(iDCliente,iDRepoAsignado,nombreFichero);  
	}//fin borrar fichero

  //metodos para obtener listados para usuarios

  /******************************************************************************
   * <div> Le pide al servicio de datos un listadoClientes, que es una consulta
   * de todos los clientes registrados</div>
   * 
   * @return cadena con todos los clientes e IDsesion separados por salto de linea
   * @throws RemoteException
   *
   *****************************************************************************/
  public String getListadoClientesSistema() throws RemoteException
    {
    return BDRemota.getListadoClientes();    
    }//fin getListadoClientessSistema
    
  /******************************************************************************
   * <div> Le pide al servicio de datos un ListadoClientesRepo que es una consulta
   *  de los clientes de un repositorio concreto</div>
   * 
   * @param nickRepositorio requiere el ID de un repositorio
   * @return cadena con los ID de los clientes asociados al repositorio del parametro
   * @throws RemoteException

   *****************************************************************************/   
  public String getListadoClientesRepo(String nickRepositorio)
         throws RemoteException
    {
    return BDRemota.listadoClientesRepo(nickRepositorio);
    }// fin getlistadoclientesrepo
  
  /******************************************************************************
   * <div>Le pide al servicio de diatos un listadoFicheroCliente que una consulta
   *  de los ficheros a los que tiene acceso el cliente iDCLiente</div>
   * 
   * @param iDCliente requiere el ID de un cliente (no es requerido que esta asociado)
   * @return cadena con los nombres de los ficheros almacenados(sin ruta)
   * @throws RemoteException
   *****************************************************************************/  
  public String getListadoFicherosCliente(String iDCliente)       
         throws RemoteException
      {
      return BDRemota.getListadoFicherosCliente(iDCliente);  
      }//fin listadoFicheroscliente

  }//fin ServicioGestorImpl