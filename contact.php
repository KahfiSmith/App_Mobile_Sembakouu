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
                <a class="nav-link" href="#">Contact</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="login.php">Login</a>
              </li>
			  <li class="nav-item">
                <a class="nav-link" href="#" style="background-color: #cd5c5c; padding: 5px 10px;">Download APK!</a>
              </li>
            </ul>
			
          </div>
        </div>
      </nav>
      <!-- Akhir Navbar -->
      
      
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