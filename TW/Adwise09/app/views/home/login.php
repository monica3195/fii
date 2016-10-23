<script src="<?php echo (PUBLIC_ROOT);?>/javascript/login.js"></script>
<html>
<head>
    <link rel="stylesheet"  href="<?php echo (PUBLIC_ROOT);?>/assets/css/forms.css">
    <title>Login form </title>
</head>
<body>
<div class="form">
    <div class="register_form_container">
        <form class="" method="post" action="">
            <fieldset class="input-fields-login">
                <div id="loginresult" style="display: none">

                </div>
                <legend class="head-legend">Login on AdWise</legend>
                <input class="username-or-email" type="text" name="username" id="username" placeholder="Username or email" />
                <input class="password" type="password" name="password" id="password" placeholder="Password" />
                <input type="checkbox" class="remember-checkbox" id="rememberme" name="rememberme" >Remember me<br>

                <button type="button" id="loginBt" class="login-button"> Login </button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>