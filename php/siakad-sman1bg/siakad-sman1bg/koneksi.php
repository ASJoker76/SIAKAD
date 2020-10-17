<?php
	
	$server		= "localhost"; 
	$user		= "u323268911_siakad_sma1bg"; 
	$password	= "iid2020@"; 
	$database	= "u323268911_siakad_sma1bg"; 
	
	$con = mysqli_connect($server, $user, $password, $database);
	if (mysqli_connect_errno()) {
	 	echo "Gagal terhubung MySQL: " . mysqli_connect_error();
	}

?>