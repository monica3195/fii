<?php

/*
 * Redirect to public directory if user tries to access
 * the project directory
 */
header("Location: public/");

die();