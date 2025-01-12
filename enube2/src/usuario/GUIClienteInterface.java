package usuario;

/********************************************************************************
 * <div>Esta clase va a implementar el interface del cliente en modo 
 * consola.</div> 
 * <div>Contiene los metodos para la visualizacion de cada menu</div>

 * Con licencia GPL v3
 *
 * @author Roberto J. de la Fuente Lopez 
 *         rfuente4@alumno.uned.es
 *         robertofl@aconute.es
 * @version 20201103
 *********************************************************************************/
public interface GUIClienteInterface
  {
 /********************************************************************************
  * <div> Este metodo captura si el fichero que se va a bajar es propio o no.</div>
  * 
  * @param iDCliente el correspondiente al cliente en ejecucion.
  * @return un caracter S, s, N, n 
  *  
  *******************************************************************************/
  public Character esFicheroPropio(String iDCliente);
	
  /********************************************************************************
   * <div> Este metodo captura el id del cliente propietario del fichero compartido
   * que se quiere bajaro</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @return cadena introducida (si intro, cadena vacia)
   *  
   *******************************************************************************/
  public String nombrePropietario(String iDCliente);
	
  /********************************************************************************
   * <div> Este metodo captura el nombre del fichero que se quiere subir/bajar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @return cadena introducida (si intro, cadena vacia)
   *  
   *******************************************************************************/
  public String nombreFichero(String iDCliente);

  //avisos
  /********************************************************************************
   * <div> pantalla de aviso para el repositorio no activo</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void avisoRepoNoActivo(String iDCliente);


  /********************************************************************************
   * <div> pantalla de aviso de subida de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @param nombreFichero el del fichero bajado
   *  
   *******************************************************************************/
  public void avisoSubidaFichero(String iDCliente,String nombreFichero);

  /********************************************************************************
   * <div> pantalla de aviso de bajada de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   * @param iDClienteComp el correspondiente al cliente que comparte
   * @param nombreFichero el del fichero bajado
   *  
   *******************************************************************************/
  public void avisoBajadaFichero(String iDCliente, String iDClienteComp, String nombreFichero);

  /********************************************************************************
   * <div> pantalla de aviso de borrado de fichero </div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void avisoFicheroBorrado(String iDCliente, String nombreFichero);
  
  //errores
  /********************************************************************************
   * <div> pantalla de aviso de error en el nombre del fichero</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorNombreFichero(String iDCliente);
	
  /********************************************************************************
   * <div> pantalla de aviso de error en el nombre del fichero repetido</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorFicheroRepe (String iDCliente);
	
  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no existe</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorFicheroNoExiste (String iDCliente);
  
  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede subir</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorSubidaFichero(String iDCliente);

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede bajar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorBajadaFichero(String iDCliente);

  /********************************************************************************
   * <div> pantalla de aviso de error : el fichero no se puede borrar</div>
   * 
   * @param iDCliente el correspondiente al cliente en ejecucion.
   *  
   *******************************************************************************/
  public void errorBorradoFichero(String iDCliente);
  
  
  //listados
	
  /********************************************************************************
   * <div> Este metodo visualiza el listado de clientes del sistema pasado
   *  por parametro</div>
   * 
   * @param listado cada registro en una linea
   *  
   *******************************************************************************/
  public void verListadoClientes(String listado);

  /********************************************************************************
   * <div> Este metodo visualiza el listado pasado por parametro</div>
   * 
   * @param iDRepositorio el correspondiente al repositorio asignado al cliente.
   * @param iDCliente el correspondiente al listado
   * @param listado cada registro en una linea
   *  
   *******************************************************************************/
  public void verListadoFicherosCliente(String iDRepositorio, String iDCliente, String listado);
  
	
  }//fin interface GUIClienteInterface
