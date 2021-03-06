package pl.net.bluesoft.rnd.processtool.authorization.impl;

import javax.servlet.http.HttpServletRequest;

import pl.net.bluesoft.rnd.processtool.authorization.IAuthorizationService;
import pl.net.bluesoft.rnd.processtool.model.UserData;

/**
 * This is mock authorization service. It simply look for user with provided
 * login and if one is found, it returns its instance. It is used only 
 * for demo purpose 
 * 
 * You should provide your own, portal-based authorization implementation
 * 
 * @author mpawlak@bluesoft.net.pl
 *
 */
public class MockAuthorizationService implements IAuthorizationService 
{

	@Override
	public UserData getUserByRequest(HttpServletRequest servletRequest) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData authenticateByLogin(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData authenticateByLogin(String login, String password,
			HttpServletRequest servletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
