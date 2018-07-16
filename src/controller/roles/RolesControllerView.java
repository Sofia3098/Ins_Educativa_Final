package controller.roles;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import controller.PMF;
import controller.Security;
import model.entity.Role;

@SuppressWarnings("serial")
public class RolesControllerView extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String query = " select from " +Role.class.getName()+ " where id == "+Long.parseLong(req.getParameter("ID"))+"";
			List<Role> res = (List<Role>)pm.newQuery(query).execute();
			Role aux = res.get(0);
			req.setAttribute("current",aux);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/view.jsp");
			rd.forward(req, resp);
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/error.jsp");
			req.setAttribute("message", e.getMessage());
			rd.forward(req, resp);
		}
	}
}