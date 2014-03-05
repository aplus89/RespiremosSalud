package cr.gov.respiremossalud.model;

public class Message {
	private String text, name, id;
	private boolean aceptado;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public static final String CLASS = "Message";
	public static final String TEXT = "text";
	public static final String FROM = "from";
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
