<?php
  session_start();
  require "../conn.php";
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Feedback System : Admin Panel - Users Feedback</title>
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
    $sql = "select * from user_feedback";
    $result = mysqli_query($conn,$sql);
  ?> 
  
  <style>

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

    body{ background-color: whitesmoke; }

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
                <li><a href="http://192.168.0.106/feedbackSystem/admin/usersdetails.php">User Information</a></li>
                <li class="active"><a href="#">User Feddback</a></li>
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
          <th>Reference Number</th>
          <th>Username</th>
          <th>Name</th>
          <th>PNR Number</th>
          <th>Time</th>
          <th>Title</th>
          <th>Feedback</th>
          <th>Status</th>
          <th>Action</th>
        </tr>
        <?php
          while($row = mysqli_fetch_assoc($result)){
            echo "<tr>";
            $id = $row['id'];
            echo "<td>".$id."</td>";
            echo "<td>".$row['username']."</td>";
            echo "<td>".$row['name']."</td>";
            echo "<td>".$row['pnr_number']."</td>";
            echo "<td>".$row['time']."</td>";
            echo "<td>".$row['feedback_heading']."</td>";
            echo "<td>".$row['feedback']."</td>";
            echo "<td>".$row['status']."</td>";
            echo '<form action="update.php" method="post">';
              echo "<td>";
              echo '<select name="status">';
              echo '<option value="unseen"';
              if($row['status'] == "unseen"){
                echo 'selected';
              }
              echo '>Unseen</option>';
              echo '<option value="received"';
              if($row['status'] == "received"){
                echo 'selected';
              }
              echo '>Received</option>';
              echo '<option value="submitted"';
              if($row['status'] == "submitted"){
                echo 'selected';
              }
              echo '>Submitted</option>';
              ?>
              <input type='hidden' name='id' id='id' value='<?php echo "$id";?>'/>
              <?php
              echo '</select>';
              echo '<button type="submit" class="btn btn-default">Submit</button>';
            echo '</form>';
            echo "</td>";
            echo "</tr>";
          }
        ?>
      </table>

          <!-- <script>
            var table = document.getElementById('table'),rIndex;
            for(var i = 1 ; i < table.rows.length; i++){
              table.rows[i].onClick = function(){
                rIndex = this.rowIndex;
                document.getElementById("id").value = this.cell[0].innerHTML;
              }
            }
          </script> -->

    </div>
  </div>
</body>
</html>