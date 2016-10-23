<?php

    session_start();

    $id = $_SESSION["id"];



    $conn = oci_connect('iustin', 'iustin', '127.0.0.1/xe');

    if (!$conn) {
        $e = oci_error();
        trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
    }

    $stid = oci_parse($conn, 'SELECT username,first_name,last_name,email FROM profiles WHERE id_profile ='.$id);
    $a = oci_define_by_name($stid, 'USERNAME', $userProfile);
    $a = oci_define_by_name($stid, 'FIRST_NAME', $userFirstName);
    $a = oci_define_by_name($stid, 'LAST_NAME', $userLastName);
    $a =  oci_define_by_name($stid, 'EMAIL', $userEmail);

    oci_execute($stid);

    oci_fetch($stid);

    oci_free_statement($stid);
    oci_close($conn);

?>


<html>

<html xml:lang="en" >
<head>
<title>AdWise!</title>
	<link href="topBar.css" rel="stylesheet">
	<link href="css/profile.css" rel="stylesheet" type="text/css" media="all">
	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
</head>
<body>
    <div class="nav-wrapper">
        <ul class="nav">
            <li id="home">
                <a href="#"><img src="images/home.png" /></a>
            </li>
            <li id="categories">
                <a href="#">Categories</a>
                <ul class="subnav">
                    <li><a href="#">Math</a></li>
                    <li><a href="#">Physics</a></li>
                    <li><a href="#">English</a></li>
                </ul>
            </li>
            <li id="banner">
                <a href="#">AdWise!</a>
            </li>
            <li id="search">
                <form action="" method="get">
                    <input type="text" name="search_text" id="search_text" placeholder="Search"/>
                    <input type="button" name="search_button" id="search_button"></a>
                </form>
            </li>
            <li id="search_options">
                <a href="#">Search options</a>
                <ul class="subnav">
                    <li><a href="#">Tag</a></li>
                    <li><a href="#">Content</a></li>
                    <li><a href="#">Tag & Content</a></li>
                </ul>
            </li>
            <li id="profile">
                <a href="#"><?php echo $userProfile ?></a>
            </li>

        </ul>
    </div>

      <div id="w">
    <div id="content" class="clearfix">
      <div id="userphoto"><img src="images/avatar.png" alt="default avatar"></div>
      <h1><?php $userProfile ?></h1>

      <nav id="profiletabs">
        <ul class="clearfix">
          <li><a href="#settings" class="sel">Profile</a></li>
          <li><a href="#bio">Badges</a></li>
        </ul>
      </nav>
      
	  <section id="settings">
	  
		<p class="setting"><span>Name </span>: <?php echo $userFirstName ?>      </p>
		
		<p class="setting"><span>Surname</span>:<?php echo $userLastName ?>      </p>
        
        <p class="setting"><span>E-mail Address</span>:<?php echo $userEmail ?>     </p>
        
        <p class="setting"><span>Profile Status</span> Public</p>
		
		<p class="setting"><span>Connected Accounts</span> None</p>
		
		<p class="setting"><span>Questions asked </span> 5</p>
        
        <p class="setting"><span>Questions answered </span> 101</p>
		
		<p class="setting"><span>Edit profile <img src="images/edit.png" alt="*Edit*"></span></p>
      </section>
	  
	  
      <section id="bio" class="hidden">
	  
      </section>
    
    </div><!-- @end #content -->
  </div><!-- @end #w -->
<script type="text/javascript">
$(function(){
  $('#profiletabs ul li a').on('click', function(e){
    e.preventDefault();
    var newcontent = $(this).attr('href');
    
    $('#profiletabs ul li a').removeClass('sel');
    $(this).addClass('sel');
    
    $('#content section').each(function(){
      if(!$(this).hasClass('hidden')) { $(this).addClass('hidden'); }
    });
    
    $(newcontent).removeClass('hidden');
  });
});
</script>


</body>
</html>
