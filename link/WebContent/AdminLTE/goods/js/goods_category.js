


var tagTreeSetting = null;
var tagTree = null;

var vm = avalon.define({
	$id:'goodscategory',
	submited:false,
	goodsCategoryList:[],
	category:{id:'', name:'', parentId:''},
	name:'',
	hasAdd:false,
	hasDelete:false,
	hasUpdate:false,
	doAU:function()
	{
		vm.submited = true;
		if(vm.category.name==""){
			return;
		}
		if(vm.category.id){
			
			if(vm.category.id == vm.category.parentId){
                alert('不能选自身为父类');
                return;
            }
			
			$.ajax({
			    url: "/link/category/doUpdate",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({category: vm.category}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 100) {
			    		console.log('sucess');
			    		vm.initTree();
			    		vm.doGetParent();
			    		vm.submited = false;
			    		vm.category = {id:'', name:'', parentId:''};
	                }else{
	                	alert(res.data);
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
			
		}else{
			
			$.ajax({
			    url: "/link/category/doAdd",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({category: vm.category}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 100) {
			    		console.log('sucess');
			    		vm.initTree();
			    		vm.doGetParent();
			    		vm.submited = false;
			    		vm.category = {id:'', name:'', parentId:''};
	                }else{
	                	alert(res.data);
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
		}
		
	},
	doDelete:function()
	{
		$.ajax({
		    url: "/link/category/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:vm.category.id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		console.log('sucess');
		    		vm.initTree();
		    		vm.doGetParent();
		    		vm.submited = false;
		    		vm.category = {id:'', name:'', parentId:''};
                }else{
                	alert(res.data);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
	},
	toAdd:function()
	{
		vm.category = {id:'', name:'', parentId:''};
	},
	init:function()
	{
		tagTreeSetting = {
	            edit: {
	                drag: {
	                    autoExpandTrigger: true,
	                    // prev: dropPrev,
	                    // inner: dropInner,
	                    // next: dropNext
	                },
	                enable: true,
	                showRemoveBtn: false,
	                showRenameBtn: false
	            },
	            data: {
	                simpleData: {
	                    enable: true
	                },
	                key: {
	                    children: "nodes"
	                }
	            },
	            callback: {
	                onClick: vm.tagTreeOnClick,
	                // beforeDrag: beforeDrag,
	                // beforeDrop: beforeDrop,
	                // beforeDragOpen: beforeDragOpen,
	                // onDrag: onDrag,
	                // onDrop: onDrop,
	                // onExpand: onExpand
	            }
	        };
	        vm.initTree();
	        vm.doGetParent();
	},
	tagTreeOnClick : function (e, treeId, treeNode) {
        vm.currentNode = treeNode;
        var parentNode = treeNode.getParentNode();
        if(parentNode){
        	vm.category.parentId = parentNode.id;
        } else {
        	vm.category.parentId = "";
        }
        vm.category.id = treeNode.id;
        vm.category.name = treeNode.name;
    },
	doGetParent:function()
	{
		$.ajax({
		    url: "/link/category/doGetParent",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		vm.goodsCategoryList = res.data;
                }else{
                	alert(res.data);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
	},
	initTree:function()
	{
        $.ajax({
		    url: "/link/category/doTree",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		console.log('sucess');
		    		tagTree = $.fn.zTree.init($("#goodsCateory"), tagTreeSetting, res.data);
		    		tagTree.expandAll(true);
		            var nodes = tagTree.getNodes();
		            tagTree.selectNode(nodes[0]);
		            //vm.tagTreeOnClick(null,null,nodes[0]);
                }else{
                	alert(res.data);
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
vm.init();
avalon.scan();