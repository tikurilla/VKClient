<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8">
    <style type="text/css"> <#include "newmessagestyle.css"> </style>
    <title>Welcome!</title>
</head>
<body>
<header>
    <h1>Сообщения стены ВК</h1>
    <nav><a href="/wall/10">Сообщения</a></nav>
</header>
<div class="container">
  <form action="/newmessage" method="POST">
  ${errors!""}
    <div class="row">
      <div class="col-100">
        <textarea id="subject" name="subject" input placeholder="Сообщение.." style="height:200px">${subject}</textarea>
      </div>
    </div>
    <div class="row">
      <input type="submit" value="Отправить">
    </div>
  </form>
</div>

</body>
</html>