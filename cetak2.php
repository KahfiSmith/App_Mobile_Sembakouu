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
<html>
<head>
	<title>CETAK PRINT DATA</title>
</head>
<body>
  <style type="text/css">
  body{
    font-family: sans-serif;
  }
  table{
    margin: 20px auto;
    border-collapse: collapse;
  }
  table th,
  table td{
    border: 1px solid #3c3c3c;
    padding: 3px 8px;
  }
  a{
    background: blue;
    color: #fff;
    padding: 8px 10px;
    text-decoration: none;
    border-radius: 2px;
  }

    .tengah{
        text-align: center;
    }
 </style>
 
        <div class="pagetitle">
		    <h2>DATA LAPORAN PENJUALAN</h2>
        </div>
 
	<?php 
	include 'config.php';
	?>
 
      <table align="center">
				<tr>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>No</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Transaksi</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Jumlah</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Total Harga</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Waktu</b></td>
				</tr>

                <?php 
			        $no = 1;
			        if(isset($_GET['bln'])){
                $cek = mysqli_query($conn, "SELECT penjualan.kdPenjualan, detail_penjualan.kdBarang, 
                detail_penjualan.Jumlah, detail_penjualan.TotalHarga, penjualan.Waktu
                FROM detail_penjualan
                JOIN penjualan
                ON detail_penjualan.kdPenjualan = penjualan.kdPenjualan
                WHERE penjualan.Waktu LIKE '%".$_GET['bln']."%' 
                ORDER BY penjualan.Waktu DESC"); ?>
            <?php
			        }else{
                $cek = mysqli_query($conn, "SELECT penjualan.kdPenjualan, detail_penjualan.kdBarang, 
                detail_penjualan.Jumlah, detail_penjualan.TotalHarga, penjualan.Waktu
                FROM detail_penjualan
                JOIN penjualan
                ON detail_penjualan.kdPenjualan = penjualan.kdPenjualan
                ORDER BY penjualan.Waktu DESC"); 
			        }
			        while ($tampil = mysqli_fetch_array($cek)){
                ?>
                  <tr>
                    <td align='center'><?php echo $no++ ?></td>
                    <td align='center'><?php echo $tampil['kdPenjualan']; ?></td>
                    <td align='center'><?php echo $tampil['kdBarang']; ?></td>
                    <td align='center'><?php echo $tampil['Jumlah']; ?></td>
                    <td align='center'><?php echo 'Rp. ' . number_format($tampil['TotalHarga'],2,',','.');?></td>
                    <td align='center'><?php echo $tampil['Waktu']; ?></td>
                  </tr>

                <?php
                }
                ?>
            </tbody>
    </table>
    <?php
    if(isset($_GET['bln'])){
      $cek3 = "SELECT penjualan.Waktu,SUM(detail_penjualan.TotalHarga) AS total
      FROM penjualan 
      JOIN detail_penjualan 
      ON penjualan.kdPenjualan = detail_penjualan.kdPenjualan
      WHERE penjualan.Waktu LIKE '%".$_GET['bln']."%' ";

      $result1 = mysqli_query($conn, $cek3);
      if ($result1->num_rows > 0) {
      $tampil3 = mysqli_fetch_array($result1);
      $total = $tampil3['total'];
      }else $total = 0;
    }else{
      $cek3 = "SELECT penjualan.Waktu,SUM(detail_penjualan.TotalHarga) AS total
      FROM penjualan 
      JOIN detail_penjualan 
      ON penjualan.kdPenjualan = detail_penjualan.kdPenjualan";

      $result1 = mysqli_query($conn, $cek3);
      if ($result1->num_rows > 0) {
      $tampil3 = mysqli_fetch_array($result1);
      $total = $tampil3['total'];
      }else $total = 0;
    }
    ?>
                  <tr>
                    <b>Total Pendapatan : </b><td align='center'><?php echo 'Rp. ' . number_format($total,2,',','.'); ?></td>
                  </tr>
	<script>
		window.print();
	</script>
 
</body>
</html>