<?php
require("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST') {
    $idPembeli = $_POST['idPembeli'];

    $perintah = "SELECT * FROM `detail_transaksi` JOIN `barang` 
    ON `detail_transaksi`.`kdBarang` = `barang`.`kdBarang` JOIN `transaksi` ON `transaksi`.`idTransaksi` = `detail_transaksi`.`idtransaksi` 
    WHERE `transaksi`.`idPembeli` = '$idPembeli' AND `transaksi`.`status` = 'selesai' GROUP BY `detail_transaksi`.`idTransaksi`;";

    $eksekusi = mysqli_query($konek, $perintah);
    $cek= mysqli_affected_rows($konek);

if($cek > 0) {
    $response["kode"] = 1;
    $response["message"] = "Data Tersedia";
    $response["data"] = array();

    while($ambil= mysqli_fetch_object($eksekusi)) {
        $F["idTransaksi"] = $ambil->idTransaksi;
        $F["NamaBarang"] = $ambil->NamaBarang;
        $F["Jumlah"] = $ambil->jmlh;
        $F["gambar"] = $ambil->gambar;
        $F["HargaJual"] = $ambil->HargaJual;
        $F["sub_total"] = $ambil->sub_total;
        $F["tanggal"] = $ambil->tanggal;
        

        array_push($response["data"], $F);
    }
}
else {
    $response["kode"] = 0;
    $response["message"] = "Data Tidak Tersedia";
    $response["data"] = null;

}
}
echo json_encode($response);
mysqli_close($konek);
?>  