package controller.level;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.PMF;
import controller.Security;
import model.entity.Level;

@SuppressWarnings("serial")
public class LevelControllerAdd extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(Security.garantyAccess(req.getServletPath(), pm)){
				throw new Exception("Su usuario no tiene permisos suficientes.");
			}
			String nombre = req.getParameter("name");
			String amount = req.getParameter("amount");
			if(nombre!=null && amount!=null && !nombre.equals("")){
				Level c = new Level(nombre,Integer.parseInt(amount));
				pm.makePersistent(c);
				resp.sendRedirect("/level");
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Views/Levels/add.jsp");
				rd.forward(req, resp);
			}
		} catch(Exception e) {
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
