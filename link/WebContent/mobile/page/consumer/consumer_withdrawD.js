/**
 * 
 */

var vm = avalon.define({
	$id:'consumerwithdrawdetail',
	/*consumerId:getUrlData('id'),*/
	consumer:{id:'',name:'',bankAccountName:'',bankName:'',bankAddress:'',bankCard:'',userName:'',prizeCoin:'0', withdraws:'0'},
	withdrawCount:'',
	submited:false,
	isUpdate:false,
	getConsumer:function()
	{
		
			vm.isUpdate = true;
			$.ajax({
			    url: "../../../consumer/doGetByUserJJB",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		vm.consumer = res.data;
			    		vm.withdrawCount = vm.consumer.prizeCoin - vm.consumer.withdraws;
	                }else if(res.status == -110){
	                	window.location.href = "../login/login.html";
	                }else if(res.status == -114){
	                	window.location.href = "../checkpwd/check_pwd.html";
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
	},
	
	removeInput:function(){
		vm.withdrawCount = '';
	},
	goback:function()
	{
		window.location.href = '#/consumer/list';
	}
});

vm.getConsumer();
avalon.scan();