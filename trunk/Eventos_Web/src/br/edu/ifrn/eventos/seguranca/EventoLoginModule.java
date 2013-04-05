package br.edu.ifrn.eventos.seguranca;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@ManagedBean
public class EventoLoginModule implements LoginModule {

	@EJB
	private UsuarioDAORemote usuarioMB;
	
	private boolean commitSucceeded = false;
	private boolean succeeded = false;

	private User user;
	private Set roles = new HashSet();

	protected Subject subject;
	protected CallbackHandler callbackHandler;
	protected Map sharedState;
	private String dataSourceName;
	private String sqlUser;
	private String sqlRoles;

	/**
	 * Login do usuário.
	 */
	protected String loginInformado;

	/**
	 * Senha do usuário.
	 */
	protected String senhaInformado;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		dataSourceName = (String) options.get("dataSourceName");
		sqlUser = (String) options.get("sqlUser");
		sqlRoles = (String) options.get("sqlRoles");

	}

	@Override
	public boolean abort() throws LoginException {
		if (!succeeded) {
			return false;
		} else if (succeeded && !commitSucceeded) {
			succeeded = false;
		} else {
			succeeded = false;
			logout();
		}

		this.subject = null;
		this.callbackHandler = null;
		this.sharedState = null;
		this.roles = new HashSet();

		return succeeded;
	}

	@Override
	public boolean commit() throws LoginException {
		// adiciona o usuario no principals
		if (user != null && !subject.getPrincipals().contains(user)) {
			subject.getPrincipals().add(user);
		}
		// adiciona as roles no principals
		if (roles != null) {
			Iterator it = roles.iterator();
			while (it.hasNext()) {
				Role role = (Role) it.next();
				if (!subject.getPrincipals().contains(role)) {
					subject.getPrincipals().add(role);
				}
			}
		}

		commitSucceeded = true;
		return true;
	}

	@Override
	public boolean login() throws LoginException {
		// recupera o login e senha informados no form
		getUsernamePassword();

		// acidiona o usuario e roles no mapa de compartilhamento
		sharedState.put("javax.security.auth.principal", user);
		sharedState.put("javax.security.auth.roles", roles);

		// valida o usuario  
        validaUsuario();
		return true;
	}

	private void validaUsuario() throws LoginException {
		Usuario usuario = null; //usuarioMB.validarAutenticacao(loginInformado, senhaInformado);
		
		if(usuario != null){
			user = new User(usuario.getEmail());
			recuperaRoles();  
            user.setRoles(roles); 
		return;  
		} else {  
        throw new LoginException("Senha ou login Inválido.");  
    }  
		
	}

	private void recuperaRoles() {
		roles.add("Role_Participante");
		
	}

	private void getUsernamePassword() throws LoginException {
		if (callbackHandler == null)
			throw new LoginException(
					"Error: no CallbackHandler available to garner authentication information from the user");

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("Login");
		callbacks[1] = new PasswordCallback("Senha", false);
		try {
			callbackHandler.handle(callbacks);
			loginInformado = ((NameCallback) callbacks[0]).getName();
			char[] tmpPassword = ((PasswordCallback) callbacks[1])
					.getPassword();
			senhaInformado = new String(tmpPassword);
			((PasswordCallback) callbacks[1]).clearPassword();
		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException(
					"Error: "
							+ uce.getCallback().toString()
							+ " not available to garner authentication information from the user");
		}
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().removeAll(roles);  
        subject.getPrincipals().remove(user);  
        return true;
	}

}
