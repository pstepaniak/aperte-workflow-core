package pl.net.bluesoft.rnd.processtool.roles.impl;

import java.util.Collection;

import org.hibernate.Session;

import pl.net.bluesoft.rnd.processtool.dao.UserRoleDAO;
import pl.net.bluesoft.rnd.processtool.model.UserData;
import pl.net.bluesoft.rnd.processtool.model.UserRole;
import pl.net.bluesoft.rnd.processtool.plugins.ProcessToolRegistry;
import pl.net.bluesoft.rnd.processtool.roles.IUserRolesManager;
import pl.net.bluesoft.rnd.processtool.roles.exception.RoleCreationExceptionException;
import pl.net.bluesoft.rnd.processtool.roles.exception.RoleNotFoundException;
import pl.net.bluesoft.rnd.processtool.roles.exception.UserWithRoleNotFoundException;

public class AperteUserRolesManager implements IUserRolesManager 
{
	private ProcessToolRegistry reg;

	@Override
	public boolean isRoleExist(String roleName) 
	{
		return false;
	}

	@Override
	public void createRole(String roleName, String description) throws RoleCreationExceptionException 
	{
		UserRoleDAO userRoleDao = reg.getUserRoleDao(getSession());
		
		UserRole userRole = new UserRole();
		userRole.setName(roleName);
		userRole.setDescription(description);
		
		userRoleDao.saveOrUpdate(userRole);

	}

	@Override
	public void updateRoleDescription(String roleName, String description) throws RoleNotFoundException 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getRoleNamesForCompanyId(Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<UserData> getUsersByRole(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getAllRolesNames() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private Session getSession()
	{
		Session session = reg.getSessionFactory().getCurrentSession();
		if(session == null)
			session = reg.getSessionFactory().openSession();
		
		return session;
	}

	@Override
	public UserData getFirstUserWithRole(String roleName)
			throws RoleNotFoundException, UserWithRoleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
