package usuario; 

/********************************************************************************
 * <div>Esta clase va a implementar el interface del entorno de usuario en modo 
 * consola.</div> 
 * <div>Contiene los metodos para la visualizacion de cada menu</div>
 *
 * Con licencia GPL v3
 *
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *********************************************************************************/
public interface GUIUsuarioInterface
  {
  //incio y fin
  
  /*********************************************************************************
   * <div>Este metodo visualiza el menu inicial del usuario, para el registro y 
   *  autentificacion</div>
   * 
   * @return devuelve el primer caracter pulsado con numero que se pulsa
   * o 'n' si intro u otra cosa    
   ********************************************************************************/
  public Character menuInicio(); 
  
  /*********************************************************************************
   * <div>Visualiza un mensaje de fin de programa</div> 
   * 
   * 
   ********************************************************************************/
   public void finDePrograma();

  //captura de datos
  /*********************************************************************************
   *  <div>Visualiza la obtencion de nombre de repositorio y contrasena
   *  no se permiten nombre de Repositorio en blanco;contrasena si.</div>
   *
   *  @param tipoLogin si tipoLogin=1, es el registro, si es =2 es autentificar
   *
   ********************************************************************************/
  public AcreditacionUsuario login(Character tipoLogin);  
    
  //mensajes
  /*********************************************************************************
   * <div>Visualiza mensaje de usuario (repositorio o cliente)registrado 
   * correctamente</div>
   * 
   ********************************************************************************/  
  public void altaUsuarioCorrecta();
  
  

  //alta y acreditacion

   
  //errores 

  /***************************************************************************
   * Visualiza mensaje de error en registro/autenticacion repositorio:
   *
   **************************************************************************/  
  public void errorAcreditacion();

  /***************************************************************************
   * Visualiza mensaje de error en autenticacion usuario
   *
   ***************************************************************************/  
  public void errorAutenticacion();

    
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   * 
   *************************************************************************/  
   public void errorRegistroRMI();
    
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param er  Cadena con el error
   * 
   *************************************************************************/  
  public void errorAccesoRemotoObjeto(String er); 
 
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param er Cadena con el error
   * 
   *************************************************************************/  
  public void errorNoEncuentraObjRemoto(String er);
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param er Cadena con el error
   * 
   *************************************************************************/  
  public void errorObjRemotoYaRegistrado(String er);
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param er Cadena con el error
   * 
   *************************************************************************/  
  public void errorGenerico(String er);

       
   
  /***************************************************************************
   * Visualiza mensaje de sesion abierta para el usuario tipoUsuario
   *
   * @param tipoUsuario repositorio o cliente
   **************************************************************************/  
  public void errorSesionAbierta(String tipoUsuario);
   
  /***************************************************************************
   * Visualiza mensaje de error si el cliente no tiene repos disponibles
   *
   **************************************************************************/  
  public void errorRepoNoDisponible();





  /*********************************************************************************
   * <div>Se visualiza el menu del usuario, según corresponda una vez acreditado.</div>
   * 
   * @param iDUsuario obtiene el ID del usuario
   * @return devuelve el primer caracter pulsado con numero que se pulsa (u otra cosa)
   * o 'n' si intro
   ********************************************************************************/ 
  public Character menuUsuarioAcreditado(String iDUsuario);
  
 
     
     
}//fin clase GUIUsuarioInterface
