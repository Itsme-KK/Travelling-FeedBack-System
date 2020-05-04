<?php
    session_start();
    require "../conn.php";
    if(!$_SESSION['username']){
        header("location: login.php");
        die;
    }
    $id = $_POST['id'];
    $status = $_POST["status"];
    if($conn){
        $sqlUpdateQuery = "UPDATE `user_feedback` SET `status` = '$status' WHERE `id` = '$id'";
        if(mysqli_query($conn, $sqlUpdateQuery)){
            echo "Successfully Updated";
            header("location: feedbacks.php");
        } else {
            echo "Cannot Update to Database Value";
        }
    } else{
        echo "Connection Error";
    }


?>