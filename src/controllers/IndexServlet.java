package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;
/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final int PAGE_COUNT = 5;

        EntityManager em = DBUtil.createEntityManager();

        // ページ番号を取得（デフォルトは1ページ目）
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        // 表示するタスクを取得
        List<Task> tsk = em.createNamedQuery("getAllTasks", Task.class)
                .setFirstResult(PAGE_COUNT * (page - 1))
                .setMaxResults(PAGE_COUNT)
                .getResultList();

        // 件数を取得
        long task_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("tasklist", tsk);
        request.setAttribute("task_count", task_count);     // 全件数
        request.setAttribute("page_count", PAGE_COUNT);     // 1ページの件数
        request.setAttribute("page", page);                 // ページ番号

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasklist/index.jsp");
        rd.forward(request, response);
    }
}
