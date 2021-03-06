package pl.net.bluesoft.rnd.processtool.usersource.impl;

import java.util.Collection;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import org.aperteworkflow.integration.liferay.utils.LiferayUserConverter;

import pl.net.bluesoft.rnd.processtool.model.UserData;
import pl.net.bluesoft.rnd.processtool.usersource.IPortalUserSource;
import pl.net.bluesoft.rnd.processtool.usersource.IUserSource;
import pl.net.bluesoft.rnd.processtool.usersource.exception.UserSourceException;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * {@link IUserSource} implementation for Liferay Portal
 * 
 * @author mpawlak@bluesoft.net.pl
 *
 */
public class LiferayUserSource implements IPortalUserSource 
{

	@Override
	public UserData getUserByLogin(String login) throws UserSourceException 
	{
        if (login == null) {
            return null;
        }
        try {
            long[] companyIds = PortalUtil.getCompanyIds();
            for (int i = 0; i < companyIds.length; ++i) {
                long ci = companyIds[i];
                try {
                    User u = UserLocalServiceUtil.getUserByScreenName(ci, login);
                    if (u != null) {
                        return LiferayUserConverter.convertLiferayUser(u);
                    }
                }
                catch (NoSuchUserException e) {
                    // continue
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new UserSourceException(e);
        }
	}

	@Override
	public UserData getUserByLogin(String login, Long companyId) throws UserSourceException 
	{
        try {
            User user = UserLocalServiceUtil.getUserByScreenName(companyId, login);
            return LiferayUserConverter.convertLiferayUser(user);
        }
        catch (Exception e) {
            throw new UserSourceException(e);
        }
	}

	@Override
	public UserData getUserByEmail(String email) 
	{
        if (email == null) 
            return null;
        
        try {
            long[] companyIds = PortalUtil.getCompanyIds();
            for (int i = 0; i < companyIds.length; ++i) {
                long ci = companyIds[i];
                try {
                    User u = UserLocalServiceUtil.getUserByEmailAddress(ci, email);
                    if (u != null) {
                        return LiferayUserConverter.convertLiferayUser(u);
                    }
                }
                catch (NoSuchUserException e) {
                    // continue
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new UserSourceException(e);
        }
	}

	@Override
	public Collection<UserData> getAllUsers() 
	{
        try {
            return LiferayUserConverter.convertLiferayUsers(UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount()));
        }
        catch (SystemException e) {
            throw new UserSourceException(e);
        }
	}

	@Override
	public UserData getUserByRequest(HttpServletRequest request) 
	{
        try {
            User user = PortalUtil.getUser(request);
            return LiferayUserConverter.convertLiferayUser(user);
        }
        catch (Exception e) 
        {
        	throw new UserSourceException("User not found", e);
        }
	}
	
	@Override
	public UserData getUserByRequest(RenderRequest request) 
	{
		/* Get HttpServletRequest from RenderRequest */
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
		
		return getUserByRequest(httpRequest);
	}



}
