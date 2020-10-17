<?php
	
	 include_once "koneksi.php";

	 class usr{}
	
	$nip = $_POST['nip'];
	$matpel = $_POST['matpel'];
	$pertemuan = $_POST['pertemuan'];
	 
	
	 if ((empty($nip)) || (empty($matpel))|| (empty($pertemuan))) { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom tidak boleh kosong"; 
	 	die(json_encode($response));
	 }
	
	 $query = mysqli_query($con, "SELECT * FROM modul_guru WHERE nip='$nip' AND matpel='$matpel' AND pertemuan='$pertemuan'");
	
	 $row = mysqli_fetch_array($query);
	
	 if (!empty($row)){
	 	$response = new usr();
	 	$response->success = 1;
	 	$response->message = "Display Data";
	 	$response->gambar = $row['gambar'];
        $response->video = $row['video'];
	 	$response->word = $row['word'];
	 	$response->pdf = $row['pdf'];
	 	die(json_encode($response));
		
	 } else { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Data Kosong";
	 	die(json_encode($response));
	 }
	
	 mysqli_close($con);

?>