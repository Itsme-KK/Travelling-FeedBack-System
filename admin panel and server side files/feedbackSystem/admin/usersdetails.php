<?php
  session_start();
  require "../conn.php";
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <?php

    if(!$_SESSION['username']){
      header("location: login.php");
      die;
    }

    $username = $_SESSION['username'];
    $sql = "select * from users";
    $result = mysqli_query($conn,$sql);
  ?> 

  <style>

    body{ background-color: whitesmoke; }

    table {
      border-collapse: collapse;
      border-spacing: 0;
      width: 100%;
      border: 1px solid #ddd;
    }

    th, td {
      text-align: left;
      padding: 8px;
    }

    tr:nth-child(even){background-color: #f2f2f2}

  </style>
</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
            <a class="navbar-brand" href="#">Feedback System</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">User Information</a></li>
                <li><a href="http://192.168.0.106/feedbackSystem/admin/feedbacks.php">User Feddback</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
            </div>
        </div>
    </nav>
    
  <hr>
  <div class="container">
    <div style="overflow-x:auto;">
      <table>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email ID</th>
          <th>Username</th>
          <th>Password</th>
        </tr>
        <?php
          while($row = mysqli_fetch_assoc($result)){
            echo "<tr>";
            echo "<td>".$row['id']."</td>";
            echo "<td>".$row['name']."</td>";
            echo "<td>".$row['email']."</td>";
            echo "<td>".$row['username']."</td>";
            echo "<td>".$row['password']."</td>"; 
            echo "</tr>";
          }
        ?>
      </table>
    </div>
  </div>

</body>
</html>
