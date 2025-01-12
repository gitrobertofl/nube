package servidor;

import java.rmi.*;
import java.util.logging.Logger;
import java.util.logging.Level;


//import java.net.*;
//import java.io.*;
//import java.security.*;
//import java.security.Policy.*;

/*******************************************************************************
 * Servidor 
 * 
 * <div> es el servidor, su host tendrá tambien el servicio del registro RMI.
 *</div> 
 * <div> Primero crea el registro (si no esta creado lo referencia) y luego crea la
 * base de datos y los objetos remotos</div>
 *
 * <div>Luego visualiza el menu correspondiente al servidor, con sus listados</div>
 * 
 * <div>Los repositorios y clientes deberan conocer su IP</div>
 *
 * @see MiRegistroRMI
 * @see NubeRMI
 * 
 * 
 * Con licencia GPL v3
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 * 
 * 
 *********************************************************************************/
public class Servidor
  {
  /*============================================================================
    Atributos de la clase
   ============================================================================*/
  //anadimos un Logger
  private static final Logger TRAZA=Logger.getLogger(MiRegistroRMI.class.getName());
//creamos el objeto remoto del servicio de datos
  private static ServicioDatosInterface servicioDatos;
  //creamos el objeto remoto del servicio gestor
  private static ServicioGestorInterface servicioGestor;//=new ServicioGestorImpl();
  //creamos el objeto remoto del servicio autentificacion
  private static ServicioAutenticacionInterface servicioAutenticacion;//=new ServicioAutenticacionImpl();
  
	
  //anade nueva ruta a codebase si esta configurada.   
      
           
  public static void main (String args[])
    {
           
      
      /*
      //gestor de seguridad 
       
      System.setProperty ("java.rmi.server.useCodebaseOnly","false");
      
      //definimos el repositorio de descarga din�mica de clases.
       
       //El classpath de basededatos
       System.setProperty ("java.rmi.server.codebase",
                "file:/D:/roberto/UNED/ASIGNATURAS_GRADO/G0305_sistemas_distribuidos/barquitos/basededatos/");
                //PROBAR file:./basededatos/
       //definimos las pol�ticas.OJO ESTa TOTALMENTE DESPROTEGIDO.
       System.setProperty("java.security.policy",
       "file:./basededatos/javaAll.policy"); //directorio del main
      
        //carga del gestor de seguridad
       System.setSecurityManager(new RMISecurityManager( ));
    
       System.out.println("antas"+System.getProperty("java.rmi.server.codebase").toString());
    
       System.out.println(System.getProperty("java.security.policy").toString());
      
      System.out.println(System.getProperty("java.rmi.server.useCodebaseOnly").toString());
       //rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false
       
     setCodeBase(Jugador.class);
      System.setProperty ("java.rmi.server.codebase","file:../fermin");
     System.out.println("despues"+System.getProperty("java.rmi.server.codebase").toString());
      
      */ 
       
       
    //=============================================================================
    //variables 
    //=============================================================================
     
    char opcion=0; //opcion de menu
    //final char opcionInicialMenu='1';
    final char opcionFinalMenu='4';
    
	//ip del servidor
    String direccionIP=MiRegistroRMI.obtenerIPLocalhost();
	//para cerrar adecuadamente la sesión
    Integer cierre=0;
    
    
    
    //=============================================================================
    //  cuerpo del programa 
    //=============================================================================
    //prepara la traza y elimina la consola
  	TRAZA.setLevel(Level.ALL);//ALL O NONE PARA ACTIVAR O DESACTIVAR 
    //levantamos el registro RMI
    MiRegistroRMI.refAMiRegRMI();
    //creamos el interfaz de usuario  TEXTO O Graficos
    GUIServidorInterface GUI = new GUIServidorTexto(direccionIP); 
    //primero comprobamos si el registro RMI es funcional, sino mensaje de error
    assert MiRegistroRMI.getRefRegistroRMI()==null : "referencia null a registro RMI";
    if (MiRegistroRMI.getRefRegistroRMI()==null)
      {
      GUI.errorRegistroRMI();
      }//fin si error en registro
     else
      {
      //si el registro esta levantando, registramos objeto remoto de datos  
      try
        {
        //creamos el objeto remoto servicio de datos.
        servicioDatos= new ServicioDatosImpl();
        //registramos el objeto remoto de servicio de datos. Al ser local con el objeto
        //servicioRMI.getRefRegistroRMI().rebind(bindDatos,servicioDatos);   
        MiRegistroRMI.registrarObjetoRemoto(NubeRMI.getBindDatos(),servicioDatos);
        //si no hubo error con el objeto (que hubiese otro servidor en ejecucion), registrado
        cierre=1;
        TRAZA.info("Registrado. objeto remoto base de datos en registro RMI");
        //creamos el objeto remoto servicio gestor
        servicioGestor=new ServicioGestorImpl(servicioDatos); 
        //registramos el objeto remoto de servicio gestor. Al ser local con el objeto
        //servicioRMI.getRefRegistroRMI().rebind(bindGestor,servicioGestor);   
        MiRegistroRMI.registrarObjetoRemoto(NubeRMI.getBindGestor(), servicioGestor);
        //si no hubo error con el objeto, registrado
        cierre=2;
        TRAZA.info("Registrado. objeto remoto gestor en registro RMI");
        //creamos el objeeto remoto servicio autenticacion
        servicioAutenticacion = new ServicioAutenticacionImpl(servicioDatos);
        //registramos el objeto remoto de servicio gestor. Al ser local con el objeto
        //servicioRMI.getRefRegistroRMI().rebind(bindAutenticacion,servicioAutenticacion);   
        MiRegistroRMI.registrarObjetoRemoto(NubeRMI.getBindAutenticacion(), servicioAutenticacion);
        //si no hubo error con el objeto, registrado
        cierre=3;
        TRAZA.info("Registrado. objeto remoto autenticacion en registro RMI");
        
        //una vez montada la estructura distribuida, ver pantalla. 
        
        do
          {
          //mostramos el menu principal     
          opcion=GUI.menuInicioServidor();
          switch (opcion)
            {
            case '1'://listar clientes
                     {
                     try
                       {
                       GUI.verListadoClientes(servicioDatos.getListadoClientes());
                    //esto se quita luego
                       //GUI.listaRegistro(MiRegistroRMI.getRefRegistroRMI().list());
                       }
                      catch (RemoteException re)
                       {
                       GUI.errorAccesoRemotoObjeto("error listar clientes : "+re+"\n");
                       }//fin remoteException
                      //GUI.pulsaTecla();
                     }//fin opcion estado del servidor
                      
                      break;
             case '2'://lista repositorios
                      GUI.verListadoRepositorios(servicioDatos.getListadoRepositorios());
                      break;
             case '3'://listar parejas Repositorio-Cliente
                      GUI.verListadoRepoClie(servicioDatos.getListadoRepoClie());
                      break;
             case '4'://salir
                      {
                      //debe avisar a cliente y repositorio que va a cerrar
                      
                          
                      GUI.finDePrograma();
                      
                      
                      }//fin 3 salir 
                      break;
             default :GUI.opcionErronea();   
             }//fin switch
           
            }
         while(opcion!=opcionFinalMenu);
          
        }//fin try
       catch (RemoteException re)
        {
        GUI.errorAccesoRemotoObjeto(re.toString());  
        }//fin catch RemoteException
        
       catch (AlreadyBoundException re)
        {
    	GUI.servidorEjecutandose(re.toString());   
        }
      
       catch (Exception e)
        {
        GUI.errorGenerico(e.toString());
        }//fin catch generico registro RMI
       finally
          {
    	   //desregistramos los servicios remotos de servidor
    	 //OJO CON TAREAS PENDIENTES.
    	  try  
    	   {
    	   switch (cierre)
    	     {
    	     case 3: MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindAutenticacion()); 
    	     case 2: MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindGestor());
       	     case 1: MiRegistroRMI.desRegistrarObjetoRemoto(NubeRMI.getBindDatos());
    	     case 0:
    	     default:	  
    	    }//fin switch 
    	   }//fin try
    	  catch (Exception e) {} 
          
    	  
    	
     	  //
          }
    }//fin else servicio registro no operativo  
    //no comprueba si el registro esta vacio,  se cierra el sistema.
    System.exit(0);    
    }//fin main
    
  //anade nueva ruta a codebase si esta configurada.   
  
  public static void setCodeBase(Class<?> c) {
        
        String CODEBASE = "java.rmi.server.codebase";
        String ruta = c.getProtectionDomain().getCodeSource()
                       .getLocation().toString();
        
    System.out.println("la ruta de getProtectionDomain().getCodeSource().getLocation()"+ruta);
                   //imprime)file:/D:/roberto/UNED/ASIGNATURAS_GRADO/G0305_sistemas_distribuidos/barquitos/    
        String path = System.getProperty(CODEBASE);
        //si esta vac�o o nulo, es que no se ha inicializado y no hace falta.
        if (path != null && !path.isEmpty()) {
            ruta = path + " " + ruta;  
        }
        
       System.out.println("la ruta fina es "+ruta); 
        
        
        //System.setProperty(CODEBASE, ruta);
    }
       
  
  
  
  }//fin clase Servidor