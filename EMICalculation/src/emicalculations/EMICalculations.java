package emicalculations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EMICalculations extends HttpServlet {

	public void init() throws ServletException {
		System.out.println("Init servlet");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Float principal = Float.parseFloat(request.getParameter("principal"));
		Float roi = Float.parseFloat(request.getParameter("roi"));
		Float timePeriod = Float.parseFloat(request.getParameter("timePeriod"));

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");

		System.out.println(roi + " roi");
		roi = ((roi / 100)) / 12;
		String stringResponse = "[";
		for (int index = 1; index <= timePeriod; index++) {
			Float paymentAmount = (float) ((roi * principal) * Math.pow((1 + roi), timePeriod)
					/ ((Math.pow((1 + roi), timePeriod)) - 1));
			Float principalAmount = (float) (paymentAmount * Math.pow((1 + roi), -(1 + timePeriod - index)));
			Float intrestAmount = paymentAmount - principalAmount;
			Float outstanding = (intrestAmount / roi) - principalAmount;
			stringResponse += (index == 1) ? "" : ",";
			stringResponse += "{ \"paymentAmount\":" + (paymentAmount) + ",\"principalAmount\":"
					+ (principalAmount) + ",\"intrestAmount\":" + (intrestAmount)
					+ ", \"outstanding\": " + (outstanding) + "}";
		}
		stringResponse += "]";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(stringResponse);

	}

	public void destroy() {
		System.out.println("destroy servlet");
	}

}
