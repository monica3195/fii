<link rel="script" href="<?php echo(PUBLIC_ROOT.'/javascript/forms.js')?>" />
<link rel="stylesheet" href="<?php echo(PUBLIC_ROOT.'/assets/css/post-question.css')?>" />
<div class="post-question-wrapper">
    <div id="post-question">
        <form method="post" action="<?php echo(PUBLIC_ROOT."/question/postquestion");?>">
            <div id="tags">
                <button type="button" onclick="add_field()" id="add-another-tag">Add another tag</button><br>
                Tags:
            </div>
            <div class="categories">
                <legend>Select category </legend>
                <select name="category" class="categories-to-search">
                    <option value="Math">Math</option>
                    <option value="IT">IT</option>
                    <option value="Programming">Programming</option>
                    <option value="PHP">PHP</option>
                    <option value="Iasi">Iasi</option>
                </select>
            </div>
            <div class="textarea-button">
                <textarea name="content" maxlength="500" rows="9" cols="75"></textarea>
                <div class="post-button">
                    <input type="submit" value="Post" style="font: 20px Arial">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>