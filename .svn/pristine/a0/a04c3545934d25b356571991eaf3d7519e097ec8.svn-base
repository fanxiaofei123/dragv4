var UIBootbox = function () {

    var handleDemo = function() {

            $('#demo_3').click(function(){
                bootbox.confirm("Are you sure?", function(result) {
                   alert("Confirm result: "+result);
                }); 
            });
            var strWarning = '<div class="iconQuestion"><i class="icon-question"></i></div>是否删除选中项';

            $('.icon-trash').click(function(){
                bootbox.dialog({
                    message: strWarning,
                    title: "提示",
                    buttons: {
                      success: {
                        label: "确定",
                        className: "green",
                        callback: function() {
                            alert("成功")
                        }
                      },
                      danger: {
                        label: "关闭",
                        className: "grey-salsa btn-outline",
                        callback: function() {
                          alert("退出");
                        }
                      }
                    }
                });
            });
            //end #demo_7

    }

    return {

        //main function to initiate the module
        init: function () {
            handleDemo();
        }
    };

}();

jQuery(document).ready(function() {    
   UIBootbox.init();
});