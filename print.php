<?php
	session_start();
	$timeout = 1; // setting timeout dalam menit
	$logout = "login.php"; // redirect halaman logout

	$timeout = $timeout * 360; // menit ke detik
	if(isset($_SESSION['start_session'])){
		$elapsed_time = time()-$_SESSION['start_session'];
		if($elapsed_time >= $timeout){
			session_destroy();
			echo "<script type='text/javascript'>alert('Sesi telah berakhir');window.location='$logout'</script>";
		}
	}

	$_SESSION['start_session']=time();

	include('config.php');
	if($_SESSION['status_login'] != true){
		echo '<script>window.location="login.php"</script>';
	}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>

<body>
    <h2 style="text-align: center;">Toko Lestari</h2>
    <p style="text-align: center;">Jalan Letjen S Parman 1 Nganjuk</p>

    <?php
    include "config.php";
    $id = $_GET['id'];
    $sql = "SELECT * FROM penjualan INNER JOIN penjual ON penjualan.NamaAdmin=penjual.NamaAdmin WHERE penjualan.kdPenjualan = '$id'";
    $query = mysqli_query($conn, $sql);
    while ($data=mysqli_fetch_array($query)) {
        $waktu = $data['Waktu'];
        ?>
            <p style="text-align: center;">Nama Kasir : <?php echo $_SESSION['user_global']->NamaAdmin ?></p>
            <p style="text-align: center;">Tanggal : <?php echo date('d F Y'); ?></p>
    <?php } ?>

    <table align="center" width="50%"  style="text-align: center;" >
        <thead>
            <tr>
                <td>No</td>
                <td>Barang</td>
                <td>Harga</td>
                <td>Jumlah</td>
                <td>Total</td>
            </tr>
        </thead>
        <tbody>
        <?php
        $id = $_GET['id'];
        $sql = "SELECT * FROM penjualan INNER JOIN penjual ON penjualan.NamaAdmin=penjual.NamaAdmin JOIN detail_penjualan ON penjualan.kdPenjualan=detail_penjualan.kdPenjualan JOIN barang ON detail_penjualan.kdBarang = barang.kdBarang WHERE penjualan.kdPenjualan = '$id'";
        $query = mysqli_query($conn, $sql);
        $no = 1;
        $Jumlah = 0;
        while ($data=mysqli_fetch_array($query)) { ?>
            
            <tr>
                <td><?= $no; ?></td>
                <td><?= $data['NamaBarang'] ?></td>
                <td><?= number_format($data['HargaJual']) ?></td>
                <td><?= $data['Jumlah'] ?></td>
                <td><?= number_format($data['TotalHarga']) ?></td>
            </tr>
            
            <?php $Jumlah += $data['TotalHarga'];  $no++;  } ?>
            <tr>
                <td colspan="4" >Total Bayar</td>
                <td style="padding: 5px 25px 5px 25px;"> <?= number_format($Jumlah); ?></td>
            </tr>
            <?php
            $id = $_GET['id'];
            $cek2 = mysqli_query($conn,"SELECT * FROM penjualan WHERE kdPenjualan = '$id' ");
            while ($tampil = mysqli_fetch_array($cek2)){
            $bayar = $tampil['bayar']; 
            $kembalian = $tampil['bayar'] - $tampil['TotalBayar']; }?> 
            <tr>
                <td colspan="4" >Bayar</td>
                <td style="padding: 2px 25px 2px 25px;"> <?= number_format($bayar); ?></td>
            </tr>
            <?php   $no++;   ?>
            <tr>
                <td colspan="4" >Kembalian</td>
                <td style="padding: 2px 25px 2px 25px;"> <?= number_format($kembalian); ?></td>
            </tr>
        </tbody>
    </table>
    <script>
        window.print();
    </script>
</body>

</html>