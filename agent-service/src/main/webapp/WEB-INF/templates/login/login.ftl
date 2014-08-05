<head>
    <title>Agent Portal</title>
    <link rel="shortcut icon" href="<@url value='/dstatic/images/favicon.ico'/>" type="image/vnd.microsoft.icon"/>
    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
    <@js src="html5.js"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <@js src="json2.js"/>
    <![endif]-->
<@css href="foundation.min.css" rel="stylesheet" type="text/css"/>
<@js src="jquery.min.js,common.js,jquery.validate.js,jquery.form.js"/>
    <script type="text/javascript">
        $(document).ready(function () {
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
                messages: {
                    emailAddress: {
                        required: "<strong>Error:</strong> Email Address is a rquired field",
                        email: "<strong>Error:</strong> Please enter a valid email address."
                    },
                    password: {
                        required: "<strong>Error:</strong> Password is a rquired field."
                    }
                },
                errorPlacement: function (error) {
                    error.appendTo($("#errInfo"));
                },
                success: function (error) {
                    error.parent("li").remove();
                    error.remove();
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
                <div class="row">
                    <div class="large-12 large-centered columns">
                        <p id="errInfo" class="errorBox"></p></div>
                </div>
                <div style="display: none" id="infoErr" class="font16 textCenter pt10 pb10 errorColor displayNone">
                    Invalid Username or Password. Please Try Again.
                </div>
                <div>
                    <h2 class="form-signin-heading">Please sign in</h2>
                </div>
                <input type="text" name="emailAddress" id="emailAddress" class="input-block-level required email" placeholder="Email Address" autofocus>
                <input type="password" name="password" id="password" class="input-block-level required" placeholder="Password">
                <button id="sign" class="btn btn-large btn-primary btn-block" onclick="login()">Sign in</button>
                <a id="register" href="<@url value='/register'/>">Register</a>
            </form>
        </div>

    </div>

</section>
<!-- End Main Section -->
<script type="text/javascript">
    function login() {
        $("#errorInfo").text('');
        $("#infoErr").hide();
        if (!$("#loginForm").valid()) {
            return false;
        }

        $("#loginForm").ajaxSubmit({callback: function (result) {
            if (result.msgType == "error") {
                $("#infoErr").show();
            }
            if (result.msgType = "success") {
                window.location.href = "<@url value='/home'/>";
            }
        }, validate: false});
    }
</script>
</body>