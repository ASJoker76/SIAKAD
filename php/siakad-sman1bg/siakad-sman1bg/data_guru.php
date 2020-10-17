<?php 
	
	include_once "koneksi.php";

    $level = "Guru"; 
    $verifikasi = "Aktif"; 

	$query = mysqli_query($con, "SELECT * FROM user where verifikasi='".$verifikasi."' AND level='".$level."' ORDER BY id ASC ");	
	
	$json = array();	
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
?>