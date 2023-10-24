<?php
require("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST') {
    $idPembeli = $_POST['idPembeli'];

    $perintah = "SELECT * FROM `detail_transaksi` JOIN `barang` 
    ON `detail_transaksi`.`kdBarang` = `barang`.`kdBarang` JOIN `transaksi` ON `transaksi`.`idTransaksi` = `detail_transaksi`.`idtransaksi` 
    WHERE `transaksi`.`idPembeli` = '$idPembeli' AND `transaksi`.`status` = 'pending' GROUP BY `detail_transaksi`.`idTransaksi`;";

    $eksekusi = mysqli_query($konek, $perintah);
    $cek= mysqli_affected_rows($konek);

if($cek > 0) {
    $response["kode"] = 1;
    $response["message"] = "Data Tersedia";
    $response["data"] = array();

    while($ambil= mysqli_fetch_object($eksekusi)) {
        $F["idTransaksi"] = $ambil->idTransaksi;
        $F["list_barang"] = array();
        $idTransaksi = $ambil -> idTransaksi;
    $get_barang = "SELECT * , SUM(`detail_transaksi`.`sub_total`) AS `harga_total` , SUM(`detail_transaksi`.`jumlah`) AS `jumlah_total`  FROM `detail_transaksi` JOIN `barang` 
    ON `detail_transaksi`.`kdBarang` = `barang`.`kdBarang` JOIN `transaksi` ON `transaksi`.`idTransaksi` = `detail_transaksi`.`idtransaksi` 
    WHERE `transaksi`.`idPembeli` = '$idPembeli' AND `transaksi`.`status` = 'pending' AND `transaksi`.`idTransaksi` = '$idTransaksi' GROUP BY `detail_transaksi`.`kdBarang`;";

    $eksekusi_get_barang = mysqli_query($konek, $get_barang);
    $cek_barang = mysqli_affected_rows($konek);        
    if($cek_barang > 0){
        while($detail = mysqli_fetch_object($eksekusi_get_barang)){
        $DATABARANG["NamaBarang"] = $detail->NamaBarang;
        $DATABARANG["Jumlah"] = $detail->jumlah_total;
        $DATABARANG["gambar"] = $detail->gambar;
        $DATABARANG["HargaJual"] = $detail->HargaJual;
        $DATABARANG["sub_total"] = $detail->harga_total;
        $DATABARANG["tanggal"] = $detail->tanggal;
        array_push($F['list_barang'],$DATABARANG);
        }
    }

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