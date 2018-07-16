package controller.enrollment;

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
import model.entity.Enrollment;
import model.entity.Level;

@SuppressWarnings("serial")
public class EnrollmentControllerAdd extends HttpServlet {

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String student = req.getParameter("student");
			if (student!=null&&!student.equals("")) {
				Long level = Long.parseLong(req.getParameter("levelId"));
				Enrollment pens = new Enrollment(student, level);
				pm.makePersistent(pens);
				resp.sendRedirect("/enroll");
			} else {
				List<Level> res = (List<Level>) pm.newQuery(Level.class).execute();
				req.setAttribute("levels", res);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Enrollments/add.jsp");
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