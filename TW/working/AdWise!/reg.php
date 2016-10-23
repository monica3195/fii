<?php

$conn = oci_connect('iustin', 'iustin', '127.0.0.1/xe');

if (!$conn) {
    $e = oci_error();
    trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
}

$username = $_REQUEST["reg_username"];
echo($username);
$password = $_REQUEST["reg_password"];
echo($password);
$email = $_REQUEST["reg_email"];
echo($email);
$connected = -1;

$stid = oci_parse($conn, 'begin :ok := ACCESS_UTILS.INREGISTRARE(:pass, :user, :mail); end;');
oci_bind_by_name($stid,':user',$username);
oci_bind_by_name($stid,':pass',$password);
oci_bind_by_name($stid,':mail',$email);
oci_bind_by_name($stid,':ok',$connected,40);

oci_execute($stid);

oci_free_statement($stid);
oci_close($conn);

echo($connected);

?>