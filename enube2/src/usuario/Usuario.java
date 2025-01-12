package usuario;


/*INTERFAZ REMOT RECUERDA:CUANDO ACABES PASAR UNA COPIA AL OTRO PAQUETE*/

import servidor.ServicioGestorInterface;
import servidor.ServicioAutenticacionInterface;

import servidor.MiRegistroRMI;//conocer el registro
import servidor.NubeRMI;//servicios nube en el registro

/*FIN INTERFAZ REMOTA*/

import java.rmi.*;
/******************************************************************************
 * 
 * *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21).
 *
 * <div>Esta es la clase que representa entidad Usuario, que es extendida
 * por una de las otras entidades: repositorio y cliente</DIV>
 * 
 * Con licencia GPL v3
 * @see MiRegistroRMI
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 * 
 ******************************************************************************/
public abstract class Usuario
  {
  //constantes de clase
  //Dirección y puerto del registro RMI
  //la IP del host con el servicio RMI   
  private static String iPHostRegistroRMI=NubeRMI.getIPServidorRMI();//"localhost";
  //EL PUERT DE ESCUCHA DEL REGISTRO RMI
  private static Integer puertoServidorRMI=NubeRMI.getPuertoServidorRMI();//1099;  

  //rutas relativas donde se ubican los ficheros
  protected static String FICHEROS_DE_REPOS="reposFile";	
  protected static String FICHEROS_DE_CLIENTES="clientesFiles";
  
  //cadenas que identifican el tipo de entidad
  protected final static String TIPO_CLIENTE="cliente";
  protected final static String TIPO_REPOSITORIO="repositorio";
  
  //variables de clase.
  //creamos los objetos proxy con acceso a servicios remotos de servidor
  protected  static ServicioGestorInterface gestor; //servicio gestor en servidor
  protected  static ServicioAutenticacionInterface autenticacion; //servicio autenticacion en ser
  
  //ATRIBUTOS DE INSTANCIA
  
  protected GUIUsuarioInterface gUI;
  protected String tipoUsuario="";
  
  //ID de Sesion
  private static String iDSesion="";
  
  //monitorizar la conexion con el servidor
  Boolean conectadoConSRV=false;
  
  /*****************************************************************************
   * constructor de la clase. crea un gui de usuario de uno u otro tipo,
   * (cliente o repositorio) segun el parametro
   *
   *  @param tipoUsr cadena con el tipo de usuario
   * 
   *****************************************************************************/
  public Usuario(String tipoUsr)
    {
	//** Configuracion del cliente
    //preparamos el GUI y marcamos el tipo de entidad 
	if (tipoUsr.equals(TIPO_CLIENTE))
	  {
	  tipoUsuario=TIPO_CLIENTE;
	  gUI = new GUIClienteTexto(tipoUsuario);
	  }
	 else
	  {	
	  tipoUsuario=TIPO_REPOSITORIO;	 
	  gUI = new GUIRepositorioTexto(tipoUsuario);
	  }//fin else
	//si tenemos servicios conectamos con RMI
	if (conexionRMI())
	  {
	  try
	    {
		//obtenemos los servicios del servidor	
		//inicializamos los objetos proxy con acceso a servicios remotos
		autenticacion=(ServicioAutenticacionInterface)MiRegistroRMI.getRefRegistroRMI().lookup(NubeRMI.getBindAutenticacion());
		gestor=(ServicioGestorInterface)MiRegistroRMI.getRefRegistroRMI().lookup(NubeRMI.getBindGestor());
		//no error de registro ni de proxy a Servidor
		conectadoConSRV=true;	
		}//fin try
		  catch (RemoteException re)
		   {
		  this.gUI.errorAccesoRemotoObjeto(re.toString());  
		   }//fin catch REmoteException
		  catch (NotBoundException be)
		   {
		   this.gUI.errorNoEncuentraObjRemoto(be.toString());  
		   }//fin catchnotboundException
		  catch (Exception e)
		   {
		   this.gUI.errorGenerico(e.toString());  
		   }//fin catch 
	      finally
	       {
	    	
	    	  
	    	  
	       }//fin finally
		  
		  
		  }	//fin si correcto
		 else
		  {
		  this.gUI.errorRegistroRMI();	
		  }	
	    //si hubo error, ya lo visualizó
	
	
	}//fin constructor Usuario (tipoUsuario)
 
  
  
  
  /*****************************************************************************
   *
   *  Método principal de ejecución main. Prepara el acceso a los servicios
   *  remotos de servidor.
   *  Para determinar el tipo de entidad, cliente o repositorio,
   *  necesita parámetro de entrada, instanciando la que corresponda.
   *  
   *
   *  @param args una cadena con el tipo de entidad
   *
  *************************************************************************** */
  public static void main (String args[])
    {
    
    
    //=============================================================================
    // ejecucion 
    //=============================================================================
    //carga del gestor de seguridad
    
    
    //System.setSecurityManager(new RMISecurityManager( ));
	
	//preparamos el usuario según el tipo
	Usuario usuario;
	//creamos el objeto usuario que corresponde según parametro
    if (args[0].equals(TIPO_CLIENTE))
	  usuario = new UsrCliente();
	 else
	  usuario = new UsrRepositorio();
	if (usuario.getConectadoConSRV())
	  //una vez montado el esquema remoto, iniciamos el menu principal
	  usuario.menuUsuario();    
	 else
      usuario.getGUI().finDePrograma();		 

    //termina todo  
 	System.exit(0); 
	}//fin del main   



  public Boolean getConectadoConSRV()
    {
    return conectadoConSRV;	  
    }

  public GUIUsuarioInterface getGUI()
    {
    return gUI;	
	}

  /*****************************************************************************
   *
   *  Este método se encarga de la visualizaición del menu inicial del usuario
   *  A partir de el se da de alta o autentifica un usuario (repositorio o cliente)
   *
   *************************************************************************** */
  public void menuUsuario()
	{
	final String ERROR_SESION_ABIERTA="La sesión ya estaba abierta";		
	final String ERROR_NO_REPO_DISPONIBLE="repo_no_disponible";	
	//opcion de menu
	Character opcion='0';
	//creamos una acreditacion de Cliente
	AcreditacionUsuario acreditacionUsuario;
	try
	  {
	  do
	    {
	    //capturamos la operacion a realizar desde el GUI    
	    opcion=gUI.menuInicio();
	    switch (opcion)
	      {
	      case '1'://ALTA de usuario
	               //obtenemos nick y contrasena por teclado  
	               acreditacionUsuario=gUI.login(opcion);
	               //tenemos los datos de acreditacion
	               //llama al servicio de autentificacion:alta de usuario según tipo
	               //informa si hubo error o no
	                    
	                     //hacer con captura de errores de java. es m�s elegante
	                    //entonces: 
	               // si true: alta correcta, si false: error
	               if (autenticacion.altaUsuario(acreditacionUsuario.getNombre(),
	                                             acreditacionUsuario.getPassword(),
	                                             tipoUsuario))
	                      {
	                      gUI.altaUsuarioCorrecta();    
	                      }
	                     else
	                      {
	                      //excepcion
	                      gUI.errorAcreditacion();  
	                      }
	                    //como permite mas altas, no se registra el obj remoto.
	                    //para dar de alta no hace falta registrar
	                    //vuelve al menu de inicio (no entra directamente)
	                    opcion='0';
	                    break;
	      case '2'://inicio sesion
	               //obtenemos nick y contrasena por teclado  
	               acreditacionUsuario=gUI.login(opcion);
	               //llama al servicio de autentificacion de repositorio
	               //no permite dos sesiones
	               //hacer con captura de errores de java. es m�s elegante
	               //entonces: 
	               // si cadena true: alta correcta
	               // si "": error
	               iDSesion=autenticacion.autenticarUsuario(acreditacionUsuario.getNombre(),
	                  										 acreditacionUsuario.getPassword(),
	                   										 tipoUsuario);
	               //**** hacerlo con Exceptions  *****//
	               //se ha marcado como activo se inicia la sesión
				   switch (iDSesion)
				     {
					  case ("")://pantalla de error de acreditacion y vuelta al menu
						        gUI.errorAutenticacion();
								break;
					  case(ERROR_SESION_ABIERTA):gUI.errorSesionAbierta(tipoUsuario);
												break;
					  case (ERROR_NO_REPO_DISPONIBLE):gUI.errorRepoNoDisponible();
												break;
					  default:inicioSesionUsuario(acreditacionUsuario.getNombre());;							
						 
					 }//fin switch
/*	            
				if ((!IDSesion.equals(""))
	            		   &&(!IDSesion.equals(ERROR_NO_REPO_DISPONIBLE))
	            		   &&(!IDSesion.equals(ERROR_SESION_ABIERTA)))
	                 inicioSesionUsuario(acreditacionUsuario.getNombre());
	                else
					  if (IDSesion.equals(""))	
					    //pantalla de error de acreditacion y vuelta al menu
						gUI.errorAutenticacion();	
					   else if (IDSesion.equals(ERROR_SESION_ABIERTA))
						      gUI.errorSesionAbierta(tipoUsuario);
					         else
						      //este error solo se da en los clientes
						      gUI.errorRepoNoDisponible();   
	*/					  
	               //solo quedan los servicios de servidor
	               //vuelve al menu de inicio
	               opcion='0'; 
	               break;
	      case '3'://salir
	               //pantalla informativa fin
	               gUI.finDePrograma(); 
	               break;
	      default :System.out.print("opcion erronea");   
	      }//fin switch
	    }
	  while(opcion!='3');
	  }//fin try main
	 catch (RemoteException re)
	  {
	  gUI.errorAccesoRemotoObjeto(re.toString());  
	  }//fin catch REmoteException
	 catch (AlreadyBoundException re)
	  {
	  gUI.errorObjRemotoYaRegistrado(re.toString());	 
	  }
	 catch (NotBoundException re)
	  {
	  gUI.errorNoEncuentraObjRemoto(re.toString()); 
	  }
	 catch (Exception e)
	  {
	  gUI.errorGenerico(e.toString());  
	  }//fin catch 
	finally
	  {
		//quitar este try y capturar en usrCliente o usrRepo según corresponda
		gUI.finDePrograma();
	  }//fin finally
	
	
	}//fin menuUsuario
	 
  /********************************************************************************
   * Método que establece la conexión con RMI 
   *
   *
   *  @return true si fue correcto y false en caso contrario
   ******************************************************************************/
  private static Boolean conexionRMI()
    {
	Boolean correcto=true;  
	//establece la referencia al servidor RMI en atributo static de MiRegistroRMI
	MiRegistroRMI.refAMiRegRMI(iPHostRegistroRMI,puertoServidorRMI); 
	//comprobamos si hubo error de conexión con el registro,
	 if (MiRegistroRMI.getRefRegistroRMI()==null)
	   correcto=false;
	//y devolvemos el resultado
	return correcto;
    }//FIN CONEXIÓN RMI
	   
  /********************************************************************************
   * Método abstracto que inicia la sesion.
   * Debe ser implementado por la entidad con sus propios datos
   *******************************************************************************/  
	  
  protected abstract void inicioSesionUsuario(String iDUsuario) 
		  throws RemoteException,AlreadyBoundException,NotBoundException;
	    
  }//fin clase Usuario	   
	