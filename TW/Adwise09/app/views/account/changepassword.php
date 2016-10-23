<script src="../javascript/changePassword.js"></script>
<div class="register_form_container">
        <form class="" method="post" action="/Adwise09/public/account/changepassword">
            <fieldset>
                <legend>Change password</legend>
                <div id="changepasswordresult" style="display: none">

                </div>
                <p>
                    Old password :
                    <input type="password" name="oldpassword" id="oldpassword" placeholder="Old password" />
                </p>
                <p>
                    New password :
                    <input type="password" name="newpassword" id="newpassword" placeholder="New password" />
                </p>
                <p>
                    Confirm password :
                    <input type="password" name="cnewpassword" id="cnewpassword" placeholder="Confirm new password" />
                </p>
                <button type="button" id="changePasswordBt"> Change password </button>
            </fieldset>
        </form>
    </div>