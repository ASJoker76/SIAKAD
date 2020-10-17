<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $username = $_POST["username"];
	 $password = $_POST["password"];
	 $confirm_password = $_POST["confirm_password"];
     $level = $_POST["level"];

     if ((empty($username))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom username tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($password))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom password tidak boleh kosong";
	 	die(json_encode($response));
	 } 
     else if ((empty($level))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom level tidak boleh kosong";
	 	die(json_encode($response));
	 }else if ((empty($confirm_password)) || $password != $confirm_password) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Konfirmasi password tidak sama";
	 	die(json_encode($response));
	 } else {
		 if (!empty($username) && $password == $confirm_password){
		 	$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM user WHERE username='".$username."'"));
		 	    if($num_rows == 0){
    		 		$query = mysqli_query($con, "INSERT INTO user (id,username, password,level) VALUES(0,'".$username."','".$password."','".$level."')");
    
    		 		if ($query){
    		 			$response = new usr();
    		 			$response->success = 1;
    		 			$response->message = "Register berhasil, silahkan login.";
    		 			die(json_encode($response));
    
    		 		} else {
    		 			$response = new usr();
    		 			$response->success = 0;
    		 			$response->message = "Register gagal";
    		 			die(json_encode($response));
    		 		}
		 	    }
                 else {
                    $response = new usr();
                    $response->success = 0;
                    $response->message = "Username sudah ada";                    
                    die(json_encode($response));
		 	    } 
		    }                
	}

	mysqli_close($con);

?>	