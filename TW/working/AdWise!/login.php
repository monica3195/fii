<?php
session_start();
if(isset($_SESSION['remember'])){
    header("location: /profilePage/index.php");
}
?>

<html>
<head>
	<link rel="stylesheet" href="forms.css" />
	<title>Login form </title
</head>
<body>
	<div class="register_form">
		<div class="register_form_container">
			<form class="" method="post" action="checker.php">
				<fieldset>
					<legend>Login on AdWise</legend>
					<input type="text" name="reg_username_email" placeholder="Username or email" />
					<input type="password" name="reg_password" placeholder="Password" />
					
					<input type="submit" value="Login up" />
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>