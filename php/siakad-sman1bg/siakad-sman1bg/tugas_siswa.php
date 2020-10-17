<?php 
	
	include_once "koneksi.php";

    $nip = $_POST['nip'];

	$query = mysqli_query($con, "SELECT * FROM tugas_siswa WHERE nip LIKE '%".$nip."%'");		
	
	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
			    "id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
			    "nip": "'.str_replace($char,'`',strip_tags($row['nip'])).'",
			    "kategori": "'.str_replace($char,'`',strip_tags($row['kategori'])).'",
			    "judul": "'.str_replace($char,'`',strip_tags($row['judul'])).'",
			    "deskripsi": "'.str_replace($char,'`',strip_tags($row['deskripsi'])).'",
				"file": "'.str_replace($char,'`',strip_tags($row['file'])).'"			
			},';
		}

		$json = substr($json,0,strlen($json)-1);

		$json .= ']}';

	} else {
		$json = '{"value":0, "message": "Data tidak ditemukan."}';
	}

	echo $json;

	mysqli_close($con);
?>