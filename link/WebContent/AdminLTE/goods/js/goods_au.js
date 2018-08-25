
var setting = {
        treeNodeKey: "id",
        treeNodeParentKey: "parentId",

        view: {
            dblClickExpand: true
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {
                "Y": "ps", "N": "ps"
            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"

            },
            key: {
                children: "nodes"
            }, keep: {
                parent: true,
                open: true
            }
        }
    };
var zTreeObj = null;
var vm = avalon.define({
	$id:'goodsau',
	id : getUrlData('id'),
	submited:false,
	model:{id:'',name:'',shopPrice:'',tag:'',unit:'',quantity:'',description:'',discountedPrice:'', imageUrl:'', categoryList:[]},
	dwList:['袋','瓶'],
	absoluteUrl:[],
	relativeUrl:[],
	tempUrl:'',
	imgUrl:'',
	//zTreeObj: null,
	shopCategoryList:[],
	getGoods:function()
	{
		if(vm.id){
			$.ajax({
    		    url: "/link/goods/doGet",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: {id:vm.id},    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		vm.model = res.data;
    		    		if(editor) {
                            editor.html(vm.model.description);
                        }
                        if(vm.model.imageUrl){
                            vm.relativeUrl = vm.model.imageUrl.split(',');
                        }
                        if(vm.model.showUrl){
                            vm.absoluteUrl = vm.model.showUrl.split(',');
                        }
                    }else{
                    	alert(res.data);
                    }
    		    },
    		    error: function() {
    		    	console.log('error');
    		    }
    		});
		}else{
			vm.model = {id:'',name:'',shopPrice:'',tag:'',unit:'',quantity:'',description:'',discountedPrice:'', imageUrl:'', categoryList:[]};
		}
	},
	onAdd:function()
	{
		vm.submited = true;
		
		if(vm.model.name == '' || vm.absoluteUrl.length == 0 || vm.model.shopPrice == '' || vm.model.shopPrice <= '' || vm.model.discountedPrice <= 0 || vm.model.unit == ''){
			return;
		}
		var nodes = zTreeObj.getCheckedNodes();
        var child = [];
        if (null != nodes) {
            for (var i = 0; i < nodes.length; i++) {
                if (!nodes[i].isParent) {
                    child.push({categoryId: nodes[i].id});
                }

            }
        }
        vm.model.categoryList = child;
		vm.model.imageUrl = '';
        for(var i=0; i<vm.relativeUrl.length; i++){

            if(i == vm.relativeUrl.length-1){
                vm.model.imageUrl = vm.model.imageUrl + vm.relativeUrl[i];
            }else{
                vm.model.imageUrl = vm.model.imageUrl + vm.relativeUrl[i] + ",";
            }
        }
        vm.model.description = editor.html();
        
		if(vm.id){
			$.ajax({
    		    url: "/link/goods/doUpdate",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: JSON.stringify(vm.model),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		vm.submited = false;
    		    		alert(res.data);
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
    		    url: "/link/goods/doAdd",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: JSON.stringify(vm.model),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		vm.submited = false;
    		    		alert(res.data);
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
	remove :function (index) {
        vm.absoluteUrl.splice(index, 1);
        vm.relativeUrl.splice(index, 1);
    },
    getShopCateoryList: function () {
    	$.ajax({
		    url: "/link/category/doTree",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		//vm.shopCategoryList = res.data;
		            zTreeObj = $.fn.zTree.init($("#goodsCateory"), setting, res.data);
		            zTreeObj.expandAll(true);
		            console.log(vm.model.id);
		            console.log(99999);
		            if(vm.model.id){
		            	$.ajax({
		        		    url: "/link/category/doSearchGoodsCategory",    //请求的url地址
		        		    dataType: "json",   //返回格式为json
		        		    data: {goodsId:vm.model.id},    //参数值
		        		    type: "post",   //请求方式
		        		    success: function(res) {
		        		    	if (res.status == 100) {
		        		    		for(var i=0; i < res.data.length; i++){
		                                zTreeObj.checkNode(zTreeObj.getNodeByParam("id", res.data[i].id, null), true, true);
		                            }
		                        }else{
		                        	alert(res.data);
		                        }
		        		    },
		        		    error: function() {
		        		    	console.log('error');
		        		    }
		        		});
		            }
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
		window.location.href = '#/goods/list';
	},
	init:function()
	{
		
	}
	
});

var editor = KindEditor.create('textarea[name="content"]', {
    resizeType : 1,
    uploadJson :'http://127.0.0.1:8080/link/fileupload/uploadImg',
    urlType : 'absolute',
    allowFileManager : false,
    allowPreviewEmoticons : false,
    allowImageUpload : true,
    autoHeightMode : true,
    afterCreate : function() {
        this.loadPlugin('autoheight');
    },
    afterBlur : function() {
        this.sync();
    },
    items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'insertorderedlist', 'insertunorderedlist', '|', 'emoticons', 'link',"image" ]
});

var upLoadImg = function(){
    $.ajaxFileUpload({
        url:'/link/fileupload/uploadImg',
        secureuri:false,
        fileElementId:'fileToUpload',
        dataType:'json',
        data:{},
        success:function(data, status){
            //var obj = jQuery.parseJSON(data);
            vm.imgUrl = data.absolute;
            vm.absoluteUrl.push(data.absolute);
            vm.relativeUrl.push(data.relative);
            console.log(123);
        },
        error:function(data, status, e){

        }
    });
    console.log(document.getElementById("fileToUpload").value);
    $("#fileToUpload").remove();
    var input = " <input  id=\"fileToUpload\" type=\"file\" name=\"imgFile\" onchange=\"upLoadImg()\" />";
    $("#myupload").append(input);
};
vm.getGoods();
vm.getShopCateoryList();
avalon.scan();