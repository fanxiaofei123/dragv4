/**
 * Created by cdyoue on 2017/5/23.
 */
$(function () {
    function getVeget() {
        $.ajax({
            url:basePath+"/vegetable/names.do",
            type: 'get',
            dataType: 'json',
            beforeSend:function () {

            },
            success:function (json) {
                $('.veget').empty();
                // $.each()
                //alert(1)
            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }

        })
    }
    //getVeget();
})
