package br.edu.ifrn.eventos.dao;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Permissao;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Stateless
public class CadastroUsuario implements CadastroUsuarioRemote {

	@PersistenceContext
	private EntityManager em;

	


	@Override
	public Usuario CadastrarUsuario(Usuario usuario) throws MessagingException,
			UnsupportedEncodingException {
		try{
		usuario = em.merge(usuario);
		//enviarEmailConfirmacao(usuario.getEmail(), usuario.getNome(),
		//usuario.getChaveConfirmacao());
		}catch(Exception e){
			return null;
		}
		
		return usuario;
	}
	

	// verificar esse método
	public void enviarEmailConfirmacao(String para, String nome, String chave)
			throws MessagingException, UnsupportedEncodingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"eventualizesistema@gmail.com",
								"eventualizeifrn");
					}
				});
		try {

			MimeMessage message = new MimeMessage(session);

			// Obtendo nome, porta e contexto do servidor

			message.setFrom(new InternetAddress("eventualizesistema@gmail.com"));

			final String enderecoServidor = "http://localhost:8080/Eventos_Web2";

			String html = "<p><h2>Olá " + nome + ", </h2></p> ";
			html += "<p><h4>Seja bem-vindo(a) a Eventualize!</h4></p>";
			html += "<p><h4>Atenção: sua conta encontra-se desativada. Ative já a sua conta clicando <a href='http://"
					+ enderecoServidor
					+ "/login.jsf?chave="
					+ chave
					+ "'>AQUI</a>.</h4></p>";
			html += "<p></br><h4>Atenciosamente,</h4></p>";
			html += "<p><h4>Eventualize - você atualizado no que a de novo no mercado</h4></p>";

			message.addRecipient(RecipientType.TO, new InternetAddress(para));
			message.setSubject("Confirmação de Cadastro Eventualize");
			message.setContent(html, "text/html");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}


	@Override
	public Usuario AlterarUsuario(Usuario usuario) throws MessagingException{
		return this.em.merge(usuario);
	}
	
}
