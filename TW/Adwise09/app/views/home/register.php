<html>
<head>
    <link rel="stylesheet"  href="<?php echo (PUBLIC_ROOT);?>/assets/css/forms.css">
    <title>Register form </title>
</head>
<body>
<div class="form">
    <div class="register_form_container">
        <form class="" method="post" action="">
            <fieldset class="input-fields-login">
                <legend class="head-legend">Sign up on AdWise</legend>
                <input class="username-or-email" type="text" name="username" placeholder="Username" />
                <input class="username-or-email" type="password" name="password" placeholder="Password" />
                <input class="username-or-email" type="password" name="cpassword" placeholder="Confirm password" />
                <input class="username-or-email" type="email" name="email" placeholder="Email" />
                <div class="captcha_image">
                </div>
                <div class="captcha_button">
                </div>
                <input class="username-or-email" type="text" name="reg_captcha" placeholder="Captcha" />
                <input class="login-button" type="submit" value="Sign up" />
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>