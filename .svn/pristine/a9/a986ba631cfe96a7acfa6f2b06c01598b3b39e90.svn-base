<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]><html lang="en" class="ie8 no-js"><![endif]-->
<!--[if IE 9]><html lang="en" class="ie9 no-js"><![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 登录</title>
    <jsp:include page="/content/css/login/baseCSS.jsp" flush="true"/>
    <link rel="stylesheet" href="${basePath}/content/css/login/login.css">
</head>
<!-- END HEAD -->

<body class=" login" style="background:#053945 url(${basePath}/images/loginback.jpg) no-repeat;">
<!-- BEGIN LOGO -->
<div class="logoHead1">
    <img src="${basePath}/content/images/global/logoh.png" alt="" />
</div>
<div class="logo">
    <img src="${basePath}/content/images/global/modellogo.png" alt="" />
</div>
<div class="textlogo">
    <img src="${basePath}/content/images/global/textlogo.png" alt="" />
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <input type="hidden" id="tips" value="${messageTips}">

    <form class="login-form"  action="index.html" method="post">
        <div class="reasonDiv">


        <div class="mod_close"><span class="wel_text">欢迎登陆</span ><span class="register-btn1"><a href="javascript:;" id="register-btn" class="uppercase lawColor">注册</a></span> <span class="no_account">没有账号吗？</span></div>
        <%--<h3 class="form-title font-green">登录</h3>--%>
        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span> 请输入用户名和密码 </span>
        </div>
        <div class="form-group">
            <div class="labelDiv1">
                <label class="userTall">邮箱地址或用户名</label>&nbsp;<label class="importCon">*</label>
            </div>
            <input class="form-control form-control-solid placeholder-no-fix" id="wsc-username" type="text" autocomplete="off" placeholder="请输入邮箱地址或用户昵称" name="loginname" /> </div>
        <div class="form-group">
            <div class="labelDiv2">
                <label class="userTall">用户密码</label>&nbsp;<label class="importCon">*</label><a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>
            </div>
            <input class="form-control form-control-solid placeholder-no-fix" id="wsc-password" type="password" autocomplete="off" placeholder="请输入密码" name="password" /> </div>
        <div class="form-actions loginDiv">
            <button type="submit" class="btn green uppercase btnLogin" onclick="saveUserInfo()">登录</button>
        </div>
            <div class="labelDiv2">
                <label class="rememberme check mt-checkbox mt-checkbox-outline">
                    <input type="checkbox" id="rmbUser" name="remember" value="1" class="check1 " />记住密码
                    <span></span>
                </label>
           </div>

        <%--<div class="create-account">--%>
            <%--<p>--%>
               <%----%>
            <%--</p>--%>
        <%--</div>--%>
     </div>
    </form>
    <!-- END LOGIN FORM -->
    <!-- BEGIN FORGOT PASSWORD FORM -->
    <form class="forget-form"  method="post">
        <div class="mod_close"><span class="wel_text">忘记密码？</span > <button type="button" id="back-btn"  class="btn backHead uppercase">返回</button></div>

        <div class="labelDiv1">
            <label class="userTall">请输入注册时的邮箱地址来重置密码</label>&nbsp;<label class="importCon">*</label>
        </div>
        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span> 邮箱不正确 </span>
        </div>
        <div class="form-group">
            <input  class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请填写邮箱地址" name="email" /> </div>
        <div class="form-actions  margin-bottom-20  loginDiv1">

            <button type="submit" class="btn btn-success uppercase margin-top-20 ">提交</button>
        </div>
    </form>
    <!-- END FORGOT PASSWORD FORM -->

    <!-- BEGIN REGISTRATION FORM -->
    <form class="register-form"   method="post">

        <div class="mod_close"><span class="wel_text">注册账号</span ><span class="register-btn1"><a href="${basePath}" class="uppercase lawColor">登录</a></span> <span class="no_account">已注册过了？</span></div>
        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span> 用户已存在 </span>
        </div>

        <%--<div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">昵称</label>
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入昵称" name="name" /> </div>--%>
        <div class="form-group">
            <div class="labelDiv1">
                <label class="userTall">注册邮箱地址</label>&nbsp;<label class="importCon">*</label>
            </div>
            <input class="form-control placeholder-no-fix" type="text" id="email" placeholder="请输入注册用邮箱地址" name="email" /> </div>
        <div class="form-group">
            <div class="labelDiv1">
                <label class="userTall">注册用户名</label>&nbsp;<label class="importCon">*</label>
            </div>
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入注册用户名" id="loginname" name="loginname" /> </div>
        <div class="form-group">
            <div class="labelDiv1">
                <label class="userTall">注册用户密码</label>&nbsp;<label class="importCon">*</label>
            </div>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="请输入注册密码" name="password" /> </div>
        <div class="form-group">
            <%--<label class="control-label visible-ie8 visible-ie9">注册用户密码</label>--%>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="请再次输入注册密码" name="rpassword" /> </div>

        <div class="form-group  labelDiv2">
            <label class="mt-checkbox margin-bottom-5 mt-checkbox-outline">
                <input type="checkbox" id="tnc" name="tnc" value="checkbox"/> 我已阅读并同意
                <a href="#list" data-toggle="modal" class="lawColor">拖拽平台服务条例 </a>
                <%--<a href="#law" data-toggle="modal">政策法规 </a>--%>
                <span></span>
            </label>
            <div id="register_tnc_error"> </div>
        </div>
        <div class="form-actions margin-bottom-40 loginDiv1">
            <%--<button type="button" id="register-back-btn" class="btn green btn-outline">返回</button>--%>
            <button type="submit"  id="register-submit-btn" class="btn btn-success uppercase ">提交</button>
        </div>
    </form>
    <!-- END REGISTRATION FORM -->
</div>
<%--<div class="copyright"> 2016 &copy; Youedata.All rights reserved. </div>--%>

<!-- /#list -->
<div class="modal fade bs-modal-lg" id="list" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">拖拽平台服务条例</h4>
            </div>
            <div class="modal-body" id="modalList">
                <p>
                    尊敬的用户，您好！<br/>
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    欢迎您使用可视化据挖掘平台，为了能够更好的为您提供服务，您应当阅读并遵守《国信优易集团用户协议》（以下简称“本协议”）。请您务必审慎阅读，充分理解各条款的内容，并选择接受或不接受。除非您已阅读并接受本协议所有条款，否则您无法使用本产品。您在本产品的登陆、查看、发布信息等行为即视为您已阅读并同意受本协议的约束。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【协议的范围】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    本协议是您与国信优易集团之间关于可视化据挖掘平台产品的法律协议。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【可视化平台个人信息的保护】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.您在申请本产品提供服务的过程中，需要填写一些必要的信息，请保持这些信息的及时更新，以便国信优易集团能够更好的为您提供服务，并给予您帮助。若国家法律法规有特殊规定的，您需要填写真实的身份信息。若您填写的信息不完整或不准确，则可能无法使用本产品或在使用过程中受到限制。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2. 国信优易集团与用户一同致力于个人信息的保护，保护用户的个人信息是国信优易集团的一项基本原则。若没有经过您的同意，国信优易集团不会向本协议已明确的以外的任何公司、组织和个人披露您的个人信息。但您违反本协议约定导致他人投诉或者主管机关追究责任，以及法律法规另有规定的除外。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.您理解并同意：鉴于产品提供的是据挖掘与分析服务，为改善用户体验，国信优易集团可以：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    （1）对您提交的信息进行使用，该使用结果可能不准确，请您在使用相关服务时谨慎独立判断；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    （2）对您的用户名、头像以及在本产品中的相关操作信息等信息进行使用，并可通过国信优易集团的产品向您本人、其他用户展示此类信息。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.为更好的保护您的个人信息，您应尽快、充分了解本产品的相关功能、规则和设置，同时，您应合理设定您的个人信息的公开范围。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    您理解并同意：鉴于产品提供的是据挖掘与分析服务，为改善用户体验，国信优易集团可能会将您的个人信息的公开范围默认设置为公开，该默认设置会导致他人接触或获取您的个人信息；如您希望变更默认设置，请您在相关服务页面予以变更。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5. 您应对通过本产品了解、接收或可接触到的包括但不限于其他用户在内的任何人的个人信息予以充分尊重，您不应以搜集、复制、存储、传播或以其他任何方式使用其他用户的个人信息，否则，由此产生的后果需要由您自行承担。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【平台用户使用规则】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    如果您想使用本产品或者国信优易集团的其他产品、程序和服务的更多功能，您必须注册相应的账号并且于注册页面上提供相关的个人信息。您可以按照产品说明随时终止使用您的账号，本产品将会依据本协议规定保留或终止您的账号。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    您必须承诺和保证：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.您了解并同意，本产品是一个【应用服务产品】，用户须对注册信息的真实性、合法性、有效性承担全部责任；用户不得冒充他人，不得利用他人的名义发布任何信息；不得恶意使用账号导致其他用户误认；否则我们有权立即停止提供服务，且您须独自承担由此而产生的一切法律责任。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.您使用本产品与国信优易集团的其他产品、程序及服务的行为必须合法，您必须为自己账号下的所有行为负责，包括您所发表的任何内容以及由此产生的任何结果。用户应对其中的内容自行加以判断，并承担因使用内容而引起的所有风险，包括因对内容的正确性、完整性或实用性的依赖而产生的风险。国信优易集团无法且不会对因用户行为而导致的任何损失或损害承担责任。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.您对您的登录信息保密、不被其他人获取并使用并且对您在本产品账号下的所有行为负责。您必须将任何有可能触犯法律的，未授权使用或怀疑为未授权使用的行为在第一时间通知本产品，本产品不对您因未能遵守上述要求而造成的损失承担责任。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.您通过本网站发表的信息，信息如果是公开的，其他用户或第三方均可以通过本产品获取用户发表的信息，用户对任何信息的发表并认可该信息为公开的信息，并单独对此行为承担法律责任；任何用户不愿被其他人获知的信息都应该在该平台上进行合理设置。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    您必须知悉并确认：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.我们因业务发展需要，单方面对本产品的全部或部分服务内容在任何时候不经任何通知的情况下变更、暂停、限制、终止或撤销我们服务的权利，用户需承担此风险。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.我们提供的服务中可能包括广告等活动，用户同意在使用过程中显示我们及关联方与第三方供应商、合作伙伴提供的广告以及其他活动。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.我们有权自行全权决定以任何理由，对违反有关法律法规或本协议约定；或侵犯、妨害、威胁任何人权利或安全的内容，或者假冒他人的行为，有权依法停止传输任何相关内容，并有权依其自行判断对违反本协议的任何人士采取适当的法律行动，包括但不限于，从服务中删除具有违法性、侵权性、不当性等内容，终止违反者的成员资格，阻止其使用我们全部或部分服务，并且依据法律法规保存有关信息并向有关部门报告等。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.我们的某些产品与服务必须在联网的情况下才可以使用，您必须自行负担个人上网或第三方（包括但不限于电信或移动通讯提供商）收取的通讯费、信息费等相关费用。如涉及电信增值服务，我们建议您与增值服务提供商确认相关费用问题。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【可视化平台用户内容】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.用户内容是指该用户上传、发布、下载或以其他方式使用本产品与国信优易集团其他产品、程序及服务时产生的所有内容（例如：您的信息、图片、据或其他内容）；您是您的用户内容唯一的责任人，您将承担因您的用户内容披露而导致的您或任何第三方被识别的风险。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.您通过上传、发行或其他方式使用您在本产品与国信优易集团其他产品、程序及服务的用户内容时，即视为自动授权且承诺和保证您有权授权给我们不可撤销的、非独家的、免版税的且足额缴纳的全球许可，用以：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）仅为向您提供本产品与国信优易集团其他产品和程序服务或改进本产品与国信优易集团其他产品、程序及服务之目的，复制、发行、公开展示和表演、制作衍生作品、将其纳入其他作品或以其他方式使用您的用户账户（您的个人信息除外）；并授予上述事项的转让许可；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）仅为向目标接收者提供个人信息之目的，复制、发布用户内容中的私人信息；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）您同意不可撤销的、放弃的（并会导致被放弃）关于您的用户内容的道德权利和归属的声明。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【用户权利与义务】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.合法使用本产品的权利。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.在您所有的设备使用国信优易集团的产品、程序及服务的权利。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.账号的所有权归国信优易集团及其关联公司所有，基于账号安全性的考虑，禁止使用他人的手机号、邮箱地址注册国信优易集团的账号。您完成申请注册手续后，获得国信优易集团账号的使用权，该使用权仅属于初始申请注册人，禁止赠与、借用、租用、转让或售卖。国信优易集团因经营需要，有权回收用户的账号。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.您有权更改、删除在本产品上的个人资料、注册信息及发布内容等，但需注意，删除有关信息的同时也会删除任何您储存在系统中的文字和图片，用户需承担该风险。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5.您有责任妥善保管注册账号信息及账号密码的安全，您需要对注册账号以及密码下的行为承担法律责任。用户同意在任何情况下不使用其他成员的账号或密码。在您怀疑他人在使用您的账号或密码时，您同意立即通知国信优易集团。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    6.权利限制：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）您不能对本产品的内容或国信优易集团其他产品、程序及服务（包括但不限于内容或产品中的广告或赞助内容）进行任何形式的许可、出售、租赁、转让、发行或做其他商业用途。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）您不得以创建相同或竞争服务为目的访问本产品或使用国信优易集团其他产品、程序及服务。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）除非法律明文规定，否则您不能对本产品或国信优易集团其他产品、程序及服务（包括但不限于内容或产品中的广告或赞助内容）的任何部分以任何形式或方法进行复制、发行、再版、下载、显示、张贴、修改、翻译、合并、利用、分解或反向编译等。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4）您已同意通过上传、发布或以其他方式使用本产品或国信优易集团其他产品、程序和服务，在使用过程中，您将承担因下述行为所造成的风险而产生的全部法律责任：破坏宪法所确定的基本原则的；危害国家安全、泄露国家秘密、颠覆国家政权、破坏国家统一的;损害国家荣誉和利益的；煽动民族仇恨、民族歧视，破坏民族团结的；破坏国家宗教政策，宣扬邪教和封建迷信的；散布谣言，扰乱社会秩序，破坏社会稳定的；散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；侮辱或者诽谤他人，侵害他人合法权益的；含有法律法规、行政规章所禁止的其他内容的。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5）您已同意不在本产品或利用国信优易集团其他产品、程序及服务从事下列行为：上传或发布电脑病毒、蠕虫、恶意代码、故意破坏或改变计算机系统或据的软件；未授权的情况下，收集其他用户的信息或据，例如电子邮箱地址等；禁用本产品的网络连接，给本产品造成过度的负担或以其他方式干扰或损害产品服务器和网络链接；在未授权的情况下，尝试访问本产品的网络链接；干扰、破坏其它用户正常使用本网站或国信优易集团产品、程序及服务。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【损害赔偿】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.您已同意无害的使用本产品和国信优易集团其他产品、程序及服务，避免国信优易集团因下述行为或相关行为遭受来自第三方的任何投诉、诉讼、损失、损害、责任、成本和费用（包括但不限于诉讼费、律师费等）：您使用本产品和国信优易集团其他产品、程序及服务的行为；您的用户内容；您违反本协议的行为。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.用户内容是指该用户下载、发布或以其他方式使用本产品与国信优易集团其他产品、程序及服务时产生的所有内容（例如：您的信息、图片、据或其他内容）；您是您的用户内容唯一的责任人，您将承担因您的用户内容披露而导致的您或任何第三方被识别的风险。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.您已同意，除非获得国信优易集团书面同意，您不能在您与国信优易集团共同对第三方提起的诉讼中单方和解。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.国信优易集团将尽可能的努力将此类诉讼、诉讼行为或进程通知您。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5.在任何情况下，国信优易集团都不对您或任何第三方因本协议产生的任何间接性、后果性、惩戒性的、偶然的、特殊或惩罚性的损害赔偿承担责任。访问、使用本产品和国信优易集团其他产品、程序及服务所产生的损坏计算机系统或移动通讯设备据库的风险将由您个人承担。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【免责声明】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.如发生下述情形，国信优易集团不承担任何法律责任和第三方被识别的风险。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）依据法律规定或相关政府部门的要求提供您的个人信息；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）由于您的使用不当或其他自身原因而导致任何个人信息的泄露；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）任何由于黑客攻击，电脑病毒的侵入，非法内容信息、骚扰信息的屏蔽，政府管制以及其他任何网络、技术、通信线路、信息安全管理措施等原因造成的服务中断、受阻等不能满足用户要求的情形；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4）用户因第三方如运营商的通讯线路故障、技术问题、网络、电脑故障、系统不稳定及其他因不可抗力造成的损失的情形；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5）使用国信优易集团的产品、程序及服务可能存在的来自他人匿名或冒名的含有威胁、诽谤、令人反感或非法内容的信息而招致的风险；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    6）用户之间通过本产品或国信优易集团其他产品、程序及服务与其他用户交往，因受误导或欺骗而导致或可能导致的任何心理、生理上的伤害以及经济上的损失；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    7）本产品和国信优易集团其他产品、程序及服务明文声明，不以明示、默示或以任何形式对国信优易集团及合作公司服务之及时性、安全性、准确性做出担保。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    8）据法律法规规定可以免责的其他情形。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.用户在本网站所发布的任何内容并不代表和反映国信优易集团的任何观点或政策，国信优易集团对此不承担任何责任。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.在任何情况下，国信优易集团均不对任何间接性、后果性、惩罚性、偶然性、特殊性或刑罚性的损害，包括因用户使用国信优易集团提供的服务而遭受的经济损失，承担责任。尽管本协议中可能含有相悖的规定，我们对您承担的全部责任，无论因何原因或何种行为方式，始终不超过您在注册期内因使用国信优易集团服务而支付给国信优易集团的费用(如有)。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【知识产权】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.用户在国信优易的网站或互动平台上发布的信息不得侵犯任何人的知识产权，未经具有相关所有权所有者之事先书面同意，用户不得以任何方式上传、发布、修改、传播或复制任何受知识产权保护的材料、商标或属于其他人的专有信息。如果收到任何著作权人或其合法代表发给国信优易集团的适当通知后，我们将在审查的基础上移除该等侵犯他人著作权的内容。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.国信优易集团提供的服务中所涉及的图形、文字或其组成，以及其他国信优易集团的标志及产品、服务名称，均为国信优易集团之商标。未经国信优易集团事先书面同意，用户不得将国信优易集团标识以任何方式展示或使用或作其他处理，任何单位及个人不得以任何方式或理由对该商标的任何部分进行使用、复制、修改、传播、抄录或与其它产品捆绑使用销售。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.除前述规定以外，如果您认为有人复制并在国信优易集团的网站服务上发布您的作品，并已构成对您著作权的侵犯，请及时与我们联系，并提供包含如下信息的书面通知：
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）证明您作为涉嫌侵权内容所有者的权属证明；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）明确的身份证明、住址、联系方式；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）涉嫌侵权内容在国信优易集团服务上的位置；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4）您声称遭受侵犯的著作权作品的描述；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5）您著作权遭受侵犯的相关证明；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    6）在同意承担伪证处罚之后果的前提下，出具书面陈述以声明您在通知中所述内容是准确和真实的。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【修改与终止】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.修改
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）本协议容许变更。如果本协议有任何实质性变更，我们将通过您提供的电子邮件或本网站的公告来通知您。变更通知之后，继续使用本产品和国信优易集团其他产品、程序及服务则视为您已知晓此类变更并同意受条款其约束；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）国信优易集团保留在任何时候无需通知而修改、保留或关闭本产品、国信优易集团其他产品、程序或任何服务之权利；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）您已同意国信优易集团无需因修改、保留或关闭本产品、国信优易集团其他产品、程序或其他服务的行为对您或第三方承担责任。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.终止
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）本协议自您接受之日起生效，在您使用本产品和国信优易集团其他产品、程序及服务的过程中持续有效，直至依据本协议终止；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）尽管有上述规定，如果您使用本产品和国信优易集团其他产品、程序及服务的时间早于您接受本协议的时间，您在此知晓或应当知晓并同意本协议于您第一次使用本网站和国信优易集团其他产品、程序及服务时生效，除非依据本协议提前终止。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3. 我们可能会：依据法律的规定，保留您使用本产品、国信优易集团其他产品、程序及服务、或者本网站账户的权利；无论是否通知，我们将在任何时间以任何原因终止本协议，包括出于善意的相信您违反了我们可接受使用政策或本协议的其他规定。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.不受前款规定所限，如果用户侵犯第三人的版权并且国信优易集团接到版权所有人或版权所有人的合法代理人的通知后，国信优易集团保留终止本协议的权利。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5.一旦本协议终止，您的网站账户和使用本产品和国信优易集团产品、程序及服务的权利即告终止。您应当知晓您的网站账户终止意味着您的用户内容将从我们的活动据库中删除。国信优易集团不因终止本协议对您承担任何责任，包括终止您的用户账户和删除您的用户内容。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    6.任何本网站的更新版本或国信优易集团的产品、程序及服务的未来版本、更新或者其他变更将受到本协议约束。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    【其他】
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1.反馈
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）您对国信优易集团提出建议（或称“反馈”），即视为您向国信优易集团转让“反馈”的全部权利并同意国信优易集团有权利以任何合理方式使用此反馈及其相关信息。我们将视此类反馈信息为非保密且非专有；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）您已同意您不会向国信优易集团提供任何您视为保密和专有的信息；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）我们将保留基于我们的判断检查用户内容的权利（而非义务）。无论通知与否，我们有权在任何时间以任何理由删除或移动您的用户内容。依据第8条规定，我们有权保留或终止您的账户。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2.通知
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    您必须提供您最近经常使用的有效的电子邮件地址。您所提供的电子邮件地址无法使用或者因任何原因我们无法将通知送达给您而产生的风险，国信优易集团不承担责任。本网站发布的公告通知及向您所发送的包含此类通知的电子邮件毫无疑问构成有效通知。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3.适用法律
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）本协议适用中华人民共和国法律；
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）如果双方发生纠纷，应本着友好的原则协商解决；如协商不成，应向国信优易集团有限公司所在地的法院提起诉讼。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    4.独立性
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    若本协议中的某些条款因故无法适用，则本协议的其他条款继续适用且无法适用的条款将会被修改，以便其能够依法适用。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    5.完整性
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    1）本协议是您和国信优易集团之间关于本产品和国信优易集团其他产品、程序及服务相关事项的最终的、完整的、排他的协议，且取代和合并之前当事人关于此类事项（包括之前的最终用户许可、服务条款）的讨论和协议。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    2）每部分的题目只为阅读之便而无任何法律或合同义务。
                </p>
                <p>
                    <br/>
                </p>
                <p>
                    3）除非国信优易集团书面同意，您不得转让本协议所规定的权利义务。任何违反上述规定企图转让的行为均无效。
                </p>
                <p>
                    <br/>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn dark btn-outline" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<script src="${basePath}/assets/global/plugins/respond.min.js"></script>
<script src="${basePath}/assets/global/plugins/excanvas.min.js"></script>
<jsp:include page="/content/css/login/baseJS.jsp" flush="true"/>
<script src="${basePath}/assets/global/plugins/jquery.cookie.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery.md5.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<!-- END THEME LAYOUT SCRIPTS -->
</body>
</html>