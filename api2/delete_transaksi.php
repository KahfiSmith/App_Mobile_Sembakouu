<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $idTransaksi = $_POST['idTransaksi'];
    $idPembeli = $_POST['idPembeli'];

    $query = "DELETE FROM transaksi WHERE idTransaksi = '$idTransaksi' AND idPembeli = '$idPembeli'";
     $response = mysqli_query($konek, $query);
     $check = mysqli_affected_rows($konek);

     if($check > 0){
         $data['kode'] = 1;
         $data['message'] = 'idTransaksi berhasil dihapus';
     }else{
         $data['kode'] = 0;
         $data['message'] = 'idTransaksi gagal dihapus';
     }
     echo json_encode($data);
     mysqli_close($konek);
}
?>