package controller.course;

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
import model.entity.Level;

@SuppressWarnings("serial")
public class CourseControllerAdd extends HttpServlet {

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String name = req.getParameter("name");
			String dep = req.getParameter("dep");
			String teacher = req.getParameter("teacher");
			if (name!=null&&dep!=null&&teacher!=null&&!name.equals("")) {
				Long level = Long.parseLong(req.getParameter("levelId"));
				Course pens = new Course(name, dep, level, teacher);
				pm.makePersistent(pens);
				resp.sendRedirect("/course");
			} else {
				List<Level> res = (List<Level>) pm.newQuery(Level.class).execute();
				req.setAttribute("levels", res);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Courses/add.jsp");
				rd.forward(req, resp);
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