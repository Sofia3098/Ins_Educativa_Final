package controller.pension;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.*;
import model.entity.Pension;

@SuppressWarnings("serial")
public class PensionControllerAdd extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String nombre = req.getParameter("name");
			String monto = req.getParameter("amount");
			if (nombre == null)
				nombre = "";
			if (nombre.equals("")) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Pensions/add.jsp");
				rd.forward(req, resp);
			} else {
				Pension pens = new Pension(nombre, Integer.parseInt(monto));
				pm.makePersistent(pens);
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