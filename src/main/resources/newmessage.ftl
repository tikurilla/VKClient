<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8">
    <style type="text/css"> <#include "newmessagestyle.css"> </style>
    <title>Welcome!</title>
</head>
<body>
<header>
    <h1>VK wall messages</h1>
    <nav><a href="/wall/10?token=${authToken}&user=${authUser}">Wall messages</a></nav>
</header>
<div class="container">
  <form action="/newmessage?token=${authToken}&user=${authUser}" method="POST">
  ${errors!""}
    <div class="row">
      <div class="col-100">
        <textarea id="subject" name="subject" input placeholder="New message here.." style="height:200px">${subject}</textarea>
      </div>
    </div>
    <div class="row">
      <input type="submit" value="Send">
    </div>
  </form>
</div>

</body>
</html>