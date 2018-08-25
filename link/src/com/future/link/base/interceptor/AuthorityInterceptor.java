package com.future.link.base.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.future.link.common.Result;
import com.future.link.user.model.User;
import com.future.link.utils.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AuthorityInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation invocation) {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=invocation.getController().getRequest();
        User user=(User)httpServletRequest.getSession().getAttribute(Constant.SESSION_USER);
        if(user==null){
           invocation.getController().renderJson(Result.flomErrorData(Constant.LOGIN_INVALID));
           return;
            
        }
        httpServletRequest.getSession().setAttribute(Constant.SESSION_USER, user);
        invocation.invoke();
	}

}
