

var vm = avalon.define({
	$id:'financiallist',
	totalPageCount:-1,
    currentPage:1,
    count:10,
	name:'',
	type:'',
	startTime:'',
    endTime:'',
	startDate:'',
	endDate:'',
	withdrawalList:[],
	withdraw:{id:'',consumerName:'', withdrawCount:'',bankAccountName:'',bankCard:'',bankName:'',bankAddress:''},
	showDetail:false,
	
	getWithdrawalList:function(pageNumber, pageSize)
	{
		if(!vm.changeDate()){
			return;
		}
			
		$.ajax({
		    url: "../consumer/doPageWithdraw",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {pageNumber:pageNumber, pageSize:pageSize, name:vm.name, type:vm.type, startTime:vm.startTime, endTime:vm.endTime},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		vm.withdrawalList = res.data.list;
	                
	                var morePageCount = 0;
	                if(res.data.totalPage > 0){
	                    morePageCount = res.data.totalPage - res.data.pageNumber;
	                }

	                if (vm.totalPageCount == -1 || (morePageCount + vm.currentPage) != vm.totalPageCount) {
	                	vm.totalPageCount = morePageCount + vm.currentPage;

	                    var options = {
	                        bootstrapMajorVersion : 3,
	                        currentPage : vm.currentPage,
	                        totalPages : vm.totalPageCount,
	                        onPageClicked : function(e, originalEvent, type, page) {
	                        	vm.currentPage = page;
	                        	vm.getWithdrawalList(vm.currentPage, vm.count);
	                        },
	                        onPageChanged : function(event, oldPage, newPage) {
	                        }};
	                    $('.pagination').bootstrapPaginator(options);
	                }
                }
		        
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
	},
	 search :function () {
        vm.totalPageCount=-1;
        vm.currentPage=1;
        vm.getWithdrawalList(1, vm.count);
    },
     preset : function () {
        vm.name = '';
        vm.type ='';
        vm.startDate = '';
        vm.endDate = '';
        vm.getWithdrawalList(vm.currentPage, vm.count);
    },
    changeDate:function()
    {
    	console.log(1111);
    	console.log(vm.startDate);
    	var str1 = vm.startDate.replace(/-/g,"/");
        var str2 = vm.endDate.replace(/-/g,"/");
        vm.startTime = new Date(str1).getTime();
        vm.endTime = new Date(str2).getTime();
        console.log(vm.startTime);
        if(vm.startTime > vm.endTime){
            alert('起始时间应小于结束时间');
            return false;
        }
        if(str1==""){
        	vm.startTime = null;
        }
        if(str2==""){
        	vm.endTime = null;
        }
        return true;
    },
     toAU : function (id) {
        window.location = '#/consumer/au?id=' + id;
    },
    toShowDetail:function(obj){
    	vm.withdraw.id = obj.id;
    	vm.withdraw.consumerName = obj.consumerName;
    	vm.withdraw.withdrawCount = obj.withdrawCount;
    	vm.withdraw.bankAccountName=obj.bankAccountName;
    	vm.withdraw.bankCard = obj.bankCard;
    	vm.withdraw.bankName = obj.bankName;
    	vm.withdraw.bankAddress = obj.bankAddress;
    	vm.showDetail = true;
    },
    hideShow:function(){
    	vm.showDetail = false;
    },
    paysalary:function(id)
    {
    	$.ajax({
		    url: "../consumer/paySalary",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		alert(支付成功);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    onDelete:function(id)
    {
    	$.ajax({
		    url: "../consumer/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		if(vm.consumerList.length == 1){
		    			vm.currentPage = 1;
                        vm.getConsumerList(1, vm.count);
                    }else{
                        vm.getConsumerList(vm.currentPage, vm.count);
                    }
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    }
});
vm.getWithdrawalList(1, 10);
avalon.scan();
$('.input-datepicker').datetimepicker(
        {
        }).on('changeDate', function (ev) {
    $(this).datetimepicker('hide');
});