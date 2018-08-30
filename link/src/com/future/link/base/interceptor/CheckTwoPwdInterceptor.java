package com.future.link.base.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.future.link.common.Result;
import com.future.link.user.model.User;
import com.future.link.utils.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class CheckTwoPwdInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation invocation) {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=invocation.getController().getRequest();
        Integer checkflag = (Integer)httpServletRequest.getSession().getAttribute(Constant.CHECKPWD_FLAG);
        if(checkflag == null){
           invocation.getController().renderJson(Result.flomErrorData(Constant.CHECK_TWO_PWD_ERR));
           return;
            
        }
        httpServletRequest.getSession().setAttribute(Constant.CHECKPWD_FLAG, checkflag);
        invocation.invoke();
	}

}
