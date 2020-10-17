<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $username = $_POST['username'];
	 $verifikasi = $_POST['verifikasi'];	 

     if ((empty($username))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom username tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($verifikasi))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom verifikasi tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else {
		$query = mysqli_query($con, "UPDATE user SET verifikasi='".$verifikasi."' where username='".$username."'");
    
     		if ($query){
     		    $response = new usr();
    			$response->success = 1;
	 			$response->message = "Verifikasi berhasil";
	 			die(json_encode($response));
        	} else {
    		 	$response = new usr();
    			$response->success = 0;
	 			$response->message = "Verifikasi Gagal";
	 			die(json_encode($response));
        	}
	}

	mysqli_close($con);

?>	