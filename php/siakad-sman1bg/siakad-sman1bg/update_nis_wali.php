<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $username = $_POST['username'];
	 $nis_anak = $_POST['nis_anak'];	 
	 

     if ((empty($username))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom username tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($nis_anak))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nis tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else {
		$query = mysqli_query($con, "UPDATE user SET nis_anak='".$nis_anak."' where username='".$username."'");
    
     		if ($query){
     		    $response = new usr();
    			$response->success = 1;
	 			$response->message = "Nis diperbaharui";
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