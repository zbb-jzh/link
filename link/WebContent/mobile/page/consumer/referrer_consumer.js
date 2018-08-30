
var vm = avalon.define({
	$id:'referrers',
	
	referrerList:[],
	referrerName:'',
	getReferrers: function () {
    	$.ajax({
		    url: "/link/consumer/doSearchReferrers",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		vm.referrerList = res.data;
		    		var user = JSON.parse(localStorage.getItem("user"));
		            vm.referrerName = user.name;
                }else if(res.status == -110){
                	window.location.href = "../login/login.html";
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

vm.getReferrers();
avalon.scan();
