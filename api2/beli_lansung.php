<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");


if ($_SERVER['REQUEST_METHOD']==='POST'){
    $idTransaksi = $_POST['idTransaksi'];
    $idkeranjang = $_POST['idkeranjang'];
    $kdBarang = $_POST['kdBarang'];
    $idPembeli = $_POST['idPembeli'];
    $Jumlah = $_POST['Jumlah'];
    $HargaJual = $_POST['HargaJual'];
    $sub_total = $_POST['sub_total'];

    $queryDelete = "DELETE FROM keranjang WHERE idkeranjang = '$idkeranjang' AND idPembeli = '$idPembeli'";
    $insertDetailTrx = "INSERT INTO detail_transaksi (idTransaksi, kdBarang, idPembeli, Jumlah, sub_total,idKeranjang) VALUES ('$idTransaksi', '$kdBarang', '$idPembeli', '$Jumlah', '$sub_total',0)";
    $result = mysqli_query($konek, $insertDetailTrx);
    $check = mysqli_affected_rows($konek);

     if($check > 0){
            $data['kode'] = 1;
            $data['message'] = 'Insert berhasil';

     }else{
        $data['kode'] = 0;
        $data['message'] = 'Insert dan Hapus Gagal';
     }
     echo json_encode($data);
     mysqli_close($konek);
}
?>