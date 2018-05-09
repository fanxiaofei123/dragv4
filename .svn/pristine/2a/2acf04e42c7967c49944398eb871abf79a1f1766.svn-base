/**
 * Created by sky on 2017/8/7.
 */
// //复选框的功能
// $(function(){
//
//     selectState();
//     allView();
//     addAllState();
//
// })
// function allView(){
//     $('.buttonState').on('click',function(){
//         $('.xianshi').show()
//     });
//
// }
// function selectState(){
//     var stateCount=0;//选择个数
//
//     //设置全部显示的标签为灰色给个开关
//     var allView=true;
//     $('.selectDiv').on('click','.xiala',function(){
//         $('.xianshi').show()
//     });
//     $('.xianshi').mouseleave(function(){
//         $('.xianshi').hide()
//     })
//     //移动显示状态
//     $('#selectALL').on('hover','.selectOne',function(){
//         $('.selectOne').css({color:'#000',background:'#ffffff'})
//         $(this).css({color:'#ffffff',background:"#1bbc9b"})
//         var selectOne=$(this).text();
//         $('.setOne').html(selectOne)
//         //
//     });
//     //选择添加状态
//     var stateCountAll=$('.selectOne').length-1;
//     // 获取下拉文本
//     var stateTxt = [];
//     $.each($('.selectOne'),function(){
//         stateTxt.push($(this).html())
//     });
//     stateTxt.join(',');
//     $('#selectALL').on('click','.selectOne',function(){
//         $(".addAllState").hide();
//         $(this).remove();
//         var selectOne=$(this).text();
//         $("#allShow").append("<div class=\"buttonState\" value="+selectOne+"><span>"+selectOne+"</span><font>&times;</font></div>");
//         stateCount=stateCount+1;
//         //全部显示标签显示为灰色
//
//         if(stateCount>0){
// //						$('.buttonStates').attr("disabled","disabled")
//             $('.buttonStates').css("background","#dfe4e6")
//             //全部显示失效
//             allView=false;
//         }
//
//         if(stateCount>=stateCountAll){
//             allView=false;
//             $('.before').hide()
//         }
//
//     });
//
//     //$('.buttonState>span').
//     //删除状态
//     $('#allShow').on('click','.buttonState',function(){
//         var state=$(this).attr("value");
//         $(this).remove();
//         $('.xianshi').append("<div class=\"selectOne\">"+state+"</div>");
//         stateCount=stateCount-1;
//         if(stateCount<3){
//             $('.before').show()
//         }
//         if(stateCount==0){
//             //全部显示
//             $('.buttonStates').css("background","#1BBC9B");
//             $('.buttonStates>span').show();
//             $('.buttonStates>font').hide();
//         }
//     });
//     //全部显示
//
//     $('.buttonStates').hover(function(){
//         if(allView==false){
//             $('.buttonStates').css("background","#f98937")
//             $('.buttonStates>span').hide();
//             $('.buttonStates>font').show()
//
//
//         }
//
//     });
//     var colorState=false;
//     $('.buttonStates').mouseleave(function(){
//         if(colorState==true){
//             $('.buttonStates').css("background","#1BBC9B");
//             $('.buttonStates>span').show();
//             $('.buttonStates>font').hide();
//             colorState=false;
//         }else{
//             $('.buttonStates').css("background","#dfe4e6");
//             $('.buttonStates>span').show();
//             $('.buttonStates>font').hide()
//         }
//     });
//     $('.buttonStates').on('click',function(){
//         //触发全部显示
//         if(allView==false){
//             $('.buttonStates').css("background","#1BBC9B");
//             $('.buttonStates>span').show();
//             $('.buttonStates>font').hide();
//             colorState=true;
//             //删除状态标签
//             $(".buttonState").remove();
//             $('.selectOne').remove();
//             var str="";
//             for(var i=0;i<stateTxt.length;i++){
//                 str+="<div class=\"selectOne\">"+stateTxt[i]+"</div>"
//             }
//             $('.xianshi').append(str);
//             $('.before').show();
//             //恢复xianshi全部状态
//             $('.selectOne').show();
//             stateCount=0;
//
//         }
//     })
//
// }
// function addAllState(){
//     $('.addState').on('click',function(){
//         $(".addAllState").show();
//         $(".addAllState").css("display","inline-block")
//     })
// }


// 复选框功能新增
$(function () {
    clickAdd("type");
    clickAdd("type2");

    // 点击添加按钮执行事件
    function clickAdd(id) {
        var type="#"+id;
        var stateTxt = [];
        $.each($(type+' .option'),function(){
            stateTxt.push($(this).html())
        });
        stateTxt.join(',');
        $(type+' .add').off('click').on('click',function (e) {
            // console.log(m);
            e.stopPropagation();
            $(type+' .add').addClass("pointNone");
            showChoose(type);
            addAndDele(type,stateTxt);
        });
        removeChild(type)
    }
    // 显示选择的状态等添加到页面上
    function showChoose(id) {
        // var type="#"+id;
        $(id+" .choose").removeClass('dn');
        $(id+" .choose .selectChoose").on('click','.option',function (e) {
            e.stopPropagation();
            $(id+" .allTypeNum").append('<div class="typeChild"><span class="selected">'+$(this).text()+'</span><span class="dn"><i class="icon iconfont icon-wrong1"></i></span></div>');
            $(this).remove();
            selectByType();
            var m= $(id+" .option").length;
            if(m<=1){
                $(id+" .add").addClass('dn');
            }
            $(id+" .choose").addClass('dn');
            $(id+" .choose .selectChoose").off('click');
            $(id+' .add').removeClass("pointNone");
        })

    }

    function addAndDele(id,stateTxt) {
        // var type="#"+id;
        // 获取下拉文本
        var m=$(id+" .allType").length;
        if(m>=1){
            $(id+" .allType:first").addClass("noClick");
            $(id+" .noClick").on('click',function (e) {
                e.stopPropagation();
                $(this).removeClass('noClick');
                $(id+" .typeChild").remove();
                $(id+" .add").removeClass('dn');
                // 添加选择
                var str="";
                for(var i=0;i<stateTxt.length;i++){
                    str+="<div class=\"option\">"+stateTxt[i]+"</div>"
                }
                $(id+" .selectChoose").html(str);

                selectByType();
            })
        }
    }
    function removeChild(id) {
        $(id).on('click',".typeChild",function (e) {
            e.stopPropagation();
            var m=$(this).text();
            $(this).remove();
            $(id+" .selectChoose").append('<div class="option">'+m+'</div>');
            var n= $(id+" .typeChild").length;
            if(n<1){
                $(id+" .allType").removeClass('noClick');
            }
            $(id+" .add").removeClass('dn');
            selectByType();
        })
    }
});
