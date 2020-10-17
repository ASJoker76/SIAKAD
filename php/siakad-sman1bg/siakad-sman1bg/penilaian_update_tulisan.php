<?php 
	
	include_once "koneksi.php";

	$nis = $_POST['nis'];
	$kategori = "Tulisan";
	$matpel = $_POST['matpel'];
	
	$query = mysqli_query($con, "SELECT * FROM tugas_siswa_last WHERE nis = '".$nis."' AND kategori = '".$kategori."' AND matpel = '".$matpel."'");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
			    "nilai": "'.str_replace($char,'`',strip_tags($row['nilai'])).'"
			},';
		}

		$json = substr($json,0,strlen($json)-1);

		$json .= ']}';

	} else {
		
	}

	echo $json;

	mysqli_close($con);
?>