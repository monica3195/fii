<script src="../javascript/editProfile.js"></script>
<div class="register_form">
    <div class="register_form_container">
        <form class="" method="post" action="">
            <fieldset>
                <legend>Edit Profile</legend>
                <div id="editprofileresult" style="display: none">

                </div>
                <p>
                    First name:
                    <input type="text" name="first_name" id="first_name"  value="<?php echo ($data?$data->getFirstName() : 'none'); ?>" placeholder="First name" />
                </p>
                <p>
                    Last name:
                    <input type="text" name="last_name" id="last_name" value="<?php echo ($data?$data->getLastName() : 'none'); ?>" placeholder="Last name" />
                </p>
                <p>
                    Email:
                    <input type="email" name="email" id="email" value="<?php echo ($data?$data->getEmail() : 'none'); ?>" placeholder="Email" />
                </p>
                <p>
                    Age:
                    <input type="text" name="age" id="age" value="<?php echo ($data?$data->getAge() : 'none'); ?>" placeholder="Age" />
                </p>
                <p>
                    Country:
                    <input type="text" name="country" id="country" value="<?php echo ($data?$data->getCountry() : 'none'); ?>" placeholder="Country" />
                </p>
                <p>
                    City:
                    <input type="text" name="city" id="city" value="<?php echo ($data?$data->getCity() : 'none'); ?>" placeholder="City" />
                </p>
                <button type="button" id="editProfileBt"> Edit profile </button>
            </fieldset>
        </form>
    </div>
</div>

