<?php
    require_once('../app/utils/Database.php');
    require_once('../app/models/Helper.php');
    $categories = Helper::getCategories();
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet"  href="<?php echo (PUBLIC_ROOT);?>/assets/css/toolbar.css" />
    <title>AdWise</title>
    <script>
        function add_field(){

            var nmb = document.querySelectorAll('.tag').length;
            if (nmb<7) {
                var form = document.getElementById("tags"),
                    input = document.createElement('input');
                input.setAttribute('type', 'text');
                input.setAttribute('class', 'tag');
                input.setAttribute('name', 'tag['+nmb+']');
                input.setAttribute('maxlength','20');
                form.appendChild(input);
            }
        };

        function getPaging(str) {
            document.getElementById('head').textContent=str;
        }
    </script>
</head>
<body>
<div class="toolbar">
    <header class="main-header">
        <div >
            <a href="<?php echo (PUBLIC_ROOT);?>" class="logo"> Ad wise!</a>
        </div>
        <div class="navigation">
            <div class="search-form">
                <form method="get" action="<?php echo (PUBLIC_ROOT."/QuestionsSelector/index"); ?>">
                    <input type="text" class="tftextinput" name="search-content" size="21" maxlength="120">
                    <input type="submit" value="search" class="tfbutton">
                    <select name="type-of-search" class="type-of-search">
                        <option value="by-tag">by tag</option>
                        <option value="by-content">by content</option>
                    </select>
                    <li id="dropdown">
                        <div id="head">Categories</div>
                        <ul class="drop-nav">
                            <?php
                            foreach($categories as $category) {
                            ?>
                            <div class="category">
                                        <a href=<?php echo(PUBLIC_ROOT);?>/QuestionsSelector/index?type-of-search=by-category&search-content=<?php echo $category ?>>
                                            <?php echo $category ?>
                                        </a>
                                    <?php
                                    }
                                ?>
                        </ul>
                    </li>
                </form>
            </div>

            <?php
                if(!isset($_SESSION['logged'])){
            ?>
                    <div class="login-or-logout-button">
                        <a href="<?php echo (PUBLIC_ROOT);?>/home/login" >Login</a>
                    </div>
                    <div class="sign-up-or-profile-button">
                        <a href="<?php echo (PUBLIC_ROOT);?>/home/register" >Sign up</a>
                    </div>
            <?php
            }else{
            ?>
                    <div class="login-or-logout-button">
                        <a href="/Adwise09/public/account/index"><?= $data->getUserName(); ?> </a>
                    </div>
                    <div class="sign-up-or-profile-button">
                        <a href="/Adwise09/public/home/logout">Logout</a>
                    </div>
            <?php
                }?>
        </div>
    </header>
</div>
</body>
</html>