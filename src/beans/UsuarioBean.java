package beans;

public class UsuarioBean {

	private int id;
	private String login;
	private String senha;
	private String email;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String ibge;
	private String fotoBase64;
	private String miniaturaBase64;
	private String arquivoBase64;
	private String contentType;
	private String contentTypeArquivo;
	private String tempAvatar;
	private boolean atualizarImagem = true;
	private boolean atualizarPdf = true;
	
	public String getTempAvatar() {
		tempAvatar = "data:"+ contentType +";base64,"+ fotoBase64;
		return tempAvatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getMiniaturaBase64() {
		return miniaturaBase64;
	}

	public void setMiniaturaBase64(String miniaturaBase64) {
		this.miniaturaBase64 = miniaturaBase64;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getArquivoBase64() {
		return arquivoBase64;
	}

	public void setArquivoBase64(String arquivoBase64) {
		this.arquivoBase64 = arquivoBase64;
	}

	public String getContentTypeArquivo() {
		return contentTypeArquivo;
	}

	public void setContentTypeArquivo(String contentTypeArquivo) {
		this.contentTypeArquivo = contentTypeArquivo;
	}
	
	public boolean isAtualizarImagem() {
		return atualizarImagem;
	}

	public void setAtualizarImagem(boolean atualizarImagem) {
		this.atualizarImagem = atualizarImagem;
	}

	public boolean isAtualizarPdf() {
		return atualizarPdf;
	}

	public void setAtualizarPdf(boolean atualizarPdf) {
		this.atualizarPdf = atualizarPdf;
	}

	@Override
	public String toString() {
		return "UsuarioBean [id=" + id + ", login=" + login + ", senha=" + senha + ", email=" + email + ","
				+ " rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado="
				+ estado + ", ibge=" + ibge + "]";
	}

}
