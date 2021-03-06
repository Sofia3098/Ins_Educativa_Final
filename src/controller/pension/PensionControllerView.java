package controller.pension;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import controller.PMF;
import controller.Security;
import model.entity.Pension;

@SuppressWarnings("serial")
public class PensionControllerView extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (Security.garantyAccess(req.getServletPath(), pm)) {
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String query = " select from " + Pension.class.getName() + " where id == "
					+ Long.parseLong(req.getParameter("ID"));
			List<Pension> pensions = (List<Pension>) pm.newQuery(query).execute();
			req.setAttribute("pension", pensions.get(0));
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Pensions/view.jsp");
			rd.forward(req, resp);
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/error.jsp");
			req.setAttribute("message", e.getMessage());
			rd.forward(req, resp);
		}
	}
}