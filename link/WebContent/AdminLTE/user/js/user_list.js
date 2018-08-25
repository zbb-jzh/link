


var vm = avalon.define({
	$id:'userlist',
	totalPageCount:-1,
    currentPage:1,
    count:10,
	name:'',
	userList:[],
	hasAdd:false,
	hasDelete:false,
	hasUpdate:false,
	
	getUserList:function(pageNumber, pageSize)
	{
		$.ajax({
		    url: "../user/doPage",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {pageNumber:pageNumber, pageSize:pageSize, name:vm.name},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		vm.userList = res.data.list;
	                
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
	                        	vm.getUserList(vm.currentPage, vm.count);
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
        vm.getUserList(1, vm.count);
    },
     preset : function () {
        vm.name = '';
        vm.getUserList(vm.currentPage, vm.count);
    },
    
     toAU : function (id) {
        window.location = '#/user/au?id=' + id;
    },
    onDelete:function(id)
    {
    	$.ajax({
		    url: "../user/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 1) {
		    		if(vm.userList.length == 1){
		    			vm.currentPage = 1;
                        vm.getUserList(1, vm.count);
                    }else{
                        vm.getUserList(vm.currentPage, vm.count);
                    }
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    }
	
});
if(btns[0].has){
	vm.hasAdd = true;
}
if(btns[1].has){
	vm.hasDelete = true;
}
console.log(vm.hasDelete);
if(btns[2].has){
	vm.hasUpdate = true;
}

vm.getUserList(1, 10);
avalon.scan();