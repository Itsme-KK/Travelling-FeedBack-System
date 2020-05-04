<?php

    require "conn.php";

    $name = $_POST["name"];
    $email = $_POST["email"];
    $username = $_POST["username"];
    $password = $_POST["password"];

    $isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);
    if($conn){
        if(strlen($password) > 40 || strlen($password) < 8){
            echo "Password must be less than 40 and more than 8 character!";
        } elseif ($isValidEmail === false) {
            echo "This email is not valid!";
        } else {
            $sqlCheckUsername = "SELECT * from `users` WHERE `username` LIKE '$username'";
            $usernameQuery = mysqli_query($conn, $sqlCheckUsername);

            $sqlCheckEmail = "SELECT * from `users` WHERE `email` LIKE '$email'";
            $emailQuery = mysqli_query($conn, $sqlCheckEmail);

            if(mysqli_num_rows($usernameQuery) > 0){
                echo "Username is already used type another one.";
            } elseif(mysqli_num_rows($emailQuery) > 0) {
                echo "Email is already used! type another one.";
            } else{
                $sql_register = "INSERT INTO `users` (`name`,`email`,`username`,`password`) VALUES ('$name','$email','$username','$password')";

                if(mysqli_query($conn, $sql_register)){
                    echo "Successfully Registered";
                } else {
                    echo "Failed to Register!";
                }
            }
        }
    } else{
        echo "Connection Error!";
    }

?>