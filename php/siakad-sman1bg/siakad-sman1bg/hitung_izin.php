<?php
	
	 include_once "koneksi.php";

	 class usr{}
	
	 $nis = $_POST["nis"];
	 $hadir = "Izin";
	
	 if ((empty($nis))) { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom tidak boleh kosong"; 
	 	die(json_encode($response));
	 }

	 $query = mysqli_query($con, "SELECT ketabsen,COUNT(ketabsen='$hadir') AS izin FROM absen_siswa WHERE nis='$nis' AND ketabsen='$hadir'");
	
	 $result = mysqli_fetch_array($query);
	
	 if (!empty($result)){
	 	$response = new usr();
	 	$response->success = 1;
	 	$response->izin = $result['izin'];
	 	die(json_encode($response));
		
	 } else { 
	 	$response = new usr();
	 	$response->success = 0;
	 	die(json_encode($response));
	 }
	
	 mysqli_close($con);

?>