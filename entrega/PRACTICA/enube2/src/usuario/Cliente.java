package usuario;

/******************************************************************************
 * <div>Clase ejecutora de cliente.</div>
 * 
 * <div> esta clase existe por la imposición del enunciado de ejecutar los
 * clientes y repositorios sin parametros.</div> 
 * <div>Utiliza la clase UsrCliente para llamar a la parte de cliente, con la
 * herencia desde Usuario)</div>
 * 
 * @see UsrCliente
 * @see Usuario
 * 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20210601
 *****************************************************************************/
public class Cliente
  {
  //parametro necesario para UsrUsuario;	
  private static final String TIPO_USUARIO="cliente";
  
  /*****************************************************************************
  *
  *  <div>Método principal de ejecución main. Prepara el acceso a los servicios
  *  remotos de servidor.</div>
  *  <div>Para determinar el tipo de entidad, cliente,
  *  necesita parámetro de entrada, instanciando la que corresponda.</div>
  *
  *  @param args una cadena con el tipo de entidad
  *
  *************************************************************************** */
  public static void main (String args[])
    {
	//gestor de seguridad
    Usuario usuario;
    usuario = new UsrCliente();
	if (usuario.getConectadoConSRV())
	  //una vez montado el esquema remoto, iniciamos el menu principal
	  usuario.menuUsuario();    
	 else
   	  {	 
	  GUIUsuarioInterface gUI = new GUIClienteTexto(TIPO_USUARIO);	 
      gUI.finDePrograma(); 
	  }
    //termina todo  
 	System.exit(0); 
		
	}//fin del main
  	  
  }//fin clase Cliente