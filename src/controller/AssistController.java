package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AssistDAO;
import model.AssistDTO;
import model.DepartDAO;
import model.DepartDTO;

/**
 * Servlet implementation class AssistController
 */

@WebServlet({"/assist-inputdata.do", "/assist-info.do", "/assist-register.do", "/assist-delete.do", "/assist-list.do", "/assist-update.do"})
public class AssistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssistController() {
        super();
        // TODO Auto-generated constructor stub
    }

    ArrayList<AssistDTO> alMember = null;
    AssistDTO member = null;
    HttpSession sesobj = null;
    AssistDAO dao = new AssistDAO();
    String query = null;
    
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");		

		sesobj = request.getSession();
		
		System.out.println("process");		
		
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf('/'); 
		String action = uri.substring(lastIndex + 1); 
		
		if(action.equals("assist-update.do")) {
			System.out.println(request.getParameter("name"));
			Update(request, response);
		}else if(action.equals("assist-info.do")){
			Info(request, response);
		}else if(action.equals("assist-register.do")) {
			Insert(request, response);
		}else if(action.equals("assist-delete.do")) {
			Delete(request, response);
		}else if(action.equals("assist-list.do")) {
			Inquiry(request, response);
		}else if(action.equals("assist-inputdata.do")){
			inputdata(request,response);
		}
		else
			;
		
	}//
	private void Delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		dao.delete(request, response);
		
		response.sendRedirect("assist-list.do");
	}
	private void Inquiry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		alMember = dao.list();

		request.setAttribute("alMember", alMember);
		
		RequestDispatcher dis = request.getRequestDispatcher("ad_assist.jsp");
		dis.forward(request, response);
	}
	private void Info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		member = dao.info(id);
		
		ArrayList<DepartDTO> daoDepartlist = new ArrayList<DepartDTO>();
		DepartDAO daoDepart = new DepartDAO();
		daoDepartlist = daoDepart.List();
		request.setAttribute("Depart", daoDepartlist);
		
		request.setAttribute("member", member);
		RequestDispatcher dis = request.getRequestDispatcher("ad_assistInfo.jsp"); 
		dis.forward(request, response);
	}
	private void Update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{

		dao.update(request, response);
	    response.sendRedirect("assist-list.do");
	}
	private void Insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		dao.insert(request, response);
	    response.sendRedirect("assist-list.do");
	}
	private void inputdata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		ArrayList<DepartDTO> daoDepartlist = new ArrayList<DepartDTO>();
		DepartDAO daoDepart = new DepartDAO();
		daoDepartlist = daoDepart.List();
		request.setAttribute("Depart", daoDepartlist);
		
		RequestDispatcher dis = request.getRequestDispatcher("ad_assistnew.jsp"); 
		dis.forward(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			process(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			process(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
