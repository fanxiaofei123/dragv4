$(function () {
    // var liLength = $("#pageSidebarMenu li").length;
    // console.log(liLength);
    // for(var b = 0; b < liLength; b++){
    //     if(window.nav == $("#pageSidebarMenu li").eq(b).data("li")){
    //         $("#pageSidebarMenu li").eq(b).addClass("active open");
    //     }
    // }
    function setTreeStyle(obj){
        var objStyle = obj.children("b");
        var objList = obj.siblings("ul");
        if(objList.length == 1){
            var style = objStyle.attr("class");
            objStyle.attr("class","On2Off");
            setTimeout(
                function(){
                    if(style == "Off"){
                        objList.parent().siblings("li").children("span").children(".On").each(function(){
                            setTreeStyle($(this).parent());
                        });
                        var H = objList.innerHeight();
                        console.log(H)
                        objList.css({display:"block",height:"0"});
                        objList.animate({height:H},300,function(){$(this).css({height:"auto"});});
                        objStyle.attr("class","On");
                    }
                    else if(style == "On"){
                        objList.find("li").children("span").children(".On").each(function(){
                            setTreeStyle($(this).parent());
                        });
                        var H = objList.innerHeight();
                        objList.animate({height:0},300,function(){$(this).css({height:"auto",display:"none"})});
                        objStyle.attr("class","Off");
                    }
                },
                42
            );
        }
    }
    $("#tree_root").on('click','li span',function () {
        setTreeStyle($(this));
    });
    var liLength = $("#tree_root li").length;

    for(var b = 0; b < liLength; b++){
        if(window.nav == $("#tree_root li").eq(b).data("li")){
            console.log($("#tree_root li>a").eq(b).html())
            console.log(b)
            $("#tree_root li>a").eq(b).addClass("tree_active");

            var ulList=$("#tree_root li>a").eq(b).parent().parent();
            if(ulList.attr("id") !="tree_root"){
                setTreeStyle(ulList.prev());
            }

            // if(ulList.attr("id") !="tree_root"){
            //
            //     ulList.find("li").children("span").children(".On").each(function(){
            //         setTreeStyle(ulList.prev("span"));
            //     });
            //     var H = ulList.innerHeight();
            //     ulList.css({display:"block",height:"0"});
            //     ulList.animate({height:H},100,function(){ulList.prev("span").css({height:"auto"});});
            // }

        }
    }
});