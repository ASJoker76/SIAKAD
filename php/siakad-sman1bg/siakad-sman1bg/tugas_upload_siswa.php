<?php 
	
	include_once "koneksi.php";

    $nip = $_POST['nip'];
	$judul = $_POST['judul'];
	$kategori = $_POST['kategori'];

	$query = mysqli_query($con, "SELECT * FROM tugas_siswa_last WHERE nip LIKE '%".$nip."%' AND judul LIKE '%".$judul."%' AND kategori LIKE '%".$kategori."%'");		
	
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
			    "nis": "'.str_replace($char,'`',strip_tags($row['nis'])).'",
				"nama": "'.str_replace($char,'`',strip_tags($row['nama'])).'",
				"file": "'.str_replace($char,'`',strip_tags($row['file'])).'",
				"kesimpulan": "'.str_replace($char,'`',strip_tags($row['kesimpulan'])).'",
				"nilai": "'.str_replace($char,'`',strip_tags($row['nilai'])).'"			
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