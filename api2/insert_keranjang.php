<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $idPembeli = $_POST['idPembeli'];
    $kdBarang = $_POST['kdBarang'];
    $Jumlah = $_POST['Jumlah'];
    $HargaJual = $_POST['HargaJual'];
    $sub_total = $_POST['sub_total'];

    $query = "INSERT INTO keranjang (idPembeli, kdBarang, Jumlah, sub_total) VALUES ('$idPembeli', '$kdBarang', '$Jumlah', '$sub_total')";
    $response = mysqli_query($konek, $query);
    $check = mysqli_affected_rows($konek);

     if($check > 0){
         $data['kode'] = 1;
         $data['message'] = 'Simpan Data Berhasil';
     }else{
         $data['kode'] = 0;
         $data['message'] = 'Gagal Menyimpan Data';
     }
     echo json_encode($data);
     mysqli_close($konek);
}

?>