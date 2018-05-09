/**
 * Created by cdyoue on 2017/9/20.
 */

// var zNodes = nodeList;
$(function () {
    jQuery(".slideBox").slide({mainCell:".bd",effect:"fold",autoPlay:true});
    // 获取屏幕高度，设置距离顶部
    var marginTop=parseInt($(window).height())-parseInt($(".bd").height())-80;
    $(".page-content .slideBox").css({"margin-top":marginTop+"px"});
    setTimeout(function () {
        $(".slide .slideBox").removeClass("vh");
    },100);
    $(".spinnerBox").on("click",function () {
        if($(".slide .slideBox").hasClass("vh")){
            $(".slide .slideBox").removeClass("vh");
            $(".slideSpinner:hover:after").css({content:"close"})
        }else {
            $(".slide .slideBox").addClass("vh");
            $(".slideSpinner:hover:after").css({content:"start"})
        }
    });
    setInterval(function () {
        $('.flow .circle').removeClass("circle-choose");
        svgMove();
    },6000);
    // 2D效果
    function svgMove() {
        setTimeout(function () {
            $('.flow2 .circle').addClass("circle-choose");
        },800);
        setTimeout(function () {
            $('.flow3 .circle').addClass("circle-choose");
        },2200);
        setTimeout(function () {
            $('.flow4 .circle').addClass("circle-choose");
        },3600);
        setTimeout(function () {
            $('.flow5 .circle').addClass("circle-choose");
        },5000);
    }
// 3D
//     var Width=$(".page-content").width()+60;
    var Width=$(".page-content-wrapper").width();
    var Height=parseInt($(".page-content").css("min-height"));
    console.log(Height);
    function init() {
        var data=showData;
        // var data = [
        //     // {
        //     //     id: 1,
        //     //     size: 1.5,
        //     //     name: 'demo1',
        //     //     level: 1
        //     // },
        //     {
        //         id: 1,
        //         size: 1.5,
        //         name: '营商指数',
        //         level: 1
        //     },
        //     {
        //         id: 9,
        //         pid: 1,
        //         size: 1,
        //         name: '政府服务',
        //         level: 2
        //     },
        //     {
        //         id: 10,
        //         pid: 1,
        //         size: 1,
        //         name: '行政审批',
        //         level: 2
        //     },
        //     {
        //         id: 11,
        //         pid: 1,
        //         size: 1,
        //         name: '社会环境',
        //         level: 2
        //     },
        //     {
        //         id: 12,
        //         pid: 11,
        //         size: 0.7,
        //         name: '投融资',
        //         level: 3
        //     },
        //     {
        //         id: 13,
        //         pid: 11,
        //         size: 0.7,
        //         name: '人才供给',
        //         level: 3
        //     },
        //     {
        //         id: 14,
        //         pid: 11,
        //         size: 0.7,
        //         name: '企业信用',
        //         level: 3
        //     },
        //     {
        //         id: 15,
        //         pid: 12,
        //         size: 0.5,
        //         name: '投融资机构数',
        //         level: 4
        //     },
        //     {
        //         id: 16,
        //         pid: 14,
        //         size: 0.5,
        //         name: '失信企业数',
        //         level: 4
        //     },
        //     {
        //         id: 17,
        //         pid: 14,
        //         size: 0.5,
        //         name: '老赖名单',
        //         level: 4
        //     }
        // ];
        var group;
        var textMesh;
        var INTERSECTED = null;
        group = new THREE.Object3D();

        var scene = new THREE.Scene();

        var camera = new THREE.PerspectiveCamera(45, Width / Height, 0.1, 1000);

        var webGLRenderer = new THREE.WebGLRenderer();
        webGLRenderer.setClearColor('rgb(6, 47, 65)', 1.0);
        webGLRenderer.setSize(Width, Height);
        webGLRenderer.shadowMap.enabled = true;

        var polyhedron = createMesh(new THREE.IcosahedronGeometry(20, 1));

        //创建粒子的形状
//        使用贴图创建粒子
        spriteImg = new THREE.TextureLoader().load( "../../content/images/index/pointL.png" );
//        鼠标hover贴图改变
        hoverSpriteImg=new THREE.TextureLoader().load( "../../content/images/index/point-hover.png" );

        group.add(polyhedron);
        createSprites();
        createLinks();
//        console.log(group);
        scene.add(group);

        // 添加相机
        camera.position.x = -30;
        camera.position.y = 40;
        camera.position.z = 50;
        camera.lookAt(new THREE.Vector3());
        //        使用控件设置相机
        var orbitControls = new THREE.OrbitControls(camera);
        orbitControls.autoRotate = true;
        orbitControls.userZoom=false;
        orbitControls.userPan=false;
        orbitControls.userPanSpeed=0;
        var clock = new THREE.Clock();

        // 添加环境光
        var ambientLight = new THREE.AmbientLight(0x0c0c0c);
        scene.add(ambientLight);

        //        设置光源（方向光）
//        var pointColor = "#fff";
        var pointColor = "#1bbc9b";
        var directionalLight = new THREE.DirectionalLight(pointColor,1);
        scene.add(directionalLight);

        var raycaster = new THREE.Raycaster();
        var mouse = new THREE.Vector2();

        // 向容器添加图形
        document.getElementById("IcosahedronGeometry").appendChild(webGLRenderer.domElement);

        // 调用渲染
        var step = 0;
        render();

        //监听粒子移入、点击事件
        document.addEventListener( 'mousedown', onDocumentMouseDown, false );
        document.addEventListener( 'mousemove', onDocumentMouseMove, false );

        //粒子点击方法
        function onDocumentMouseDown(event){
            event.preventDefault();
            mouse.set( ( event.clientX / window.innerWidth ) * 2 - 1, - ( event.clientY / window.innerHeight ) * 2 + 1 );
            raycaster.setFromCamera(mouse, camera);
            var intersects = raycaster.intersectObjects(group.children);
//            var newY = group.rotation.y;
            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = event.pageX || event.clientX + scrollX;
            var y = event.pageY || event.clientY + scrollY;
            if(intersects.length){
                function renderHover() {
//                    group.rotation.y = newY;
                    orbitControls.autoRotate = false;
                    requestAnimationFrame(renderHover);
                    webGLRenderer.render(scene, camera);
                    $('#spritesName').css({
                        'left': (x + 10 ) + 'px',
                        'top': y + 'px'
                    });
                    $('#spritesName').html(intersects[0].object.name);
                }
                for (var i = 0; i < intersects.length; i++) {
                    if (intersects[i].object.type == 'Sprite') {
                        renderHover();
                        return false;
                    }
                }
            }
            else {
                function renderHoverOut() {
//                    group.rotation.y = newY += 0.005;
                    orbitControls.autoRotate = true;
                    requestAnimationFrame(renderHoverOut);
                    webGLRenderer.render(scene, camera);
                    $('#spritesName').html('');
                }
                renderHoverOut();
            }
        }

        //粒子移入方法
        function onDocumentMouseMove(event){
            event.preventDefault();
            mouse.set( ( event.clientX / window.innerWidth ) * 2 - 1, - ( event.clientY / window.innerHeight ) * 2 + 1 );
            raycaster.setFromCamera(mouse, camera);
            var newGroup = [];
            for (var h = 0; h < group.children.length; h++) {
                if (group.children[h].type == 'Sprite') {
                    newGroup.push(group.children[h]);
                }
            }
            console.log(newGroup);
            var intersects = raycaster.intersectObjects(newGroup);
            var currentScale;
            if(intersects.length){
                for (var i = 0; i < intersects.length; i++) {
                    if (intersects[i].object.type == 'Sprite') {
                        if (INTERSECTED != intersects[i].object && INTERSECTED == null) {
                            INTERSECTED = intersects[i].object;
//                            currentScale = INTERSECTED.scale.x;
//                            INTERSECTED.currentScale = currentScale;
//                            var newIntersectsSize = currentScale + 1;
//                            INTERSECTED.scale.set(newIntersectsSize, newIntersectsSize, newIntersectsSize);
//                            test
                            INTERSECTED.material.map=hoverSpriteImg;
//                            orbitControls.autoRotate = false;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
//                        INTERSECTED.scale.set(INTERSECTED.currentScale, INTERSECTED.currentScale, INTERSECTED.currentScale);
                        INTERSECTED.material.map=spriteImg;
//                        orbitControls.autoRotate = true;
                    }
                }

            }
            else {
                if ( INTERSECTED ) {
//                    INTERSECTED.scale.set(INTERSECTED.currentScale, INTERSECTED.currentScale, INTERSECTED.currentScale);
                    INTERSECTED.material.map=spriteImg;
                }
                INTERSECTED = null;
            }
        }

        //创建球体
        function createMesh(geom) {
            // assign two materials
            var mats = [];
            for (var i = 0; i < geom.faces.length; i++) {
                geom.faces[i].materialIndex = i;
                if (i < 52) {
                    mats.push(new THREE.MeshPhongMaterial({ color: 0x1dbaa1, shading: THREE.FlatShading, vertexColors: THREE.VertexColors, shininess: 0 ,emissive:"#085e5d"}));

                }

                if (i >= 52) {
                    mats.push(new THREE.MeshPhongMaterial({ color: 0x1392b3, shading: THREE.FlatShading, vertexColors: THREE.VertexColors, shininess: 0 ,emissive:"#085e5d"}));

                }
            }
            var materials = [
//            mats,
                new THREE.MeshFaceMaterial(mats),
                new THREE.MeshBasicMaterial( { color: 0x0e7570, shading: THREE.FlatShading, wireframe: true, transparent: true } )
            ];
            // create a multimaterial
            var mesh = THREE.SceneUtils.createMultiMaterialObject(geom, materials);
//            var mesh = new THREE.Mesh(geom, meshMaterial);
            return mesh;
        }

        //创建粒子群
        function createSprites() {
            var range = 50;
            for (var i = 0; i < data.length; i++) {
                if (data[i].hasOwnProperty('pid')) {
                    for (var l = 0; l < data.length; l++) {
                        if (data[l].id == data[i].pid) {
                            for (var k = 1; k < group.children.length; k++) {
                                if (group.children[k].name == data[l].name) {
                                    var parentPosition = group.children[k].position;
                                    group.add(createSprite(data[i].size, true, 1, 0x1AD5A2, i % 7, range, data[i].name, parentPosition));
                                }
                            }
                        }
                    }
                }
                else {
                    group.add(createSprite(data[i].size, true, 1, 0x1AD5A2, i % 7, range, data[i].name));
                }
            }
        }

        //创建粒子
        function createSprite(size, transparent, opacity, color, spriteNumber, range, name, parentPosition) {
            var spriteMaterial = new THREE.SpriteMaterial({
                    opacity: opacity,
                    color: color,
                    lights: true,
                    transparent: transparent,
//                    map: getTexture()
                    map: spriteImg
                }
            );

            // we have 1 row, with five sprites
            spriteMaterial.depthTest = true;
            spriteMaterial.blending = THREE.AdditiveBlending;

            var sprite = new THREE.Sprite(spriteMaterial);
            sprite.scale.set(size, size, size);
            if (parentPosition) {
                var x=parentPosition.x - parseInt(10 * Math.random());
                var y=parentPosition.y - parseInt(10 * Math.random());
                var z=parentPosition.z - parseInt(10 * Math.random());
                sprite.position.set(x, y, z);
            }
            else {
                var x=Math.random() * range - range / 2;
                var y=Math.random() * range - range / 2;
                var z=Math.random() * range - range / 2;
                sprite.position.set(x, y, z);
            }
            sprite.velocityX = 5;
            sprite.name = name;
            return sprite;
        }

        //创建粒子间的连线
        function createLinks() {
            var linkA;
            var linkB;
            var lineGeometry = [];
            var line;

            for (var i = 0; i < data.length; i++) {
                lineGeometry[i] = new THREE.Geometry();
                line = new THREE.Line( lineGeometry[i], new THREE.LineBasicMaterial(
                    {
                        color: 0x1AD5A2,
                        opacity: 1
                    }
                ));
                if (data[i].hasOwnProperty('pid')) {
                    for (var l = 0; l < data.length; l++) {
                        if (data[l].id == data[i].pid) {
                            for (var k = 1; k < group.children.length; k++) {
                                if (group.children[k].name == data[i].name) {
                                    linkA = group.children[k].position;
                                    lineGeometry[i].vertices.push( linkA );
                                }
                                if (group.children[k].name == data[l].name) {
                                    linkB = group.children[k].position;
                                    lineGeometry[i].vertices.push( linkB );
                                }
                                group.add(line);
                            }
                        }
                    }
                }
            }
//            console.log(lineGeometry);
        }

        //渲染图形
        function render() {
//            group.rotation.y = step += 0.005;

            var delta = clock.getDelta();
            orbitControls.update(delta);

            // render using requestAnimationFrame
            requestAnimationFrame(render);
//            spotLight.position.x=camera.position.x;
//            spotLight.position.y=camera.position.y;
//            spotLight.position.z=camera.position.z;
            directionalLight.position.x=camera.position.x;
            directionalLight.position.y=camera.position.y;
            directionalLight.position.z=camera.position.z;
            webGLRenderer.render(scene, camera);
        }

    }
    window.onload = init;

});


//上传弹框树形目录(工作流目录)
// var setting = {
//     async: {
//         enable: true,
//         url:basePath+"/drag/work/selectZtreeWindow.do",
//         autoParam:["id", "name","pId","isParent"],
//         otherParam:{"otherParam":"zTreeAsyncTest"},
//         dataFilter: filter
//     },
//     view: {
//         // addHoverDom: addHoverDom,
//         // removeHoverDom: removeHoverDom,
//         showIcon: showIconForTree,
//         selectedMulti: false,
//         showLine: false
//     },
//     edit: {
//         enable: true,
//         showRemoveBtn: false,
//         showRenameBtn: false
//     },
//     data: {
//         keep: {
//             parent:true,
//             leaf:true
//         },
//         simpleData: {
//             enable: true
//         }
//     },
//     callback: {
//         beforeDrag: beforeDrag,
//         // beforeRename: beforeRename,
//         beforeExpand: beforeExpand,
//         onExpand: onExpand,
//         onClick: onClick
//
//     }
// };
// function showIconForTree(treeId, treeNode) {
//     return treeNode.level != 2;
// };
// function filter(treeId, parentNode, childNodes) {
//     console.log(childNodes);
//     if (!childNodes) return null;
//     var aa  = JSON.stringify(childNodes.obj);
//     var cc = JSON.parse(aa);
//     return cc;
//
// }
//
// function beforeDrag(treeId, treeNodes) {
//     return false;
// }
// var curExpandNode = null;
// function beforeExpand(treeId, treeNode) {
//     var pNode = curExpandNode ? curExpandNode.getParentNode():null;
//     var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//     for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
//         if (treeNode !== treeNodeP.children[i]) {
//             zTree.expandNode(treeNodeP.children[i], false);
//         }
//     }
//     while (pNode) {
//         if (pNode === treeNode) {
//             break;
//         }
//         pNode = pNode.getParentNode();
//     }
//     if (!pNode) {
//         singlePath(treeNode);
//     }
//
// }
// function singlePath(newNode) {
//     if (newNode === curExpandNode) return;
//
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
//         rootNodes, tmpRoot, tmpTId, i, j, n;
//
//     if (!curExpandNode) {
//         tmpRoot = newNode;
//         while (tmpRoot) {
//             tmpTId = tmpRoot.tId;
//             tmpRoot = tmpRoot.getParentNode();
//         }
//         rootNodes = zTree.getNodes();
//         for (i=0, j=rootNodes.length; i<j; i++) {
//             n = rootNodes[i];
//             if (n.tId != tmpTId) {
//                 zTree.expandNode(n, false);
//             }
//         }
//     } else if (curExpandNode && curExpandNode.open) {
//         if (newNode.parentTId === curExpandNode.parentTId) {
//             zTree.expandNode(curExpandNode, false);
//         } else {
//             var newParents = [];
//             while (newNode) {
//                 newNode = newNode.getParentNode();
//                 if (newNode === curExpandNode) {
//                     newParents = null;
//                     break;
//                 } else if (newNode) {
//                     newParents.push(newNode);
//                 }
//             }
//             if (newParents!=null) {
//                 var oldNode = curExpandNode;
//                 var oldParents = [];
//                 while (oldNode) {
//                     oldNode = oldNode.getParentNode();
//                     if (oldNode) {
//                         oldParents.push(oldNode);
//                     }
//                 }
//                 if (newParents.length>0) {
//                     zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
//                 } else {
//                     zTree.expandNode(oldParents[oldParents.length-1], false);
//                 }
//             }
//         }
//     }
//     curExpandNode = newNode;
// }
//
// function onExpand(event, treeId, treeNode) {
//     if(treeNode.isParent==true){
//         curExpandNode = treeNode;
//     }
// }
// //创建新的工作流
// function createWorkFlow() {
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//     nodes = zTree.getSelectedNodes();
//     if(nodes.length==0){
//         toastr.error("请先选择一个文件夹");
//     }else{
//         treeNode = nodes[0];
//   /*      var workFlowName = $("#nameWork").val();
//         var flowExplain = $("#flowExplain").val();*/
//         var workFlowName="这是你的工作流名称2";
//         var flowExplain="这是说明2"
//         $.ajax({
//             url: basePath + "/drag/flow/creatworkflowbyindex.do",
//             type: 'POST',
//             // dataType: "json",
//             data: {"workSpaceId": treeNode.id,"workFlowName": workFlowName,"flowExplain":flowExplain},
//             success: function (json) {
//                 var returnData = JSON.parse(json);
//                 switch (returnData.code) {
//                     case 417:
//                         toastr.error(returnData.msg);
//                         $("#basic").modal("hide");
//                         break;
//                     case 200:
//                         toastr.success(returnData.msg);
//                         window.location.href=basePath+"/drag/dataModel/select.do?name="+workFlowName+"&flowId="+returnData.obj+"&workSpaceName="+treeNode.name;
//                         break;
//                 }
//             }
//             // complete:function(){ //生成分页条
//             //     selectTempalateByName(null,Aid,txt)
//             // }
//
//         });
//     }
//
// }
//
// function onClick(e,treeId, treeNode) {
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//     zTree.expandNode(treeNode, null, null, null, true);
// }
//
// 从模板创建
// function indexCreateNewFlow() {
// /*
//     var templateId=$(this).parents(".creat").find("input[name=templateId]").val();
//     var flowId=$(this).parents(".creat").find("input[name='flowId']").val();
//
//     $("#templateId").val(templateId);
//     $("#flowId").val(flowId);
// */
//     $("#basic").modal("show");
//
//
//
// }
// // 从模板创建 end
//
// // 弹框目录树滚动条
// $(window).on("load", function () {
//     $("body .treeDeomBox").mCustomScrollbar({
//         theme: "dark-thin",
//         axis:"y",
//         setLeft: 0,
//         scrollbarPosition: "inside"
//     });
// });
// // 弹框ztree end
function indexCreateNewOpenFlow() {

    window.location.href= basePath+"/drag/dispatcher/workSpace.do?open=1";


}
