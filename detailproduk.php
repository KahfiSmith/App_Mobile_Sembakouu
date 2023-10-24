<?php
	session_start();
	include('config.php');
?>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/stylesDetail.css">
    <link rel="stylesheet" type="text/css" href="fontawesome/css/all.min.css">
    <title>Sembakouu</title>
  </head>
  <body>

    <!-- Awal Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <img src="img/logononame50.png" alt="Sembakouu" width="50" height="50" class="">
                Sembakouu
              </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <form class="d-flex" class="ms-auto my-4 my-lg-0" action="" method="POST">
                <input class="form-control me-2" type="search" name="cari" placeholder="Cari Barang..." aria-label="Search">
                <button type="button" name="submit" class="btn btn-light"><i class="fa-solid fa-magnifying-glass"></i></button>
            </form>
            <ul class="navbar-nav ms-auto">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="index.php">Barang</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="about.php">Tentang Kami</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="login.php">Login</a>
              </li>
			  <li class="nav-item">
                <a class="nav-link" href="https://drive.google.com/file/d/1KpMTEvCF8FK1mg8R4rZRz8QfBH4zQ3Nt/view?usp=share_link" style="background-color: #cd5c5c; padding: 5px 10px;">Download APK!</a>
              </li>
            </ul>
			
          </div>
        </div>
      </nav>
      <!-- Akhir Navbar -->
      
      <!-- Breadcrumb -->
      <div class="container">
        <nav aria-label="breadcrumb" style="background-color: #fff" class="mt-3">
          <ol class="breadcrumb p-3">
            <li class="breadcrumb-item"><a href="index.php" class="text-decoration-none">Home</a></li>
            <li class="breadcrumb-item active"><a aria-current="page" value="<?php 
            $_GET['kdBarang'];
            $cek = mysqli_query($conn,"SELECT * FROM barang WHERE kdBarang='".$_GET['kdBarang']."' ");
            while($tampil = mysqli_fetch_assoc($cek)){	
              $nama = $tampil['NamaBarang'];
              $jenis_barang = $tampil['jenis_barang'];
            ?>
            "><?php echo $jenis_barang ?></a></li>
            <li class="breadcrumb-item active" aria-current="page"><?php echo $nama ?></li>
          <?php
            }
            ?>
          </ol>
        </nav>
      </div>
      <!-- Akhir Breadcrumb -->
    
      <!-- Awal Single Produk -->
      <div class="container">
        <div class="row row-produk">
          <?php 
          if(isset($_POST['detail']) && (isset($_GET['submit'])) && (isset($_GET['cari'])) && (isset($_GET['kdBarang'])) ){
            $cek = mysqli_query($conn,"SELECT * FROM barang ");
            if(mysqli_num_rows($cek) > 0){
              while($tampil = mysqli_fetch_assoc($cek)){		
              $nama = $tampil['NamaBarang'];
              $gambar = $tampil['gambar'];
              $desk = $tampil['deskripsi'];
              $HargaJual = $tampil['HargaJual'];
              $Stok = $tampil['Stok'];
              $Satuan = $tampil['Satuan'];
              ?>
          
          <?php 
            }
          }
        }else{
            $_GET['kdBarang'];
            $cek = mysqli_query($conn,"SELECT * FROM barang WHERE kdBarang='".$_GET['kdBarang']."' ");
            if(mysqli_num_rows($cek) > 0){
              while($tampil = mysqli_fetch_assoc($cek)){		
              $nama = $tampil['NamaBarang'];
              $gambar = $tampil['gambar'];
              $desk = $tampil['deskripsi'];
              $HargaJual = $tampil['HargaJual'];
              $Stok = $tampil['Stok'];
              $Satuan = $tampil['Satuan'];
              }
            ?>
            <div class="col-lg-5">
              <figure class="figure">
                <img src="<?php echo $gambar ?>" class="figure-img img-fluid" style="border-radius: 5px; width: 450px;">
                <figcaption class="figure-caption d-flex justify-content-evenly">
                  <a href="">
                    <img src="<?php echo $gambar ?>" class="figure-img img-fluid" style="border-radius: 5px; width: 70px; transform: scale(1.2); transition: 0.2s;">
                  </a>
                </figcaption>
              </figure>
            </div>

          <div class="col-lg-7">
            <h3><?php echo $nama?></h3>
              <div class="garis-nama"></div>
                <h4 class="text-muted mb-3"><?php echo 'Rp. ' . number_format($HargaJual,2,',','.'); ?> </h4>

                <h5>Deskripsi Barang : </h5>
                <a><?php echo $desk?></a>
                <!-- <button type="button" class="btn btn-dark btn-sm"><i class="fas fa-minus"></i></button>
                <span class="mx-2"></span>
                <button type="button" class="btn btn-warning btn-sm"><i class="fas fa-plus text-white"></i></button> 
                
                <span class="mx-2">Tersisa <?php echo $Stok ; echo $Satuan?></span>-->
          </div>
        <?php
        }else{
          echo 'Barang Tidak Ditemukan!';
        }
      }
          ?>
        </div>
      </div>
      <!-- Akhir Single Produk -->
	  <!-- Footer -->
	<footer class="bg-light p-5 mt-5">
		<div class="container">
			<div class="row">
				<div class="col-md-6 textmd-start text-center pt-2 pb-2">
					<a href="#" class="text-decoration-none">
						<img src="img/logononame50.png" style="width: 40px;">
					</a>
					<span class="ps-1">Copyright @2020 | Created with <i class="fa-solid fa-heart text-danger" ></i> by <a href=" " class="text-decoration-none text-dark fw-bold">Sembakouu</a> </span>
				</div>

				<div class="col-md-6 textmd-end text-center pt-2 pb-2">
					<a href="#" class="text-decoration-none">
						<img src="assets/sosialMedia/facebook.png" class="ms-2" style="width: 32px;">
					</a>
					<a href="#" class="text-decoration-none">
						<img src="assets/sosialMedia/instagram.png" class="ms-2" style="width: 30px;">
					</a>
					<a href="#" class="text-decoration-none">
						<img src="assets/sosialMedia/twitter.png" class="ms-2" style="width: 30px;">
					</a>
					<a href="#" class="text-decoration-none">
						<img src="assets/sosialMedia/linkedin.png" class="ms-2" style="width: 30px;">
					</a>
				</div>
			</div>
		</div>
	</footer>
	  <!-- Akhir Footer -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script> 
</body>
</html>