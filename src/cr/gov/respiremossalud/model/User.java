package cr.gov.respiremossalud.model;

public class User {
	private String nombre, sinFumar, facebookId, tipoString, id;
	private int puntos, tipo;
	public boolean selected;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSinFumar() {
		return sinFumar;
	}
	public void setSinFumar(String sinFumar) {
		this.sinFumar = sinFumar;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTipoString() {
		return tipoString;
	}
	public void setTipoString(String tipoString) {
		this.tipoString = tipoString;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static final String FB_ID = "facebookId";
	public static final String TYPE = "tipo";
	public static final String SIN_FUMAR = "sinFumar";
	public static final int TYPE_NO = 1;
	public static final int TYPE_EX = 2;
	public static final int TYPE_YES = 3;
	public static final int TYPE_LEAVING = 4;
	public static final String TYPE_NO_STR = "No fuma";
	public static final String TYPE_EX_STR = "Ex fumador";
	public static final String TYPE_YES_STR = "Fumador";
	public static final String TYPE_LEAVING_STR = "Dejando de Fumar";
	public static final String NAME = "nombre";

}
