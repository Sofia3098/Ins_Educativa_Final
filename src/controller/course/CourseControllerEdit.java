package controller.course;

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
public class CourseControllerEdit extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (Security.garantyAccess(req.getServletPath(), pm)) {
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Course c = pm.getObjectById(Course.class, Long.parseLong(req.getParameter("ID")));
			String name = req.getParameter("name");
			String depa = req.getParameter("depa");
			String teach = req.getParameter("teach");
			if (name != null && teach != null && depa!= null && !name.equals("")) {
				if (c.getLevelId() == null) {
					c.setLevelId(Long.parseLong(req.getParameter("levelId")));
				}
				c.setName(name);
				c.setDepartament(depa);
				c.setTeacher(teach);
				resp.sendRedirect("/course");
			} else {
				req.setAttribute("course", c);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Courses/edit.jsp");
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