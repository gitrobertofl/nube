package usuario;

import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.io.*;

/*INTERFAZ REMOT RECUERDA:CUANDO ACABES PASAR UNA COPIA AL OTRO PAQUETE*/

import servidor.MiRegistroRMI;
import servidor.NubeRMI;
/*FIN INTERFAZ REMOTA*/

/******************************************************************************
 * 
 * *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21).
 *
 * <div>Esta es la clase que representa entidad Cliente.</div>
 * <div> Esta clase hereda los atributos y metodos de la clase abstracta 
 *       Usuario.</div>
 * <div> Implementa los metodo especificos de un cliente de nube</div>
 * 
 * 
 * 
 * Con licencia GPL v3
 * 
 * @see MiRegistroRMI
 * @see Usuario
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20210310
 * 
 ******************************************************************************/

public class UsrCliente extends Usuario 
  {
  /*==============================================================================
   * Atributos de la clase (static)
   ===============================================================================*/ 
  /*==============================================================================
   * Atributos de instancia
   ===============================================================================*/ 
  //creamos un objeto para el servicio del cliente para repo
  private ServicioDiscoClienteInterface servicioDiscoCliente;
  //objeto remoto para conexion con su repositorio
  private ServicioClOperadorInterface svcClOperador;
  //ID del repositorio asignado
  String iDRepoAsignado;
  //para GUI de cliente
  GUIClienteInterface gUICliente=(GUIClienteInterface)gUI;
  /*===============================================================================
   * Constructores de la clase
   * ==============================================================================*/  
  /******************************************************************************
   * Constructor por defecto. Llama al constructor Usuario en el que le dice el tipo
   * de usuario es. Usuario inicializa la conexion con RMI.
   * ******************************************************************************/
  public UsrCliente()
	{
	super(TIPO_CLIENTE);
	}//fin constructor Cliente()

  
  /*================================================================================
        Metodos abstractos publicos o protected 
   ================================================================================*/
  /******************************************************************************
   * Este metodo se encarga de preparar el inicio de sesion como Cliente.
   * 
   * Una vez ha iniciado la sesion correctamente en Usuario,
   * nos manda a un menu con las operaciones del cliente, 
   * identificado por su ID. 
   * 
   * @param iDCliente cadena con el ID del cliente
   * @throws RemoteException
   * @throws AlreadyBoundException
   * @throws NotBoundException
   * 
   * ******************************************************************************/
  @Override
  protected void inicioSesionUsuario(String iDCliente) 
		  throws RemoteException, AlreadyBoundException,NotBoundException
    {
	//crear y registrar servicio discoCliente en RMI    
    servicioDiscoCliente=new ServicioDiscoClienteImpl(iDCliente);
   gestor.registraServicioCliente(iDCliente,servicioDiscoCliente);
	//asignamos una carpeta para la bajada de ficheros desde repo
	//el directorio raiz del paquete sera la ubicacion actual del cliente
	servicioDiscoCliente.carpetaParaCliente(iDCliente);
	//entramos en menu de operaciones del cliente 
	menuCliente(iDCliente);
    //como vamos a salir, marcamos como cliente inactivo, 
    // si tiene un repo enganchado? avisar o no hacerlo
    autenticacion.desactivarUsuario(iDCliente,tipoUsuario); 
    // desregistramos los servicios de RMI
    gestor.desRegistrarServicioCliente(iDCliente);
    }//fin inicioSesionUsuairo

  /*================================================================================
        Metodos privados 
   ================================================================================*/
  /******************************************************************************
   * Este metodo se encarga de mostrar y ejecutar las operaciones del cliente
   * 
   * @param iDCliente cadena con el ID del cliente
   * @throws RemoteException
   * @throws AlreadyBoundException
   * @throws NotBoundException
   * 
   * ******************************************************************************/
  private void menuCliente(String iDCliente)
		 throws RemoteException, AlreadyBoundException,NotBoundException
    {
    //-------------------     variables ---------------------------------------------        
    //opcion de menu
    Character opcion='0';
    String nombreFichero="";
    //-------------------     cuerpo ---------------------------------------------        
  	do
      {
      //visualizamos el menu y obtenemos operacion        
      opcion=gUI.menuUsuarioAcreditado(iDCliente);
      switch (opcion)
        {
        case '1'://subir fichero
	             //si esta activo, obtenemos el ID del repo asignado
                 //con el ID es suficiente para reconstruir la URL con MiRegistroRMI
	             iDRepoAsignado=gestor.repoAsignadoCliente(iDCliente);
                 // si repositorio asignado, esta activo
	             if (iDRepoAsignado!="")
	               {
	               //ordenamos a servidor que nos reserve carpeta en ese repositorio, si no lo tenia.
                   gestor.carpetaClienteEnRepo(iDCliente,iDRepoAsignado); 
	               //obtenemos el nombre del fichero
				   nombreFichero=gUICliente.nombreFichero(iDCliente); 
	               //si se obtuvo un nombre (sino pantalla de error)
                   if (nombreFichero.equals(""))
                     gUICliente.errorNombreFichero(iDCliente); 
                    else
                     //comprobamos si esta repetido en la base de datos
                     if (gestor.existeFichero(iDCliente,iDCliente,iDRepoAsignado,nombreFichero))
					   gUICliente.errorFicheroRepe (iDCliente);
                      else
                       try
						 {
                    	 //capturar error de fichero no exite  
                    	 FileInputStream aux= new FileInputStream(nombreFichero);
                    	 aux.close();
						 //crea el objeto Fichero
                    	 Fichero fichero= new Fichero(nombreFichero,iDCliente);
                         //notificamos fichero a la Base de datos (cliente,repo y nombre fichero)
                         gestor.subirFichero(iDCliente,iDRepoAsignado,nombreFichero);
                         //lo subimos al repositorio asignado.
                         //obtenemos referencia servicio cloperador del repo asignado
                         svcClOperador=(ServicioClOperadorInterface)
                		 MiRegistroRMI.getRefRegistroRMI().lookup(NubeRMI.getBindRepoCl()+iDRepoAsignado);
                		 //pedimos a svcClOperador que se quede con el fichero
                         if(svcClOperador.subirFichero(fichero))
                           gUICliente.avisoSubidaFichero(iDCliente,nombreFichero);
                         else
                           gUICliente.errorSubidaFichero(iDCliente);
                      //controlar si ha subido el fichero no se ven los errores
                       							  
						 }//fin try fichero						
						catch (FileNotFoundException io)
						  {
						   
						  gUICliente.errorFicheroNoExiste(iDCliente);	   
						  }
						 catch (Exception io)
                   		  {}
				         
                       //fin si no error de nombre de fichero
				   }//fin si repo asignado
	              else
                   {
	               gUICliente.avisoRepoNoActivo(iDCliente);	
                   }//fin si no repo asignado
		
          	     //vuelve al menu de inicio de cliente
                 opcion='0';
                 break;
        case '2'://bajar fichero
				 //si esta activo, obtenemos el ID del repo asignado
                 
			//aqui la URL completa, no solo el ID. pERO LA URL ESTA EMBEDIDA EN NUBE
				 Character tecla='n';
				 String iDClienteProp=iDCliente;//propietario del fichero
				 //preguntamos si es propio o ajeno
				 //ajeno: preguntamos si es de otro propietario antes del nombre del fichero 
				 do
				   {
				   tecla=gUICliente.esFicheroPropio(iDCliente); 
				   }
        	     while ((tecla!='s')&&(tecla!='S')&&(tecla!='n')&&(tecla!='N')) ;
        	     //si no es propio, asigna el id del propietario para obtener su repositorio
				 if ((tecla=='n')||(tecla=='N'))
				   {	 
				   //si lo es le preguntamos el ID del propietario para obtener su repo
				   iDClienteProp=gUICliente.nombrePropietario(iDCliente); 
				   iDRepoAsignado=gestor.repoAsignadoCliente(iDClienteProp);
				   }
				  else
					iDRepoAsignado=gestor.repoAsignadoCliente(iDCliente);
				//aunque no este activo, esta asignado; si no lo esta borra el repo
				if (!gestor.esRepositorioActivo(iDRepoAsignado))
				  iDRepoAsignado="";

				 
                 // si repositorio asignado, esta activo
	             if (iDRepoAsignado!="")
	               {
	               //obtenemos referencia servicio Sroperador del repo asignado
                   //svcSrOperador=NubeRMI.obtenerRefSrOperadorRepo(iDRepoAsignado);
		           
                   //obtenemos el nombre del fichero
				   nombreFichero=gUICliente.nombreFichero(iDCliente); 
                   //si se obtiene un nombre buscamos el fichero, sino pantalla de error
                   if (nombreFichero.equals(""))
                     gUICliente.errorNombreFichero(iDCliente); 
                    else
                     //comprobamos si existe en la base de datos
				     if (!gestor.existeFichero(iDCliente,iDClienteProp,iDRepoAsignado,nombreFichero))
				 	    gUICliente.errorFicheroNoExiste (iDCliente);
                      else
                       {
					   //gestor comprueba que existe, que se lo pasa a repositorio 
					   //que  usa el serviciodiscocliente 
                       if (!gestor.bajarFichero(iDCliente,iDClienteProp,iDRepoAsignado,nombreFichero))
						  gUICliente.errorBajadaFichero(iDCliente);
                       else
                    	  gUICliente.avisoBajadaFichero(iDCliente,iDClienteProp,nombreFichero); 
                        //a la base de datos le da igual
                       //controlar si ha BAJADO el fichero por el otro lado
                       
					   
					   }//fin si no error de nombre de fichero
				   }//fin si repo asignado
	              else
                   {
	               gUICliente.avisoRepoNoActivo(iDCliente);	
                   }//fin si no repo asignado
                 //vuelve al menu de inicio de juego
                 opcion='0'; 
                 break;
        case '3'://borrar fichero
                 //si esta activo, obtenemos el ID del repo asignado
                 iDRepoAsignado=gestor.repoAsignadoCliente(iDCliente);
                 // si repositorio asignado, esta activo
	             if (iDRepoAsignado!="")
	               {
	               //obtenemos el nombre del fichero
				   nombreFichero=gUICliente.nombreFichero(iDCliente); 
                   //si existe creamos el fichero, sino pantalla de error
                   if (nombreFichero.equals(""))
                     gUICliente.errorNombreFichero(iDCliente); 
                    else
                     //comprobamos si esta en la base de datos
                     if (!gestor.existeFichero(iDCliente,iDCliente,iDRepoAsignado,nombreFichero))
					   gUICliente.errorFicheroNoExiste (iDCliente);
                      else
                       {						
                       //notificamos fichero a la Base de datos (cliente,repo y nombre fichero)
                       gestor.borrarFichero(iDCliente,iDRepoAsignado,nombreFichero);
                       //lo borramos del repositorio asignado.
					   //obtenemos referencia servicio cloperador del repo asignado
                       svcClOperador=(ServicioClOperadorInterface)
                	   MiRegistroRMI.getRefRegistroRMI().lookup(NubeRMI.getBindRepoCl()+iDRepoAsignado);
                	   //pedimos a svcClOperador del repositorio que borre con el fichero
                       if(svcClOperador.borrarFichero(iDCliente,nombreFichero))
                    	 gUICliente.avisoFicheroBorrado(iDCliente,nombreFichero);
                    	else
                    	 gUICliente.errorBorradoFichero(iDCliente);	 
                       	  
					    }//fin else existe fichro						
				       
					   //fin si no error de nombre de fichero
				   }//fin si repo asignado
	              else
                   {
	               gUICliente.avisoRepoNoActivo(iDCliente);	
                   }//fin si no repo asignado
                 //vuelve al menu de inicio de juego
                 opcion='0'; 
                 break;
        case '4'://compartir fichero
                 //damos el nombre del fichero a compartir
        	     //podria hacerse tambien con IDUSUARIO concreto.
        	     
        	     //obtenemos el nombre del fichero
			     nombreFichero=gUICliente.nombreFichero(iDCliente); 
                 //si existe creamos el fichero, sino pantalla de error
                 if (nombreFichero.equals(""))
                   gUICliente.errorNombreFichero(iDCliente); 
                  else
                   //comprobamos si existe en la base de datos
			       //se puede eliminar este metodo si controlamos en bajarFichero*/
                   if (!gestor.existeFichero(iDCliente,iDCliente,iDRepoAsignado,nombreFichero))
				     gUICliente.errorFicheroNoExiste (iDCliente);
                    else
                     {
					 //es del cliente. si esta compartido o no
					 //lo marcamos en la base de datos.
					 gestor.compartirFichero(iDCliente,iDRepoAsignado,nombreFichero);
					 //gui indicando que se comprtio adecuadamente.
  				     //comprobar que se ha compartido
				     }//fin si no error de nombre de fichero
                 //vuelve al menu de inicio de inicio de juego
                 opcion='0'; 
                 break;
        case '5'://listar ficheros
                 gUICliente.verListadoFicherosCliente(iDRepoAsignado,
                                               iDCliente,
                                               gestor.getListadoFicherosCliente(iDCliente));
                 //vuelve al menu de inicio de inicio de juego
                 opcion='0'; 
                 break;
        case '6'://Listar clientes del sistema                    
                  gUICliente.verListadoClientes(gestor.getListadoClientesSistema());
                 //vuelve al menu de inicio de inicio de juego
                 opcion='0'; 
                 break; 
        case '7'://cerrar sesion de Cliente (logout)
                 //no hace nada pues ya cierra el menu inicial los obj. remotos 
                 break;
        default :System.out.print("opcion erronea");   
        }//fin switch
      }//fin bucle do
    while(opcion!='7');
    }//fin menu de cliente
  }//fin clase Cliente