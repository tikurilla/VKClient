<!DOCTYPE html>
<html>
<head>
  <style type="text/css"> <#include "wallstyle.css"> </style>
  <title>Welcome!</title>
</head>
<header>
    <h1>Сообщения стены ВК</h1>
    <nav><a href="/newmessage">Новое сообщение</a></nav>
</header>
<body>
<ul class="zebra">

</ul>
	<dl class="holiday">
		<#list posts as post>
        <dt>${post.date}<span><a href="https://vk.com/id${post.userId}">${post.authorName}</a></span></dt>
        <dd>${post.postText}</dd>
        </#list>

    </dl>
</html>
</body>