package controller.level;

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
import model.entity.Course;
import model.entity.Enrollment;
import model.entity.Level;


@SuppressWarnings("serial")
public class LevelControllerDelete extends HttpServlet {

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Long id = Long.parseLong(req.getParameter("ID"));
			String query = "select from "+Enrollment.class.getName()+" where levelId =="+id+"";
			List<Enrollment> res = (List<Enrollment>) pm.newQuery(query).execute();
			for (int i = 0; i < res.size(); i++) {
				res.get(i).setLevelId(null);
			}
			query = "select from "+Course.class.getName()+" where levelId =="+id+"";
			List<Course> res1 = (List<Course>) pm.newQuery(query).execute();
			for (int i = 0; i < res1.size(); i++) {
				res1.get(i).setLevelId(null);
			}
			Level c=pm.getObjectById(Level.class,id);
			pm.deletePersistent(c);
			resp.sendRedirect("/level");
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