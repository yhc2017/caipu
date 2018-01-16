package com.sun.servlet;

import com.sun.dao.UserDAO;
import com.sun.vo.UserListVector;
import com.sun.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunyang on 2016/12/8.
 * �û���½��servlet
 */
public class Userlogin extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        // ǰ̨�õ��û�����
        UserDAO userDao = new UserDAO();
        UserVo user = userDao.judgeUserPassword(userName, userPassword);
        // ���÷����ж��û��Ƿ����
        String message = "�û������������";
        if (user == null) {
            // ����û������ڣ����µ�¼
            request.setAttribute("message", message);
            request.getRequestDispatcher("/userlogin.jsp").forward(request,
                    response);

        } else {
            // ����û����ڣ��������ݣ����������б���ʾҳ��
            UserListVector ul=new UserListVector();//ÿ��½һ���û������һ��
            ul.addUser(user);
            String id=String.valueOf(user.getUserId());
            request.getRequestDispatcher("/rec_sys.jsp?page=1&id="+id).forward(request,
                    response);
        }

    }
}
