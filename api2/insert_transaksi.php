<?php
require("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='POST') {
    $total = $_POST['total'];
    $idPembeli = $_POST['idPembeli'];
    $status = $_POST['status'];
    $konek->autocommit(false);
    try {

    $queryKode = $konek->query("SELECT idTransaksi from transaksi ORDER BY tanggal DESC, idTransaksi DESC LIMIT 1");
                $maxKode = mysqli_fetch_row($queryKode);
                if ($maxKode == null) {
                    $kodeTransaksi = "T" . date("dmY") . "-0001";
               
                } else {
                    $kodeTransaksi = $maxKode[0];
                    $checkTanggal = substr($kodeTransaksi, 1, 8);
                    $checkNomor = (int) substr($kodeTransaksi, 10, 4);
                    $tanggalSekarang = date("dmY");
                    if ($checkTanggal == $tanggalSekarang) {
                        $checkNomor++;
                        $kodeAkhir = sprintf("%04s", $checkNomor);
                        $kodeTransaksi = "T" . $checkTanggal . "-" . $kodeAkhir;
                    } else {
                        $kodeTransaksi = "T" . date("dmY") . "-0001";
                    }
                }
                $parsingTanggal = date("Ymd");
                $konek->query("INSERT INTO transaksi (idTransaksi, tanggal, total, idPembeli, status) VALUES('$kodeTransaksi', '$parsingTanggal', '$total', '$idPembeli', '$status')");
            
                     $response['idTransaksi'] = $kodeTransaksi;
                $response['pesan'] = "Data Telah Masuk";
                $response['kode'] = 1;
                $konek->commit();
}catch (Exception $e) {
    $response['pesan'] = $e->getMessage();
    $response['kode'] = 0;
    $response['jumlah'] = null;
    $konek->rollback();
}
echo json_encode($response);
mysqli_close($konek);
}
?>