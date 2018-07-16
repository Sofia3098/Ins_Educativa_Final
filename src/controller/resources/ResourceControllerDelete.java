package controller.resources;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.PMF;
import controller.Security;
import model.entity.Access;
import model.entity.Resource;

@SuppressWarnings("serial")
public class ResourceControllerDelete extends HttpServlet {

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Long id = Long.parseLong(req.getParameter("ID"));
			String query = "select from "+Access.class.getName()+" where resourceId =="+id+"";
			List<Access> Accesses = (List<Access>) pm.newQuery(query).execute();
			for (int i = 0; i < Accesses.size(); i++) {
				Accesses.get(i).setResourceId(null);
			}
			Resource c=pm.getObjectById(Resource.class,Long.parseLong(req.getParameter("ID")));
			pm.deletePersistent(c);
			resp.sendRedirect("/resource");
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/error.jsp");
			req.setAttribute("message",e.getMessage());
			rd.forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}