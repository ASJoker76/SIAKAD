<?php
	
	 include_once "koneksi.php";

	 class usr{}
	
	 $nis = $_POST["nis"];
	 $hadir = "Hadir";
	
	 if ((empty($nis))) { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom tidak boleh kosong"; 
	 	die(json_encode($response));
	 }

	 $query = mysqli_query($con, "SELECT ketabsen,COUNT(ketabsen='$hadir') AS hadir FROM absen_siswa WHERE nis='$nis' AND ketabsen='$hadir'");
	
	 $result = mysqli_fetch_array($query);
	
	 if (!empty($result)){
	 	$response = new usr();
	 	$response->success = 1;
	 	$response->hadir = $result['hadir'];
	 	die(json_encode($response));
		
	 } else { 
	 	$response = new usr();
	 	$response->success = 0;
	 	die(json_encode($response));
	 }
	
	 mysqli_close($con);

?>