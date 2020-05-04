<?php

    require "conn.php";

    $reference_number = $_POST["reference_number"];

    if($conn){
        $sql_checkStatus = "SELECT * FROM `user_feedback` WHERE `id` LIKE '$reference_number'";
        $checkStatusQuery = mysqli_query($conn, $sql_checkStatus);

        if(mysqli_num_rows($checkStatusQuery) > 0){
            $sql_status = "SELECT `status` FROM `user_feedback` WHERE `id` LIKE '$reference_number'";
            $checkStatus = mysqli_query($conn, $sql_status);

            if(mysqli_num_rows($checkStatus) > 0){
                $row = mysqli_fetch_assoc($checkStatus);
                $status = $row['status'];

                if($status == "submitted"){
                    echo "3";
                } elseif($status == "received"){
                    echo "2";
                } elseif($status == "unseen"){
                    echo "1";
                }
            } else {
                echo "Reference Number Does Not Exist";
            }
        } else {
            echo "Reference Number Not Found";
        }
    } else {
        echo "Connection Error!";
    }

?>