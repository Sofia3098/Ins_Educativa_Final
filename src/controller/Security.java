package controller;

import java.util.List;
import javax.jdo.PersistenceManager;
import com.google.appengine.api.users.UserServiceFactory;
import model.entity.*;

public class Security {
	static com.google.appengine.api.users.User uGoogle;
	static List<User> uSearch;
	static List<Resource> rSearch;
	static boolean gave;

	private static boolean niv_1Login() throws Exception {
		uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		gave =false;
		if (uGoogle == null) {
			return false;
		}
		if (uGoogle.getEmail().equals("cramirez@unsa.edu.pe") ||uGoogle.getEmail().equals("gtamo@unsa.edu.pe")||uGoogle.getEmail().equals("mlaymea@unsa.edu.pe")||uGoogle.getEmail().equals("spenares@unsa.edu.pe") ) {
			gave = true;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private static boolean niv_2User(PersistenceManager pm) throws Exception {
		if (niv_1Login()) {
			String query = " select from " + User.class.getName() + " where email == '" + uGoogle.getEmail() + "'"
					+ "&& status==true";
			uSearch = (List<User>) pm.newQuery(query).execute();
			if (!uSearch.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static boolean niv_3Resource(String servletPath, PersistenceManager pm) throws Exception {
		if (niv_2User(pm)) {
			String query = " select from " + Resource.class.getName() + " where name == '" + servletPath + "'"
					+ "&& status==true";
			rSearch = (List<Resource>) pm.newQuery(query).execute();
			if (!rSearch.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static boolean niv_4Access(String servletPath, PersistenceManager pm) throws Exception {
		if (niv_3Resource(servletPath, pm)) {
			String query = " select from " + Access.class.getName() + " where roleId ==" + uSearch.get(0).getRoleId()
					+ "&& resourceId==" + rSearch.get(0).getId() + " && status==true";
			List<Access> aSearch = (List<Access>) pm.newQuery(query).execute();
			if (!aSearch.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static boolean garantyAccess(String servletPath, PersistenceManager pm) throws Exception {
		return !(niv_4Access(servletPath, pm) || gave);
	}
}
