


var vm = avalon.define({
	$id:'goodsall',
	totalPageCount:-1,
    currentPage:1,
    count:10,
	name:'',
	goodsList:[],
	sellStatus:'',
	isDeals:'',
	shopCategoryId:'',
	secendShopCategoryList:[],
	hasAdd:false,
	hasDelete:false,
	hasUpdate:false,
	
	getGoodsList:function(pageNumber, pageSize)
	{
        var para = {pageNumber: pageNumber, pageSize: pageSize, titleKeyword:vm.name, shopCategoryId:vm.shopCategoryId,sellStatus:vm.sellStatus,isDeals:vm.isDeals};
		$.ajax({
		    url: "/link/goods/doPage",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: JSON.stringify(para),    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		vm.goodsList = res.data.list;
	                
		    		for(var i=0; i<vm.goodsList.length; i++){
                        if(vm.goodsList[i].showUrl != undefined){
                            vm.goodsList[i].showUrl = vm.goodsList[i].showUrl.split(',')[0];
                        }
                    }
		    		
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
	                        	vm.getGoodsList(vm.currentPage, vm.count);
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
	getSecendShopCategory:function()
	{
		$.ajax({
		    url: "/link/category/dosearchSec",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		vm.secendShopCategoryList = res.data;
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
        vm.getGoodsList(1, vm.count);
    },
    preset : function () {
        vm.name = '';
        vm.getGoodsList(vm.currentPage, vm.count);
    },
    
    toAU : function (id) {
        window.location = '#/goods/au?id=' + id;
    },
    changeSellStatus:function(goods, sellStatus)
    {
    	$.ajax({
		    url: "/link/goods/changeSellStatus",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:goods.id, sellStatus:sellStatus},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		goods.sellStatus = sellStatus;
		    		console.log('success');
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
		    url: "/link/goods/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		if(vm.goodsList.length == 1){
		    			vm.currentPage = 1;
                        vm.getGoodsList(1, vm.count);
                    }else{
                        vm.getGoodsList(vm.currentPage, vm.count);
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
vm.getGoodsList(1, 10);
vm.getSecendShopCategory();
avalon.scan();