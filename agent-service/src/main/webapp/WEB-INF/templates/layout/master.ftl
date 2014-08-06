<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <title>Agent</title>
    <meta charset="utf-8"/>
<@css href="public.css,foundation.min.css" rel="stylesheet" type="text/css"/>
<@js src="jquery.min.js,jquery.form.js,common.js,jquery.validate.js"/>
    <script type="text/javascript">
        golbalRootUrl = "<@url value='/' />";
    </script>
</head>
<body>
<nav class="top-bar">
    <ul class="title-area">
        <li class="name">
            <h1><a href="<@url value='/home'/>"><span style="color:White;">Agent</span></a></h1>
        </li>
    </ul>
    <ul class="right">
        <li class="has-form"><a style="margin-left: 5px;" class="small radius button" href="<@url value='/signOut'/>">Logout</a></li>
    </ul>
</nav>

<@body/>
</body>
<script>$(document).foundation();</script>