package controller.access;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.PMF;
import controller.Security;
import model.entity.Access;

@SuppressWarnings("serial")
public class AccessControllerEdit extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Access c = pm.getObjectById(Access.class, Long.parseLong(req.getParameter("ID")));
			String stat = req.getParameter("stat");
			if (stat == null) {
				req.setAttribute("access", c);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/edit.jsp");
				rd.forward(req, resp);
			} else {
				if(c.getRoleId()==null){
					c.setRoleId(Long.parseLong(req.getParameter("roleId")));
				}
				if(c.getResourceId()==null){
					c.setResourceId(Long.parseLong(req.getParameter("resourceId")));
				}
				c.setStatus(Boolean.parseBoolean(stat));
				resp.sendRedirect("/access");
			}
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/error.jsp");
			req.setAttribute("message", e.getMessage());
			rd.forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}