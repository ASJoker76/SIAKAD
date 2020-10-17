	
<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){
  	
	include_once "koneksi.php";	
  	
        $originalImgName= $_FILES['filename']['name'];
		$tempName= $_FILES['filename']['tmp_name'];

        $judul = $_POST['judul']; 
        $deskripsi = $_POST['deskripsi']; 
		$nip = $_POST['nip']; 
		$kategori = $_POST['kategori']; 
        $folder="uploads/";
		
		
        
        
			$query = "INSERT INTO tugas_siswa (id,nip,kategori,judul,deskripsi,file) VALUE(0,'".$nip."','".$kategori."','".$judul."','".$deskripsi."','".$originalImgName."')";
			
                if(mysqli_query($con,$query)){
                move_uploaded_file($tempName,$folder.$originalImgName);    
					$query= "SELECT * FROM tugas_siswa WHERE file='$originalImgName'";
	                 $result= mysqli_query($con, $query);
	                 $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $emparray[] = $row;
                                   }
                                   echo json_encode(array( "status" => "true","message" => "Successfully file added!" , "data" => $emparray) );
                                   
	                     }else{
	                     		echo json_encode(array( "status" => "false","message" => "Failed") );
	                     }
			   
                }else{
                	echo json_encode(array( "status" => "false","message" => "Failed Save Database!") );
                }
        	
  }
?>