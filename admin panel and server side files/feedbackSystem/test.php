<?php

    $username = $_POST["username"];
	$pnr_number = $_POST["pnr_number"];
	$feedback_heading = $_POST["feedback_heading"];
    $feedback = $_POST["feedback"];
    
    // $array = array("info"=>
	// 			array("reference_no"=>$reference_no, "message"=>$message)
	// 		);
    
    $array = array("username"=>$username, "pnr_number"=>$pnr_number, "feedback_heading"=>$feedback_heading, "feedback"=>$feedback);
    echo json_encode($array);

    // echo $username;
    // echo $pnr_number;
    // echo $feedback_heading;
    // echo $feedback;
?>