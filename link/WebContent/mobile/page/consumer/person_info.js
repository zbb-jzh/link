/**
 * 
 */



var vm = avalon.define({
	$id:'personinfo',
	consumerId:getUrlData('id'),
	consumer:{id:'',name:'', type:'1',area:'1', description:'',contactPerson:'',phone:'',contact:'',address:'',parentId:'',bankAccountName:'',bankName:'',bankAddress:'',bankCard:'',zipCode:'',userName:'',userPwd:''},
	submited:false,
	getConsumer:function()
	{
		
			$.ajax({
			    url: "../../../consumer/doGetByUser",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.consumerId},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		vm.consumer = res.data;
	                }else if(res.status == -114){
	                	window.location.href = "../checkpwd/check_pwd.html";
	                }else if(res.status == -110){
	                	window.location.href = "../login/login.html";
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
	},
	add:function()
	{
		
			$.ajax({
			    url: "../../../consumer/doUpdatePersonInfo",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({consumer: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		console.log('sucess');
			    		vm.getConsumer();
			    		//vm.goback();
	                }else{
	                	alert(res.data);
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
	},
	goback:function()
	{
		window.location.href = '#/consumer/list';
	}
});

vm.getConsumer();
avalon.scan();