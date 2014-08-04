<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <title>Agent Portal</title>
    <meta charset="utf-8"/>
    <link rel="shortcut icon" href="<@url value='/dstatic/images/favicon.ico'/>" type="image/vnd.microsoft.icon"/>
    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
    <@js src="html5.js"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <@js src="json2.js"/>
    <![endif]-->
<@css href="foundation.min.css" rel="stylesheet" type="text/css"/>
<@js src="jquery.min.js,common.js"/>
    <script type="text/javascript">
        golbalRootUrl = "<@url value='/' />";
        $(document).ready(function () {
            if ($("#getMsgType").val() == "error") {
                $("#infoNor").hide();
                $("#infoErr").show();
            }
            $("#site-list > a").mouseover(function () {
                $("#site-list ul").show();
            })
            $("#site-list ul li a").click(function () {
                $("#site-list ul").hide();
            });

            $("#loginForm").validate({
                rules: {
                    emailAddress: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true
                    }
                },
                onfocusout: false,
                onkeyup: false,
                onclick: false,
                messages: {
                    emailAddress: {
                        required: "<strong>Error:</strong> Email Address is a rquired field",
                        email: "<strong>Error:</strong> Please enter a valid email address."
                    },
                    password: {
                        required: "<strong>Error:</strong> Password is a rquired field."
                    }
                },
                errorPlacement: function (error, element) {
                    var errorMsg = $(error).text();
                    if (errorMsg != "") {
                        $("#infoNor").hide();
                        $("#infoErr").show();
                    }
                },
                success: function (error, element) {
                },
                wrapper: "li"
            });
        });
    </script>
</head>
<body>

<!-- Header and Nav -->
<nav class="top-bar">
    <ul class="title-area">
        <!-- Title Area -->
        <li class="name">
            <h1><span style="color:White;">Agent</span></h1>
        </li>
    </ul>
</nav>
<!-- End Header and Nav -->

<!-- Main Section -->
<section class="main">

    <div class="row">

        <div class="large-4 large-centered column">
            <form id="loginForm" action="<@url value='/login'/>" class="form-signin" method="post">
                <div id="infoNor" class="font16 textCenter pt10 pb10 displayNone">
                    Please Enter Your Email Address and Password.
                </div>
                <div id="infoErr" class="font16 textCenter pt10 pb10 errorColor displayNone">
                    Invalid Username or Password. Please Try Again.
                </div>
            <#if msgType??>
                <input type="hidden" id="getMsgType" value="${msgType}"/>
            </#if>
                <div>
                    <h2 class="form-signin-heading">Please sign in</h2>
                </div>
                <div id="info">
                </div>
                <input type="text" name="emailAddress" id="emailAddress" class="input-block-level required email" placeholder="Email Address" autofocus>
                <input type="password" name="password" id="password" class="input-block-level required" placeholder="Password">
                <button id="sign" class="btn btn-large btn-primary btn-block" type="submit">Sign in</button>
                <a id="register" href="<@url value='/register'/>">Register</a>
            </form>
        </div>

    </div>

</section>
<!-- End Main Section -->
</body>
</html>