/**
 * 
 */

var vm = avalon.define({
	$id:'consumerwithdraw',
	consumerId:getUrlData('id'),
	consumer:{name:'',bankAccountName:'',bankName:'',bankAddress:'',bankCard:'',userName:'',prizeCoin:'0',withdrawCount:''},
	submited:false,
	isUpdate:false,
	getConsumer:function()
	{
		
			vm.isUpdate = true;
			$.ajax({
			    url: "../../../consumer/doGetByUser",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.consumerId},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		vm.consumer = res.data;
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
			    url: "../../../consumer/doAddWithDraw",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({withdraw: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		console.log('sucess');
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
vm.getConsumerTreeList();
avalon.scan();