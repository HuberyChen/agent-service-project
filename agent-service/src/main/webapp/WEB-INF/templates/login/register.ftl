<head>
    <title>Agent</title>
    <meta charset="utf-8"/>
<@css href="public.css,foundation.min.css" rel="stylesheet" type="text/css"/>
<@js src="jquery.min.js,jquery.form.js,common.js,jquery.validate.js"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#registerForm").validate({
                rules: {
                    confirmPassword: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {
                    confirmPassword: {
                        required: "<strong>Error:</strong> Confirm is a rquired field",
                        equalTo: "<strong>Error:</strong> Please confirm password."
                    },
                    emailAddress: {
                        required: "<strong>Error:</strong> Email Address is a rquired field",
                        email: "<strong>Error:</strong> Please enter a valid email address."
                    },
                    password: {
                        required: "<strong>Error:</strong> Password is a rquired field."
                    },
                    userName: {
                        required: "<strong>Error:</strong> User Name is a rquired field."
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
<div class="main">
    <div class="container">
        <form id="registerForm" class="custom" action="<@url value='/register'/>" method="post">
            <div class="row">
                <div class="large-12 large-centered columns">
                    <p class="text-center">* is required.</p>

                    <p id="errInfo" class="errorBox"></p>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">*Email Address:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" class="required email" id="emailAddress" name="emailAddress" placeholder="Email Address"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">*User Name:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" class="required" id="userName" name="userName" placeholder="User Name"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">*Password:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="password" class="required" id="password" name="password" placeholder="Password"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">Confirm:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">First Name:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" id="firstName" name="firstName" placeholder="First Name"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">Last Name:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" id="lastName" name="lastName" placeholder="Last Name"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">Phone:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" id="phone" name="phone" placeholder="Phone"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">Address:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" id="address" name="address" placeholder="Address"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <div class="large-4 small-3 columns text-right"><label style="margin-top:10px;">Zip Code:</label></div>
                    <div class="large-8 small-9 columns">
                        <input type="text" id="zipCode" name="zipCode" placeholder="Zip Code"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 large-centered columns">
                    <p>
                        <input style="margin-left: 180px;margin-top: -10px;" type="button" onclick="register()" value="register"/>
                    </p>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function register() {
        $("#errInfo").text('');
        if (!$("#registerForm").valid()) {
            return false;
        }

        $("#registerForm").ajaxSubmit({callback: function (result) {
            if (result.msgType = "error") {
                $("<label class='error'><strong>Error:</strong>" + result.msg + "</label>").appendTo($("#errInfo"));
            }

            if (result.msgType == "success") {
                alert(result.msg);
                window.location.href = "<@url value = '/login'/>"
            }
        }, validate: false});
    }
</script>
</body>