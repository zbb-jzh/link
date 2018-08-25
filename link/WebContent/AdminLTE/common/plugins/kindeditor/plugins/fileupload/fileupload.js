KindEditor.plugin('fileupload', function(K) {
        var editor = this, name = 'fileupload';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
              $("#uploadImageForKindeditor").click();
        });
});