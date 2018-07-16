package controller.users;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.PMF;
import controller.Security;
import model.entity.*;


@SuppressWarnings("serial")
public class UsersControllerEdit extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			User c = pm.getObjectById(User.class,Long.parseLong(req.getParameter("ID")));
			String email=req.getParameter("email");
			if (email == null) {
				req.setAttribute("user", c);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/edit.jsp");
				rd.forward(req, resp);
			}else {
				if(c.getRoleId()==null){
					c.setRoleId(Long.parseLong(req.getParameter("roleId")));
				}
				c.setEmail(email);
				resp.sendRedirect("/user");	
			}
		}catch(Exception e){
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