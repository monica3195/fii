<?php
session_start();

$conn = oci_connect('iustin', 'iustin', '127.0.0.1/xe');

if (!$conn) {
    $e = oci_error();
    trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
}

$username = $_REQUEST["reg_username_email"];
$password = $_REQUEST["reg_password"];

$stid = oci_parse($conn, 'begin ACCESS_UTILS.LOG_IN(:pass,:user,:profId,:isAdmin,:isBanned); end;');
oci_bind_by_name($stid,':user',$username);
oci_bind_by_name($stid,':pass',$password);

oci_bind_by_name($stid,':profId',$profileId,4);
oci_bind_by_name($stid,':isAdmin',$admin,4);
oci_bind_by_name($stid,':isBanned',$banned,4);

oci_execute($stid);

var_dump($profileId);

oci_free_statement($stid);
oci_close($conn);

if($profileId != null){
    $_SESSION["id"] = $profileId;
    header("location: profilePage/index.php");
}

?>