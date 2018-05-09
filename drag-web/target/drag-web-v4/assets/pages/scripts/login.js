var Login = function() {

    var handleLogin = function() {
        // 只能输入英文
        jQuery.validator.addMethod("english", function(value, element) {
            var pattern = /^[\u4E00-\u9FA5]{1,6}$/;
            return !pattern.test(value);
        }, "只能输入字母");
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            // errorClass: 'help-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                loginname: {
                    required: true,
                    english :true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                loginname: {
                    required: "请填写用户名",
                    english : "只能输入字母"
                },
                password: {
                    required: "请填写密码"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit
                var nameLength = $("input[name = 'loginname']").val().length;
                var passwordsLength = $("input[name = 'password']").val().length;
                if (nameLength == 0) {
                    errroTips("用户名不能为空");
                }
                if (passwordsLength == 0) {
                    errroTips("密码不能为空");
                }
                if (nameLength == 0 && passwordsLength == 0) {
                    errroTips("用户名不能为空");
                }
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
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function() {
                var formData = new FormData($(".login-form")[0]);
                $.ajax({
                    url:basePath+"/drag/user/login.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        switch (returndata.code){
                            case 404:errroTips(returndata.msg);
                                break;
                            case 423:errroTips(returndata.msg) ;
                                break;
                            case 200:window.location.href=basePath+"/drag/dispatcher/drag.do";
                                break;
                        }


                    }
                });


            }
        });

        
        
        
        
        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit(); //form validation success, call ajax form submit
                }
                return false;
            }
        });
    }

    
    function errroTips(html) {
        var $tipSpan = $('.alert-danger', $('.login-form'));

        $tipSpan.find("span").html(html);
        $tipSpan.show()
    }
    
    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required :true,
                    email: true
                }
            },

            messages: {
                email: {
                    required : "请填写邮箱账号",
                    email: "填写正确的邮箱格式",
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

           /* errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },*/

            submitHandler: function(form) {

                var formData = new FormData($(".forget-form")[0]);
                $.ajax({
                    url:basePath+"/drag/user/retrieve.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        switch (returndata.code){
                            case 200:$('.register-form')[0].reset();$('.login-form')[0].reset();toastr.success(returndata.msg);$("#back-btn").click();
                                break;
                            case 404:$('.alert-danger', $('.forget-form')).show();
                                break;
                        }


                    }
                });


            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });

        jQuery('#forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });

        jQuery('#back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });

    }

    var handleRegister = function() {

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
        $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                name :{
                    required: true,
                    remote:{                                          //验证用户名是否存在
                        type:"POST",
                        url:basePath + "/drag/user/validate.do",             //servlet
                        data:{
                            name:function(){return $("input[name='name']").val();}
                        }
                    }
                },
                email:{
                    required:true,
                    email:true,
                    remote:{                                          //验证用户名是否存在
                        type:"POST",
                        url:basePath + "/drag/user/validate.do",             //servlet
                        data:{
                            email:function(){
                                return $("#email").val();}
                        }
                    }
                },
                address: {
                    required: true
                },

                city: {
                    required: true
                },
                country: {
                    required: true
                },

                loginname: {
                    required: true,
                    english: true,
                    remote:{                                          //验证用户名是否存在
                        type:"POST",
                        url:basePath + "/drag/user/validate.do",             //servlet
                        data:{

                            loginname:function(){
                                return $("#loginname").val();}
                        }
                    }
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 18
                },
                rpassword: {
                    equalTo: "#register_password",
                    minlength: 6,
                    maxlength: 18
                },
                tnc :{
                    required:true
                }

            },

            messages: { // custom messages for radio buttons and checkboxes
                name:{
                    required: "请填写昵称",
                    remote : "昵称已存在"
                },
                loginname: {
                    required: "请填写用户名",
                    remote : "用户名已存在"
                },
                password: {
                    required: "请填写密码",
                    minlength: "密码长度不能小于6位",
                    maxlength: "密码长度不能大于18位"
                },
                rpassword: {
                    equalTo: "两次密码不一致",
                    minlength: "密码长度不能小于6位",
                    maxlength: "密码长度不能大于18位"
                },
                email:{
                    required: "请输入邮箱",
                    email: "请输入正确邮箱格式",
                    remote : "邮箱已存在"
                },
                tnc: {
                    required: "请同意服务条例"
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
                    error.css('color','#f98937')
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.removeClass('help-block');
                    error.addClass('help-error');
                    element.parent().find("span").remove();
                    error.appendTo(element.parent().children(":first"));
                }
                // $("input.placeholder-no-fix").css('outline','1px solid #f98937')
            },

            submitHandler: function(form) {
                var formData = new FormData($(".register-form")[0]);
                $.ajax({
                    url:basePath+"/drag/user/register.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        switch (returndata.code){
                            case 200:$('.register-form')[0].reset();$('.login-form')[0].reset();toastr.success(returndata.msg);$('#register-back-btn').click();
                                setTimeout('location.reload(true);',4000);
                                break;
                            case 403:errroRegisterTips(returndata.msg);
                                break;
                            case 417:errroRegisterTips(returndata.msg);
                                break;
                            case 412:errroRegisterTips(returndata.msg);
                                break;
                        }


                    }
                });
            }
        });




        function errroRegisterTips(html) {
            var $tipSpan = $('.alert-danger', $('.register-form'));

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

            handleLogin();
            handleForgetPassword();
            handleRegister();

        }

    };

}();

function messageTips() {
    var tip = $("#tips").val();
    switch (tip){
        case 'activited':toastr.success("恭喜你!激活成功");break;
        case 'recover':toastr.success("恭喜你!密码重置成功");break;
    }
}

//记住用户名
function saveUserInfo() {
    if ($("#rmbUser").prop("checked") == true) {
        var userName = $("#wsc-username").val();
        var passWord = $("#wsc-password").val();
        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
        $.cookie("userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie
        $.cookie("passWord", passWord, { expires: 7 }); // 存储一个带7天期限的 cookie
    }
    else {
        $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
        $.cookie("userName", '', { expires: -1 });
        $.cookie("passWord", '', { expires: -1 });
    }
}


jQuery(document).ready(function() {
    var height = $("#list").css("height");
    var boxHeight = parseInt(height) - 195;
    $('#modalList').slimScroll({
        color: '#999',
        railColor: '#eee',
        size: '5px',
        height: boxHeight + 'px',
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });

    if ($.cookie("rmbUser") == "true") {
        console.log($.cookie("userName"), $.cookie("passWord"));
        $("#rmbUser").prop("checked", true);
        $("#wsc-username").val($.cookie("userName"));
        $("#wsc-password").val($.cookie("passWord"));
    }
    Login.init();
    messageTips();


});


