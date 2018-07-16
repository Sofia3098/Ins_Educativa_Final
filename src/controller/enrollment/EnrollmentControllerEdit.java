package controller.enrollment;

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
public class EnrollmentControllerEdit extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (Security.garantyAccess(req.getServletPath(), pm)) {
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Enrollment c = pm.getObjectById(Enrollment.class, Long.parseLong(req.getParameter("ID")));
			String student = req.getParameter("student");
			if (student != null && !student.equals("")) {
				if (c.getLevelId() == null) {
					c.setLevelId(Long.parseLong(req.getParameter("levelId")));
				}
				c.setStudent(student);
				resp.sendRedirect("/enroll");
			} else {
				req.setAttribute("current", c);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Enrollments/edit.jsp");
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