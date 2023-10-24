<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $kdBarang = $_POST['kdBarang'];

    $query = "DELETE FROM keranjang WHERE kdBarang = '$kdBarang'";
     $response = mysqli_query($konek, $query);
     $check = mysqli_affected_rows($konek);

     if($check > 0){
         $data['kode'] = 1;
         $data['message'] = 'Barang berhasil dihapus';
     }else{
         $data['kode'] = 0;
         $data['message'] = 'Gagal menghapus barang';
     }
     echo json_encode($data);
     mysqli_close($konek);
}
?>