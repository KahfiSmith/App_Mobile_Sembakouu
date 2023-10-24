<?php
require("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='GET') {
    $idPembeli = $_GET['idPembeli'];

    $perintah = "SELECT keranjang.*, barang.*, SUM(`keranjang`.`Jumlah`)  AS Jumlah FROM keranjang JOIN barang ON keranjang.kdBarang = barang.kdBarang WHERE idPembeli = '$idPembeli' GROUP BY barang.`kdBarang`;";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek= mysqli_affected_rows($konek);

if($cek > 0) {
    $response["kode"] = 1;
    $response["message"] = "Data Tersedia";
    $response["data"] = array();

    while($ambil= mysqli_fetch_object($eksekusi)) {
        $F["idkeranjang"] = $ambil->idkeranjang;
        $F["kdBarang"] = $ambil->kdBarang;
        $F["idPembeli"] = $ambil->idPembeli;
        $F["Jumlah"] = $ambil->Jumlah;
        $F["NamaBarang"] = $ambil->NamaBarang;
        $F["gambar"] = $ambil->gambar;
        $F["HargaJual"] = $ambil->HargaJual;
        $F["Stok"] = $ambil->Stok;
        $F["Satuan"] = $ambil->Satuan;
        $F["idPembeli"] = $ambil->idPembeli;

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