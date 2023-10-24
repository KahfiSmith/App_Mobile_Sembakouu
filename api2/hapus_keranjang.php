<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");


if ($_SERVER['REQUEST_METHOD']==='POST'){


    $idkeranjang = $_POST['idkeranjang'];
  $idPembeli = $_POST['idPembeli'];
 
 $queryDelete = "DELETE FROM keranjang WHERE idkeranjang = '$idkeranjang' AND idPembeli = '$idPembeli'";
 
 $result = mysqli_query($konek, $queryDelete);
        $check = mysqli_affected_rows($konek);
        if($check > 0) {
            $data['kode'] = 1;
            $data['message'] = 'Hapus berhasil';

     	}else{
        $data['kode'] = 0;
        $data['message'] = 'Hapus Gagal';
    }
}
 
       echo json_encode($data);
     mysqli_close($konek);


?>
