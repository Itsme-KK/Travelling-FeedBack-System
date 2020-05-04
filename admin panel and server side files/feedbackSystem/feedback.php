<?php
	require "conn.php";

	$username = $_POST["username"];
	$pnr_number = $_POST["pnr_number"];
	$feedback_heading = $_POST["feedback_heading"];
	$feedback = $_POST["feedback"];

	 if($conn){
		$sql_name = "SELECT `name` FROM users WHERE `username` LIKE '$username'";
		$nameQuery = mysqli_query($conn, $sql_name);
		$name = mysqli_fetch_array($nameQuery);
		$name = $name['name'];

		$sql_submit = "INSERT INTO `user_feedback` (`username`,`name`,`pnr_number`,`feedback_heading`,`feedback`) VALUES ('$username','$name','$pnr_number','$feedback_heading','$feedback')";

		if(mysqli_query($conn, $sql_submit)){
		    $reference_no = mysqli_insert_id($conn);
			$message = "Successfully Submitted";
			$array = array("reference_no"=>$reference_no, "message"=>"$message");
            echo json_encode($array);
		} else {
		    echo "Failed to Submit!";
		}
	} else {
		echo "Connection Error!";
	}
?>