<?php
    session_start();
    require "conn.php";

    $username = $_POST["username"];
    $password = $_POST["password"];

    if($conn){
        $sqlCheckUsername = "SELECT * FROM `users` WHERE `username` LIKE '$username'";
        $usernameQuery = mysqli_query($conn, $sqlCheckUsername);

        if(mysqli_num_rows($usernameQuery) > 0 && $username == 'admin'){

            $sqlAdminLogin = "SELECT * FROM `users` WHERE `username` LIKE '$username' AND `password` LIKE '$password'";
            $adminLoginQuery = mysqli_query($conn, $sqlAdminLogin);
            if(mysqli_num_rows($adminLoginQuery) > 0){
                $_SESSION['username'] = $username;
                header("location: admin/usersdetails.php");
            } else {
                echo "Invalid Password";
            }
        } elseif (mysqli_num_rows($usernameQuery) > 0){
            $sqlLogin = "SELECT * FROM `users` WHERE `username` LIKE '$username' AND `password` LIKE '$password'";
            $loginQuery = mysqli_query($conn, $sqlLogin);
            if(mysqli_num_rows($loginQuery) > 0){
                echo "Login Successful";
            } else {
                echo "Invalid Password!";
            }
        } else {
            echo "Username not found";
        }
    } else {
        echo "Connection Error!";
    }

?>