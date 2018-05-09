var Login = function() {
    
    function errroTips(html) {
        var $tipSpan = $('.alert-danger', $('.login-form'));

        $tipSpan.find("span").html(html);
        $tipSpan.show()
    }
    

    var handleRetrieve = function() {

        function format(state) {
            if (!state.id) { return state.text; }
            var $state = $(
             '<span><img src="../assets/global/img/flags/' + state.element.value.toLowerCase() + '.png" class="img-flag" /> ' + state.text + '</span>'
            );
            
            return $state;
        }

        if (jQuery().select2 && $('#country_list').size() > 0) {
            $("#country_list").select2({
	            placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
	            templateResult: format,
                templateSelection: format,
                width: 'auto', 
	            escapeMarkup: function(m) {
	                return m;
	            }
	        });


	        $('#country_list').change(function() {
	            $('.register-form').validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
	        });
    	}


        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");
        // 只能输入英文
        jQuery.validator.addMethod("english", function(value, element) {
            var pattern = /^[\u4E00-\u9FA5]{1,6}$/;
            return !pattern.test(value);
        }, "账户不能为中文");
        $('.retrieve-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                password :{
                    required: true,
                    minlength: 6,
                    maxlength: 18
                },
                rePassword:{
                    equalTo: "#password",
                    minlength: 6,
                    maxlength: 18
                }

            },

            messages: { // custom messages for radio buttons and checkboxes
                password:{
                    required: "请填写密码",
                    minlength: "密码长度不能小于6位",
                    maxlength: "密码长度不能大于18位"
                },
                rePassword: {
                    equalTo: "两次密码不一致",
                    minlength: "密码长度不能小于6位",
                    maxlength: "密码长度不能大于18位"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                var formData = new FormData($(".retrieve-form")[0]);
                $.ajax({
                    url:basePath+"/drag/user/modify.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        switch (returndata.code){
                            case 200:;
                                window.location.href = basePath + "/drag/dispatcher/index.do?messageTips=recover";
                                break;
                            case 417:errroRetrieveTips(returndata.msg);
                                break;
                        }

                    }
                });
            }
        });




        function errroRetrieveTips(html) {
            var $tipSpan = $('.alert-danger', $('.retrieve-form'));

            $tipSpan.find("span").html(html);
            $tipSpan.show()
        }




        $('.register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.register-form').validate().form()) {
                    $('.register-form').submit();
                }
                return false;
            }
        });

        jQuery('#register-btn').click(function() {
            jQuery('.login-form').hide();
            jQuery('.register-form').show();
        });

        jQuery('#register-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.register-form').hide();
        });
    }

    return {
        //main function to initiate the module
        init: function() {

            handleRetrieve();

        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});