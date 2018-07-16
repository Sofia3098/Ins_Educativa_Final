package controller.pension;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.PMF;
import controller.Security;
import model.entity.Pension;

@SuppressWarnings("serial")
public class PensionControllerEdit extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			Pension c = pm.getObjectById(Pension.class, Long.parseLong(req.getParameter("ID")));
			String name = req.getParameter("name");
			if (name == null) {
				req.setAttribute("pension", c);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Pensions/edit.jsp");
				rd.forward(req, resp);
			} else {
				c.setName(name);
				c.setAmount(Integer.parseInt(req.getParameter("amount")));
				c.setDate(new Date());
				resp.sendRedirect("/pension");
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