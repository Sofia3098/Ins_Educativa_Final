package controller.users;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.*;

@SuppressWarnings("serial")
public class UsersControllerLogin extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserService us=UserServiceFactory.getUserService();
		User user= us.getCurrentUser();
		if (user==null) {
			resp.sendRedirect(us.createLoginURL("/user/login"));
		} else {
			req.setAttribute("user", user);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/login.jsp");
			rd.forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}