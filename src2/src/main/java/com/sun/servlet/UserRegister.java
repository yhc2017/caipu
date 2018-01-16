package com.sun.servlet;

import com.sun.dao.UserDAO;
import com.sun.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by sunyang on 2016/12/8.
 * �û�ע���servlet
 */
public class UserRegister extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // ���ñ����ʽΪ UTF-8
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        // ǰ̨�õ� �û���������

        UserVo user = new UserVo();
        // ʵ����һ��VO����
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        // ��ǰ̨�õ������ݴ���֣�
        UserDAO userDao = new UserDAO();
        // ʵ����һ�����ݿ��������
        userDao.insertUser(user);
        // ���������û�����
        request.getRequestDispatcher("/userlogin.jsp").forward(request,
                response);
        // ת����¼ҳ��
    }
}
