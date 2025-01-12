package servidor; 

import java.util.Vector;
//import java.rmi.RemoteException;
import java.util.Random;

/********************************************************************************
   
 *  PRACTICA DE SISTEMAS DISTRIBUIDOS (20-21).
 *
 * <div>Esta es la clase que representa la base de datos, que son las tablas
 * con cada una de la entidades de la misma y sus accesos</div>
 * 
 * 
 * <div>Para garantizar la exclusion mutua, todos los metodos son synchronized.
 * </div>
 * 
 * <div>Con licencia GPL v3</div>
 *
 *  @see ClienteBD
 *  @see RepositorioBD
 *  @see RepoClieBD
 *  @see FicheroBD
 *
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * 
 * @version 20201207
 * 
 ********************************************************************************/
public class Datos
  {
  /*===========================================================================
    Atributos de la clase
  ==============================================================================*/   
  //Cada vector es una tabla con cada uno de los tipos de registros creados
  private Vector<ClienteBD> clientes=new Vector<ClienteBD>(); 
  private Vector<RepositorioBD> repositorios=new Vector<RepositorioBD>();
  private Vector<RepoClieBD> repoClies=new Vector<RepoClieBD>();
  private Vector<FicheroBD> ficheros=new Vector<FicheroBD>();
 
  /*===========================================================================
   * Constructores de la clase
   * ==========================================================================*/      
    
  /*****************************************************************************
   * Por defecto construye una estructura de datos vacia
   *********************************************************************************/
  public Datos()
    {
    //lo hacen las asignaciones por defecto    
    }//fin constructor Datos()

  /*=================================================================================
   * Metodos publicos
   * ==============================================================================*/    
  
  //metodos get 
  public synchronized Vector<ClienteBD> getListaClientes()
    {
    return clientes;
    }
   
  public synchronized Vector<RepositorioBD> getListaRepositorios()
    {
    return repositorios;  
    }
    
  public synchronized Vector<FicheroBD> getFicheros()
    {
    return ficheros;
    }
  
  //metodos set. No hay
  
  // métodos alta registros

  /**********************************************************************************
   * <div>Este metodo anade un Repositorio nuevo a la base de datos</div>
   * <div>Primero comprueba que el Repositorio no existe y si es asi lo anade.</div>
   * 
   * @param  nombre es una cadena con el nombre de Repositorio
   * @param pwd es la cadena que representa la contrasena
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * 
   *********************************************************************************/
  public synchronized Boolean insertaRepositorio(String nombre, String pwd)
    {
    boolean anadido=false;
    // si no existe, lo anade
    if (!this.existeRepositorio(nombre))
      {
      this.repositorios.add(new RepositorioBD(nombre,pwd));
      anadido=true;
      }
    return anadido;
    }//fin addRepositorio   


  /**********************************************************************************
   * <div>Este metodo anade un Cliente nuevo a la base de datos</div>
   * <div>Primero comprueba que el Cliente no existe y si es asi lo anade.</div>
   * 
   * @param  nombre es una cadena con el nombre de Cliente
   * @param pwd es la cadena que representa la contrasena
   * @return objeto TRUE si se efectuo con exito, FALSO en caso contrario (ya existe)
   * 
   *********************************************************************************/
  public synchronized Boolean insertaCliente(String nombre, String pwd)
    {
    boolean anadido=false;
    // si no existe, lo anade
    if (!this.existeCliente(nombre))
      {
      this.clientes.add(new ClienteBD(nombre,pwd));
      anadido=true;
      }
    return anadido;
    }//fin addCliente
 
  /******************************************************************************
   * <div> asignarRepoclie asigna un cliente al repositorio:</div>
   * <ul> <li>Si ya estaba asignado, obtiene el ID del repositorio</li>
   * <li> si no tiene asignado, le da uno de los activos, si lo hay; comprueba si
   * hay alguno activo, si lo hay elige aleatoriamente uno de los activos (evita que
   * siempre se coja el primero, lo que sobrecargara siempre el mismo, si estan todos
   * activos).</li></ul>
   * 
   * @param iDCliente necesita el ID del cliente a asignar
   * @return devuelve el ID del repositorio asignado, cadena vacia si no
   * se asigna por no estar disponible.
   *****************************************************************************/
  public synchronized String asignarRepoClie(String iDCliente)
    {
	String iDRepositorio="";
    int pos=0;
    Boolean encontrado=false;
    Random random = new Random();
    //busca si hay repo asignado 
    iDRepositorio=this.obtenerRepoClie(iDCliente);
    //sin no tenia repositorio asigna un activo, si hay
    if (iDRepositorio.equals(""))
      {
      pos=0;
        
      while(pos<repositorios.size())
        {
        //"" significa que no esta activo    
        if (!(((RepositorioBD)this.repositorios.get(pos)).getIDSesion().equals("")))
          {
          do//sabemos que hay al menos uno activo, repetimos hasta que encuentre ese u otro 
            {
            //sobre repositorios.size(), obtener un numero al azar 
            pos=random.nextInt(repositorios.size());
            //si el siguiente está activo
            if  (!((RepositorioBD)this.repositorios.get(pos)).getIDSesion().equals(""))   
              {
              encontrado=true;
              //insertar en la base de datos y devolver el nombre elegido.
              iDRepositorio=((RepositorioBD)this.repositorios.get(pos)).getNombreRepositorio();
              this.repoClies.add(new RepoClieBD(iDCliente, iDRepositorio));
              }//fin si es activo
            }//fin dowhile
          while (!encontrado);
          pos=repositorios.size();    
          }
         else //no encontro uno activo
          pos++;
        }//fin bucle    
      }//fin si no hay asignado
         
    return iDRepositorio;
    }//fin asignar repoclie
   
  /******************************************************************************
   * <div> Anade el nombre de fichero del cliente y repositorio indicado</div>
   * <div> no comprueba que el cliente y repositorio estan asociados</div>
   * 
   * @param iDCliente cadena con el ID de cliente
   * @param iDRepo cadena con el ID de repositorio
   * @param nFichero cadena con el nombre del fichero (sin ruta)
   * 
   *****************************************************************************/  
  public synchronized void altaFichero(String iDCliente, String iDRepo, String nFichero)
    {
    this.ficheros.add(new FicheroBD(iDCliente,iDRepo,nFichero));
    }//fin altaFichero

  //metodos de bajas
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
   *****************************************************************************/  
  public synchronized Boolean borrarFichero(String iDCliente, String iDRepo, String nFichero)
    {
	int pos=0;
    Boolean encontrado=false;
    //busca el fichero 
	while(pos<ficheros.size())
      {
	  FicheroBD siguiente=(FicheroBD)this.ficheros.get(pos);  
      if ((siguiente.getNombreRepositorio().equals(iDRepo))
		  && (siguiente.getNombreCliente().equals(iDCliente))
          && (siguiente.getNombreFichero().equals(nFichero) ))
         {
         //si esta, lo borra
		 this.ficheros.remove(pos);
		 encontrado=true;
         pos=ficheros.size();    
         }
        else
         pos++;
       }
     
	 return encontrado;
	 
    }//fin borrarFichero
  
  
  //modificaciones
  
    /**********************************************************************************
   * <div> Este metodo nos dice si los datos de acceso de un repositorio (ID, contrasena)
   * son iguales a los de los parametros.</div>
   * <div> Se utiliza en la autenticacion de repositorio</div>
   * <div> Si los datos son correctos, lo marca como activo (asigna ID sesion)</div>
   * 
   * @param  nombreRepo cadena con el ID del repositorio
   * @param  pwd cadena con la contrasena del repositorio
   * @return String con IDSesion o cadena vacia en caso de error.
   *********************************************************************************/
  public synchronized String activarRepositorioAcreditado(String nombreRepo, String pwd)
    {
	String ERROR_SESION_ABIERTA="La sesión ya estaba abierta";  
    String iDSesion="";
    int pos=0; 
    RepositorioBD repoAux;//(RepositorioBD)this.repositorios.get(pos).
    while(pos<repositorios.size())
      {
      repoAux=(RepositorioBD)this.repositorios.get(pos);	
      //si los parametros ya pertenecen a un repositorio, marca encontrado y activo   
      if ((nombreRepo.equals(repoAux.getNombreRepositorio()))
           &&
          (pwd.equals(repoAux.getPasswordRepositorio()))
          )
        {
        //si no esta activo (no admite dos sesiones) y no la sustituye.
        if (repoAux.getIDSesion().equals(""))
          {      
          //asignamos ID
          iDSesion=this.ObtenerIDSesion();
          ((RepositorioBD)this.repositorios.get(pos)).setIDSesion(iDSesion);
          }//fin si no activo
         else
          iDSesion=ERROR_SESION_ABIERTA; 
        	
        //si encontro. finalizar bucle  
        pos=repositorios.size();  
        }//fin si encontrado 
       else 
        pos++;
      }//fin while pos<size
    return iDSesion;  
    }//fin RepositorioAcreditado     
  
  /**********************************************************************************
   * <div> Este metodo nos dice si los datos de acceso de un cliente (ID, contrasena)
   * son iguales a los de los parametros.</div>
   * <div> Se utiliza en la autenticacion de cliente</div>
   * <div> Si los datos son correctos, lo marca como activo (asigna ID sesion)</div>
   * 
   * 
   * @param  nombreCliente cadena con el ID del cliente
   * @param  pwd cadena con la contrasena del cliente
   * @return String con iDSesion o cadena vacia en caso de error.
   *********************************************************************************/   
  public synchronized String activarClienteAcreditado(String nombreCliente, String pwd)
    { 
	String ERROR_NO_REPO_DISPONIBLE="repo_no_disponible";
    String ERROR_SESION_ABIERTA="La sesión ya estaba abierta";	  
    String iDSesion="";
    String repositorioAsignado="";
    int pos=0;
    ClienteBD clienteAux;
    //(ClienteBD)this.clientes.get(pos)
    while(pos<clientes.size())
      {
      clienteAux=(ClienteBD)this.clientes.get(pos);	
      //si los parametros son correctos obtiene el cliente,
      if ((nombreCliente.equals(clienteAux.getNombreCliente()))
           &&
          (pwd.equals(clienteAux.getPasswordCliente()))
          )
        {
        //si no esta activo (no admite dos sesiones y no la sustituye) 
        //se asigna repositorio nuevo, si no lo tenia ya
        if (clienteAux.getIDSesion().equals(""))
          {
          repositorioAsignado=this.asignarRepoClie(nombreCliente);
          //si no está en blanco y si esta activo, se asigna
          //sino no se puede iniciar sesion
          if ((!repositorioAsignado.equals(""))
        		  &&
        	  (this.esRepositorioActivo(repositorioAsignado)))
            {
        	//asignamos ID
            iDSesion=this.ObtenerIDSesion();
            clienteAux.setIDSesion(iDSesion);  
        	}//fin si hay repo asignado
          else
        	iDSesion= ERROR_NO_REPO_DISPONIBLE; 
          } //fin sesion no iniciada
         else
          iDSesion=ERROR_SESION_ABIERTA;	
        //si encontro, salimos del bucle      
        pos=clientes.size();  
        }//fin si encontrado 
       
      pos++;
     
      }//fin mientras
    return iDSesion;  
    }//fin ClienteAcreditado  
     
  /*****************************************************************************
   * <div> desactivarRepositorio es necesario antes de cerrar sesion 
   * para quitar el ID de sesion.</div>
   * 
   * <div> AHORA NO COMPRUEBA QUE HAYA CLIENTES ACTIVOS USANDOLO</DIV>
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * @return true si se desactivo, false en caso contrario
   * 
   ****************************************************************************/
  public synchronized Boolean desactivarRepositorio(String iDRepositorio)
         
    {
    Boolean desactivado=false;    
    int pos=0;
     while(pos<repositorios.size())
       {
       if (((RepositorioBD)this.repositorios.get(pos)).getNombreRepositorio().equals(iDRepositorio))
         {
         //debe comprobar si tiene algun cliente activo    
         ((RepositorioBD)this.repositorios.get(pos)).setIDSesion("");
         desactivado=true;
         pos=repositorios.size();    
         }
        else
         pos++;
       }
    return desactivado;    
    }//fin desactivarRepositorio

  /******************************************************************************
   * <div> desactivarCliente es necesario antes de cerrar sesion para quitar el
   * ID de sesion.</div>
   * 
   * @param iDCliente requiere el ID del cliente a desactivar
   *****************************************************************************/
  public synchronized  void desactivarCliente(String iDCliente)
    {
    //boolean encontrado=false;
     int pos=0;
     while(pos<clientes.size())
       {
       if (((ClienteBD)this.clientes.get(pos)).getNombreCliente().equals(iDCliente))
         {
         ((ClienteBD)this.clientes.get(pos)).setIDSesion("");
         pos=clientes.size();    
         }
        else
         pos++;
       }
        
    }//fin desactivarCliente     

	
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
   *****************************************************************************/  
  public synchronized Boolean compartirFichero(String iDCliente, String iDRepositorio, String nFichero)
	  {
	  boolean encontrado=false;
      int pos=0;
      while(pos<repositorios.size())
        {
        FicheroBD siguiente=(FicheroBD)this.ficheros.get(pos);  
        if ((siguiente.getNombreRepositorio().equals(iDRepositorio))
     			  && (siguiente.getNombreCliente().equals(iDCliente))
     	          && (siguiente.getNombreFichero().equals(nFichero) ))
	      {
          encontrado=true;
		  siguiente.setCompartir();
		  pos=ficheros.size();    
		  }
        pos++;
        }
      return encontrado;  
       }//fin compartir fichero
  
  //consultas

  /**********************************************************************************
   * <div> existeRepositorio nos dice si el ID del repositorio existe en la BD</div>
   * 
   * @param  nombre requiere una cadena con el ID del repositorio
   * @return objeto TRUE si ya existe FALSO en caso contrario o vacio
   *********************************************************************************/
   public synchronized Boolean existeRepositorio(String nombre)
     {
     boolean encontrado=false;
     int pos=0;
     while((pos<repositorios.size())&&(!encontrado))
       {
       if (((RepositorioBD)this.repositorios.get(pos)).getNombreRepositorio().equals(nombre))
         encontrado=true;
       pos++;
       }
     return encontrado;  
     }//fin clienteEncontrado     
  
  /**********************************************************************************
   * <div> existecliente nos dice si el ID del cliente existe en la BD</div>
   * 
   * @param  nombre requiere una cadena con el ID del Cliente
   * @return objeto TRUE si ya existe FALSO en caso contrario o vacio
   *********************************************************************************/
   public synchronized Boolean existeCliente(String nombre)
     {
     boolean encontrado=false;
     int pos=0;
     while((pos<clientes.size())&&(!encontrado))
       {
       if (((ClienteBD)this.clientes.get(pos)).getNombreCliente().equals(nombre))
         encontrado=true;
       pos++;
       }
     return encontrado;  
     }//fin clienteEncontrado
  
   /*********************************************************************************
    * <div>existeFichero nos dice si ya existe para el cliente y repositorio 
    *  correspondiente</div>
    * <div> Si el cliente es distitno de propietario, ademas debe estar marcado
    * como compartido</div>
    * 
    * @param iDCliente cadena con el ID de cliente
    * @param iDClienteProp cadena con ID del cliente propietario del fichero
    * @param iDRepo cadena con el ID de repositorio
    * @param nombreFichero cadena con el nombre del fichero (sin ruta)
    * @return TRUE si se encontro, FALSE en caso contrario.
    * 
    ********************************************************************************/  
   public synchronized Boolean existeFichero(String iDCliente, String iDClienteProp, 
 		  String iDRepo,String nombreFichero)   
     {
     Boolean encontrado=false;
     FicheroBD ficheroAux;
     //si el propietario y el cliente son el mismo, se busca sin comparticion
     if (iDCliente.equals(iDClienteProp))
       {
       int pos=0;
       while(pos<ficheros.size())
         {
    	 ficheroAux=(FicheroBD)this.ficheros.get(pos);  
         //si coinciden los tres parametros
         if (ficheroAux.getNombreFichero().equals(nombreFichero)
           &&
          ficheroAux.getNombreCliente().equals(iDCliente)
          &&
          ficheroAux.getNombreRepositorio().equals(iDRepo)
           )
  	  	   {
    	   encontrado=true;
		   pos=ficheros.size(); //fin de bucle
		   }
         pos++;
         }//fin while 
       }//fin si es el mismo cliente
      else
      {//si no son iguales, entra en juego la comparticion
      int pos=0;
      while(pos<ficheros.size())
        {
        ficheroAux=(FicheroBD)this.ficheros.get(pos);  
        //si coinciden los tres parametros y compartido
        if (ficheroAux.getNombreFichero().equals(nombreFichero)
             &&
            ficheroAux.getNombreCliente().equals(iDClienteProp)
             &&
             ficheroAux.getNombreRepositorio().equals(iDRepo)
             &&
             ficheroAux.getEstaCompartido()
              )
     	  	   {
       	   encontrado=true;
   		   pos=ficheros.size(); //fin de bucle
   		   }
            pos++;
            }//fin while 	  
    	  
      }//fin no son iguales
    
     return encontrado;  
     }//fin existefichero   

  /*****************************************************************************
   * <div> Comprueba si el repositorio pasado por parametro esta activo</div>
   * 
   * 
   * @param iDRepositorio Requiere el ID del repositorio a desactivar
   * @return true si se desactivo, false en caso contrario
   * 
   ****************************************************************************/
  public synchronized Boolean esRepositorioActivo(String iDRepositorio)
    {
    Boolean estado=false;    
    int pos=0;
    RepositorioBD repoAux;
    while(pos<repositorios.size())
      {
   	  repoAux=(RepositorioBD)this.repositorios.get(pos);
      if (repoAux.getNombreRepositorio().equals(iDRepositorio))
        {
        //si es distinto a "" esta activo    
        if (!(repoAux.getIDSesion().equals("")))
          estado=true;
        pos=repositorios.size();    
        }
       else
        pos++;
      }
    return estado;    
    }//fin esRepositorioActivo   
   
  /************************************************************************************
   * <div> Obtenemos el ID del repositorio asignado a un cliente</div>
   * 
   * @param iDCliente requiere una cadena con el ID del cliente
   * @return cadena con el ID del repositorio asignado, vacio si no lo tiene
   ***********************************************************************************/
   public synchronized String obtenerRepoClie(String iDCliente)
     {
     String iDRepositorio="";
     int pos=0;
     //busca repo asignado    
     while(pos<repoClies.size())
       {
       //si lo hay, lo asigna    
       if (((RepoClieBD)this.repoClies.get(pos)).getNombreCliente().equals(iDCliente))    
         {
         iDRepositorio=((RepoClieBD)this.repoClies.get(pos)).getNombreRepositorio();
         pos=repoClies.size();//sale 
         }    
       pos++;
       }//fin busqueda de cliente
     
     
     return iDRepositorio;
     }//fin obtenerRepoClie 
   
   //listados

    
  /******************************************************************************
   * <div>listadoRepositorios consulta de todos los repositorios registrados</div>
   *
   *  @return cadena con todos los repositorios e IDsesion 
   *  separados por salto de linea
   *****************************************************************************/
  public synchronized String listadoRepositorios()
    {
	final String SEPARADOR=" , "; 
    StringBuffer resultado= new StringBuffer("");
    int pos=0;
    while(pos<repositorios.size())
       {
       RepositorioBD aux=(RepositorioBD)this.repositorios.get(pos);	
       resultado.append((String.format("%-15s",aux.getNombreRepositorio())));
       resultado.append(SEPARADOR);
       resultado.append(aux.getIDSesion());
       resultado.append("\n");
       pos++;
       }
    return resultado.toString();
    }//fin listadoRepositorios

  /******************************************************************************
   * <div> listadoClientes consulta todos los clientes registrados</div>
   * 
   * @return cadena con todos los clientes e IDsesion separados por salto de linea
   *****************************************************************************/
  public synchronized String listadoClientes()
    {
	final String SEPARADOR=" , ";  
    StringBuffer resultado= new StringBuffer("");
    int pos=0;
    while(pos<clientes.size())
       {
       ClienteBD aux=(ClienteBD)this.clientes.get(pos);
       resultado.append(String.format("%-15s ",aux.getNombreCliente()));	
       resultado.append(SEPARADOR);
       resultado.append(aux.getIDSesion());
       resultado.append("\n");
       pos++;
       }
    return resultado.toString();
    }//fin listadoClientes
    
  /******************************************************************************
   * <div>listadoRepoclie consulta todos los repositorios con sus clientes 
   *  asociados</div>
   * 
   * @return cadena con los ID de repositorio seguido de ID de cliente
   *****************************************************************************/
  public synchronized String listadoRepoClie()
    {
	final String SEPARADOR=" , ";   
    StringBuffer resultado= new StringBuffer("");
    int pos=0;
    while(pos<repoClies.size())
       {
       RepoClieBD aux=	(RepoClieBD)this.repoClies.get(pos);
       resultado.append(((String.format("%-22s",aux.getNombreRepositorio()+" "))));
       resultado.append(SEPARADOR);
       resultado.append(aux.getNombreCliente());
       resultado.append("\n");
       pos++;
       }
    return resultado.toString();
    }//fin listadoRepositorios
  
  /******************************************************************************
   * <div> ListadoClientesRepo consulta los clientes de un repositorio concreto</div>
   * 
   * @param iDRepositorio requiere el ID de un repositorio
   * @return cadena con los ID de los clientes asociados al repositorio parametro
   *****************************************************************************/   
  public synchronized String listadoClientesRepo(String iDRepositorio)
    {
	final String SEPARADOR=" , ";    
	StringBuffer resultado= new StringBuffer("");
    int posRC=0;
    while(posRC<repoClies.size())
       {
       RepoClieBD aux=(RepoClieBD)this.repoClies.get(posRC);	
       //solo los que coincida el IDRepositorio
       if  (aux.getNombreRepositorio().equals(iDRepositorio))   
         {  
         resultado.append(aux.getNombreCliente());
         resultado.append(SEPARADOR);
         //indicamos si tiene la sesion abierta o no
         int posC=0;
         while(posC<clientes.size())
            {
        	ClienteBD auxC=(ClienteBD)this.clientes.get(posC);  
            if (aux.getNombreCliente().equals(auxC.getNombreCliente()))
              {
              resultado.append(auxC.getIDSesion());	
              posC=	clientes.size();
              }//fin si lo encuentra
        	posC++ ;
            }
        resultado.append("\n");
        }//fin si es del repositorio
       posRC++;
       } 
    return resultado.toString();
    }//fin listadoClientesRepo
   
  /******************************************************************************
   * <div>listadoFicheroCliente consulta los ficheros de un cliente en un
   *   repositorio </div>
   * 
   * @param iDCliente requiere el ID de un cliente (no es requerido que 
   *        esta asociado)
   * @return cadena con los nombres de los ficheros almacenados(sin ruta)
   *****************************************************************************/  
  public synchronized String listadoFicherosCliente(String iDCliente)
    {
    StringBuffer resultado= new StringBuffer("");
    int posFichero=0;
	
    while(posFichero<ficheros.size())
       {
	   FicheroBD auxFichero= (FicheroBD)this.ficheros.get(posFichero);
	   //si esta compartido por otro lo mete
       if (auxFichero.getEstaCompartido())
	     {
		 resultado.append("Fichero : "+auxFichero.getNombreFichero());
         resultado.append(" en  repositorio "+auxFichero.getNombreRepositorio());
         resultado.append(" compartido por "+auxFichero.getNombreCliente());
		 resultado.append("\n");		 
		 	 
		 }//fin si compartido por otros  
        else
	     //solo los que coincida el IDCliente
         if (auxFichero.getNombreCliente().equals(iDCliente))   
           {  
           resultado.append("Fichero : "+auxFichero.getNombreFichero());
           resultado.append(" en  repositorio "+auxFichero.getNombreRepositorio());
           resultado.append("\n");
           }
		posFichero++;
       }//fin while posFichero 
    return resultado.toString();
    }//fin listadoficheroscliente
    
  /*=================================================================================
   * Metodos privados
   * ==============================================================================*/    
    
  /*********************************************************************************
   * <div> Calcula el ID de sesion. Obtiene una cadena de LONID caracteres 
   * alfanumericos elegidos al azar, de forma que lo mas probable es que sea 
   *  unico (OJO)</div>
   *
   * @return cadena alfanumerica aleatoria de longitud LONGID
   ********************************************************************************/     
       
  private String ObtenerIDSesion()
    {
    //caracteres del ID      
    char[] caracteresID = "0123456789ABCDEFGHIJKLNMOPQRSTUVWZabcdefghijklmnopqrstuvwxyz".toCharArray();
    //longitud del ID la mitad, para obtener max. num. combinaciones
    final int LONGID=(int)(caracteresID.length/2);
	
	//creamos la cadena vacia
    StringBuffer cadenaID = new StringBuffer(LONGID);
    
    Random random = new Random();
    //elegimos al azar un caracter de la lista hasta cubrir la cadena
    for (int i = 0; i < LONGID; i++)
       {
       char c = caracteresID[random.nextInt(caracteresID.length)];
       cadenaID.append(c);
       }
    //devolvemos el resultado   
    return cadenaID.toString();
    //return "cadena de sesion";
    }  
    
  }//fin de clase