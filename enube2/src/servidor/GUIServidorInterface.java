package servidor;  

/******************************************************************************
 * <div>esta interface va a contener los métodos para el entorno de usuario
 * principal del programa</div> 
 * 
 * <div>Con licencia GPL v3</div>
 
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *****************************************************************************/

public interface GUIServidorInterface
  {
  //inicio y fin 

  /**************************************************************************
   * 
   * <div> Este es el menu principal del servidor</div>
   * <div> Si se pulsa intro o una tecla que no este en el intervalo de 
   * opcion, devuelve el caracter 'n'. En caso contrario, la primera tecla
   * pulsada</div>
   *
   *  @return un char con la opción elegida o 'n' en caso contrario 
   * 
   *************************************************************************/  
   public char menuInicioServidor();


  /**************************************************************************
   * 
   * <div> Pantalla informativa donde finaliza la ejecución del servidor,
   *  incluido el registro RMI. Utiliza pulsaTecla para esperar la 
   *  actución del usuaruio</div>
   *  
   * 
   *************************************************************************/  
   public void finDePrograma();
   
   //ver listados

  /**************************************************************************
   * 
   * <div> Pantalla en la que se visualiza de forma simple el listado de 
   * clientes dados de alta en el sistema.</div>
   * <div>Recibe una cadena con una fila por cada registro de la consulta
   *  solicitada al servicio de datos.</div>
   *   
   *  @param listado Cadena con una fila por cada cliente en el sistema
   * 
   *************************************************************************/  
   public void verListadoClientes(String listado);

  /**************************************************************************
   * 
   * <div> Pantalla en la que se visualiza de forma simple el listado de 
   * repositorios dados de alta en el sistema.</div>
   * <div>Recibe una cadena con una fila por cada registro de la consulta
   *  solicitada al servicio de datos.</div>
   *   
   *  @param listado Cadena con una fila por cada repositorio en el sistema
   * 
   *************************************************************************/  
   public void verListadoRepositorios(String listado);

  /**************************************************************************
   * 
   * <div> Pantalla en la que se visualiza de forma simple el listado de 
   * clientes asociados a repositorios </div>
   * <div>Recibe una cadena con una fila por cada registro de la consulta
   *  solicitada al servicio de datos.</div>
   *   
   *  @param listado Cadena con una fila por cada asociacion cliente-repositorio
   * 
   *************************************************************************/  
   public void verListadoRepoClie(String listado);
   
  
   
   
   //errores
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param error Cadena con el error
   * 
   *************************************************************************/  
   public void servidorEjecutandose (String error);
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  
   * 
   *************************************************************************/  
   public void errorRegistroRMI();
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param en Cadena con el error
   * 
   *************************************************************************/  
   public void errorAccesoRemotoObjeto(String en);
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   *  @param en Cadena con el error
   * 
   *************************************************************************/  
   public void errorGenerico(String en);
   
  /**************************************************************************
   * 
   * <div> Pantalla con mensaje del error (autoexplicativa) </div>
   *   
   * 
   * 
   *************************************************************************/  
   public void opcionErronea();
   
   //otros complementarios
   
   public void listaRegistro(String [] nombres); 

   
}//fin clase GUIClienteTexto
