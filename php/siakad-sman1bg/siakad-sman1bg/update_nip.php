<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $username = $_POST['username'];
	 $nama = $_POST['nama'];
	 $nip = $_POST['nip'];	 
	 $matpel = $_POST['matpel'];	 

     if ((empty($username))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom username tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($nama))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nama tidak boleh kosong";
		die(json_encode($response));
	}
	 else if ((empty($nip))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nip tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($matpel))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom matpel tidak boleh kosong";
		die(json_encode($response));
	}	
	 else {
		$query = mysqli_query($con, "UPDATE user SET nama='".$nama."',nip='".$nip."',matpel='".$matpel."' where username='".$username."'");
    
     		if ($query){
     		    $response = new usr();
    			$response->success = 1;
	 			$response->message = "Nip diperbaharui";
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