package controller.users;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.PMF;
import controller.Security;
import model.entity.Role;
import model.entity.User;

@SuppressWarnings("serial")
public class UsersControllerAdd extends HttpServlet {

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String email = req.getParameter("email");
			if (email==null) {
				List<Role> roles = (List<Role>) pm.newQuery(Role.class).execute();
				req.setAttribute("roles", roles);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/add.jsp");
				rd.forward(req, resp);
			} else {
				boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
				Long role = Long.parseLong(req.getParameter("roleId"));
				DateFormat c=new SimpleDateFormat("yyyy-MM-dd");
				Date d = c.parse(req.getParameter("date"));
				User pens = new User(role, email, gender, d);
				pm.makePersistent(pens);
				resp.sendRedirect("/user");
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