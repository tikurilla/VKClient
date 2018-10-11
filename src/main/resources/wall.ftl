<!DOCTYPE html>
<html>
<head>
  <style type="text/css"> <#include "wallstyle.css"> </style>
  <title>Welcome!</title>
</head>
<header>
    <h1>VK wall messages</h1>
    <nav><a class="menu" href="/newmessage">New message</a></nav>
</header>
<body>
<ul class="zebra">
<div align="center">
    <nav class="amount">
        <a href="/wall/10">10</a>
        <a href="/wall/20">20</a>
        <a href="/wall/40">40</a>
    </nav>
</div>
</ul>
	<dl class="holiday">
		<#list posts as post>
        <dt>${post.date}<span><a href="https://vk.com/id${post.userId}">${post.authorName}</a></span></dt>
        <dd>${post.postText}</dd>
        </#list>

    </dl>
</html>
</body>