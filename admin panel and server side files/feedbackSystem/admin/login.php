<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
h2 {text-align: center;}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px;
  box-sizing: border-box;
  border-radius: 05px;
  }

body{ background-color: whitesmoke; }

button {
  background-color: #008577;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}

button:hover {
  opacity: 0.8;
}

.container {
    margin: auto;
    width: 60%;
    border: 3px solid #008577;
    padding: 10px;
    margin-top: 50px;
}

</style>
</head>
<body>

<h2>Feedback System</h2>
<h2>Admin Login</h2>

<form action="../login.php" method="post">

  <div class="container">
    <label for="username"><b>Username</b></label>
    <input id="username" type="text" placeholder="Enter Username" name="username" required>

    <label for="password"><b>Password</b></label>
    <input id="password" type="password" placeholder="Enter Password" name="password" required>
        
    <button type="submit">Login</button>
    
  </div>
</form>

</body>
</html>