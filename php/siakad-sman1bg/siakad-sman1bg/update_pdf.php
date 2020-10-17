<?php

$target_dir = "uploads/";
$target_file = $target_dir .basename($_FILES["file"]["name"]);

$path = $_POST[$target_file];
$name = basename($_FILES["file"]["name"]);
$names = $_POST[$name];
$response = array();
$error = "";

if(isset($_FILES["file"])){
    	    if(move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)){
    		$success = true;
    		$message = "Successfully";

    	} else {
    		$success = false;
    		$message = "Error";
    	} 
}
else {
	$success = false;
	$message = "Required Field Missing";
}

$response["Successfully"] = $success;
$response["Message"] = $message;
echo json_encode($response);
 
?>